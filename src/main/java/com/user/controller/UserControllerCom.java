package com.user.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.industry.model.IndustryService;
import com.quo.model.QuoService;
import com.reqorder.model.ReqOrderService;
import com.security.model.MailService;
import com.security.model.RandomPasswordGenerator;
//import com.dept.model.DeptVO;
import com.user.model.UserService;
import com.user.model.UserVO;
//import com.dept.model.DeptService;

@Controller

@RequestMapping("/com")

public class UserControllerCom {

	private final PasswordEncoder passwordEncoder;
	
	@Autowired
    public UserControllerCom(PasswordEncoder passwordEncoder) {
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

//	==========================================================================================


//	@PostMapping("getOne_For_Update")
//	public String getOne_For_Update(@RequestParam("userId") String userId, ModelMap model) {
//		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
//		/*************************** 2.開始查詢資料 *****************************************/
//		// EmpService empSvc = new EmpService();
//		UserVO userVO = userSvc.getOneUser(Integer.valueOf(userId));
//
//		/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
//		model.addAttribute("userVO", userVO);
//		return "front-end/com/editmember_user"; // 查詢完成後轉交update_user_input.html
//	}

//	=======================================廠商編輯update====================
	@PostMapping("updateAboutUs")
	public String update(@Valid UserVO userVO, BindingResult result, HttpServletRequest request, ModelMap model, 
			@RequestParam("comAboutImage") MultipartFile part,
			@RequestParam(value = "comImage1", required = false) MultipartFile comImage1,
            @RequestParam(value = "comImage2", required = false) MultipartFile comImage2,
            @RequestParam(value = "comImage3", required = false) MultipartFile comImage3,
            @RequestParam(value = "comImage4", required = false) MultipartFile comImage4) throws IOException {
		
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
		result = removeFieldError(userVO, result, "comAboutImage");
		UserVO updatedUserVO = new UserVO();
	    updatedUserVO.setComAboutImage(userVO.getComAboutImage());
		// 检查是否有上传的新图片
		 if (!part.isEmpty()) {
		        byte[] comAboutImage = part.getBytes();
		        userVO.setComAboutImage(comAboutImage);
		    } else {
		        // 如果用户未选择上传新图片，则保留原有图片
		        byte[] existingImage = userSvc.getOneUser(userVO.getUserId()).getComAboutImage();
		        userVO.setComAboutImage(existingImage);
		    }
		 // 处理 comImage1 字段
		    if (comImage1 == null || comImage1.isEmpty()) {
		        byte[] existingImage1 = userSvc.getOneUser(userVO.getUserId()).getComImage1();
		        userVO.setComImage1(existingImage1);
		    }

		    // 处理 comImage2 字段
		    if (comImage2 == null || comImage2.isEmpty()) {
		        byte[] existingImage2 = userSvc.getOneUser(userVO.getUserId()).getComImage2();
		        userVO.setComImage2(existingImage2);
		    }

		    // 处理 comImage3 字段
		    if (comImage3 == null || comImage3.isEmpty()) {
		        byte[] existingImage3 = userSvc.getOneUser(userVO.getUserId()).getComImage3();
		        userVO.setComImage3(existingImage3);
		    }

		    // 处理 comImage4 字段
		    if (comImage4 == null || comImage4.isEmpty()) {
		        byte[] existingImage4 = userSvc.getOneUser(userVO.getUserId()).getComImage4();
		        userVO.setComImage4(existingImage4);
		    }
	    
		if (result.hasErrors()) {
			 for (FieldError error : result.getFieldErrors()) {
			        System.out.println("Field: " + error.getField() + ", Message: " + error.getDefaultMessage());
			    }
			return "front-end/com/editmember_aboutus";
		}
		/*************************** 2.開始修改資料 *****************************************/
		  userSvc.addUser(userVO);
		  request.getSession().setAttribute("loggingInUser", userVO);

		/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
		  model.addAttribute("success", "編輯成功,請前往預覽頁面查看");
		  model.addAttribute("editSuccess", true);
		  userVO = userSvc.getOneUser(Integer.valueOf(userVO.getUserId()));
		  model.addAttribute("userVO", userVO);
		  return "front-end/com/editmember_aboutus";
		}
	
//	==============================廣告圖片更新========================================

	@PostMapping("updateimage")
	public String updateimage(@RequestParam("comImage1") MultipartFile comImage1,
	                          @RequestParam("comImage2") MultipartFile comImage2,
	                          @RequestParam("comImage3") MultipartFile comImage3,
	                          @RequestParam("comImage4") MultipartFile comImage4,
	                          @Valid UserVO userVO, BindingResult result,
	                          HttpServletRequest request, ModelMap model) throws IOException {

	    // 处理图片
		handleImageUpload(comImage1, userVO, "comImage1");
		handleImageUpload(comImage2, userVO, "comImage2");
		handleImageUpload(comImage3, userVO, "comImage3");
		handleImageUpload(comImage4, userVO, "comImage4");

	    // 更新用户信息
	    userSvc.updateUser(userVO);
	    request.getSession().setAttribute("loggingInUser", userVO);

	    // 设置成功信息并准备转发到预览页面
	    model.addAttribute("success", "編輯成功,請前往預覽頁面查看");
	    model.addAttribute("editSuccess", true);
	    userVO = userSvc.getOneUser(Integer.valueOf(userVO.getUserId()));
	    model.addAttribute("userVO", userVO);
	    return "front-end/com/editmember_ad";
	}
	private void handleImageUpload(MultipartFile file, UserVO userVO, String fieldName) throws IOException {
	    if (!file.isEmpty()) {
	        byte[] imageData = file.getBytes();
	        switch (fieldName) {
	            case "comAboutImage":
	                userVO.setComAboutImage(imageData);
	                break;
	            case "comImage1":
	                userVO.setComImage1(imageData);
	                break;
	            case "comImage2":
	                userVO.setComImage2(imageData);
	                break;
	            case "comImage3":
	                userVO.setComImage3(imageData);
	                break;
	            case "comImage4":
	                userVO.setComImage4(imageData);
	                break;
	            default:
	                // 如果fieldName不匹配任何已知字段名，则不进行处理
	                // 可以选择在这里抛出异常或执行其他适当的处理
	                break;
	        }
	    }
	}

	 /*************************** 去除BindingResult *****************************************/
	
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