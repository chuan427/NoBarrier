package com.limitsale.controller;

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

import com.limitsale.model.LimitSaleService;
import com.limitsale.model.LimitSaleVO;
import com.order.model.OrderService;

@Controller
@RequestMapping("/limitSale")
public class LimitSaleController {

	@Autowired
	LimitSaleService limitSaleSvc;

	@Autowired
	OrderService orderSvc;

	/*
	 * This method will serve as addEmp.html handler.
	 */
	@GetMapping("addLimitSale")
	public String addLimitSale(ModelMap model) {
		LimitSaleVO limitSaleVO = new LimitSaleVO();
		model.addAttribute("limitSaleVO", limitSaleVO);
		return "back-end/limitSale/addLimitSale";
	}

	/*
	 * This method will be called on addEmp.html form submission, handling POST request It also validates the user input
	 */
	@PostMapping("insert")
	public String insert(@Valid LimitSaleVO limitSaleVO, BindingResult result, ModelMap model,
			@RequestParam("limImage") MultipartFile[] parts) throws IOException {

		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
		result = removeFieldError(limitSaleVO, result, "limImage");

		if (parts[0].isEmpty()) { // 使用者未選擇要上傳的圖片時
			model.addAttribute("errorMessage", "關於我們圖片: 請上傳照片");
		} else {
			for (MultipartFile multipartFile : parts) {
				byte[] buf = multipartFile.getBytes();
				limitSaleVO.setLimImage(buf);
			}
		}
		if (result.hasErrors() || parts[0].isEmpty()) {
			return "back-end/limitSale/addLimitSale";
		}
		/*************************** 2.開始新增資料 *****************************************/
		// EmpService empSvc = new EmpService();
		limitSaleSvc.addLimitSale(limitSaleVO);
		/*************************** 3.新增完成,準備轉交(Send the Success view) **************/
		List<LimitSaleVO> list = limitSaleSvc.getAll();
		model.addAttribute("limitSaleListData", list);
		model.addAttribute("success", "- (新增成功)");
		return "redirect:/limitSale/listAllLimitSale"; // 新增成功後重導至IndexController_inSpringBoot.java的第50行@GetMapping("/user/listAllUser")
	}

	/*
	 * This method will be called on listAllEmp.html form submission, handling POST request
	 */
	@PostMapping("getOne_For_Update")
	public String getOne_For_Update(@RequestParam("limNum") String limNum, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始查詢資料 *****************************************/
		// EmpService empSvc = new EmpService();
		LimitSaleVO limitSaleVO = limitSaleSvc.getOneLimitSale(Integer.valueOf(limNum));

		/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
		model.addAttribute("limitSaleVO", limitSaleVO);
		return "back-end/limitSale/update_LimitSale_input"; // 查詢完成後轉交update_user_input.html
	}

	/*
	 * This method will be called on update_user_input.html form submission, handling POST request It also validates the user input
	 */
	@PostMapping("update")
	public String update(@Valid LimitSaleVO limitSaleVO, BindingResult result, ModelMap model,
			@RequestParam("limImage") MultipartFile[] parts) throws IOException {

		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
		result = removeFieldError(limitSaleVO, result, "limImage");

		if (parts[0].isEmpty()) { // 使用者未選擇要上傳的新圖片時
			// EmpService empSvc = new EmpService();
			byte[] limImag = limitSaleSvc.getOneLimitSale(limitSaleVO.getLimNum()).getLimImage();
			limitSaleVO.setLimImage(limImag);
	
		} else {
			for (MultipartFile multipartFile : parts) {
				byte[] limImag = multipartFile.getBytes();
				limitSaleVO.setLimImage(limImag);
			}
		}
		if (result.hasErrors()) {
			return "back-end/limitSale/update_LimitSale_input";
		}
		/*************************** 2.開始修改資料 *****************************************/
		// EmpService empSvc = new EmpService();
		limitSaleSvc.updateLimitSale(limitSaleVO);

		/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
		model.addAttribute("success", "- (修改成功)");
		limitSaleVO = limitSaleSvc.getOneLimitSale(Integer.valueOf(limitSaleVO.getLimNum()));
		model.addAttribute("limitSaleVO", limitSaleVO);
		return "back-end/limitSale/listOneLimitSale"; // 修改成功後轉交listOneUser.html
	}

	/*
	 * This method will be called on listAllUser.html form submission, handling POST request
	 */
	@PostMapping("delete")
	public String delete(@RequestParam("limNum") String limNum, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始刪除資料 *****************************************/
		// EmpService empSvc = new EmpService();
		limitSaleSvc.deleteLimitSale(Integer.valueOf(limNum));
		/*************************** 3.刪除完成,準備轉交(Send the Success view) **************/
		List<LimitSaleVO> list = limitSaleSvc.getAll();
		model.addAttribute("limitSaleListData", list);
		model.addAttribute("success", "- (刪除成功)");
		return "back-end/limitSale/listAllLimitSale"; // 刪除完成後轉交listAllUser.html
	}

	/*
	 * 第一種作法 Method used to populate the List Data in view. 如 : 
	 * <form:select path="deptno" id="deptno" items="${deptListData}" itemValue="deptno" itemLabel="dname" />
	 */
//	@ModelAttribute("deptListData")
//	protected List<DeptVO> referenceListData() {
//		// DeptService deptSvc = new DeptService();
//		List<DeptVO> list = deptSvc.getAll();
//		return list;
//	}

	/*
	 * 【 第二種作法 】 Method used to populate the Map Data in view. 如 : 
	 * <form:select path="deptno" id="deptno" items="${depMapData}" />
	 */
//	@ModelAttribute("deptMapData") //
//	protected Map<Integer, String> referenceMapData() {
//		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
//		map.put(10, "財務部");
//		map.put(20, "研發部");
//		map.put(30, "業務部");
//		map.put(40, "生管部");
//		return map;
//	}

	// 去除BindingResult中某個欄位的FieldError紀錄
	public BindingResult removeFieldError(LimitSaleVO limitSaleVO, BindingResult result, String removedFieldname) {
		List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
				.filter(fieldname -> !fieldname.getField().equals(removedFieldname))
				.collect(Collectors.toList());
		result = new BeanPropertyBindingResult(limitSaleVO, "limitSaleVO");
		for (FieldError fieldError : errorsListToKeep) {
			result.addError(fieldError);
		}
		return result;
	}

}