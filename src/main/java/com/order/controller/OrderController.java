

package com.order.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
	QuoService quoSvc;

//	@GetMapping("addOrder")
//	public String addOrder(ModelMap model) {
//		OrderVO orderVO = new OrderVO();
//		model.addAttribute("orderVO", orderVO);
//		return "back-end/order/addOrder";
//	}
	
	@GetMapping("chataddorder")
	public String chatorder(Model model) {
		OrderVO orderVO = new OrderVO();
		model.addAttribute("orderVO", orderVO);
		return "front-end/order/chataddorder";
	}
	
	
	@PostMapping("addOrder")
	public String insert(QuoVO quovo, HttpSession session) throws IOException {
		UserVO userVO = (UserVO)session.getAttribute("loggingInUser");
		ReqOrderVO reqOrderVO = quoSvc.getOrderByreqNum(quovo.getQuoNum());
//		QuoVO quonums = quoSvc.getOneQuo(quovo.getQuoNum());
		OrderVO orderVO = new OrderVO();
		orderVO.setOrdBuyerid(userVO.getUserId());
		orderVO.setOrdSellerid(quovo.getUserVO().getUserId());	
		orderVO.setOrdProdname(quovo.getQuoProdname());
		orderVO.setOrdProdprice(quovo.getQuoUnitprice());
		orderVO.setOrdProdqty(quovo.getQuoProdqty());
		orderVO.setOrdTotalamount(quovo.getQuoTotalprice());
		orderVO.setOrdUnitname(quovo.getQuoUnitname());
		orderVO.setOrdPaystat(0);
		orderVO.setOrdStat(0);
		orderVO.setOrdTranStat(0);
		orderVO.setReqOrderVO(reqOrderVO);
		orderVO.setOrdQuonum(quovo.getQuoNum());
		orderVO.setOrdIsValid(1);
		orderSvc.addOrder(orderVO,userVO,quovo);
	 	return "redirect:/order/transaction";
	}

	// 訂單（報價單）內容確認
	@GetMapping("/transaction_check")
	public String transaction_check(@RequestParam("quoNum") Integer quoNum, Model model, HttpServletRequest request) {
		System.out.println("hi我在這");
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO) session.getAttribute("loggingInUser");

		if (userVO == null) {
			return "redirect:/login"; // 如果使用者未登入，將其重定向到登入頁面
		}

		QuoVO quoVO = quoSvc.getOneQuo(quoNum);

		model.addAttribute("quoVO", quoVO);

		return "front-end/order/transaction_check"; // view
	}

	// 訂單交易 成功
	@GetMapping("/transaction")
	public String transaction(Model model, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO) session.getAttribute("loggingInUser");

		if (userVO == null) {
			return "redirect:/login"; // 如果使用者未登入，將其重定向到登入頁面
		}
		
		model.addAttribute("userVO", userVO);
		return "front-end/order/transaction"; // view
	}
	

	// 訂單交易狀態表 成功
	@GetMapping("/transaction_stat")
	public String transaction_stat(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO) session.getAttribute("loggingInUser");

		if (userVO == null) {
			return "redirect:/login"; // 如果使用者未登入，將其重定向到登入頁面
		}

		List<OrderVO> list = orderSvc.getOrdersByUserId(userVO.getUserId());
		model.addAttribute("orderListData", list);
		return "front-end/order/transaction_stat"; // view
	}

	// 訂單明細
	@GetMapping("/order_details")
	public String order_details(@RequestParam("ordNum") Integer ordNum, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO) session.getAttribute("loggingInUser");

		if (userVO == null) {
			return "redirect:/login"; // 如果用户未登录，将其重定向到登录页面
		}

		// 根据当前登录用户的ID获取订单列表
//		List<OrderVO> list = orderSvc.getOrderDetails(userVO.getUserId());
//		model.addAttribute("orderListData", list);

		OrderVO orderVO = orderSvc.getOneOrder(ordNum);
		model.addAttribute("orderVO", orderVO);
		return "front-end/order/order_details";
	}


	// 直接購買
//	@PostMapping("straightOrder")
//	public String straightOrder(@Valid OrderVO orderVO, BindingResult result, ModelMap model) throws IOException {
//
//		orderSvc.addOrder(orderVO);
//		/*************************** 3.新增完成,準備轉交(Send the Success view) **************/
//		List<OrderVO> list = orderSvc.getAll();
//		model.addAttribute("orderListData", list);
//		model.addAttribute("success", "- (新增成功)");
//		return "redirect:/order/listAllOrder"; // 新增成功後重導至IndexController_inSpringBoot.java的第50行@GetMapping("/order/listAllOrder")
//	}

	// 訂單完成
	@GetMapping("complete")
	public String complete(HttpServletRequest request,OrderVO orderVO, @RequestParam("ordNum") String ordNum, ModelMap model)
			throws IOException {
		OrderVO completeOrder = orderSvc.getOneOrder(Integer.valueOf(ordNum));// 取出要改的VO號碼
		// 改變訂單狀態 0改成1
		int valid = 1;
		completeOrder.setOrdStat(valid);
		completeOrder.setOrdTranStat(valid);
		orderSvc.updateOrder(completeOrder);// 存檔

		model.addAttribute("success", "- (完成訂單)");
		completeOrder = orderSvc.getOneOrder(Integer.valueOf(completeOrder.getOrdNum()));
		model.addAttribute("orderVO", completeOrder);
		return "redirect:/order/transaction_stat"; // 訂單完成後轉交到訂單狀態
	}

	// 付款流程
	@GetMapping("payment")
	public String payment(HttpServletRequest request,OrderVO orderVO, @RequestParam("ordNum") String ordNum, ModelMap model)
			throws IOException {
		OrderVO payment = orderSvc.getOneOrder(Integer.valueOf(ordNum));// 取出要改的VO號碼
		// 改變付款狀態 從0改成1
		int valid = 1;
		payment.setOrdPaystat(valid);
		orderSvc.updateOrder(payment);// 存檔

		model.addAttribute("success", "- (匯款完成)");
		payment = orderSvc.getOneOrder(Integer.valueOf(payment.getOrdNum()));
		model.addAttribute("payment", payment);
		return "redirect:/order/transaction_stat"; // 修改成功後轉交訂單狀態
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
		List<QuoVO> list = quoSvc.getAll();
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