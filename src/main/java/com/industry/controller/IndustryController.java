package com.industry.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.industry.model.IndustryService;
import com.industry.model.IndustryVO;
import com.reqorder.model.ReqOrderVO;
import com.user.model.UserService;


@Controller
@RequestMapping("/industry")
public class IndustryController {

	@Autowired
	IndustryService industrySvc;
	
	@Autowired
	UserService userSvc;
	
	@GetMapping("addIndustry")
	public String addIndustry(ModelMap model) {
		IndustryVO industryVO = new IndustryVO();
		model.addAttribute("industryVO", industryVO);
		return "back-end/industry/addIndustry";
	}
	
	@PostMapping("insert")
	public String insert(@Valid IndustryVO industryVO, BindingResult result, ModelMap model) throws IOException {
		
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
//		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
//		result = removeFieldError(industryVO, result, "reqProdimage");
//
//		if (parts[0].isEmpty()) { // 使用者未選擇要上傳的圖片時
//			model.addAttribute("errorMessage", "關於我們圖片: 請上傳照片");
//		} else {
//			for (MultipartFile multipartFile : parts) {
//				byte[] buf = multipartFile.getBytes();
//				industryVO.setReqProdimage(buf);
//			}
//		}
//		if (result.hasErrors() || parts[0].isEmpty()) {
//			return "back-end/reqOrder/addReqOrder";
//		}
		if (result.hasErrors()) {
	        return "back-end/industry/addIndustry";
	    }
		/*************************** 2.開始新增資料 *****************************************/
		// EmpService empSvc = new EmpService();
		industrySvc.addIndustry(industryVO);
		/*************************** 3.新增完成,準備轉交(Send the Success view) **************/
		List<IndustryVO> list = industrySvc.getAll();
		model.addAttribute("industryListData", list);
		model.addAttribute("success", "- (新增成功)");
		return "redirect:/industry/listAllIndustry"; // 新增成功後重導至IndexController_inSpringBoot.java的第50行@GetMapping("/user/listAllUser")
	}
	
	@PostMapping("getOne_For_Update")
	public String getOne_For_Update(@RequestParam("industryNum") String industryNum, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始查詢資料 *****************************************/
		// EmpService empSvc = new EmpService();
		IndustryVO industryVO = industrySvc.getOneIndustry(Integer.valueOf(industryNum));

		/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
		model.addAttribute("industryVO", industryVO);
		return "back-end/industry/update_industry_input"; // 查詢完成後轉交update_user_input.html
	}
	
	@PostMapping("update")
	public String update(@Valid IndustryVO industryVO, BindingResult result, ModelMap model) throws IOException {
		
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		
		if (result.hasErrors()) {
			return "back-end/industry/update_industry_input";
		}
		
		/*************************** 2.開始修改資料 *****************************************/
		// EmpService empSvc = new EmpService();
		industrySvc.updateIndustry(industryVO);

		/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
		model.addAttribute("success", "- (修改成功)");
		industryVO = industrySvc.getOneIndustry(Integer.valueOf(industryVO.getIndustryNum()));
		model.addAttribute("industryVO", industryVO);
		return "back-end/industry/listOneIndustry"; // 修改成功後轉交listOneEmp.html
	}
	
//	public BindingResult removeFieldError(IndustryVO industryVO, BindingResult result, String removedFieldname) {
//		List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
//				.filter(fieldname -> !fieldname.getField().equals(removedFieldname))
//				.collect(Collectors.toList());
//		result = new BeanPropertyBindingResult(industryVO, "industryVO");
//		for (FieldError fieldError : errorsListToKeep) {
//			result.addError(fieldError);
//		}
//		return result;
//	}
}
