package com.quo.controller;

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

import com.quo.model.QuoService;
import com.quo.model.QuoVO;


@Controller
@Validated
@RequestMapping("/quo")
public class QuoNumController {
	
	@Autowired
	QuoService quoSvc;

	/*
	 * This method will be called on select_page.html form submission, handling POST
	 * request It also validates the user input
	 */
	@PostMapping("getOne_For_Display")
	public String getOne_For_Display(
		/***************************1.接收請求參數 - 輸入格式的錯誤處理*************************/
		@NotEmpty(message="報價單編號: 請勿空白")
		@Digits(integer = 4, fraction = 0, message = "報價單編號: 請填數字-請勿超過{integer}位數")
		@Min(value = 1, message = "報價單編號: 不能小於{value}")
		@Max(value = 100, message = "報價單編號: 不能超過{value}")
		@RequestParam("quoNum") String quoNum,
		ModelMap model) {
		
		/***************************2.開始查詢資料*********************************************/
//		EmpService empSvc = new EmpService();
		QuoVO quoVO = quoSvc.getOneQuo(Integer.valueOf(quoNum));
		
		List<QuoVO> list = quoSvc.getAll();
		model.addAttribute("quoListData", list); // for select_page.html 第97 109行用
		
		if (quoVO == null) {
			model.addAttribute("errorMessage", "查無資料");
			return "front-end/userinformation/userpage";
		}
		
		/***************************3.查詢完成,準備轉交(Send the Success view)*****************/
		model.addAttribute("quoVO", quoVO);
		model.addAttribute("getOne_For_Display", "true"); // 旗標getOne_For_Display見select_page.html的第126行 -->
		
//		return "back-end/quo/listOneQuo";  // 查詢完成後轉交listOneEmp.html
		return "front-end/userinformation/userpage"; // 查詢完成後轉交select_page.html由其第128行insert listOneEmp.html內的th:fragment="listOneEmp-div
	}

	
	@ExceptionHandler(value = { ConstraintViolationException.class })
	//@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ModelAndView handleError(HttpServletRequest req,ConstraintViolationException e,Model model) {
	    Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
	    StringBuilder strBuilder = new StringBuilder();
	    for (ConstraintViolation<?> violation : violations ) {
	          strBuilder.append(violation.getMessage() + "<br>");
	    }
	    //==== 以下第80~85行是當前面第69行返回 /src/main/resources/templates/back-end/emp/select_page.html 第97行 與 第109行 用的 ====   
//	    model.addAttribute("empVO", new EmpVO());
//    	EmpService empSvc = new EmpService();
		List<QuoVO> list = quoSvc.getAll();
		model.addAttribute("quoListData", list); // for select_page.html 第97 109行用
		
		String message = strBuilder.toString();
	    return new ModelAndView("front-end/userinformation/userpage", "errorMessage", "請修正以下錯誤:<br>"+message);
	}
	
}