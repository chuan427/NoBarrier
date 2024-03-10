package com;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.industry.model.IndustryService;
import com.industry.model.IndustryVO;
import com.productinformation.model.ProductInformationService;
import com.productinformation.model.ProductInformationVO;
import com.quo.model.QuoService;
import com.quo.model.QuoVO;
import com.reqorder.model.ReqOrderService;
import com.reqorder.model.ReqOrderVO;
import com.rptdlist.model.RptdlistService;
import com.rptdlist.model.RptdlistVO;
import com.security.model.MailService;
import com.security.model.RandomPasswordGenerator;
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

//	@GetMapping("/")
//	public String index(Model model) {
//		model.addAttribute("message", message);
//		model.addAttribute("myList", myList);
//		return "index"; // view
//	}
	//----------------------------------------------------
	@RequestMapping("/")
	public String toIndex() {
		return "index";
	}

	@RequestMapping("/loginpage")
	public String toLoginPage() {
		return "front-end/testLogin";
	}
	
	@RequestMapping("/loginfail")
	@ResponseBody
	public String toFailLogin() {
		return "Login Failed!"; // view
	}
	
	@RequestMapping("/loginsuccess")
	public String toSuccessLogin() {
		return "front-end/successLogin"; // view
	}
	
	@RequestMapping("/forgetPasswordPage")
	public String toForgetPasswordPage() {
		return "front-end/forgetPasswordPage"; // view
	}
	
	@RequestMapping(value="/checkAccountExists",method = RequestMethod.POST)
	@ResponseBody
    public String checkAccountExists(@RequestParam String account) {
        
        boolean accountExists = userSvc.userIsExist(account);

        if(accountExists) {
        	
        	String newPassword = RandomPasswordGenerator.generateRandomPassword();
        	userSvc.resetPassword(account, newPassword);
        	
        	String to = userSvc.getUserEmail(account);
        	String subject = "NoBarrier平台:密碼重新設置";
        	String messageText = "這是您的新密碼:" + newPassword + "\n" + "請盡快登入並重設您的密碼。";
        	
        	MailService.sendMail(to,subject,messageText);
        	
        }
        
        
        return "{\"exists\": " + accountExists + "}";
    }
	
	
	
	
	// http://......../hello?name=peter1
//	@GetMapping("/hello")
//	public String indexWithParam(@RequestParam(name = "name", required = false, defaultValue = "") String name,
//			Model model) {
//		model.addAttribute("message", name);
//		return "index"; // view
//	}
	

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

	// ------------------------------------------
	@GetMapping("/reqorder/select_page")
	public String select_page_reqorder(Model model) {
		return "back-end/reqorder/select_page";
	}

	@GetMapping("/reqorder/listAllReqOrder")
	public String listAllReqOrder(Model model) {
		return "back-end/reqorder/listAllReqOrder";
	}

	@ModelAttribute("reqOrderListData") // for select_page.html 第97 109行用 // for listAllEmp.html 第117 133行用
	protected List<ReqOrderVO> referenceListData_reqorder(Model model) {

		List<ReqOrderVO> list = reqOrderSvc.getAll();
		return list;
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
	
	//------------------------------------------

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
	
	//---------------------------------------------------------------------
	
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
}