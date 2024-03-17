
package com.order.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.limitsale.model.LimitSaleService;
import com.limitsale.model.LimitSaleVO;
import com.order.model.OrderService;
import com.order.model.OrderVO;
import com.quo.model.QuoService;
import com.quo.model.QuoVO;
import com.reqorder.model.ReqOrderService;
import com.reqorder.model.ReqOrderVO;
import com.user.model.UserService;
import com.user.model.UserVO;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	OrderService orderSvc;

	@Autowired
	UserService userSvc;

	@Autowired
	LimitSaleService limitSaleSvc;

	@Autowired
	ReqOrderService reqOrderSvc;

	@Autowired
	QuoService quoOrderSvc;

	@GetMapping("addOrder")
	public String addOrder(ModelMap model) {
		OrderVO orderVO = new OrderVO();
		model.addAttribute("orderVO", orderVO);
		return "back-end/order/addOrder";
	}

	// 訂單交易狀態表 成功
	@GetMapping("/transaction_stat")
	public String transaction_stat(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO) session.getAttribute("loggingInUser");

		if (userVO == null) {
			return "redirect:/login"; // 如果使用者未登入，將其重定向到登入頁面
		}

		List<OrderVO> list = orderSvc.getOneStatOrder(userVO);
		model.addAttribute("orderListData", list);
		return "front-end/order/transaction_stat"; // view
	}

	/*
	 * This method will be called on addEmp.html form submission, handling POST
	 * request It also validates the order input
	 */
	@PostMapping("insert")
	public String insert(@Valid OrderVO orderVO, BindingResult result, ModelMap model) throws IOException {

		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
		result = removeFieldError(orderVO, result, "ordNum");

		if (result.hasErrors()) {
			return "back-end/order/addOrder";
		}
		/*************************** 2.開始新增資料 *****************************************/
		orderSvc.addOrder(orderVO);
		/*************************** 3.新增完成,準備轉交(Send the Success view) **************/
		List<OrderVO> list = orderSvc.getAll();
		model.addAttribute("orderListData", list);
		model.addAttribute("success", "- (新增成功)");
		return "redirect:/order/listAllOrder"; // 新增成功後重導至IndexController_inSpringBoot.java的第50行@GetMapping("/order/listAllOrder")
	}

	/*
	 * This method will be called on listAllEmp.html form submission, handling POST
	 * request
	 */
	@PostMapping("getOne_For_Update")
	public String getOne_For_Update(@RequestParam("ordNum") String ordNum, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始查詢資料 *****************************************/
		// EmpService empSvc = new EmpService();
		OrderVO orderVO = orderSvc.getOneOrder(Integer.valueOf(ordNum));

		/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
		model.addAttribute("orderVO", orderVO);
		return "back-end/order/update_order_input"; // 查詢完成後轉交update_order_input.html
	}

	/*
	 * This method will be called on update_order_input.html form submission,
	 * handling POST request It also validates the order input
	 */
	@PostMapping("update")
	public String update(@Valid OrderVO orderVO, BindingResult result, ModelMap model) throws IOException {

		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
		result = removeFieldError(orderVO, result, "ordNum");

		if (result.hasErrors()) {
			return "back-end/order/update_order_input";
		}
		/*************************** 2.開始修改資料 *****************************************/
		orderSvc.updateOrder(orderVO);

		/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
		model.addAttribute("success", "- (修改成功)");
		orderVO = orderSvc.getOneOrder(Integer.valueOf(orderVO.getOrdNum()));
		model.addAttribute("orderVO", orderVO);
		return "back-end/order/listOneOrder"; // 修改成功後轉交listOneorder.html
	}

	// 送出價格 轉去付款流程
	@GetMapping("sentOrder")
	public String sentOrder(ModelMap model) {
		OrderVO orderVO = new OrderVO();
		model.addAttribute("orderVO", orderVO);
		return "front-end/order/transaction";
	}

	// 直接購買
