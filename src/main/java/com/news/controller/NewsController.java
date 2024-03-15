package com.news.controller;

import java.io.IOException;
import java.sql.Date;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.news.model.NewsService;
import com.news.model.NewsVO;

@Controller
@RequestMapping("/news")
public class NewsController {

	@Autowired
	NewsService newsSvc;


	/*
	 * This method will serve as addNews.html handler.
	 */
	@GetMapping("addNews")
	public String addNews(ModelMap model) {
		NewsVO newsVO = new NewsVO();
		model.addAttribute("newsVO", newsVO);
		return "back-end/news/addNews";
	}

	/*
	 * This method will be called on addNews.html form submission, handling POST request It also validates the user input
	 */
	@PostMapping("insert")
	public String insert(@Valid NewsVO newsVO, BindingResult result, ModelMap model) throws IOException {

		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
//		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
//		result = removeFieldError(newsVO, result, "upFiles");
//
//		if (parts[0].isNewsty()) { // 使用者未選擇要上傳的圖片時
//			model.addAttribute("errorMessage", "員工照片: 請上傳照片");
//		} else {
//			for (MultipartFile multipartFile : parts) {
//				byte[] buf = multipartFile.getBytes();
//				NewsVO.setUpFiles(buf);
//			}
//		}
		if (newsVO.getNewsDate() == null) {
		    newsVO.setNewsDate(new Date(System.currentTimeMillis()));
		};
		if (result.hasErrors()) {
			return "back-end/news/addNews";
		}
		/*************************** 2.開始新增資料 *****************************************/
//		NewsService newsSvc = new NewsService();
		newsSvc.addNews(newsVO);
		/*************************** 3.新增完成,準備轉交(Send the Success view) **************/
		List<NewsVO> list = newsSvc.getAll();
		model.addAttribute("newsListData", list);
		model.addAttribute("success", "- (新增成功)");
		return "redirect:/news/listAllNews"; // 新增成功後重導至IndexController_inSpringBoot.java的第50行@GetMapping("/News/listAllNews")
	}

	/*
	 * This method will be called on listAllNews.html form submission, handling POST request
	 */
	@PostMapping("getOne_For_Update")
	public String getOne_For_Update(@RequestParam("newsNum") String newsNum, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始查詢資料 *****************************************/
//		NewsService newsSvc = new NewsService();
		NewsVO newsVO = newsSvc.getOneNews(Integer.valueOf(newsNum));

		/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
		model.addAttribute("newsVO", newsVO);
		return "back-end/news/update_News_input"; // 查詢完成後轉交update_News_input.html
	}

	/*
	 * This method will be called on update_News_input.html form submission, handling POST request It also validates the user input
	 */
	@PostMapping("update")
	public String update(@Valid NewsVO newsVO, BindingResult result, ModelMap model) throws IOException {

		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
//		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
//		result = removeFieldError(newsVO, result, "upFiles");
//
//		if (parts[0].isNewsty()) { // 使用者未選擇要上傳的新圖片時
//			// NewsService NewsSvc = new NewsService();
//			byte[] upFiles = newsSvc.getOneNews(newsVO.getNewsno()).getUpFiles();
//			newsVO.setUpFiles(upFiles);
//		} else {
//			for (MultipartFile multipartFile : parts) {
//				byte[] upFiles = multipartFile.getBytes();
//				newsVO.setUpFiles(upFiles);
//			}
//		}
		if (result.hasErrors()) {
			return "back-end/news/update_News_input";
		}
		/*************************** 2.開始修改資料 *****************************************/
//		NewsService newsSvc = new NewsService();
		newsSvc.updateNews(newsVO);

		/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
		model.addAttribute("success", "- (修改成功)");
		newsVO = newsSvc.getOneNews(Integer.valueOf(newsVO.getNewsNum()));
		model.addAttribute("newsVO", newsVO);
		return "back-end/news/listOneNews"; // 修改成功後轉交listOneNews.html
	}

	/*
	 * This method will be called on listAllNews.html form submission, handling POST request
	 */
	@PostMapping("delete")
	public String delete(@RequestParam("newsNum") String newsNum, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始刪除資料 *****************************************/
		// NewsService NewsSvc = new NewsService();
		newsSvc.deleteNews(Integer.valueOf(newsNum));
		/*************************** 3.刪除完成,準備轉交(Send the Success view) **************/
		List<NewsVO> list = newsSvc.getAll();
		model.addAttribute("newsListData", list);
		model.addAttribute("success", "- (刪除成功)");
		return "back-end/news/listAllNews"; // 刪除完成後轉交listAllNews.html
	}


	// 去除BindingResult中某個欄位的FieldError紀錄
	public BindingResult removeFieldError(NewsVO newsVO, BindingResult result, String removedFieldname) {
		List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
				.filter(fieldname -> !fieldname.getField().equals(removedFieldname))
				.collect(Collectors.toList());
		result = new BeanPropertyBindingResult(newsVO, "newsVO");
		for (FieldError fieldError : errorsListToKeep) {
			result.addError(fieldError);
		}
		return result;
	}

}