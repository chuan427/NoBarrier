package com.reqorder.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.order.model.OrderService;
import com.quo.model.QuoService;
import com.reqorder.model.ReqOrderService;
import com.reqorder.model.ReqOrderVO;
import com.user.model.UserService;

@Controller
@Validated
@RequestMapping("/reqorder")
public class ReqNumController {
	
	@Autowired
	ReqOrderService reqOrderSvc;
	
	@Autowired
	QuoService quoSvc;
	
	@Autowired
	UserService userSvc;
	
	@Autowired
	OrderService orderSvc;
	
	@PostMapping("getOne_For_Display")
	public String getOne_For_Display(
			/***************************1.接收請求參數 - 輸入格式的錯誤處理*************************/
			@NotEmpty(message="需求編號: 請勿空白")
			@Digits(integer = 100, fraction = 0, message = "需求編號: 請填數字-請勿超過{integer}位數")
			@Min(value = 0001, message = "需求編號: 不能小於{value}")
			@Max(value = 7777, message = "需求編號: 不能超過{value}")
			@RequestParam("reqNum") String reqNum,
			ModelMap model) {
			
			/***************************2.開始查詢資料*********************************************/
//			EmpService empSvc = new EmpService();
			ReqOrderVO reqOrderVO = reqOrderSvc.getOneReqOrder(Integer.valueOf(reqNum));
			
			List<ReqOrderVO> list = reqOrderSvc.getAll();
			model.addAttribute("reqOrderListData", list); // for select_page.html 第行用
			
			if (reqOrderVO == null) {
				model.addAttribute("errorMessage", "查無資料");
				return "front-end/userinformation/userpage";
			}
			
			/***************************3.查詢完成,準備轉交(Send the Success view)*****************/
			model.addAttribute("reqOrderVO", reqOrderVO);
			model.addAttribute("getOne_For_Display", "true"); // 旗標getOne_For_Display見select_page.html的第126行 -->
			
			return "front-end/userinformation/userpage"; // 查詢完成後轉交select_page.html由其第128行insert listOneEmp.html內的th:fragment="listOneUser-div
		}
			
	
	@ExceptionHandler(value = { ConstraintViolationException.class })
	//@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ModelAndView handleError(HttpServletRequest req,ConstraintViolationException e,Model model) {
	    Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
	    StringBuilder strBuilder = new StringBuilder();
	    for (ConstraintViolation<?> violation : violations ) {
	          strBuilder.append(violation.getMessage() + "<br>");
	    }
		List<ReqOrderVO> list = reqOrderSvc.getAll();
		model.addAttribute("reqOrderListData", list); // for select_page.html 第行用
		
		String message = strBuilder.toString();
	    return new ModelAndView("front-end/userinformation/userpage", "errorMessage", "請修正以下錯誤:<br>"+message);
	}

}
