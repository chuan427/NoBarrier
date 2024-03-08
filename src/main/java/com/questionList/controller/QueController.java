package com.questionList.controller;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.questionList.model.QueListService;
import com.questionList.model.QueListVO;
import com.user.model.UserService;
import com.user.model.UserVO;

@Controller
@RequestMapping("/que")
public class QueController {

	@Autowired
	QueListService queSvc;
	
	@Autowired
	UserService userSvc;


	/*
	 * This method will serve as addNews.html handler.
	 */
	@GetMapping("addQue")
	public String addQue(ModelMap model) {
		QueListVO queListVO = new QueListVO();
		model.addAttribute("queListVO", queListVO);
		return "back-end/que/addQue";
	}

	/*
	 * This method will be called on addNews.html form submission, handling POST request It also validates the user input
	 */
	@PostMapping("insert")
	public String insert(@Valid QueListVO queListVO, BindingResult result, ModelMap model,
			@RequestParam("queImage") MultipartFile part) throws IOException {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
//		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
//		result = removeFieldError(queListVO, result, "queImage");
//
		System.out.println("213");
		if (!part.isEmpty()) {
        byte[] buf = part.getBytes();
        queListVO.setQueImage(buf);
    }

		
		if (queListVO.getQueNotitime() == null) {
			queListVO.setQueNotitime(new java.sql.Timestamp(System.currentTimeMillis()));
		}

//		if (result.hasErrors()) {
//			return "back-end/que/addQue";
//		}

		/*************************** 2.開始新增資料 *****************************************/
//		NewsService newsSvc = new NewsService();
		queSvc.addQue(queListVO);
		/*************************** 3.新增完成,準備轉交(Send the Success view) **************/
		List<QueListVO> list = queSvc.getAll();
		model.addAttribute("queListData", list);
		model.addAttribute("success", "- (新增成功)");
		return "redirect:/que/listAllQue"; // 新增成功後重導至IndexController_inSpringBoot.java的第50行@GetMapping("/News/listAllNews")
	}

	/*
	 * This method will be called on listAllNews.html form submission, handling POST request
	 */
	@PostMapping("getOne_For_Update")
	public String getOne_For_Update(@RequestParam("queNum") String queNum, ModelMap model) {
		
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		
		/*************************** 2.開始查詢資料 *****************************************/
//		NewsService newsSvc = new NewsService();
		QueListVO queListVO = queSvc.getOneQue(Integer.valueOf(queNum));
		

		/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
		model.addAttribute("queListVO", queListVO);
		return "back-end/que/update_Que_input"; // 查詢完成後轉交update_News_input.html
	}

	/*
	 * This method will be called on update_News_input.html form submission, handling POST request It also validates the user input
	 */
	@PostMapping("update")
	public String update(@Valid QueListVO queListVO, BindingResult result, ModelMap model) throws IOException {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
//		result = removeFieldError(QueListVO, result, "queImage");
//
//		if (parts[0].isNewsty()) { // 使用者未選擇要上傳的新圖片時
//			// NewsService NewsSvc = new NewsService();
//			byte[] upFiles = queSvc.getOneQue(QueListVO.getQueNum()).getQueImage();
//			QueListVO.setQueImage(upFiles);
//		} else {
//			for (MultipartFile multipartFile : parts) {
//				byte[] queImage = multipartFile.getBytes();
//				QueListVO.setQueImage(queImage);
//			}
//		}
		if (result.hasErrors()) {
			return "back-end/que/update_que_input";
		}
		
		/*************************** 2.開始修改資料 *****************************************/
//		NewsService newsSvc = new NewsService();
		queSvc.updateQue(queListVO);

		/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
		model.addAttribute("success", "- (修改成功)");
		queListVO = queSvc.getOneQue(Integer.valueOf(queListVO.getQueNum()));
		model.addAttribute("queListVO", queListVO);
		return "back-end/que/listOneQue"; // 修改成功後轉交listOneNews.html
	}

	/*
	 * This method will be called on listAllNews.html form submission, handling POST request
	 */
	@PostMapping("delete")
	public String delete(@RequestParam("queNum") String queNum, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始刪除資料 *****************************************/
		// NewsService NewsSvc = new NewsService();
		queSvc.deleteQue(Integer.valueOf(queNum));
		/*************************** 3.刪除完成,準備轉交(Send the Success view) **************/
		List<QueListVO> list = queSvc.getAll();
		model.addAttribute("queListData", list);
		model.addAttribute("success", "- (刪除成功)");
		return "back-end/que/listAllQue"; // 刪除完成後轉交listAllNews.html
	}

	@ModelAttribute("userListData")
	protected List<UserVO> referenceListData(){
		List<UserVO> list = userSvc.getAll();
		return list;
	}
	
	

	// 去除BindingResult中某個欄位的FieldError紀錄
	public BindingResult removeFieldError(QueListVO queListVO, BindingResult result, String removedFieldname) {
		List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
				.filter(fieldname -> !fieldname.getField().equals(removedFieldname))
				.collect(Collectors.toList());
		result = new BeanPropertyBindingResult(queListVO, "queListVO");
		for (FieldError fieldError : errorsListToKeep) {
			result.addError(fieldError);
		}
		return result;
	}

}