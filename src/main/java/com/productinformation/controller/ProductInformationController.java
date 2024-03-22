package com.productinformation.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.productinformation.model.ProductInformationService;
import com.productinformation.model.ProductInformationVO;
import com.user.model.UserService;
import com.user.model.UserVO;

@Controller
@RequestMapping("/com")
public class ProductInformationController {

	@Autowired
	ProductInformationService productInformationSvc;
	
	@Autowired
	UserService userSvc;
	
//	@GetMapping("addProductInformation")
//	public String addProductInformation(ModelMap model) {
//		ProductInformationVO productInformationVO = new ProductInformationVO();
//		model.addAttribute("productInformationVO", productInformationVO);
//		return "back-end/productInformation/addProductInformation";
//	}

	@PostMapping("insertProduct")
	public String insert(@Valid ProductInformationVO ProductInformationVO,BindingResult result, HttpServletRequest request , ModelMap model,
			@RequestParam("pinfoImage") MultipartFile[] parts) throws IOException {
		
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
		UserVO userVO = (UserVO) request.getSession().getAttribute("loggingInUser");
		ProductInformationVO.setUserVO(userVO);
		result = removeFieldError(ProductInformationVO, result, "pinfoImage");

		if (parts[0].isEmpty()) { // 使用者未選擇要上傳的圖片時
			model.addAttribute("errorMessage", "產品圖片空白: 請上傳照片");
		} else {
			for (MultipartFile multipartFile : parts) {
				byte[] buf = multipartFile.getBytes();
				ProductInformationVO.setPinfoImage(buf);
			}
		}
		
		if (result.hasErrors() || parts[0].isEmpty()) {
			System.out.println("23");
			return "front-end/com/editmember_product_insert";
		}
		/*************************** 2.開始新增資料 *****************************************/
	    productInformationSvc.addProductInformation(ProductInformationVO);
		/*************************** 3.新增完成,準備轉交(Send the Success view) **************/
	    List<ProductInformationVO> productInformationlist = productInformationSvc.getProductInformationByUserId(userVO.getUserId());
	    model.addAttribute("productInformationList", productInformationlist);
	    model.addAttribute("success", "編輯成功,請前往預覽頁面查看");
		model.addAttribute("editSuccess", true);
	    model.addAttribute("userVO",userVO);
	    return "front-end/com/editmember_product_insert";
	}
	
	/*********************************** 產品資訊更新 *****************************************/
	
	@PostMapping("getOne_For_Update1")
	public String getOne_For_Update1(@RequestParam("pinfoNum") String pinfoNum, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始查詢資料 *****************************************/
		ProductInformationVO productInformationVO = productInformationSvc.getOnePinfoNum(Integer.valueOf(pinfoNum));
		/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
		model.addAttribute("productInformationVO", productInformationVO);
		return "back-end/productInformation/update_productInformation_input"; // 查詢完成後轉交update_user_input.html
	}
	
	@PostMapping("update")
	public String update(@Valid ProductInformationVO ProductInformationVO, BindingResult result, HttpServletRequest request,ModelMap model,
			@RequestParam("pinfoImage") MultipartFile[] parts) throws IOException {

		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
		result = removeFieldError(ProductInformationVO, result, "pinfoImage");
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO) session.getAttribute("loggingInUser");
		result = removeFieldError(ProductInformationVO, result, "pinfoImage");
		
//		if (parts[0].isEmpty()) { // 使用者未選擇要上傳的圖片時
//			model.addAttribute("errorMessage", "產品資訊: 請上傳照片");
//		} else {
//			for (MultipartFile multipartFile : parts) {
//				byte[] buf = multipartFile.getBytes();
//				ProductInformationVO.setPinfoImage(buf);
//			}
			
			if (!parts[0].isEmpty()) {
		        byte[] pinfoImage = parts[0].getBytes();
		        ProductInformationVO.setPinfoImage(pinfoImage);
		    } else {
		        // 如果用户未选择上传新图片，则保留原有图片
		        byte[] existingImage = productInformationSvc.getOnePinfoNum(ProductInformationVO.getPinfoNum()).getPinfoImage();
		        ProductInformationVO.setPinfoImage(existingImage);
		    }	
		
		if (result.hasErrors()) {
			System.out.println("666");
			return "back-end/productInformation/update_productInformation_input";
		}
		/*************************** 2.開始修改資料 *****************************************/
		productInformationSvc.updateProductInformation(ProductInformationVO);

		/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
		model.addAttribute("success", "編輯成功,請前往預覽頁面查看");
	    List<ProductInformationVO> productInformationList = productInformationSvc.getProductInformationByUserId(userVO.getUserId());
        model.addAttribute("productInformationList", productInformationList);
//        model.addAttribute("userVO", userVO);
		return "back-end/productInformation/update_productInformation_input"; // 修改成功後轉交editmember_product_view.html
	}
		
	 /*********************************** 錯誤處理部分 *****************************************/
		// 去除BindingResult中某個欄位的FieldError紀錄
	public BindingResult removeFieldError(ProductInformationVO ProductInformationVO, BindingResult result, String removedFieldname) {
		List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
				.filter(fieldname -> !fieldname.getField().equals(removedFieldname))
				.collect(Collectors.toList());
		result = new BeanPropertyBindingResult(ProductInformationVO, "productInformationVO");
		for (FieldError fieldError : errorsListToKeep) {
			result.addError(fieldError);
		}
		return result;
	}
}
