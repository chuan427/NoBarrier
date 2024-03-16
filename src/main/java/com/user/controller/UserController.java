package com.user.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.industry.model.IndustryService;
import com.quo.model.QuoService;
import com.quo.model.QuoVO;
import com.reqorder.model.ReqOrderService;
import com.security.model.MailService;
import com.security.model.RandomPasswordGenerator;
//import com.dept.model.DeptVO;
import com.user.model.UserService;
import com.user.model.UserVO;
//import com.dept.model.DeptService;

@Controller

@RequestMapping("/userinformation")

public class UserController {

	private final PasswordEncoder passwordEncoder;
	
	@Autowired
    public UserController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
	
	@Autowired
	UserService userSvc;

	@Autowired
	IndustryService industrySvc;

	@Autowired
	ReqOrderService reqOrderSvc;

	@Autowired
	QuoService quoSvc;
	
	

	/*
	 * This method will serve as addEmp.html handler.
	 */
//	@GetMapping("/memberCen1")
//	public String addUser(ModelMap model) {
//		UserVO userVO = new UserVO();
//		model.addAttribute("userVO", userVO);
//		return "front-end/userinformation/memberCen1";
//	}
	
//	@GetMapping("/memberCen1")
//	public String memberCen1(ModelMap model) {
//	    List<UserVO> userListData = userSvc.getAll(); // 假设这是您获取用户数据的方法
//	    model.addAttribute("userListData", userListData);
//	    return "front-end/userinformation/memberCen1"; // 返回到您的模板页面
//	}
	
	// 先把register1的值保存到model中
	@PostMapping("storeRegister1Data")
	public String storeRegister1Data(@ModelAttribute("userVO")@Valid UserVO userVO, ModelMap model, BindingResult result)
			throws IOException {

		model.addAttribute("userVO", userVO);// 儲存錯誤的值以免使用者還要再輸入一次

		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		if (userSvc.userIsExist(userVO.getComAccount())) {

			result.rejectValue("comCccount", "error.userVO", "此帳號已存在");

			return "front-end/userinformation/register1";
		}

		if (userSvc.userIsExistByUni(userVO.getComUniNumber())) {

			result.rejectValue("comUniNumber", "error.userVO", "此公司已註冊");

			return "front-end/userinformation/register1";
		}

		if (result.hasErrors()) {
			return "front-end/userinformation/register1";
		}

		System.out.println("comStat: " + userVO.getComStat());
		/**************************** 2.把輸入的資料儲存進model跳轉到register2*******************/

		return "front-end/userinformation/register2"; // 改用forward不然好麻煩
	}

	@PostMapping("sendVerificationCode")
	@ResponseBody
	public ResponseEntity<String> sendVerificationCode(@RequestBody Map<String, String> data) {
		// 把mail拿出來
		String verificationCode = RandomPasswordGenerator.generateRandomPassword();
		
		String to = data.get("email");
		String subject = "NoBarrier平台-註冊驗證碼";
		String messageText = "這是您的驗證碼:" + verificationCode + "\n" + "在註冊頁面輸入驗證碼。";
		MailService.sendMail(to,subject,messageText);

		// 返回響應给前端
		return ResponseEntity.ok("{\"verificationCode\": \"" + verificationCode + "\"}");
	}

	/*
	 * This method will be called on addEmp.html form submission, handling POST
	 * request It also validates the user input
	 */

	@PostMapping("insertUser")
	public String insert(@Valid UserVO userVO, BindingResult result, ModelMap model,
			@RequestParam("comAboutImage") MultipartFile[] parts) throws IOException {
		
//	@PostMapping("insert")
//	public String insert(@ModelAttribute("userVO") UserVO userVO, ModelMap model) throws IOException {

		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		 //去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
		result = removeFieldError(userVO, result, "comAboutImage");

		if (parts[0].isEmpty()) { // 使用者未選擇要上傳的圖片時
			model.addAttribute("errorMessage", "關於我們圖片: 請上傳照片");
		} else {
			for (MultipartFile multipartFile : parts) {
				byte[] buf = multipartFile.getBytes();
				userVO.setComAboutImage(buf);
			}
		}
		if (result.hasErrors() || parts[0].isEmpty()) {
			return "front-end/userinformation/memberCen";
		}

		String password = userVO.getComPassword();
		String encodeNewPassword = passwordEncoder.encode(password);
		
		userVO.setComPassword(encodeNewPassword);

		/*************************** 2.開始新增資料 *****************************************/
		// EmpService empSvc = new EmpService();
		userSvc.addUser(userVO);
		
		/*************************** 3.新增完成,準備轉交(Send the Success view) **************/
		List<UserVO> list = userSvc.getAll();
		model.addAttribute("userListData", list);
		model.addAttribute("success", "- (新增成功)");

//		return "redirect:/userinformation/memberCen"; // 新增成功後重導至IndexController_inSpringBoot.java的第50行@GetMapping("/user/listAllUser")
//	}

		return "redirect:/userinformation/register3"; // 新增成功後重導至IndexController_inSpringBoot.java的第50行@GetMapping("/user/listAllUser")
	}	//我覺得上面可能不該用redirect，用forward可能比較好，register3如果是呼叫update他也要知道是要更新哪一筆，forward可以知道是哪個使用者就可以知道更新哪個人的comIndustry


	/*
	 * This method will be called on listAllEmp.html form submission, handling POST
	 * request
	 */

