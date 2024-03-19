//package com.rptdlist.controller;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import javax.validation.Valid;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.validation.BeanPropertyBindingResult;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import com.rptdlist.model.RptdlistService;
//import com.rptdlist.model.RptdlistVO;
//
//@Controller
//@RequestMapping("/rptdlist")
//public class RptdlistController {
//
//	@Autowired
//	RptdlistService rptdlistSvc;
//	
//	@GetMapping("addRptdlist")
//	public String addRptdlist(ModelMap model) {
//		RptdlistVO rptdlistVO = new RptdlistVO();
//		model.addAttribute("rptdlistVO", rptdlistVO);
//		return "back-end/rptdlist/addRptdlist";
//	}
//	
//	//-----------------------------------------------------------
//	
//	@PostMapping("insert")
//	public String insert(@Valid RptdlistVO rptdlistVO, BindingResult result, ModelMap model) throws IOException {
//
//		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
//		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
//		
//		
////		rptdlistVO.setRptdDate(new java.sql.Date(System.currentTimeMillis()));
//		rptdlistVO.setRptdStat(1);
//		rptdlistVO.setRptdIsValid(1);
//		
//		result = removeFieldError(rptdlistVO, result, "rptdNum");
//
//		if (result.hasErrors()) {
//			return "back-end/rptdlist/addRptdlist";
//		}
//		/*************************** 2.開始新增資料 *****************************************/
//		// EmpService empSvc = new EmpService();
//		rptdlistSvc.addRptdlist(rptdlistVO);
//		/*************************** 3.新增完成,準備轉交(Send the Success view) **************/
//		List<RptdlistVO> list = rptdlistSvc.getAll();
//		model.addAttribute("RptdlistListData", list);
//		model.addAttribute("success", "- (新增成功)");
//		return "redirect:/rptdlist/listAllRptdlist"; // 新增成功後重導至IndexController_inSpringBoot.java的第50行@GetMapping("/emp/listAllEmp")
//	}
//	
//	//-----------------------------------------------------------
//	
//	@PostMapping("getOne_For_Update")
//	public String getOne_For_Update(@RequestParam("rptdNum") String rptdNum, ModelMap model) {
//		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
//		/*************************** 2.開始查詢資料 *****************************************/
//		// EmpService empSvc = new EmpService();
//		RptdlistVO rptdlistVO = rptdlistSvc.getOneRptdlist(Integer.valueOf(rptdNum));
////		quoVO.setQuoDate(new java.sql.Date(System.currentTimeMillis()));
//
//		/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
//		model.addAttribute("rptdlistVO", rptdlistVO);
//		return "back-end/rptdlist/update_rptdlist_input"; // 查詢完成後轉交update_emp_input.html
//	}
//	
//	//-----------------------------------------------------------
//
//	@PostMapping("update")
//	public String update(@Valid RptdlistVO rptdlistVO, BindingResult result, ModelMap model ) throws IOException {
//
//		rptdlistVO.setRptdDate(rptdlistVO.getRptdDate());
//		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
//		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
//		result = removeFieldError(rptdlistVO, result, "rptdNum");
//
//		if (result.hasErrors()) {
//			return "back-end/rptdlist/update_rptdlist_input";
//		}
//		/*************************** 2.開始修改資料 *****************************************/
//		// EmpService empSvc = new EmpService();
//		rptdlistSvc.updateRptdlist(rptdlistVO);
//
//		/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
//		model.addAttribute("success", "- (修改成功)");
//		rptdlistVO = rptdlistSvc.getOneRptdlist(Integer.valueOf(rptdlistVO.getRptdNum()));
//		model.addAttribute("rptdlistVO", rptdlistVO);
//		return "back-end/rptdlist/listOneRptdlist"; // 修改成功後轉交listOneEmp.html
//	}
//	
//	//-----------------------------------------------------------
//
//	@PostMapping("delete")
//	public String delete(@RequestParam("rptdNum") String rptdNum, ModelMap model) {
//		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
//		/*************************** 2.開始刪除資料 *****************************************/
//		// EmpService empSvc = new EmpService();
//		rptdlistSvc.deleteRptdlist(Integer.valueOf(rptdNum));
//		/*************************** 3.刪除完成,準備轉交(Send the Success view) **************/
//		List<RptdlistVO> list = rptdlistSvc.getAll();
//		model.addAttribute("rptdlistListData", list);
//		model.addAttribute("success", "- (刪除成功)");
//		return "back-end/rptdlist/listAllRptdlist"; // 刪除完成後轉交listAllEmp.html
//	}
//	
//	//-----------------------------------------------------------
//
//	@ModelAttribute("rptdlistListData")
//	protected List<RptdlistVO> referenceListData() {
//		// DeptService deptSvc = new DeptService();
//		List<RptdlistVO> list = rptdlistSvc.getAll();
//		return list;
//	}
//	
//	//-----------------------------------------------------------
//	
//	public BindingResult removeFieldError(RptdlistVO rptdlistVO, BindingResult result, String removedFieldname) {
//		List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
//				.filter(fieldname -> !fieldname.getField().equals(removedFieldname))
//				.collect(Collectors.toList());
//		result = new BeanPropertyBindingResult(rptdlistVO, "rptdlistVO");
//		for (FieldError fieldError : errorsListToKeep) {
//			result.addError(fieldError);
//		}
//		return result;
//	}
//}
