package com.administrator.controller;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.administrator.model.AdministratorService;
import com.administrator.model.AdministratorVO;
import com.user.model.UserService;
import com.user.model.UserVO;


@Controller
@Validated
@RequestMapping("/administrator")
public class AdminNumController {
	
	@Autowired
	AdministratorService administratorSvc;
	
	

	@PostMapping("getOne_For_Display")
	public String getOne_For_Display(
			/***************************1.接收請求參數 - 輸入格式的錯誤處理*************************/
			@NotEmpty(message="後台管理者編號: 請勿空白")
			@Digits(integer = 100, fraction = 0, message = "後台管理者編號: 請填數字-請勿超過{integer}位數")
			@Min(value = 1, message = "後台管理者編號: 不能小於{value}")
			@Max(value = 20, message = "後台管理者編號: 不能超過{value}")
			@RequestParam("adminNum") String adminNum,
			ModelMap model) {
			
			/***************************2.開始查詢資料*********************************************/
//			EmpService empSvc = new EmpService();
			AdministratorVO administratorVO = administratorSvc.getOneAdministrator(Integer.valueOf(adminNum));
			
			List<AdministratorVO> list = administratorSvc.getAll();
			model.addAttribute("administratorListData", list); // for select_page.html 第行用
			
			if (administratorVO == null) {
				model.addAttribute("errorMessage", "查無資料");
				return "back-end/administrator/select_page";
			}
			
			/***************************3.查詢完成,準備轉交(Send the Success view)*****************/
			model.addAttribute("administratorVO", administratorVO);
			model.addAttribute("getOne_For_Display", "true"); // 旗標getOne_For_Display見select_page.html的第126行 -->
			
			return "back-end/administrator/select_page"; // 查詢完成後轉交select_page.html由其第128行insert listOneEmp.html內的th:fragment="listOneUser-div
		}
	
		@ExceptionHandler(value = { ConstraintViolationException.class })
		//@ResponseStatus(value = HttpStatus.BAD_REQUEST)
		public ModelAndView handleError(HttpServletRequest req,ConstraintViolationException e,Model model) {
		    Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
		    StringBuilder strBuilder = new StringBuilder();
		    for (ConstraintViolation<?> violation : violations ) {
		          strBuilder.append(violation.getMessage() + "<br>");
		    }
			List<AdministratorVO> list = administratorSvc.getAll();
			model.addAttribute("administratorListData", list); // for select_page.html 第行用
			
			String message = strBuilder.toString();
		    return new ModelAndView("back-end/administrator/select_page", "errorMessage", "請修正以下錯誤:<br>"+message);
		}
		
		
}
