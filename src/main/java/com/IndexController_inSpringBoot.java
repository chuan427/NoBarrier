package com;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ad.model.AdService;
import com.ad.model.AdVO;
import com.addday.AdDate;
import com.advertisements.model.AdvertisementsService;
import com.advertisements.model.AdvertisementsVO;
import com.forumpost.model.ForumPostService;
import com.forumpost.model.ForumPostVO;
import com.forumreply.model.ForumReplyService;
import com.forumreply.model.ForumReplyVO;
import com.forumreport.model.ForumReportService;
import com.forumreport.model.ForumReportVO;
import com.industry.model.IndustryService;
import com.industry.model.IndustryVO;
import com.newsmodel.NewsService;
import com.newsmodel.NewsVO;
import com.order.model.OrderService;
import com.order.model.OrderVO;
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
	
	@Autowired
	NewsService newsSvc;
	
	@Autowired
	AdService adSvc;
	
	@Autowired
	AdvertisementsService adverSvc;
  
	@Autowired
	QueListService queSvc;
	
	@Autowired
	ForumPostService forumPostSvc;

	@Autowired
	ForumReplyService forumReplySvc;
	
	@Autowired
	ForumReportService forumReportSvc;
	
	@Autowired
	OrderService orderSvc;

//	@Autowired
//	NotificationService notificationSvc;

	// inject(注入資料) via application.properties