//	@PostMapping("straightOrder")
//	public String straightOrder(@Valid OrderVO orderVO, BindingResult result, ModelMap model) throws IOException {
//
//		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
//		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
//		result = removeFieldError(orderVO, result,"ordNum");
//
//		
//		if (result.hasErrors()) {
//			return "";
//		}
//		/*************************** 2.開始新增資料 *****************************************/
//		orderSvc.addOrder(orderVO);
//		/*************************** 3.新增完成,準備轉交(Send the Success view) **************/
//		List<OrderVO> list = orderSvc.getAll();
//		model.addAttribute("orderListData", list);
//		model.addAttribute("success", "- (新增成功)");
//		return "redirect:/order/listAllOrder"; // 新增成功後重導至IndexController_inSpringBoot.java的第50行@GetMapping("/order/listAllOrder")
//	}

	// 訂單完成
	@PostMapping("complete")
	public String complete(@Valid OrderVO orderVO, @RequestParam("ordNum") String ordNum, ModelMap model)
			throws IOException {
		OrderVO completeOrder = orderSvc.getOneOrder(Integer.valueOf(ordNum));// 取出要改的VO號碼
		// 無效改成有效 0改成1
		int valid = 1;
		completeOrder.setOrdStat(valid);
//		
		// 有效改成無效 1改成0
//		int invalid = 0;
//		orderVO1.setOrdStat(invalid);
//		
		orderSvc.updateOrder(completeOrder);// 存檔

		model.addAttribute("success", "- (完成訂單)");
		completeOrder = orderSvc.getOneOrder(Integer.valueOf(completeOrder.getOrdNum()));
		model.addAttribute("orderVO", completeOrder);
		return "redirect:/order/transaction_stat"; // 訂單完成後轉交到訂單狀態
	}

	// 付款流程
	@PostMapping("payment")
	public String payment(@Valid OrderVO orderVO, @RequestParam("ordNum") String ordNum, ModelMap model)
			throws IOException {
		OrderVO payment = orderSvc.getOneOrder(Integer.valueOf(ordNum));// 取出要改的VO號碼
		// 無效改成有效 0改成1
		int valid = 1;
		payment.setOrdPaystat(valid);
//		
		// 有效改成無效 1改成0
//		int invalid = 0;
//		orderVO1.setOrdStat(invalid);
//		
		orderSvc.updateOrder(payment);// 存檔

		model.addAttribute("success", "- (匯款完成)");
		payment = orderSvc.getOneOrder(Integer.valueOf(payment.getOrdNum()));
		model.addAttribute("payment", payment);
		return "front-end/order/transaction_stat"; // 修改成功後轉交listOneorder.html
	}

	@ModelAttribute("userListData")
	protected List<UserVO> referenceListData() {
		List<UserVO> list = userSvc.getAll();
		return list;
	}

	@ModelAttribute("limitSaleListData")
	protected List<LimitSaleVO> referenceListLimitSaleData() {
		List<LimitSaleVO> list = limitSaleSvc.getAll();
		return list;
	}

	@ModelAttribute("orderListData")
	protected List<OrderVO> referenceListOrderData() {
		List<OrderVO> list = orderSvc.getAll();
		return list;
	}

	@ModelAttribute("reqOrderListData")
	protected List<ReqOrderVO> referenceListReqOrderListData() {
		List<ReqOrderVO> list = reqOrderSvc.getAll();
		return list;
	}

	@ModelAttribute("quoListData")
	protected List<QuoVO> referenceListQuoOrderListData() {
		List<QuoVO> list = quoOrderSvc.getAll();
		return list;
	}

	/*
	 * This method will be called on listAllUser.html form submission, handling POST
	 * request
	 */
//	@PostMapping("delete")
//	public String delete(@RequestParam("userId") String userId, ModelMap model) {
//		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
//		/*************************** 2.開始刪除資料 *****************************************/
//		// EmpService empSvc = new EmpService();
//		userSvc.deleteUser(Integer.valueOf(userId));
//		/*************************** 3.刪除完成,準備轉交(Send the Success view) **************/
//		List<UserVO> list = userSvc.getAll();
//		model.addAttribute("userListData", list);
//		model.addAttribute("success", "- (刪除成功)");
//		return "back-end/user/listAllUser"; // 刪除完成後轉交listAllUser.html
//	}

	// 去除BindingResult中某個欄位的FieldError紀錄
	public BindingResult removeFieldError(OrderVO orderVO, BindingResult result, String removedFieldname) {
		List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
				.filter(fieldname -> !fieldname.getField().equals(removedFieldname)).collect(Collectors.toList());
		result = new BeanPropertyBindingResult(orderVO, "orderVO");
		for (FieldError fieldError : errorsListToKeep) {
			result.addError(fieldError);
		}
		return result;
	}
}