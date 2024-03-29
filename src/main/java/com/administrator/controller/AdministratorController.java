package com.administrator.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.administrator.model.AdministratorService;
import com.administrator.model.AdministratorVO;
import com.user.model.UserService;
import com.user.model.UserVO;

@Controller
@RequestMapping("/administrator")
public class AdministratorController {

	@Autowired
	AdministratorService administratorSvc;

	@Autowired
	UserService userSvc;

	@GetMapping("addAdministrator")
	public String addAdministrator(ModelMap model) {
		AdministratorVO administratorVO = new AdministratorVO();
		model.addAttribute("administratorVO", administratorVO);
		return "back-end/administrator/addAdministrator";
	}

	@PostMapping("insert")
	public String insert(@Valid AdministratorVO administratorVO, BindingResult result, ModelMap model)
			throws IOException {

		if (result.hasErrors()) {
			return "back-end/administrator/addAdministrator";
		}

		/*************************** 2.開始新增資料 *****************************************/
		// EmpService empSvc = new EmpService();
		administratorSvc.addAdministrator(administratorVO);
		/*************************** 3.新增完成,準備轉交(Send the Success view) **************/
		List<AdministratorVO> list = administratorSvc.getAll();
		model.addAttribute("administratorListData", list);
		model.addAttribute("success", "- (新增成功)");
		return "redirect:/administrator/listAllAdministrator";
	}

	@PostMapping("getOne_For_Update")
	public String getOne_For_Update(@RequestParam("adminNum") String adminNum, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始查詢資料 *****************************************/
		// EmpService empSvc = new EmpService();
		AdministratorVO administratorVO = administratorSvc.getOneAdministrator(Integer.valueOf(adminNum));

		/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
		model.addAttribute("administratorVO", administratorVO);
		return "back-end/administrator/update_administrator_input"; // 查詢完成後轉交update_user_input.html
	}

	@PostMapping("update")
	public String update(@Valid AdministratorVO administratorVO, BindingResult result, ModelMap model)
			throws IOException {

		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/

		if (result.hasErrors()) {
			return "back-end/administrator/update_administrator_input";
		}

		/*************************** 2.開始修改資料 *****************************************/
		// EmpService empSvc = new EmpService();
		administratorSvc.updateAdministrator(administratorVO);

		/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
		model.addAttribute("success", "- (修改成功)");
		administratorVO = administratorSvc.getOneAdministrator(Integer.valueOf(administratorVO.getAdminNum()));
		model.addAttribute("administratorVO", administratorVO);
		return "back-end/administrator/listOneAdministrator"; // 修改成功後轉交listOneEmp.html
	}

//	@ModelAttribute("userListData")
//	protected List<UserVO> referenceListData() {
//		List<UserVO> list = userSvc.getAll();
//		return list;
//	}
	
	
	// 去除BindingResult中某個欄位的FieldError紀錄
		public BindingResult removeFieldError(UserVO userVO, BindingResult result, String removedFieldname) {
			List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
					.filter(fieldname -> !fieldname.getField().equals(removedFieldname)).collect(Collectors.toList());
			result = new BeanPropertyBindingResult(userVO, "userVO");
			for (FieldError fieldError : errorsListToKeep) {
				result.addError(fieldError);
			}
			return result;
		}

}
