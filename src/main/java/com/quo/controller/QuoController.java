package com.quo.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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

import com.quo.model.QuoService;
import com.quo.model.QuoVO;
import com.reqorder.model.ReqOrderService;
import com.reqorder.model.ReqOrderVO;
import com.user.model.UserService;


@Controller
@RequestMapping("/userinformation")
public class QuoController {

	@Autowired
	QuoService quoSvc;
	
	@Autowired
	UserService userSvc;
	
	@Autowired
	ReqOrderService reqOrderSvc;

	/*
	 * This method will serve as addEmp.html handler.
	 */
	@GetMapping("/addQuotation")
	public String addQuo(ModelMap model) {
		QuoVO quoVO = new QuoVO();
		model.addAttribute("quoVO", quoVO);
		return "front-end/userinformation/addQuotation";
	}
	
	/*
	 * This method will be called on addEmp.html form submission, handling POST request It also validates the user input
	 */
	@PostMapping("insertQuo")
	public String insertQuo(@Valid QuoVO quoVO, BindingResult result, ModelMap model) throws IOException {

		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
		result = removeFieldError(quoVO, result, "quoNum");

		if (result.hasErrors()) {
			return "front-end/userinformation/addQuotation";
		}
		/*************************** 2.開始新增資料 *****************************************/
		// EmpService empSvc = new EmpService();
		quoSvc.addQuo(quoVO);
		/*************************** 3.新增完成,準備轉交(Send the Success view) **************/
		List<QuoVO> list = quoSvc.getAll();
		model.addAttribute("quoListData", list);
		model.addAttribute("success", "- (新增成功)");
		return "front-end/userinformation/userpage"; // 新增成功後重導至IndexController_inSpringBoot.java的第50行@GetMapping("/emp/listAllEmp")
	}

	/*
	 * This method will be called on listAllEmp.html form submission, handling POST request
	 */
//	@PostMapping("getOne_For_Update")
//	public String getOne_For_Update(@RequestParam("quoNum") String quoNum, ModelMap model) {
//		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
//		/*************************** 2.開始查詢資料 *****************************************/
//		// EmpService empSvc = new EmpService();
//		QuoVO quoVO = quoSvc.getOneQuo(Integer.valueOf(quoNum));
////		quoVO.setQuoDate(new java.sql.Date(System.currentTimeMillis()));
//		
//		System.out.println("addr"+quoVO);
//
//		/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
//		model.addAttribute("quoVO", quoVO);
//		System.out.println("fghfgh" + quoVO.getQuoDate());
//		return "back-end/quo/update_quo_input"; // 查詢完成後轉交update_emp_input.html
//	}

	/*
	 * This method will be called on update_emp_input.html form submission, handling POST request It also validates the user input
	 */
//	@PostMapping("update")
//	public String update(@Valid QuoVO quoVO, BindingResult result, ModelMap model ) throws IOException {
//
//		
//		System.out.println(quoVO.getQuoDate());
//		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
//		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
//		result = removeFieldError(quoVO, result, "quoNum");
//
//		if (result.hasErrors()) {
//			return "back-end/quo/update_quo_input";
//		}
//		
//		System.out.println("addr"+quoVO);
//		/*************************** 2.開始修改資料 *****************************************/
//		// EmpService empSvc = new EmpService();
//		quoSvc.updateQuo(quoVO);
//
//		/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
//		model.addAttribute("success", "- (修改成功)");
//		quoVO = quoSvc.getOneQuo(Integer.valueOf(quoVO.getQuoNum()));
//		model.addAttribute("quoVO", quoVO);
//		return "back-end/quo/listOneQuo"; // 修改成功後轉交listOneEmp.html
//	}

	/*
	 * This method will be called on listAllEmp.html form submission, handling POST request
	 */
//	@PostMapping("delete")
//	public String delete(@RequestParam("quoNum") String quoNum, ModelMap model) {
//		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
//		/*************************** 2.開始刪除資料 *****************************************/
//		// EmpService empSvc = new EmpService();
//		quoSvc.deleteQuo(Integer.valueOf(quoNum));
//		/*************************** 3.刪除完成,準備轉交(Send the Success view) **************/
//		List<QuoVO> list = quoSvc.getAll();
//		model.addAttribute("quoListData", list);
//		model.addAttribute("success", "- (刪除成功)");
//		return "back-end/quo/listAllQuo"; // 刪除完成後轉交listAllEmp.html
//	}

	/*
	 * 第一種作法 Method used to populate the List Data in view. 如 : 
	 * <form:select path="deptno" id="deptno" items="${deptListData}" itemValue="deptno" itemLabel="dname" />
	 */

	/*
	 * 【 第二種作法 】 Method used to populate the Map Data in view. 如 : 
	 * <form:select path="deptno" id="deptno" items="${depMapData}" />
	 */
//	@ModelAttribute("quoMapData") //
//	protected Map<Integer, String> referenceMapData() {
//		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
//		map.put(10, "財務部");
//		map.put(20, "研發部");
//		map.put(30, "業務部");
//		map.put(40, "生管部");
//		return map;
//	}

	// 去除BindingResult中某個欄位的FieldError紀錄
	public BindingResult removeFieldError(QuoVO quoVO, BindingResult result, String removedFieldname) {
		List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
				.filter(fieldname -> !fieldname.getField().equals(removedFieldname))
				.collect(Collectors.toList());
		result = new BeanPropertyBindingResult(quoVO, "quoVO");
		for (FieldError fieldError : errorsListToKeep) {
			result.addError(fieldError);
		}
		return result;
	}

}