//	@Value("${welcome.message}")
//	private String message;

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
	

	// 廠商資訊 完成
	@GetMapping("/com/com_homepage/{userId}")
	public String homepage(@PathVariable("userId") UserVO userVO, Model model) {
	    // 根據 id 執行相應的邏輯，例如獲取特定的廠商資訊
	    // 將相關數據添加到 Model 中，以便在視圖中使用
	    model.addAttribute("userVO", userVO);
	    return "front-end/com/com_homepage"; // view
	}

	// 廠商資訊 完成
	@GetMapping("/com/com")
	public String toCom() {
		return "front-end/com/com"; // view
	}

	// 廠商關於我預覽頁面 完成
	@GetMapping("/com/editmember_aboutus_view")
	public String editmember_aboutus_view() {
		return "front-end/com/editmember_aboutus_view"; // view
	}

	// 廠商編輯頁面 完成
	@GetMapping("/com/editmember_aboutus")
	public String editmember_aboutus() {
		return "front-end/com/editmember_aboutus"; // view
	}

	// 廠商廣告預覽頁面 完成
	@GetMapping("/com/editmember_ad_view")
	public String editmember_ad_view() {
		return "front-end/com/editmember_ad_view"; // view
	}

	// 廠商廣告編輯頁面 完成
	@GetMapping("/com/editmember_ad")
	public String editmember_ad() {
		return "front-end/com/editmember_ad"; // view
	}

	// 廠商產品預覽頁面 完成
	@GetMapping("/com/editmember_product_view")
	public String editmember_product_view() {
		return "front-end/com/editmember_product_view"; // view
	}

	// 廠商產品限時預覽頁面 完成
	@GetMapping("/com/editmember_sale_view")
	public String editmember_sale_view() {
		return "front-end/com/editmember_sale_view"; // view
	}

	// 廠商產品限時編輯頁面 完成
	@GetMapping("/com/editmember_sale")
	public String editmember_sale() {
		return "front-end/com/editmember_sale"; // view
	}

	// 廠商聯絡資料預覽頁面 完成
	@GetMapping("/com/editmember_user_view")
	public String editmember_user_view() {
		return "front-end/com/editmember_user_view"; // view
	}

	// 廠商聯絡資料編輯頁面 完成
	@GetMapping("/com/editmember_user")
	public String editmember_user() {
		return "front-end/com/editmember_user"; // view
	}

	// 廠商產品資訊編輯頁面 完成
	@GetMapping("/com/editmemeber_product")
	public String editmemeber_product() {
		return "front-end/com/editmemeber_product"; // view
	}

	// 廠商產品資訊編輯頁面 完成
	@GetMapping("/com/member_AboutUs/{userId}")
	public String member_AboutUs(@PathVariable("userId") UserVO userVO, Model model) {
		    // 根據 id 執行相應的邏輯，例如獲取特定的廠商資訊
		    // 將相關數據添加到 Model 中，以便在視圖中使用
		    model.addAttribute("userVO", userVO);
		    return "front-end/com/member_AboutUs"; // view
		}
	// 廠商產品資訊 完成
	@GetMapping("/com/member_Prod/{userId}")
	public String member_Prod(@PathVariable("userId") UserVO userVO, Model model) {
	    // 根據 id 執行相應的邏輯，例如獲取特定的廠商資訊
	    // 將相關數據添加到 Model 中，以便在視圖中使用
		Set<ProductInformationVO> productInformationVO = userVO.getProductInformation();
	    model.addAttribute("userVO", userVO);
	    model.addAttribute("productInformationVO", productInformationVO);
//	    System.out.println(model.addAttribute("userVO", userVO));
	    System.out.println(productInformationVO);
	    return "front-end/com/member_Prod"; // view
	}

	// 訂單聊天室 成功
	@GetMapping("/order/chatroom")
	public String chetroom() {
		return "front-end/order/chatroom"; // view
	}

	// 訂單明細 成功
	@GetMapping("/order/order_details")
	public String order_details() {
		return "front-end/order/order_details"; // view
	}

	// 訂單檢舉 成功
	@GetMapping("/order/reports")
	public String reports() {
		return "front-end/order/reports"; // view
	}

	// 訂單交易確認 成功
	@GetMapping("/order/transaction_check")
	public String transaction_check() {
		return "front-end/order/transaction_check"; // view
	}

	// 訂單交易狀態表 成功
	@GetMapping("/order/transaction_stat")
	public String transaction_stat() {
		return "front-end/order/transaction_stat"; // view
	}

	// 訂單交易 成功
	@GetMapping("/order/transaction")
	public String transaction() {
		return "front-end/order/transaction"; // view
	}

	// 聯絡我們 客服 成功
	@GetMapping("/userinformation/customer_service")
	public String customer_service(Model model) {
		QueListVO queListVO = new QueListVO(); // 創建QueListVO對象，如果需要的話
		model.addAttribute("queListVO", queListVO);
//		model.addAttribute("successMessage", "問題已成功新增");
		return "front-end/userinformation/customer_service"; // view
	}

	// 使用者修改 成功
	@GetMapping("/userinformation/memberCen")
	public String memberCen() {
		return "front-end/userinformation/memberCen"; // view
	}

	// 報價單 成功
	@GetMapping("/userinformation/quotation")
	public String quotation() {
		return "front-end/userinformation/quotation"; // view
	}

	// 註冊畫面 成功
	@GetMapping("/userinformation/register1")
	public String register1() {
		return "front-end/userinformation/register1"; // view
	}

	// 註冊產業類別畫面 成功
	@GetMapping("/userinformation/register2")
	public String register2() {
		return "front-end/userinformation/register2"; // view
	}

	// 註冊類別通知畫面 成功
	@GetMapping("/userinformation/register3")
	public String register3() {
		return "front-end/userinformation/register3"; // view
	}
	
	// 登入畫面 成功
	@GetMapping("/userinformation/sign_in")
	public String sign_in() {
		return "front-end/userinformation/sign_in"; // view
	}
	
	// 登入畫面 成功
	@GetMapping("/sign_in")
	public String sign_in1() {
		return "back-end/sign_in"; // view
	}

	// 聯絡我們
