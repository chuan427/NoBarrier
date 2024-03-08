package com;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.ad.model.AdService;
import com.ad.model.AdVO;
import com.addday.AdDate;
import com.industry.model.IndustryService;
import com.industry.model.IndustryVO;
import com.newsmodel.NewsService;
import com.newsmodel.NewsVO;
import com.productinformation.model.ProductInformationService;
import com.productinformation.model.ProductInformationVO;
import com.questionList.model.QueListService;
import com.questionList.model.QueListVO;
import com.quo.model.QuoService;
import com.quo.model.QuoVO;
import com.reqorder.model.ReqOrderService;
import com.reqorder.model.ReqOrderVO;
import com.rptdlist.model.RptdlistService;
import com.rptdlist.model.RptdlistVO;
import com.user.model.UserService;
import com.user.model.UserVO;

//@PropertySource("classpath:application.properties") // 於https://start.spring.io建立Spring Boot專案時, application.properties文件預設已經放在我們的src/main/resources 目錄中，它會被自動檢測到
@Controller
public class IndexController_inSpringBoot {

	// @Autowired (●自動裝配)(Spring ORM 課程)
	// 目前自動裝配了EmpService --> 供第60使用
	@Autowired
	QuoService quoSvc;

	@Autowired
	RptdlistService rptdlistSvc;

	@Autowired
	ReqOrderService reqOrderSvc;

	@Autowired
	IndustryService industrySvc;

	@Autowired
	ProductInformationService productInformationSvc;

	@Autowired
	UserService userSvc;
	@Autowired
	NewsService newsSvc;	@Autowired
	AdService adSvc;	@Autowired
	QueListService queSvc;


	
//	@Autowired
//	NotificationService notificationSvc;

	// inject(注入資料) via application.properties
	@Value("${welcome.message}")
	private String message;

	private List<String> myList = Arrays.asList("Spring Boot Quickstart 官網 : https://start.spring.io", "IDE 開發工具",
			"直接使用(匯入)官方的 Maven Spring-Boot-demo Project + pom.xml",
			"直接使用官方現成的 @SpringBootApplication + SpringBootServletInitializer 組態檔",
			"依賴注入(DI) HikariDataSource (官方建議的連線池)", "Thymeleaf",
			"Java WebApp (<font color=red>快速完成 Spring Boot Web MVC</font>)");

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("message", message);
		model.addAttribute("myList", myList);
		return "index"; // view
	}

	// http://......../hello?name=peter1
	@GetMapping("/hello")
	public String indexWithParam(@RequestParam(name = "name", required = false, defaultValue = "") String name,
			Model model) {
		model.addAttribute("message", name);
		return "index"; // view
	}

	 //登入頁面
	@GetMapping("/login/loginPage")  
	public String toLoginPage() {
		return "back-end/login/loginPage"; // view
	}
	
	//廠商資訊
	@GetMapping("/com")      			
	public String toCom() {
		return "front-end/com"; // view
	}
	
	//聯絡我們
	@GetMapping("/customer_service")      			
	public String customer_service() {
	return "front-end/customer_service"; // view
	}


	// =========== 以下第57~62行是提供給
	// /src/main/resources/templates/back-end/emp/select_page.html 與 listAllEmp.html
	// 要使用的資料 ===================
	@GetMapping("/quo/select_page")
	public String select_page(Model model) {
		return "back-end/quo/select_page";
	}

	@GetMapping("/quo/listAllQuo")
	public String listAllQuo(Model model) {
		return "back-end/quo/listAllQuo";
	}

	@ModelAttribute("quoListData") // for select_page.html 第97 109行用 // for listAllEmp.html 第117 133行用
	protected List<QuoVO> referenceListData(Model model) {

		List<QuoVO> list = quoSvc.getAll();
		return list;
	}

	// ------------------------------------------

	@GetMapping("/rptdlist/select_page")
	public String select_page_rptdlist(Model model) {
		return "back-end/rptdlist/select_page";
	}

	@GetMapping("/rptdlist/listAllRptdlist")
	public String listAllRptdlist(Model model) {
		return "back-end/rptdlist/listAllRptdlist";
	}

	@ModelAttribute("rptdlistListData") // for select_page.html 第97 109行用 // for listAllEmp.html 第117 133行用
	protected List<RptdlistVO> referenceListData_rptdlist(Model model) {

		List<RptdlistVO> list = rptdlistSvc.getAll();
		return list;
	}

	// -------------------需求單-----------------------
	@GetMapping("/userinformation/req_userpage")
	public String req_userpage(Model model) {
		return "front-end/userinformation/req_userpage";
	}

	@GetMapping("/userinformation/reqorder_list")
	public String reqorder_list(Model model) {
		return "front-end/userinformation/reqorder_list";
	}
	
	@GetMapping("/userinformation/addReqOrder")
	public String reqorder(Model model) {
		return "front-end/userinformation/addReqOrder";
	}


	// -------------------------------------------------

	@GetMapping("/industry/select_page")
	public String select_page_industry(Model model) {
		return "back-end/industry/select_page";
	}

	@GetMapping("/industry/listAllIndustry")
	public String listAllIndustry(Model model) {
		return "back-end/industry/listAllIndustry";
	}

	@ModelAttribute("industryListData") // for select_page.html 第97 109行用 // for listAllEmp.html 第117 133行用
	protected List<IndustryVO> referenceListData_industry(Model model) {

		List<IndustryVO> list = industrySvc.getAll();
		return list;
	}
	// ------------------------------------

	@GetMapping("/productInformation/select_page")
	public String select_page_productInformation(Model model) {
		return "back-end/productInformation/select_page";
	}

	@GetMapping("/productInformation/listAllProductInformation")
	public String listAllProductInformation(Model model) {
		return "back-end/productInformation/listAllProductInformation";
	}

	@ModelAttribute("productInformationListData") // for select_page.html 第97 109行用 // for listAllEmp.html 第117 133行用
	protected List<ProductInformationVO> referenceListData_productInformation(Model model) {

		List<ProductInformationVO> list = productInformationSvc.getAll();
		return list;
	}

	// ------------------------------------------

	@GetMapping("/user/select_page")
	public String select_page_user(Model model) {
		return "back-end/user/select_page";
	}

	@GetMapping("/user/listAllUser")
	public String listAllUser(Model model) {
		return "back-end/user/listAllUser";
	}

	@ModelAttribute("UserListData") // for select_page.html 第97 109行用 // for listAllEmp.html 第117 133行用
	protected List<UserVO> referenceListData_user(Model model) {

		List<UserVO> list = userSvc.getAll();
		return list;
	}

