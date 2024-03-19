package com.forumpost.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.data.domain.Sort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.forumpost.model.ForumPostRepository;
import com.forumpost.model.ForumPostService;
import com.forumpost.model.ForumPostVO;
import com.forumreply.model.ForumReplyService;
import com.forumreport.model.ForumReportService;
import com.user.model.UserService;
import com.user.model.UserVO;

@Controller
@RequestMapping("/forum")
public class ForumPostController {

	@Autowired
	UserService userSvc;

	@Autowired
	ForumPostService forumPostSvc;

	@Autowired
	ForumReplyService forumReplySvc;

	@Autowired
	ForumReportService forumReportSvc;
	
	@Autowired
	private ForumPostRepository forumPostRepository;

	/*
	 * This method will serve as addEmp.html handler.
	 */


	@GetMapping("/addForumPost")
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
			@RequestParam("fpImage") MultipartFile[] parts, HttpServletRequest request) throws IOException {

		// 接收請求參數 - 輸入格式的錯誤處理
		result = removeFieldError(forumPostVO, result, "fpImage");
		UserVO userVO = (UserVO) request.getSession().getAttribute("loggingInUser");

		if (!parts[0].isEmpty()) { // 使用者選擇了要上傳的圖片
			for (MultipartFile multipartFile : parts) {
				byte[] buf = multipartFile.getBytes();
				forumPostVO.setFpImage(buf);
			}
		}

		if (result.hasErrors()) {
			return "front-end/forum/addForumPost";
		}

		// 設定發文者
		if (userVO != null) {
			forumPostVO.setUserVO(userVO);
		}

		// 設定時間戳記
		long currentTimeMillis = System.currentTimeMillis();
		Timestamp currentTimestamp = new Timestamp(currentTimeMillis);
		forumPostVO.setFpTime(currentTimestamp);
		forumPostVO.setFpUpdate(currentTimestamp);

		// 開始新增資料
		forumPostSvc.addForumPost(forumPostVO);

		// 新增完成, 準備轉交(Send the Success view)
		List<ForumPostVO> list = forumPostSvc.getAll();
		model.addAttribute("forumPostListData", list);
		model.addAttribute("success", "- (新增成功)");

		return "redirect:/forum/forumIndex"; // 新增成功後重導至IndexController_inSpringBoot.java的第50行@GetMapping("/user/listAllUser")
	}

	/*
	 * This method will be called on listAllEmp.html form submission, handling POST
	 * request
	 */
	@GetMapping("getOne_For_Update")
	public String getOne_For_Update(@RequestParam("fpNum") String fpNum, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始查詢資料 *****************************************/
		// EmpService empSvc = new EmpService();
		ForumPostVO forumPostVO = forumPostSvc.getOneForumPost(Integer.valueOf(fpNum));

		/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
		model.addAttribute("forumPostVO", forumPostVO);
		return "front-end/forum/updateForumPost"; // 查詢完成後轉交update_user_input.html
	}

	@PostMapping("update")
	public String update(@Valid ForumPostVO forumPostVO, BindingResult result, ModelMap model,
			@RequestParam("fpImage") MultipartFile[] parts, RedirectAttributes redirectAttributes) throws IOException {

		result = removeFieldError(forumPostVO, result, "fpImage");
		// 檢查文章內容長度
		if (forumPostVO.getFpContent().trim().length() < 20) {
			result.rejectValue("fpContent", "error.fpContent", "文章內容不能少於20字");
		}

		// 檢查是否有其他驗證錯誤
		if (result.hasErrors()) {
			// 將有錯誤的forumPostVO和其他必要的model屬性添加到model中，以便返回到表單頁面時能夠顯示錯誤訊息和保留用戶輸入
			model.addAttribute("forumPostVO", forumPostVO);
			// 其他必要的model屬性...
			return "front-end/forum/updateForumPost";
		}

		// 處理圖片上傳
		if (!parts[0].isEmpty()) {
			byte[] buf = parts[0].getBytes();
			forumPostVO.setFpImage(buf);
		} else {
			byte[] existingImage = forumPostSvc.getOneForumPost(forumPostVO.getFpNum()).getFpImage();
			forumPostVO.setFpImage(existingImage);
		}

		// 更新文章
		long currentTimeMillis = System.currentTimeMillis();
		Timestamp currentTimestamp = new Timestamp(currentTimeMillis);
		forumPostVO.setFpUpdate(currentTimestamp);
		forumPostSvc.updateForumPost(forumPostVO);

		// 使用RedirectAttributes傳遞成功訊息
		redirectAttributes.addFlashAttribute("successMessage", "文章修改成功！");

		// 重定向到該文章的詳細頁面，確保你的URL模式和@GetMapping相匹配
		return "redirect:/forum/listOneForumPost/" + forumPostVO.getFpNum();
	}
	
	@GetMapping("/search")
	public String search(@RequestParam("search") String search, Model model,
	                     @RequestParam(defaultValue = "0") int page,
	                     @RequestParam(defaultValue = "10") int size) {
	    Page<ForumPostVO> searchResults = forumPostSvc.findByComNameOrFpTitleContaining(search, page, size);
	    model.addAttribute("searchResults", searchResults.getContent());
	    model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", searchResults.getTotalPages());
	    return "front-end/forum/searchForum"; // 指向顯示搜索結果的頁面
	}
	
	
	
	

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
		return "front-end/forum/forumIndex"; // 刪除完成後轉交listAllUser.html
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
	
	@PostMapping("getOne_For_Update_back")
	public String getOne_For_Update_back(@RequestParam("fpNum") String fpNum, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始查詢資料 *****************************************/
		// EmpService empSvc = new EmpService();
		ForumPostVO forumPostVO = forumPostSvc.getOneForumPost(Integer.valueOf(fpNum));

		/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
		model.addAttribute("forumPostVO", forumPostVO);
		return "back-end/forumPost/update_ForumPost_input"; // 查詢完成後轉交update_user_input.html
	}

	@PostMapping("update_back")
	public String update_back(@Valid ForumPostVO forumPostVO, BindingResult result, ModelMap model,
			@RequestParam("fpImage") MultipartFile[] parts, RedirectAttributes redirectAttributes) throws IOException {

		result = removeFieldError(forumPostVO, result, "fpImage");
		// 檢查文章內容長度
		if (forumPostVO.getFpContent().trim().length() < 20) {
			result.rejectValue("fpContent", "error.fpContent", "文章內容不能少於20字");
		}

		// 檢查是否有其他驗證錯誤
		if (result.hasErrors()) {
			// 將有錯誤的forumPostVO和其他必要的model屬性添加到model中，以便返回到表單頁面時能夠顯示錯誤訊息和保留用戶輸入
			model.addAttribute("forumPostVO", forumPostVO);
			// 其他必要的model屬性...
			return "back-end/forumPost/update_ForumPost_input";
		}

		// 處理圖片上傳
		if (!parts[0].isEmpty()) {
			byte[] buf = parts[0].getBytes();
			forumPostVO.setFpImage(buf);
		} else {
			byte[] existingImage = forumPostSvc.getOneForumPost(forumPostVO.getFpNum()).getFpImage();
			forumPostVO.setFpImage(existingImage);
		}

		// 更新文章
		long currentTimeMillis = System.currentTimeMillis();
		Timestamp currentTimestamp = new Timestamp(currentTimeMillis);
		forumPostVO.setFpUpdate(currentTimestamp);
		forumPostSvc.updateForumPost(forumPostVO);

		// 使用RedirectAttributes傳遞成功訊息
		redirectAttributes.addFlashAttribute("successMessage", "文章修改成功！");

		// 重定向到該文章的詳細頁面，確保你的URL模式和@GetMapping相匹配
		return "back-end/forumPost/listOneForumPost";
	}
}

