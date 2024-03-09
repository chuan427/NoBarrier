package com.forumreport.controller;

import java.io.IOException;
import java.sql.Timestamp;
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

import com.forumpost.model.ForumPostService;
import com.forumpost.model.ForumPostVO;
import com.forumreply.model.ForumReplyService;
import com.forumreply.model.ForumReplyVO;
import com.forumreport.model.ForumReportService;
import com.forumreport.model.ForumReportVO;
import com.user.model.UserService;
import com.user.model.UserVO;

@Controller
@RequestMapping("/forumReport")
public class ForumReportController {

	@Autowired
	UserService userSvc;
	
	@Autowired
	ForumPostService forumPostSvc;
	
	@Autowired
	ForumReplyService forumReplySvc;
	
	@Autowired
	ForumReportService forumReportSvc;


	/*
	 * This method will serve as addEmp.html handler.
	 */
	@GetMapping("addForumReport")
	public String addNotification(ModelMap model) {
		ForumReportVO forumReportVO = new ForumReportVO();
		model.addAttribute("forumReportVO", forumReportVO);
		return "back-end/forumReport/addForumReport";
	}
	
	@PostMapping("insert")
	public String insert(@Valid ForumReportVO forumReportVO, BindingResult result, ModelMap model) throws IOException {
		
		if (result.hasErrors()) {
	        return "back-end/forumReport/addForumReport";
		}
		
		/*************************** 2.開始新增資料 *****************************************/
		long currentTimeMillis = System.currentTimeMillis();
		Timestamp currentTimestamp = new Timestamp(currentTimeMillis);
		forumReportVO.setFrpTime(currentTimestamp);
		forumReportVO.setFrpDealtime(currentTimestamp);
		forumReportSvc.addForumReport(forumReportVO);
		
		/*************************** 3.新增完成,準備轉交(Send the Success view) **************/
		List<ForumReportVO> list = forumReportSvc.getAll();
		model.addAttribute("forumReportListData", list);
		model.addAttribute("success", "- (新增成功)");
		return "redirect:/forumReport/listAllForumReport";
	}
	
	@PostMapping("getOne_For_Update")
	public String getOne_For_Update(@RequestParam("frpNum") String frpNum, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始查詢資料 *****************************************/
		// EmpService empSvc = new EmpService();
		ForumReportVO forumReportVO = forumReportSvc.getOneForumReport(Integer.valueOf(frpNum));
		/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
		model.addAttribute("forumReportVO", forumReportVO);
		return "back-end/forumReport/update_ForumReport_input"; // 查詢完成後轉交update_user_input.html
	}
	
	@PostMapping("update")
	public String update(@Valid ForumReportVO forumReportVO, BindingResult result, ModelMap model) throws IOException {

		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		
		if (result.hasErrors()) {
			return "back-end/forumReport/update_ForumReport_input";
		}
		
		/*************************** 2.開始修改資料 *****************************************/
		long currentTimeMillis = System.currentTimeMillis();
		Timestamp currentTimestamp = new Timestamp(currentTimeMillis);
		System.out.println("ID = " + forumReportVO.getFrpNum());
		forumReportVO.setFrpDealtime(currentTimestamp);
		forumReportSvc.updateForumReport(forumReportVO);
		
		/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
		model.addAttribute("success", "- (修改成功)");
		forumReportVO = forumReportSvc.getOneForumReport(Integer.valueOf(forumReportVO.getFrpNum()));
		model.addAttribute("forumReportVO", forumReportVO);
		return "back-end/forumReport/listOneForumReport"; // 修改成功後轉交listOneEmp.html
	}
	
	@ModelAttribute("userListData")
	protected List<UserVO> referenceListData(){
		List<UserVO> list = userSvc.getAll();
		return list;
	}
	
	@ModelAttribute("forumPostListData")
	protected List<ForumPostVO> referenceListData1(){
		List<ForumPostVO> list = forumPostSvc.getAll();
		return list;
	}
	
	@ModelAttribute("forumReplyListData")
	protected List<ForumReplyVO> referenceListData2(){
		List<ForumReplyVO> list = forumReplySvc.getAll();
		return list;
	}
	
	@PostMapping("delete")
	public String delete(@RequestParam("frpNum") String frpNum, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始刪除資料 *****************************************/
		// EmpService empSvc = new EmpService();
		forumReportSvc.deleteForumReport(Integer.valueOf(frpNum));
		/*************************** 3.刪除完成,準備轉交(Send the Success view) **************/
		List<ForumReportVO> list = forumReportSvc.getAll();
		model.addAttribute("forumReportListData", list);
		model.addAttribute("success", "- (刪除成功)");
		return "back-end/forumPost/listAllForumReport"; // 刪除完成後轉交listAllUser.html
	}
	
	
	// 去除BindingResult中某個欄位的FieldError紀錄
		public BindingResult removeFieldError(ForumPostVO forumReportVO, BindingResult result, String removedFieldname) {
			List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
					.filter(fieldname -> !fieldname.getField().equals(removedFieldname)).collect(Collectors.toList());
			result = new BeanPropertyBindingResult(forumReportVO, "forumReportVO");
			for (FieldError fieldError : errorsListToKeep) {
				result.addError(fieldError);
			}
			
			return result;
		}
	
}