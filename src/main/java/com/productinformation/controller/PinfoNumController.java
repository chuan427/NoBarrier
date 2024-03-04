package com.productinformation.controller;

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

import com.productinformation.model.ProductInformationService;
import com.productinformation.model.ProductInformationVO;

@Controller
@Validated
@RequestMapping("/productInformation")
public class PinfoNumController {
	
	@Autowired
	ProductInformationService productInformationSvc;
	
	@PostMapping("getOne_For_Display")
	public String getOne_For_Display(
			/***************************1.接收請求參數 - 輸入格式的錯誤處理*************************/
			@NotEmpty(message="產品資訊編號: 請勿空白")
			@Digits(integer = 100, fraction = 0, message = "產品資訊編號: 請填數字-請勿超過{integer}位數")
			@Min(value = 0001, message = "產品資訊編號: 不能小於{value}")
			@Max(value = 7777, message = "產品資訊編號: 不能超過{value}")
			@RequestParam("pinfoNum") String pinfoNum,
			ModelMap model) {
			
			/***************************2.開始查詢資料*********************************************/
//			EmpService empSvc = new EmpService();
			ProductInformationVO productInformationVO = productInformationSvc.getOneProductInformation(Integer.valueOf(pinfoNum));
			
			List<ProductInformationVO> list = productInformationSvc.getAll();
			model.addAttribute("productInformationListData", list); // for select_page.html 第行用
			
			if (productInformationVO == null) {
				model.addAttribute("errorMessage", "查無資料");
				return "back-end/productInformation/select_page";
			}
			
			/***************************3.查詢完成,準備轉交(Send the Success view)*****************/
			model.addAttribute("productInformationVO", productInformationVO);
			model.addAttribute("getOne_For_Display", "true"); // 旗標getOne_For_Display見select_page.html的第126行 -->
			
			return "back-end/productInformation/select_page"; // 查詢完成後轉交select_page.html由其第128行insert listOneEmp.html內的th:fragment="listOneUser-div
		}

	@ExceptionHandler(value = { ConstraintViolationException.class })
	//@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ModelAndView handleError(HttpServletRequest req,ConstraintViolationException e,Model model) {
	    Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
	    StringBuilder strBuilder = new StringBuilder();
	    for (ConstraintViolation<?> violation : violations ) {
	          strBuilder.append(violation.getMessage() + "<br>");
	    }
		List<ProductInformationVO> list = productInformationSvc.getAll();
		model.addAttribute("productInformationListData", list); // for select_page.html 第行用
		
		String message = strBuilder.toString();
	    return new ModelAndView("back-end/productInformation/select_page", "errorMessage", "請修正以下錯誤:<br>"+message);
	}
}
