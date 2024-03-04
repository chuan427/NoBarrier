package com.productinformation.controller;

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

import com.productinformation.model.ProductInformationService;
import com.productinformation.model.ProductInformationVO;
import com.user.model.UserService;
import com.user.model.UserVO;

@Controller
@RequestMapping("/productInformation")
public class ProductInformationController {

	@Autowired
	ProductInformationService productInformationSvc;
	
	@Autowired
	UserService userSvc;
	
	@GetMapping("addProductInformation")
	public String addProductInformation(ModelMap model) {
		ProductInformationVO productInformationVO = new ProductInformationVO();
		model.addAttribute("productInformationVO", productInformationVO);
		return "back-end/productInformation/addProductInformation";
	}
	
	@PostMapping("insert")
	public String insert(@Valid ProductInformationVO productInformationVO, BindingResult result, ModelMap model,
			@RequestParam("pinfoImage") MultipartFile[] parts) throws IOException {
		
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
		result = removeFieldError(productInformationVO, result, "pinfoImage");

		if (parts[0].isEmpty()) { // 使用者未選擇要上傳的圖片時
			model.addAttribute("errorMessage", "關於我們圖片: 請上傳照片");
		} else {
			for (MultipartFile multipartFile : parts) {
				byte[] buf = multipartFile.getBytes();
				productInformationVO.setPinfoImage(buf);
			}
		}
		if (result.hasErrors() || parts[0].isEmpty()) {
			return "back-end/productInformation/addProductInformation";
		}
		/*************************** 2.開始新增資料 *****************************************/
		// EmpService empSvc = new EmpService();
		productInformationSvc.addProductInformation(productInformationVO);
		/*************************** 3.新增完成,準備轉交(Send the Success view) **************/
		List<ProductInformationVO> list = productInformationSvc.getAll();
		model.addAttribute("productInformationListData", list);
		model.addAttribute("success", "- (新增成功)");
		return "redirect:/productInformation/listAllProductInformation"; // 新增成功後重導至IndexController_inSpringBoot.java的第50行@GetMapping("/user/listAllUser")
	}
	
	@PostMapping("getOne_For_Update")
	public String getOne_For_Update(@RequestParam("pinfoNum") String pinfoNum, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始查詢資料 *****************************************/
		// EmpService empSvc = new EmpService();
		ProductInformationVO productInformationVO = productInformationSvc.getOneProductInformation(Integer.valueOf(pinfoNum));

		/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
		model.addAttribute("productInformationVO", productInformationVO);
		return "back-end/productInformation/update_productInformation_input"; // 查詢完成後轉交update_user_input.html
	}
	
	@PostMapping("update")
	public String update(@Valid ProductInformationVO productInformationVO, BindingResult result, ModelMap model,
			@RequestParam("pinfoImage") MultipartFile[] parts) throws IOException {

		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
		result = removeFieldError(productInformationVO, result, "pinfoImage");

		if (parts[0].isEmpty()) { // 使用者未選擇要上傳的新圖片時
			// EmpService empSvc = new EmpService();
			byte[] pinfoImage = productInformationSvc.getOneProductInformation(productInformationVO.getPinfoNum()).getPinfoImage();
			productInformationVO.setPinfoImage(pinfoImage);
		} else {
			for (MultipartFile multipartFile : parts) {
				byte[] pinfoImage = multipartFile.getBytes();
				productInformationVO.setPinfoImage(pinfoImage);
			}
		}
		if (result.hasErrors()) {
			return "back-end/productInformation/update_productInformation_input";
		}
		/*************************** 2.開始修改資料 *****************************************/
		// EmpService empSvc = new EmpService();
		productInformationSvc.updateProductInformation(productInformationVO);

		/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
		model.addAttribute("success", "- (修改成功)");
		productInformationVO = productInformationSvc.getOneProductInformation(Integer.valueOf(productInformationVO.getPinfoNum()));
		model.addAttribute("productInformationVO", productInformationVO);
		return "back-end/productInformation/listOneProductInformation"; // 修改成功後轉交listOneEmp.html
	}
	
	@ModelAttribute("userListData")
	protected List<UserVO> referenceListData(){
		List<UserVO> list = userSvc.getAll();
		return list;
	}
	
	public BindingResult removeFieldError(ProductInformationVO productInformationVO, BindingResult result, String removedFieldname) {
		List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
				.filter(fieldname -> !fieldname.getField().equals(removedFieldname))
				.collect(Collectors.toList());
		result = new BeanPropertyBindingResult(productInformationVO, "productInformationVO");
		for (FieldError fieldError : errorsListToKeep) {
			result.addError(fieldError);
		}
		return result;
	}
}