	@PostMapping("getOne_For_Update")
	public String getOne_For_Update(@RequestParam("userId") String userId, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始查詢資料 *****************************************/
		// EmpService empSvc = new EmpService();
		UserVO userVO = userSvc.getOneUser(Integer.valueOf(userId));

		/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
		model.addAttribute("userVO", userVO);
		return "front-end/com/editmember_user"; // 查詢完成後轉交update_user_input.html
	}

	@PostMapping("update")
	public String update(@Valid UserVO userVO, BindingResult result, ModelMap model,
			@RequestParam("comAboutImage") MultipartFile[] parts) throws IOException {

		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
		result = removeFieldError(userVO, result, "comAboutImage");

		if (parts[0].isEmpty()) { // 使用者未選擇要上傳的新圖片時
			// EmpService empSvc = new EmpService();
			byte[] comAboutImage = userSvc.getOneUser(userVO.getUserId()).getComAboutImage();
			userVO.setComAboutImage(comAboutImage);

		} else {
			for (MultipartFile multipartFile : parts) {
				byte[] comAboutImage = multipartFile.getBytes();
				userVO.setComAboutImage(comAboutImage);
			}
		}
		if (result.hasErrors()) {
			return "back-end/user/update_user_input";
		}
		/*************************** 2.開始修改資料 *****************************************/
		// EmpService empSvc = new EmpService();
		userSvc.updateUser(userVO);

		/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
		model.addAttribute("success", "- (修改成功)");
		userVO = userSvc.getOneUser(Integer.valueOf(userVO.getUserId()));
		model.addAttribute("userVO", userVO);
		return "front-end/com/listOneUser"; // 修改成功後轉交listOneUser.html
	}
//	==========================================================================
//	@PostMapping("updateUser")
//	public String updateUser(@Valid UserVO userVO,BindingResult result, ModelMap model,HttpServletRequest request) throws IOException {
//		result = removeFieldError(userVO, result, null);
//		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
//		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
//		if (result.hasErrors()) {
//			System.out.println("跳錯了");
//			return "front-end/com/editmember_user";
//		}
//		
//		UserVO userVO1 = userSvc.getOneUser(Integer.valueOf(userVO.getUserId()));
//		userVO1.setComAddress(userVO.getComAddress());
//		userVO1.setComPhone(userVO.getComPhone());
//		userVO1.setComMail(userVO.getComMail());
//		/*************************** 2.開始修改資料 *****************************************/
//		// EmpService empSvc = new EmpService();
//		userSvc.updateUser(userVO1);
//		// 重新获取更新后的用户信息并将其存储到会话中
//		UserVO updatedUser = userSvc.getOneUser(Integer.valueOf(userVO.getUserId()));
//		request.getSession().setAttribute("loggingInUser", updatedUser);
//		/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
//		model.addAttribute("success", "- (修改成功)");
//		model.addAttribute("userVO", updatedUser);
//		return "front-end/com/editmember_user_view"; // 修改成功後轉交listOneUser.html
//	}
	
	//修改關於我頁面====================================================================================
	@PostMapping("updateAboutUs")
	public String updateAboutUs(@Valid UserVO userVO, BindingResult result, ModelMap model,HttpServletRequest request, @RequestParam("comAboutImage") MultipartFile[] parts) throws IOException {
	    // 接收請求參數 - 輸入格式的錯誤處理
	    result = removeFieldError(userVO, result, "comAboutImage");
	    
	    // 检查是否有上传的新图片
	    if (!parts[0].isEmpty()) {
	        byte[] comAboutImage = parts[0].getBytes();
	        userVO.setComAboutImage(comAboutImage);
	    } else {
	        // 如果用户未选择上传新图片，则保留原有图片
	        byte[] existingImage = userSvc.getOneUser(userVO.getUserId()).getComAboutImage();
	        userVO.setComAboutImage(existingImage);
	    }
	    
	    // 如果存在输入格式错误，返回编辑页面
//	    if (result.hasErrors()) {
//	    	  System.out.println("老鐵666");
//	        return "front-end/com/editmember_aboutus";
//	    }
	    
	    // 执行修改数据的逻辑
	    userSvc.updateUser(userVO);
	    UserVO updateAboutUs = userSvc.getOneUser(Integer.valueOf(userVO.getUserId()));
		request.getSession().setAttribute("loggingInUser", updateAboutUs);

	    // 修改完成，准备转交
	    model.addAttribute("success", "- (修改成功)");
	    userVO = userSvc.getOneUser(Integer.valueOf(userVO.getUserId()));
	    model.addAttribute("userVO", userVO);
	    return "front-end/com/editmember_aboutus_view";
	}

//	=======================================廠商資料update====================

	/*
	 * This method will be called on listAllUser.html form submission, handling POST
	 * request
	 */
//	@PostMapping("delete")
//	public String delete(@RequestParam("userId") String userId, ModelMap model) {
//		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
//		/*************************** 2.開始刪除資料 *****************************************/
//		// EmpService empSvc = new EmpService();
//		userSvc.deleteUser(Integer.valueOf(userId));
//		/*************************** 3.刪除完成,準備轉交(Send the Success view) **************/
//		List<UserVO> list = userSvc.getAll();
//		model.addAttribute("userListData", list);
//		model.addAttribute("success", "- (刪除成功)");
//		return "back-end/user/listAllUser"; // 刪除完成後轉交listAllUser.html
//	}

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
	public BindingResult removeFieldError(UserVO userVO, BindingResult result, String removedFieldname) {
		List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
				.filter(fieldname -> !fieldname.getField().equals(removedFieldname)).collect(Collectors.toList());
		result = new BeanPropertyBindingResult(userVO, "userVO");
		for (FieldError fieldError : errorsListToKeep) {
			result.addError(fieldError);
		}
		return result;
	}

}