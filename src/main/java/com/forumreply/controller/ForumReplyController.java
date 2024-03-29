package com.forumreply.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
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

import com.forumpost.model.ForumPostService;
import com.forumpost.model.ForumPostVO;
import com.forumreply.model.ForumReplyService;
import com.forumreply.model.ForumReplyVO;
import com.forumreport.model.ForumReportService;
import com.user.model.UserService;
import com.user.model.UserVO;

@Controller
@RequestMapping("/forumReply")
public class ForumReplyController {

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
	@GetMapping("addForumReply")
	public String addForumReply(ModelMap model) {
		ForumReplyVO forumReplyVO = new ForumReplyVO();
		model.addAttribute("forumReplyVO", forumReplyVO);
		return "front-end/forum/listOneForumPost";
	}

	/*
	 * This method will be called on addEmp.html form submission, handling POST request It also validates the user input
	 */
//	@PostMapping("insert")
//	public String insert(@Valid ForumReplyVO forumReplyVO, BindingResult result, ModelMap model,
//			@RequestParam("frImage") MultipartFile[] parts,@RequestParam("fpNum") Integer fpNum ,HttpServletRequest request) throws IOException {
//
//		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
//		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
//		result = removeFieldError(forumReplyVO, result, "frImage");
//		UserVO userVO = (UserVO)request.getSession().getAttribute("loggingInUser");
//
//		if (!parts[0].isEmpty()) { // 使用者選擇了要上傳的圖片
//			for (MultipartFile multipartFile : parts) {
//				byte[] buf = multipartFile.getBytes();
//				forumReplyVO.setFrImage(buf);
//			}
//		}
//		
//		if (result.hasErrors() || parts[0].isEmpty()) {
//			return "front-end/forum/listOneForumPost";
//		}
//		
//		// 設定發文者
//	    if (userVO != null) {
//	        forumReplyVO.setUserVO(userVO);
//	    }        
//	    
//	   
//		/*************************** 2.開始新增資料 *****************************************/
//		long currentTimeMillis = System.currentTimeMillis();
//		Timestamp currentTimestamp = new Timestamp(currentTimeMillis);
//		forumReplyVO.setFrTime(currentTimestamp);
//		forumReplyVO.setFrUpdate(currentTimestamp);
//		//新增資料
//		forumReplySvc.addForumReply(forumReplyVO);
//		/*************************** 3.新增完成,準備轉交(Send the Success view) **************/
//		List<ForumReplyVO> list = forumReplySvc.getAll();
//		model.addAttribute("forumReplyListData", list);
//		model.addAttribute("success", "- (新增成功)");
//		return "redirect:/forum/listOneForumPost/" +fpNum; // 新增成功後重導至IndexController_inSpringBoot.java的第50行@GetMapping("/user/listAllUser")
//	}
	
	
	
	@PostMapping("/insert")
	public String insert(@Valid ForumReplyVO forumReplyVO, BindingResult result, ModelMap model,
	                     @RequestParam("frImage") MultipartFile[] parts, @RequestParam("fpNum") Integer fpNum,
	                     HttpServletRequest request) throws IOException {

	    UserVO userVO = (UserVO) request.getSession().getAttribute("loggingInUser");
	    if (userVO == null) {
	        // 若使用者未登入，可重導至登入頁面或顯示錯誤訊息
	        return "redirect:/loginPage";
	    }

	    // 設定留言者
	    forumReplyVO.setUserVO(userVO);

	    // 根據fpNum獲取對應的ForumPostVO
	    ForumPostVO forumPostVO = forumPostSvc.getOneForumPost(fpNum);
	    if (forumPostVO == null) {
	        // 若找不到對應的論壇文章，可重導至錯誤頁面或顯示錯誤訊息
	        return "redirect:/errorPage";
	    }

	    // 處理圖片上傳
	    if (parts.length > 0 && !parts[0].isEmpty()) {
	        byte[] buf = parts[0].getBytes();
	        forumReplyVO.setFrImage(buf);
	    }

	    // 設定留言所屬的論壇帖子
	    forumReplyVO.setForumPostVO(forumPostVO);

	    // 設定留言時間
	    long currentTimeMillis = System.currentTimeMillis();
	    forumReplyVO.setFrTime(new Timestamp(currentTimeMillis));
	    forumReplyVO.setFrUpdate(new Timestamp(currentTimeMillis));

	    // 新增留言
	    forumReplySvc.addForumReply(forumReplyVO);

	    // 新增完成後，重導至該論壇文章的詳細頁面
	    return "redirect:/forum/listOneForumPost/" + fpNum;
	}



