package com.forumpost.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.forumreport.model.ForumReportService;
import com.user.model.UserService;
import com.user.model.UserVO;

@Controller
//@RequestMapping("/forumPost")
public class ForumPostController {

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

	@GetMapping("/forum/addForumPost")
	public String addForumPost(ModelMap model) {
		ForumPostVO forumPostVO = new ForumPostVO();
		model.addAttribute("forumPostVO", forumPostVO);
		return "front-end/forum/addForumPost";
	}

	/*
	 * This method will be called on addEmp.html form submission, handling POST
	 * request It also validates the user input
	 */
	@PostMapping("insert")
	public String insert(@Valid ForumPostVO forumPostVO, BindingResult result, ModelMap model,
			@RequestParam("fpImage") MultipartFile[] parts) throws IOException {

		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
		result = removeFieldError(forumPostVO, result, "fpImage");

		if (!parts[0].isEmpty()) { // 使用者選擇了要上傳的圖片
			for (MultipartFile multipartFile : parts) {
				byte[] buf = multipartFile.getBytes();
				forumPostVO.setFpImage(buf);
			}
		}
		// 即使沒有上傳圖片，也可以繼續進行其他處理流程
		if (result.hasErrors()) {
			return "front-end/forum/addForumPost";
		}
		/*************************** 2.開始新增資料 *****************************************/
		// EmpService empSvc = new EmpService();
		long currentTimeMillis = System.currentTimeMillis();
		Timestamp currentTimestamp = new Timestamp(currentTimeMillis);
		forumPostVO.setFpTime(currentTimestamp);
		forumPostVO.setFpUpdate(currentTimestamp);
		forumPostSvc.addForumPost(forumPostVO);
		/*************************** 3.新增完成,準備轉交(Send the Success view) **************/
		List<ForumPostVO> list = forumPostSvc.getAll();
		model.addAttribute("forumPostListData", list);
		model.addAttribute("success", "- (新增成功)");
		return "redirect:/forumPost/listAllForumPost"; // 新增成功後重導至IndexController_inSpringBoot.java的第50行@GetMapping("/user/listAllUser")
	}

	/*
	 * This method will be called on listAllEmp.html form submission, handling POST
	 * request
	 */
	@PostMapping("getOne_For_Update")
	public String getOne_For_Update(@RequestParam("fpNum") String fpNum, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始查詢資料 *****************************************/
		// EmpService empSvc = new EmpService();
		ForumPostVO forumPostVO = forumPostSvc.getOneForumPost(Integer.valueOf(fpNum));

		/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
		model.addAttribute("forumPostVO", forumPostVO);
		return "back-end/forumPost/update_ForumPost_input"; // 查詢完成後轉交update_user_input.html
	}

	/*
	 * This method will be called on update_user_input.html form submission,
	 * handling POST request It also validates the user input
	 */
	@PostMapping("update")
	public String update(@Valid ForumPostVO forumPostVO, BindingResult result, ModelMap model,
			@RequestParam("fpImage") MultipartFile[] parts) throws IOException {

		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
		result = removeFieldError(forumPostVO, result, "fpImage");

		if (parts[0].isEmpty()) { // 使用者未選擇要上傳的新圖片時
			// EmpService empSvc = new EmpService();
			byte[] fpImage = forumPostSvc.getOneForumPost(forumPostVO.getFpNum()).getFpImage();
			forumPostVO.setFpImage(fpImage);

		} else {
			for (MultipartFile multipartFile : parts) {
				byte[] fpImage = multipartFile.getBytes();
				forumPostVO.setFpImage(fpImage);

			}
		}
		if (result.hasErrors()) {
			return "back-end/forumPost/update_ForumPost_input";
		}
		/*************************** 2.開始修改資料 *****************************************/
//		EmpService empSvc = new EmpService();
//		ForumPostVO originalTime = forumPostSvc.getOneForumPost(forumPostVO.getFpNum());
//		forumPostVO.setFpTime(originalTime.getFpTime());
		long currentTimeMillis = System.currentTimeMillis();
		Timestamp currentTimestamp = new Timestamp(currentTimeMillis);
		forumPostVO.setFpUpdate(currentTimestamp);
		forumPostSvc.updateForumPost(forumPostVO);
		/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
		model.addAttribute("success", "- (修改成功)");
		forumPostVO = forumPostSvc.getOneForumPost(Integer.valueOf(forumPostVO.getFpNum()));
		model.addAttribute("forumPostVO", forumPostVO);
		return "back-end/forumPost/listOneForumPost"; // 修改成功後轉交listOneUser.html
	}

//	  @GetMapping("/search")
//	    public String search(@RequestParam("search") String search, Model model) {
//	        // 假設有一個service方法可以根據標題或使用者名進行模糊查詢
//	        List<ForumPostVO> searchResult = forumPostSvc.searchByTitleOrUser(search);
//	        model.addAttribute("posts", searchResult);
//	        return "front-end/forum/forumIndex"; // 返回到顯示搜尋結果的頁面
//	    }

//	@GetMapping("/index")
//	public String showLatestPost(Model model) {
//		ForumPostVO forumPostVO = new ForumPostVO();
//		forumPostVO = forumPostSvc.getLatestPost();
//		model.addAttribute("forumPostVO", forumPostVO);
//		return "front-end/forum/forumIndex"; 
//	}

	@ModelAttribute("userListData")
	protected List<UserVO> referenceListData() {
		List<UserVO> list = userSvc.getAll();
		return list;
	}

	/*
	 * This method will be called on listAllUser.html form submission, handling POST
	 * request
	 */

	@PostMapping("delete")
	public String delete(@RequestParam("fpNum") String fpNum, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始刪除資料 *****************************************/
		// EmpService empSvc = new EmpService();
		forumPostSvc.deleteForumPost(Integer.valueOf(fpNum));
		/*************************** 3.刪除完成,準備轉交(Send the Success view) **************/
		List<ForumPostVO> list = forumPostSvc.getAll();
		model.addAttribute("forumPostListData", list);
		model.addAttribute("success", "- (刪除成功)");
		return "back-end/forumPost/listAllForumPost"; // 刪除完成後轉交listAllUser.html
	}

	// 去除BindingResult中某個欄位的FieldError紀錄
	public BindingResult removeFieldError(ForumPostVO forumPostVO, BindingResult result, String removedFieldname) {
		List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
				.filter(fieldname -> !fieldname.getField().equals(removedFieldname)).collect(Collectors.toList());
		result = new BeanPropertyBindingResult(forumPostVO, "forumPostVO");
		for (FieldError fieldError : errorsListToKeep) {
			result.addError(fieldError);
		}
		return result;
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

}