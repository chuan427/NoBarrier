package com.reqorder.controller;

import javax.validation.Valid;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.industry.model.IndustryService;
import com.industry.model.IndustryVO;
import com.reqorder.model.ReqOrderService;
import com.reqorder.model.ReqOrderVO;
import com.user.model.UserService;
import com.user.model.UserVO;



@Controller
@RequestMapping("/reqOrder")
public class ReqOrderController {

	@Autowired
	ReqOrderService reqOrderSvc;
	
	@Autowired
	UserService userSvc;
	
	@Autowired
	IndustryService industrySvc;
	
	@GetMapping("addReqOrder")
	public String addReqOrder(ModelMap model) {
		ReqOrderVO reqOrderVO = new ReqOrderVO();
		model.addAttribute("reqOrderVO", reqOrderVO);
		return "back-end/reqOrder/addReqOrder";
	}
	
	@PostMapping("insert")
	public String insert(@Valid ReqOrderVO reqOrderVO, BindingResult result, ModelMap model,
			@RequestParam("reqProdimage") MultipartFile[] parts) throws IOException {
		
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
		result = removeFieldError(reqOrderVO, result, "reqProdimage");

		if (parts[0].isEmpty()) { // 使用者未選擇要上傳的圖片時
			model.addAttribute("errorMessage", "關於我們圖片: 請上傳照片");
		} else {
			for (MultipartFile multipartFile : parts) {
				byte[] buf = multipartFile.getBytes();
				reqOrderVO.setReqProdimage(buf);
			}
		}
		if (result.hasErrors() || parts[0].isEmpty()) {
			return "back-end/reqOrder/addReqOrder";
		}
		/*************************** 2.開始新增資料 *****************************************/
		// EmpService empSvc = new EmpService();
		reqOrderSvc.addReqOrder(reqOrderVO);
		/*************************** 3.新增完成,準備轉交(Send the Success view) **************/
		List<ReqOrderVO> list = reqOrderSvc.getAll();
		model.addAttribute("reqOrderListData", list);
		model.addAttribute("success", "- (新增成功)");
		return "redirect:/reqOrder/listAllReqOrder"; // 新增成功後重導至IndexController_inSpringBoot.java的第50行@GetMapping("/user/listAllUser")
	}
	
	
	@PostMapping("getOne_For_Update")
	public String getOne_For_Update(@RequestParam("reqNum") String reqNum, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始查詢資料 *****************************************/
		// EmpService empSvc = new EmpService();
		ReqOrderVO reqOrderVO = reqOrderSvc.getOneReqOrder(Integer.valueOf(reqNum));

		/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
		model.addAttribute("reqOrderVO", reqOrderVO);
		return "back-end/reqOrder/update_reqOrder_input"; // 查詢完成後轉交update_user_input.html
	}
	
	@PostMapping("update")
	public String update(@Valid ReqOrderVO reqOrderVO, BindingResult result, ModelMap model,
			@RequestParam("reqProdimage") MultipartFile[] parts) throws IOException {

		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
		result = removeFieldError(reqOrderVO, result, "reqProdimage");

		if (parts[0].isEmpty()) { // 使用者未選擇要上傳的新圖片時
			// EmpService empSvc = new EmpService();
			byte[] reqProdimage = reqOrderSvc.getOneReqOrder(reqOrderVO.getReqNum()).getReqProdimage();
			reqOrderVO.setReqProdimage(reqProdimage);
		} else {
			for (MultipartFile multipartFile : parts) {
				byte[] reqProdimage = multipartFile.getBytes();
				reqOrderVO.setReqProdimage(reqProdimage);
			}
		}
		if (result.hasErrors()) {
			return "back-end/reqOrder/update_reqOrder_input";
		}
		/*************************** 2.開始修改資料 *****************************************/
		// EmpService empSvc = new EmpService();
		reqOrderSvc.updateReqOrder(reqOrderVO);

		/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
		model.addAttribute("success", "- (修改成功)");
		reqOrderVO = reqOrderSvc.getOneReqOrder(Integer.valueOf(reqOrderVO.getReqNum()));
		model.addAttribute("reqOrderVO", reqOrderVO);
		return "back-end/reqOrder/listOneReqOrder"; // 修改成功後轉交listOneEmp.html
	}
	@ModelAttribute("userListData")
	protected List<UserVO> referenceListData(){
		List<UserVO> list = userSvc.getAll();
		return list;
	}
	@ModelAttribute("industryListData")
	protected List<IndustryVO> referenceListData1(){
		List<IndustryVO> list = industrySvc.getAll();
		return list;
	}
	
	public BindingResult removeFieldError(ReqOrderVO reqOrderVO, BindingResult result, String removedFieldname) {
		List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
				.filter(fieldname -> !fieldname.getField().equals(removedFieldname))
				.collect(Collectors.toList());
		result = new BeanPropertyBindingResult(reqOrderVO, "reqOrderVO");
		for (FieldError fieldError : errorsListToKeep) {
			result.addError(fieldError);
		}
		return result;
	}
}