/*
 * This method will be called on update_user_input.html form submission,
 * handling POST request It also validates the user input
 */
//@PostMapping("update")
//public String update(@Valid ForumPostVO forumPostVO, BindingResult result, ModelMap model,
//		@RequestParam("fpImage") MultipartFile[] parts) throws IOException {
//
//	/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
//	// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
//	result = removeFieldError(forumPostVO, result, "fpImage");
//
//	if (parts[0].isEmpty()) { // 使用者未選擇要上傳的新圖片時
//		// EmpService empSvc = new EmpService();
//		byte[] fpImage = forumPostSvc.getOneForumPost(forumPostVO.getFpNum()).getFpImage();
//		forumPostVO.setFpImage(fpImage);
//
//	} else {
//		for (MultipartFile multipartFile : parts) {
//			byte[] fpImage = multipartFile.getBytes();
//			forumPostVO.setFpImage(fpImage);
//
//		}
//	}
//	if (result.hasErrors()) {
//		return "front-end/forum/updateForumPost";
//	}
//	/*************************** 2.開始修改資料 *****************************************/
//	ForumPostVO originalTime = forumPostSvc.getOneForumPost(forumPostVO.getFpNum());
//	forumPostVO.setFpTime(originalTime.getFpTime());
//	long currentTimeMillis = System.currentTimeMillis();
//	Timestamp currentTimestamp = new Timestamp(currentTimeMillis);
//	forumPostVO.setFpUpdate(currentTimestamp);
//	forumPostSvc.updateForumPost(forumPostVO);
//	/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
//	model.addAttribute("success", "- (修改成功)");
//	forumPostVO = forumPostSvc.getOneForumPost(Integer.valueOf(forumPostVO.getFpNum()));
//	model.addAttribute("forumPostVO", forumPostVO);
//	return "front-end/forum/forumIndex"; // 修改成功後轉交listOneUser.html
//}
