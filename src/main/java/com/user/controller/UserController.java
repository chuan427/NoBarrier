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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	
	@GetMapping("/memberCen")
	public String memberCen(Model model, HttpServletRequest request) {
		// 检查会话中是否有登录的用户信息
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO) session.getAttribute("loggingInUser");

		if (userVO == null) {
			return "redirect:/front-end/testLogin"; // 如果使用者未登入，將其重定向到登入頁面
		}

		List<UserVO> list = userSvc.getOneStatUser(userVO);
		model.addAttribute("userListData", list);
		return "front-end/userinformation/memberCen";

	}
	

	@GetMapping("/memberCen1")
	public String memberCen1(Model model, HttpServletRequest request) {
		// 检查会话中是否有登录的用户信息
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO) session.getAttribute("loggingInUser");

		
		//沒找到符合用戶的情形，請他離開
		if (userVO == null) {
			return "redirect:/front-end/testLogin"; // 如果使用者未登入，將其重定向到登入頁面
		}
	
		//拿出登入用戶的資訊，並放進Model送到更新頁面做渲染
		model.addAttribute("userVO", userVO);
		return "front-end/userinformation/memberCen1";
	}

	

	@GetMapping("/memberCen2")
	public String memberCen2(Model model, HttpServletRequest request) {
		// 检查会话中是否有登录的用户信息
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO) session.getAttribute("loggingInUser");

		if (userVO == null) {
			return "redirect:/front-end/testLogin"; // 如果使用者未登入，將其重定向到登入頁面
		}

		List<UserVO> list = userSvc.getOneStatUser(userVO);
		model.addAttribute("userListData", list);
		return "front-end/userinformation/memberCen2";
	}

	// 先把register1的值保存到model中
	@PostMapping("storeRegister1Data")
	public String storeRegister1Data(@ModelAttribute("userVO") @Valid UserVO userVO, ModelMap model,
			BindingResult result) throws IOException {

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
		/**************************** 2.把輸入的資料儲存進model跳轉到register2 *******************/

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
		MailService.sendMail(to, subject, messageText);

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
		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
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
	} // 我覺得上面可能不該用redirect，用forward可能比較好，register3如果是呼叫update他也要知道是要更新哪一筆，forward可以知道是哪個使用者就可以知道更新哪個人的comIndustry

	/*
	 * This method will be called on listAllEmp.html form submission, handling POST
	 * request
	 */
	@PostMapping("getOne_For_Update_user")
	public String getOne_For_Update(@RequestParam("userId") String userId, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始查詢資料 *****************************************/
		// EmpService empSvc = new EmpService();
		UserVO userVO = userSvc.getOneUser(Integer.valueOf(userId));

		/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
		model.addAttribute("userVO", userVO);
		return "front-end/userinformation/memberCen2"; // 查詢完成後轉交update_user_input.html
	}

	/*
	 * This method will be called on update_user_input.html form submission,
	 * handling POST request It also validates the user input
	 */
	@PostMapping("updateUser")
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
			return "front-end/userinformation/memberCen2";
		}
		/*************************** 2.開始修改資料 *****************************************/
		// EmpService empSvc = new EmpService();
		userSvc.updateUser(userVO);

		/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
		model.addAttribute("success", "- (修改成功)");
		userVO = userSvc.getOneUser(Integer.valueOf(userVO.getUserId()));
		model.addAttribute("userVO", userVO);
		return "front-end/userinformation/memberCen2"; // 修改成功後轉交listOneUser.html
	}

	
	
	//=====================updateBankInfo===============================
	@PostMapping("/updateBankInfo")
	public String updateBankInfo(@Valid UserVO userVO, BindingResult result, ModelMap model ) throws IOException {

		
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
		result = removeFieldError(userVO, result, "userId");

		if (result.hasErrors()) {
			return "front-end/userinformation/memberCen1";
		}
		/*************************** 2.開始修改資料 *****************************************/
		// EmpService empSvc = new EmpService();
		userSvc.updateUser(userVO);

		/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
		model.addAttribute("success", "- (修改成功)");
		userVO = userSvc.getOneUser(Integer.valueOf(userVO.getUserId()));
		model.addAttribute("userVO", userVO);
		return "front-end/userinformation/memberCen"; // 修改成功後轉交listOneEmp.html
	}
	
	
	//=====================changePassword===============================
	@PostMapping("/changePassword")
	public String changePassword(HttpSession session, @RequestParam("comPassword") String oldPassword,
			@RequestParam("newPassword") String newPassword) {

		UserVO userVO = (UserVO) session.getAttribute("loggingInUser");// 假设用户已登录并存在session中

		if (userVO == null) {
			return "redirect:/login"; // 用户未登录，重定向到登录页面
		}
 
		//如果輸入的舊密碼比對成功，進行新密碼的資料庫更新
		if(oldPassword == userVO.getComPassword()) {
			userVO.setComPassword(newPassword);
			userSvc.updateUser(userVO);
			return "front-end/userinformation/memberCen";
		}
		else {
			return "front-end/userinformation/memberCen2";
		}
		
	}

	

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