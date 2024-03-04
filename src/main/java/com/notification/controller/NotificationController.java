package com.notification.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.notification.model.NotificationService;
import com.notification.model.NotificationVO;

@Controller
@RequestMapping("/notification")
public class NotificationController {

	@Autowired
	NotificationService notificationSvc;
	
	@GetMapping("addNotification")
	public String addNotification(ModelMap model) {
		NotificationVO notificationVO = new NotificationVO();
		model.addAttribute("notificationVO", notificationVO);
		return "back-end/Notification/addNotification";
	}
	
	@PostMapping("insert")
	public String insert(@Valid NotificationVO notificationVO, BindingResult result, ModelMap model) throws IOException {
		
		if (result.hasErrors()) {
	        return "back-end/notification/addNotification";
		}
		
		/*************************** 2.開始新增資料 *****************************************/
		// EmpService empSvc = new EmpService();
		notificationSvc.addNotification(notificationVO);
		/*************************** 3.新增完成,準備轉交(Send the Success view) **************/
		List<NotificationVO> list = notificationSvc.getAll();
		model.addAttribute("notificationListData", list);
		model.addAttribute("success", "- (新增成功)");
		return "redirect:/notification/listAllNotification";
	}
	
	@PostMapping("getOne_For_Update")
	public String getOne_For_Update(@RequestParam("notiNum") String notiNum, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始查詢資料 *****************************************/
		// EmpService empSvc = new EmpService();
		NotificationVO notificationVO = notificationSvc.getOneNotification(Integer.valueOf(notiNum));

		/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
		model.addAttribute("notificationVO", notificationVO);
		return "back-end/notification/update_notification_input"; // 查詢完成後轉交update_user_input.html
	}
	
	@PostMapping("update")
	public String update(@Valid NotificationVO notificationVO, BindingResult result, ModelMap model) throws IOException {
		
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		
		if (result.hasErrors()) {
			return "back-end/notification/update_notification_input";
		}
		
		/*************************** 2.開始修改資料 *****************************************/
		// EmpService empSvc = new EmpService();
		notificationSvc.updateNotification(notificationVO);

		/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
		model.addAttribute("success", "- (修改成功)");
		notificationVO = notificationSvc.getOneNotification(Integer.valueOf(notificationVO.getNotiNum()));
		model.addAttribute("notificationVO", notificationVO);
		return "back-end/notification/listOneNotification"; // 修改成功後轉交listOneEmp.html
	}
}