//	-----------------------------------------------------------------------

	@GetMapping("/ad/select_page")
	public String select_page_ad(Model model) {
		return "back-end/ad/select_page";
	}

	@GetMapping("/ad/listAllEmp")
	public String listAllEmp(Model model) {
		return "back-end/ad/listAllEmp";
	}

	@GetMapping("/ad/addEmp")
	public String addEmp1(Model model) {
		AdDate adDate = new AdDate();
		model.addAttribute("adDate", adDate);
		model.addAttribute("defaultAdPrice", 50);
		return "back-end/ad/addEmp";
	}

	@ModelAttribute("adListData") // for select_page.html 第97 109行用 // for listAllEmp.html 第117 133行用
	protected List<AdVO> referenceListData_ad(Model model) {

		List<AdVO> list = adSvc.getAll();
		return list;
	}


	// ---------------------------------------------------------------------
//	
//	@GetMapping("/notification/select_page")
//	public String select_page_notification(Model model) {
//		return "back-end/notification/select_page";
//	}
//
//	@GetMapping("/notification/listAllNotification")
//	public String listAllNotification(Model model) {
//		return "back-end/notification/listAllNotification";
//	}
//
//	@ModelAttribute("notificationListData") // for select_page.html 第97 109行用 // for listAllEmp.html 第117 133行用
//	protected List<NotificationVO> referenceListData_notification(Model model) {
//
//		List<NotificationVO> list = notificationSvc.getAll();
//		return list;
//	}
//	-----------------------------QuetsionList-------------------------------
	@GetMapping("/que/select_page")
	public String select_page3(Model model) {
		return "back-end/que/select_page";
	}

	@GetMapping("/que/listAllQue")
	public String listAllQue(Model model) {
		return "back-end/que/listAllQue";
	}

	@ModelAttribute("queListData") // for select_page.html 第97 109行用 // for listAllEmp.html 第117 133行用
	protected List<QueListVO> referenceListData3(Model model) {

		List<QueListVO> list = queSvc.getAll();
		return list;
	}
//	------------------------------news-----------------------------------------
	@GetMapping("/news/select_page")
	public String select_page_news(Model model) {
		return "back-end/news/select_page";
	}

	@GetMapping("/news/listAllNews")
	public String listAllNews(Model model) {
		return "back-end/news/listAllNews";
	}

	@ModelAttribute("newsListData") // for select_page.html 第97 109行用 // for listAllEmp.html 第117 133行用
	protected List<NewsVO> referenceListData_news(Model model) {

		List<NewsVO> list = newsSvc.getAll();
		return list;
	}
	
}