	/*
	 * This method will be called on listAllEmp.html form submission, handling POST request
	 */
	@PostMapping("getOne_For_Update")
	public String getOne_For_Update(@RequestParam("frNum") String frNum, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始查詢資料 *****************************************/
		// EmpService empSvc = new EmpService();
		ForumReplyVO forumReplyVO = forumReplySvc.getOneForumReply(Integer.valueOf(frNum));

		/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
		model.addAttribute("forumReplyVO", forumReplyVO);
		return "back-end/forumReply/update_ForumReply_input"; // 查詢完成後轉交update_user_input.html
	}

	/*
	 * This method will be called on update_user_input.html form submission, handling POST request It also validates the user input
	 */
	@PostMapping("update")
	public String update(@Valid ForumReplyVO forumReplyVO, BindingResult result, ModelMap model,
			@RequestParam("frImage") MultipartFile[] parts) throws IOException {

		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
		result = removeFieldError(forumReplyVO, result, "frImage");

		if (parts[0].isEmpty()) { // 使用者未選擇要上傳的新圖片時
			// EmpService empSvc = new EmpService();
			byte[] frImage = forumReplySvc.getOneForumReply(forumReplyVO.getFrNum()).getFrImage();
			forumReplyVO.setFrImage(frImage);
	
		} else {
			for (MultipartFile multipartFile : parts) {
				byte[] frImage = multipartFile.getBytes();
				forumReplyVO.setFrImage(frImage);
			}
		}
		if (result.hasErrors()) {
			return "back-end/forumReply/update_ForumReply_input";
		}
		/*************************** 2.開始修改資料 *****************************************/
		// EmpService empSvc = new EmpService();
//		ForumReplyVO originalTime = forumReplySvc.getOneForumReply(forumReplyVO.getFrNum());
//		forumReplyVO.setFrTime(originalTime.getFrTime());
		
		long currentTimeMillis = System.currentTimeMillis();
		Timestamp currentTimestamp = new Timestamp(currentTimeMillis);
		forumReplyVO.setFrUpdate(currentTimestamp);
		forumReplySvc.updateForumReply(forumReplyVO);
		/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
		model.addAttribute("success", "- (修改成功)");
		forumReplyVO = forumReplySvc.getOneForumReply(Integer.valueOf(forumReplyVO.getFrNum()));
		model.addAttribute("forumReplyVO", forumReplyVO);
		return "back-end/forumReply/listOneForumReply"; // 修改成功後轉交listOneUser.html
	}
	
	@ModelAttribute("userListData")
	protected List<UserVO> referenceListData1(){
		List<UserVO> list = userSvc.getAll();
		return list;
	}
	
	@ModelAttribute("forumPostListData")
	protected List<ForumPostVO> referenceListData(){
		List<ForumPostVO> list = forumPostSvc.getAll();
		return list;
	}
	

	
	
	
	/*
	 * This method will be called on listAllUser.html form submission, handling POST request
	 */
	@PostMapping("delete")
	public String delete(@RequestParam("frNum") String frNum, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始刪除資料 *****************************************/
		// EmpService empSvc = new EmpService();
		forumReplySvc.deleteForumReply(Integer.valueOf(frNum));
		/*************************** 3.刪除完成,準備轉交(Send the Success view) **************/
		List<ForumReplyVO> list = forumReplySvc.getAll();
		model.addAttribute("forumReplyListData", list);
		model.addAttribute("success", "- (刪除成功)");
		return "back-end/forumReply/listAllForumReply"; // 刪除完成後轉交listAllUser.html
	}


	// 去除BindingResult中某個欄位的FieldError紀錄
	public BindingResult removeFieldError(ForumReplyVO forumReplyVO, BindingResult result, String removedFieldname) {
		List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
				.filter(fieldname -> !fieldname.getField().equals(removedFieldname))
				.collect(Collectors.toList());
		result = new BeanPropertyBindingResult(forumReplyVO, "forumReplyVO");
		for (FieldError fieldError : errorsListToKeep) {
			result.addError(fieldError);
		}
		return result;
	}

}