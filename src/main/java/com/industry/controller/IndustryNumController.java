package com.industry.controller;

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

import com.industry.model.IndustryService;
import com.industry.model.IndustryVO;

@Controller
@Validated
@RequestMapping("/industry")
public class IndustryNumController {
	
	@Autowired
	IndustryService industrySvc;
	
	@PostMapping("getOne_For_Display")
	public String getOne_For_Display(
			/***************************1.接收請求參數 - 輸入格式的錯誤處理*************************/
			@NotEmpty(message="產業類別編號: 請勿空白")
			@Digits(integer = 100, fraction = 0, message = "產業類別編號: 請填數字-請勿超過{integer}位數")
			@Min(value = 1, message = "產業類別編號: 不能小於{value}")
			@Max(value = 20, message = "產業類別編號: 不能超過{value}")
			@RequestParam("industryNum") String industryNum,
			ModelMap model) {
			
			/***************************2.開始查詢資料*********************************************/
//			EmpService empSvc = new EmpService();
			IndustryVO industryVO = industrySvc.getOneIndustry(Integer.valueOf(industryNum));
			
			List<IndustryVO> list = industrySvc.getAll();
			model.addAttribute("industryListData", list); // for select_page.html 第行用
			
			if (industryVO == null) {
				model.addAttribute("errorMessage", "查無資料");
				return "back-end/industry/select_page";
			}
			
			/***************************3.查詢完成,準備轉交(Send the Success view)*****************/
			model.addAttribute("industryVO", industryVO);
			model.addAttribute("getOne_For_Display", "true"); // 旗標getOne_For_Display見select_page.html的第126行 -->
			
			return "back-end/industry/select_page"; // 查詢完成後轉交select_page.html由其第128行insert listOneEmp.html內的th:fragment="listOneUser-div
		}

	@ExceptionHandler(value = { ConstraintViolationException.class })
	//@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ModelAndView handleError(HttpServletRequest req,ConstraintViolationException e,Model model) {
	    Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
	    StringBuilder strBuilder = new StringBuilder();
	    for (ConstraintViolation<?> violation : violations ) {
	          strBuilder.append(violation.getMessage() + "<br>");
	    }
		List<IndustryVO> list = industrySvc.getAll();
		model.addAttribute("industryListData", list); // for select_page.html 第行用
		
		String message = strBuilder.toString();
	    return new ModelAndView("back-end/industry/select_page", "errorMessage", "請修正以下錯誤:<br>"+message);
	}
}
