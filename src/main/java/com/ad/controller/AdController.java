package com.ad.controller;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ad.model.AdService;
import com.ad.model.AdVO;
import com.addday.AdDate;
import com.advertisements.model.AdvertisementsService;
import com.advertisements.model.AdvertisementsVO;
import com.user.model.UserVO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/ad")
public class AdController {

	@Autowired
	AdService adSvc;

	@Autowired
	AdvertisementsService advertisementsSvc;

	/*
	 * This method will serve as addEmp.html handler.
	 */
//	@GetMapping("addEmp")
//	public String addEmp(ModelMap model) {
//		EmpVO empVO = new EmpVO();
//		model.addAttribute("empVO", empVO);
//		return "back-end/emp/addEmp";
//	}

	/*
	 * This method will be called on addEmp.html form submission, handling POST
	 * request It also validates the user input
	 */
	@PostMapping("insert")
	public String insert(@ModelAttribute AdDate addate, BindingResult result, ModelMap model,
			@RequestParam("adImageadd") MultipartFile[] parts,HttpServletRequest request) throws IOException {
		// 去除BindingResult中upFiles欄位的FieldError紀錄
	    result = removeFieldError(addate, result, "adImageadd");
//	    result = removeFieldError(addate, result, "adPriceadd");
		 UserVO userVO = (UserVO)request.getSession().getAttribute("loggingInUser");

//
		if (parts[0].isEmpty()) {
			model.addAttribute("errorMessage", "廣告圖片: 請上傳照片");
		} else {
			for (MultipartFile multipartFile : parts) {
				byte[] buf = multipartFile.getBytes();
				addate.setAdImageadd(buf);
			}
		}
		if (addate.getAdPriceadd() == null)
			model.addAttribute("errorMessage1", "廣告金額: 請輸入金額");

		Set<Date> uniqueAdsDays = new LinkedHashSet<>();
		for (AdvertisementsVO adv : addate.getAdvertisements()) {
		    if (adv.getAdsDays() == null || !uniqueAdsDays.add(adv.getAdsDays())) {
		        model.addAttribute("errorMessage2",
		                adv.getAdsDays() == null ? "廣告日期: 請選擇日期" : "廣告日期重複: " + adv.getAdsDays() + "請重新選取");
		        return "back-end/ad/addEmp";
		    }
		}

		if (parts[0].isEmpty() || addate.getAdPriceadd() == null) {
			return "back-end/ad/addEmp";
		}

		// 开始新增数据
		adSvc.addAdAndAdvertisements(addate,userVO);
		model.addAttribute("success","訂單成功新增");

		// 新增完成，准备重定向到成功页面
//		return  "redirect:/ad/listAllEmp";
		return  "back-end/ad/addEmp";

	}

	/*
	 * This method will be called on listAllEmp.html form submission, handling POST
	 * request
	 */
	@PostMapping("getOne_For_Update")
	public String getOne_For_Update(@RequestParam("adOrdernum") String adOrdernum, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始查詢資料 *****************************************/
		// EmpService adSvc = new EmpService();
		AdVO adVO = adSvc.getOneEmp(Integer.valueOf(adOrdernum));

		/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
		model.addAttribute("adVO", adVO);
		return "back-end/ad/update_emp_input"; // 查詢完成後轉交update_emp_input.html
	}

	/*
	 * This method will be called on update_emp_input.html form submission, handling
	 * POST request It also validates the user input
	 */
	@PostMapping("update")
	public String update(@Valid AdVO adVO, BindingResult result, ModelMap model,
			@RequestParam("adImage") MultipartFile[] parts) throws IOException {

		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
		result = removeFieldError(adVO, result, "adImage");

		if (parts[0].isEmpty()) { // 使用者未選擇要上傳的新圖片時
			// EmpService adSvc = new EmpService();
			byte[] ad_images = adSvc.getOneEmp(adVO.getAdOrdernum()).getAdImage();
			adVO.setAdImage(ad_images);
		} else {
			for (MultipartFile multipartFile : parts) {
				byte[] ad_images = multipartFile.getBytes();
				adVO.setAdImage(ad_images);
			}
		}
		if (result.hasErrors()) {
			return "back-end/ad/update_emp_input";
		}
		/*************************** 2.開始修改資料 *****************************************/
		// EmpService adSvc = new EmpService();
		adSvc.updateEmp(adVO);

		/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
		model.addAttribute("success", "- (修改成功)");
		adVO = adSvc.getOneEmp(Integer.valueOf(adVO.getAdOrdernum()));
		model.addAttribute("adVO", adVO);
		return "back-end/ad/listOneEmp"; // 修改成功後轉交listOneEmp.html
	}

	/*
	 * This method will be called on listAllEmp.html form submission, handling POST
	 * request
	 */
	@PostMapping("delete")
	public String delete(@RequestParam("adOrdernum") String adOrdernum, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始刪除資料 *****************************************/
		// EmpService adSvc = new EmpService();
		adSvc.deleteEmp(Integer.valueOf(adOrdernum));
		/*************************** 3.刪除完成,準備轉交(Send the Success view) **************/
		List<AdVO> list = adSvc.getAll();
		model.addAttribute("adListData", list);
		model.addAttribute("success", "- (刪除成功)");
		return "back-end/ad/listAllEmp"; // 刪除完成後轉交listAllEmp.html
	}

	/*
	 * 第一種作法 Method used to populate the List Data in view. 如 : <form:select
	 * path="deptno" id="deptno" items="${deptListData}" itemValue="deptno"
	 * itemLabel="dname" />
	 */
//	@ModelAttribute("deptListData")
//	protected List<DeptVO> referenceListData() {
//		// DeptService deptSvc = new DeptService();
//		List<DeptVO> list = deptSvc.getAll();
//		return list;
//	}

	/*
	 * 【 第二種作法 】 Method used to populate the Map Data in view. 如 : <form:select
	 * path="deptno" id="deptno" items="${depMapData}" />
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
	public BindingResult removeFieldError(AdVO adVO, BindingResult result, String removedFieldname) {
		List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
				.filter(fieldname -> !fieldname.getField().equals(removedFieldname)).collect(Collectors.toList());
		result = new BeanPropertyBindingResult(adVO, "adVO");
		for (FieldError fieldError : errorsListToKeep) {
			result.addError(fieldError);
		}
		return result;
	}

	public BindingResult removeFieldError(AdDate addate, BindingResult result, String removedFieldname) {
		List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
				.filter(fieldname -> !fieldname.getField().equals(removedFieldname)).collect(Collectors.toList());
		result = new BeanPropertyBindingResult(addate, "addate");
		for (FieldError fieldError : errorsListToKeep) {
			result.addError(fieldError);
		}
		return result;
	}

}