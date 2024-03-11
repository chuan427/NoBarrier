package com.forumpost.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Digits;
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

import com.forumpost.model.ForumPostService;
import com.forumpost.model.ForumPostVO;

@Controller
@Validated
@RequestMapping("/forumPost")
public class FpNumController {

	@Autowired
	ForumPostService forumPostSvc;

	/*
	 * This method will be called on select_page.html form submission, handling POST
	 * request It also validates the user input
	 */
	@PostMapping("getOne_For_Display")
	public String getOne_For_Display(/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
	@NotEmpty(message = "貼文編號: 請勿空白") @Digits(integer = 100, fraction = 0, message = "會員編號: 請填數字-請勿超過{integer}位數")
	@RequestParam("fpNum") String fpNum, ModelMap model) {

		/***************************
		 * 2.開始查詢資料
		 *********************************************/
//		EmpService empSvc = new EmpService();
		ForumPostVO forumPostVO = forumPostSvc.getOneForumPost(Integer.valueOf(fpNum));

		List<ForumPostVO> list = forumPostSvc.getAll();
		model.addAttribute("forumPostListData", list); // for select_page.html 第行用

		if (forumPostVO == null) {
			model.addAttribute("errorMessage", "查無資料");
			return "back-end/forumPost/select_page1";
		}

		/***************************
		 * 3.查詢完成,準備轉交(Send the Success view)
		 *****************/
		model.addAttribute("forumPostVO", forumPostVO);
		model.addAttribute("getOne_For_Display", "true"); // 旗標getOne_For_Display見select_page.html的第126行 -->

		return "back-end/forumPost/select_page1"; // 查詢完成後轉交select_page.html由其第128行insert
													// listOneEmp.html內的th:fragment="listOneUser-div
	}

	@ExceptionHandler(value = { ConstraintViolationException.class })
	// @ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ModelAndView handleError(HttpServletRequest req, ConstraintViolationException e, Model model) {
		Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
		StringBuilder strBuilder = new StringBuilder();
		for (ConstraintViolation<?> violation : violations) {
			strBuilder.append(violation.getMessage() + "<br>");
		}
		List<ForumPostVO> list = forumPostSvc.getAll();
		model.addAttribute("forumPostListData", list); // for select_page.html 第行用

		String message = strBuilder.toString();
		return new ModelAndView("back-end/forumPost/select_page1", "errorMessage", "請修正以下錯誤:<br>" + message);
	}

}