//	@GetMapping("/")      			
//	public String customer_service() {
//	return "front-end/customer_service"; // view
//	}

	// =========== 以下第57~62行是提供給
	// /src/main/resources/templates/back-end/emp/select_page.html 與 listAllEmp.html
	// 要使用的資料 ===================

	// ----------------報價單--------------------
	@GetMapping("/userinformation/addQuotation")
	public String addQuotation(Model model) {
		return "front-end/userinformation/addQuotation";
	}
	
	@GetMapping("/userinformation/quotation_list")
	public String quotation_list(Model model) {
		return "front-end/userinformation/quotation_list";
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
	public String addReqOrder(Model model) { 
		model.addAttribute("reqOrderVO", new ReqOrderVO());
		return "front-end/userinformation/addReqOrder";
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

	// ------------------------------------------

	@GetMapping("/user/select_page")
	public String select_page_user(Model model) {
		return "back-end/user/select_page";
	}

	@GetMapping("/user/listAllUser")
	public String listAllUser(Model model) {
		return "back-end/user/listAllUser";
	}

	@ModelAttribute("userListData") // for select_page.html 第97 109行用 // for listAllEmp.html 第117 133行用
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
		return "back-end/ad/addEmp";
	}

	@ModelAttribute("adListData") // for select_page.html 第97 109行用 // for listAllEmp.html 第117 133行用
	protected List<AdVO> referenceListData_ad(Model model) {

		List<AdVO> list = adSvc.getAll();
		return list;
	}
	
	@ModelAttribute("adervListData") // for select_page.html 第97 109行用 // for listAllEmp.html 第117 133行用
	protected List<AdvertisementsVO> referenceListData_aderv(Model model) {

		List<AdvertisementsVO> list = adverSvc.getAll();
		return list;
	}

	//------------------------ForumPost--------------------------------------
	
	@GetMapping("/forumPost/select_page1")
	public String select_page1(Model model) {
		return "back-end/forumPost/select_page1";
	}

	@GetMapping("/forum/forumIndex")
	public String listAllForumPost(Model model) {
		return "front-end/forum/forumIndex";
	}

	@ModelAttribute("forumPostListData") // for select_page.html 第行用 // for listAllUser.html 第行用
	protected List<ForumPostVO> referenceListData1(Model model) {

		List<ForumPostVO> list = forumPostSvc.getAll();
		return list;
	}

	// -----------------------ForumReply--------------------------------------

	@GetMapping("/forumReply/select_page2")
	public String select_page2(Model model) {
		return "back-end/forumReply/select_page2";
	}

	@GetMapping("/forumReply/listAllForumReply")
	public String listAllForumReply(Model model) {
		return "back-end/forumReply/listAllForumReply";
	}

	@ModelAttribute("forumReplyListData") // for select_page.html 第行用 // for listAllUser.html 第行用
	protected List<ForumReplyVO> referenceListData2(Model model) {

		List<ForumReplyVO> list = forumReplySvc.getAll();
		return list;

	}
	
	// -----------------------ForumReport-----------------------------------------
	
	@GetMapping("/forumReport/select_page")
	public String select_page4(Model model) {
		return "back-end/forumReport/select_page";
	}

	@GetMapping("/forumReport/listAllForumReport")
	public String listAllForumReport(Model model) {
		return "back-end/forumReport/listAllForumReport";
	}

	@ModelAttribute("forumReportListData") // for select_page.html 第行用 // for listAllUser.html 第行用
	protected List<ForumReportVO> referenceListData4(Model model) {

		List<ForumReportVO> list = forumReportSvc.getAll();
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
	
	
	@ModelAttribute("queListData1") // for select_page.html 第97 109行用 // for listAllEmp.html 第117 133行用
	protected List<QueListVO> referenceListData5(Model model) {
		List<QueListVO> list = queSvc.getONE1StatQuestions();
		return list;
	}
	
	@ModelAttribute("queListData0") // for select_page.html 第97 109行用 // for listAllEmp.html 第117 133行用
	protected List<QueListVO> referenceListData6(Model model) {
		List<QueListVO> list = queSvc.getONEStat0Questions();
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
	
//	------------------------------order--------------------------------------
	
	@GetMapping("/order/transactionStat")
	public String transactionStat(Model model) {
		return "front-end/order/transaction_stat";
	}
	@ModelAttribute("orderListData")
	protected List<OrderVO> referenceListOrderData(){
	List<OrderVO> list = orderSvc.getAll();
	return list;
	}
}

