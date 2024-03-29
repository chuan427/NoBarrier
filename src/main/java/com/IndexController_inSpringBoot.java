package com;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ad.model.AdService;
import com.ad.model.AdVO;
import com.addday.AdDate;
import com.administrator.model.AdministratorService;
import com.administrator.model.AdministratorVO;
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
import com.limitsale.model.LimitSaleService;
import com.limitsale.model.LimitSaleVO;
import com.news.model.NewsService;
import com.news.model.NewsVO;
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

//	@Autowired
//	RptdlistService rptdlistSvc;

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
	LimitSaleService limitSaleSvc;
	
	@Autowired
	AdministratorService administratorSvc;

	@Autowired
	OrderService orderSvc;
	
	
//	@Autowired
//	NotificationService notificationSvc;

//	 inject(注入資料) via application.properties
//	@Value("${welcome.message}")
//	private String message;

	private List<String> myList = Arrays.asList("Spring Boot Quickstart 官網 : https://start.spring.io", "IDE 開發工具",
			"直接使用(匯入)官方的 Maven Spring-Boot-demo Project + pom.xml",
			"直接使用官方現成的 @SpringBootApplication + SpringBootServletInitializer 組態檔",
			"依賴注入(DI) HikariDataSource (官方建議的連線池)", "Thymeleaf",
			"Java WebApp (<font color=red>快速完成 Spring Boot Web MVC</font>)");


private ReqOrderVO reqOrderVO;


//	@GetMapping("/")
//	public String index(Model model) {
//		model.addAttribute("message", message);
//		model.addAttribute("myList", myList);
//		return "index"; // view
//	}
	// ----------------------------------------------------
	@RequestMapping("/")
	public String toIndex(Model model) {
		return "index";
	}

	@RequestMapping("/loginpage")
	public String toLoginPage() {

		return "front-end/testLogin";
	}

	@RequestMapping("/loginfail")
	public String toFailLogin() {
		return "/front-end/failLogin"; // view
	}

	@RequestMapping("/loginsuccess")
	public String toSuccessLogin() {
		return "front-end/successLogin"; // view
	}


	@RequestMapping("/forgetPasswordPage")
	public String toForgetPasswordPage() {
		return "front-end/forgetPasswordPage"; // view
	}

	@RequestMapping(value = "/checkAccountExists", method = RequestMethod.POST)
	@ResponseBody
	public String checkAccountExists(@RequestParam String account) {

		boolean accountExists = userSvc.userIsExist(account);

		if (accountExists) {

			String newPassword = RandomPasswordGenerator.generateRandomPassword();
			userSvc.resetPassword(account, newPassword);

			String to = userSvc.getUserEmail(account);
			String subject = "NoBarrier平台:密碼重新設置";
			String messageText = "這是您的新密碼:" + newPassword + "\n" + "請盡快登入並重設您的密碼。";

			MailService.sendMail(to, subject, messageText);

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
	public String homepage(@PathVariable("userId") String userId, Model model) {
		// 根據 id 執行相應的邏輯，例如獲取特定的廠商資訊
		// 將相關數據添加到 Model 中，以便在視圖中使用
		//確認使用者身分
		UserVO userVo = userSvc.getOneUser(Integer.valueOf(userId));
		//判斷是否會隱藏限時特賣
		boolean and = adSvc.hasValidAdOrder(userVo);
		//顯示限時特賣商品
		List<LimitSaleVO> limitSaleOneData = limitSaleSvc.getOneLimitSalebyUserid(userVo);
//		System.out.println(and);
		model.addAttribute("and", and);
		model.addAttribute("userVO", userVo);
		model.addAttribute("limitSaleOneData", limitSaleOneData);
		return "front-end/com/com_homepage"; // view
	}

	// 廠商資訊 完成
	@GetMapping("/com/com")
	public String toCom() {
		return "front-end/com/com"; // view
	}

	// 廠商關於我預覽頁面 完成
	@GetMapping("/com/editmember_aboutus_view")
	public String editmember_aboutus_view(Model model, HttpServletRequest request, HttpServletResponse response) {
			HttpSession session = request.getSession();
			UserVO userVO = (UserVO) session.getAttribute("loggingInUser");
	        model.addAttribute("userVO", userVO);
		return "front-end/com/editmember_aboutus_view"; // view
	}

//	 廠商產品預覽頁面 完成
	@GetMapping("/com/editmember_aboutus")
	public String editmember_aboutus(Model model, HttpServletRequest request, HttpServletResponse response) {
			HttpSession session = request.getSession();
			UserVO userVO = (UserVO) session.getAttribute("loggingInUser");
	        model.addAttribute("userVO", userVO);
	    return "front-end/com/editmember_aboutus"; // 返回 view 的名稱
	}

	// 廠商廣告預覽頁面 完成
	@GetMapping("/com/editmember_ad_view")
	public String editmember_ad_view(Model model, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO) session.getAttribute("loggingInUser");
        model.addAttribute("userVO", userVO);; // view
        return "front-end/com/editmember_ad_view";
	}

	// 廠商廣告編輯頁面 完成
	@GetMapping("/com/editmember_ad")
	public String editmember_ad(Model model, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO) session.getAttribute("loggingInUser");
        model.addAttribute("userVO", userVO);
		return "front-end/com/editmember_ad"; // view
	}

	
	@GetMapping("/com/editmember_product_insert")
	public String update_productInformation_input_new(Model model, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO) session.getAttribute("loggingInUser");
        List<ProductInformationVO> productInformationList = productInformationSvc.getProductInformationByUserId(userVO.getUserId());
        // 添加到模型中
        model.addAttribute("userVO", userVO);
        model.addAttribute("productInformationVO",new ProductInformationVO());
        model.addAttribute("productInformationList", productInformationList);
    return "front-end/com/editmember_product_insert"; // 返回 view 的名稱
	}

//	 廠商產品預覽頁面 完成
	@GetMapping("/com/editmember_product_view")
	public String editmember_product_view(Model model, HttpServletRequest request, HttpServletResponse response) {
			HttpSession session = request.getSession();
			UserVO userVO = (UserVO) session.getAttribute("loggingInUser");
	        List<ProductInformationVO> productInformationList = productInformationSvc.getProductInformationByUserId(userVO.getUserId());
	        // 添加到模型中
	        model.addAttribute("productInformationList", productInformationList);
	    return "front-end/com/editmember_product_view"; // 返回 view 的名稱
	}
	
	// 廠商產品編輯頁面 完成
	@GetMapping("/com/editmember_product")
	public String editmember_product(Model model, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO) session.getAttribute("loggingInUser");
        List<ProductInformationVO> productInformationList = productInformationSvc.getProductInformationByUserId(userVO.getUserId());
        // 添加到模型中
        model.addAttribute("userVO", userVO);
        model.addAttribute("productInformationVO",new ProductInformationVO());
        model.addAttribute("productInformationList", productInformationList);
    return "front-end/com/editmember_product"; // 返回 view 的名稱
	}

	// 廠商產品限時預覽頁面 完成
	@GetMapping("/com/editmember_sale_view")
	public String editmember_sale_view(Model model,HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO) session.getAttribute("loggingInUser");
		List<LimitSaleVO> limitSaleOneData2 = limitSaleSvc.getOneLimitSalebyUserid(userVO);
		model.addAttribute("limitSaleOneData2", limitSaleOneData2);

		return "front-end/com/editmember_sale_view"; // view
	}

	// 廠商產品限時編輯頁面 完成
	@GetMapping("/com/editmember_sale")
	public String editmember_sale(Model model,HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO) session.getAttribute("loggingInUser");
		List<LimitSaleVO> limitSaleOneData2 = limitSaleSvc.getOneLimitSalebyUserid(userVO);
		model.addAttribute("limitSaleVO", new LimitSaleVO());
		model.addAttribute("limitSaleOneData2", limitSaleOneData2);
		return "front-end/com/editmember_sale"; // view
	}
	
	//從session中取的userVO
	@ModelAttribute("userVOlist")
	protected List<UserVO> referenceListData_user(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO) session.getAttribute("loggingInUser");
		if (userVO == null) {
			return null;
		} else {
			return userSvc.getOneStatUser(userVO);
		}
	}
	

//	// 廠商聯絡資料預覽頁面 修改前暫時不用
//	@GetMapping("/com/editmember_user_view")
//	public String editmember_user_view() {
//		return "front-end/com/editmember_user_view"; // view
//	}

//	// 廠商聯絡資料編輯頁面 修改前暫時不用
//	@GetMapping("/com/editmember_user")
//	public String editmember_user() {
//		return "front-end/com/editmember_user"; // view
//	}
//	  廠商聯絡資料編輯頁面 修改前暫時不用
//    @GetMapping("/com/editmember_user/{userId}")
//    public String editmember_user(@PathVariable("userId") String userId, Model model) {
//        // 根據 id 執行相應的邏輯，例如獲取特定的廠商資訊
//        // 將相關數據添加到 Model 中，以便在視圖中使用
//        model.addAttribute("userVO", userVO);
//        return "front-end/com/editmember_user_view"; // view
//    }

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

//	 廠商產品資訊編輯頁面 完成
	@GetMapping("/com/member_AboutUs")
	public String member_AboutUs () {
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
		return "front-end/com/member_Prod"; // view
	}
	
//		@GetMapping("/com/member_Prod")
//		public String member_Prod () {
//				    return "front-end/com/member_Prod"; // view
//				}

	// 訂單聊天室 成功
	@GetMapping("/caht/chat")
	public String chetroom() {
		return "front-end/caht/chat"; // view
	}

	

	// 聯絡我們 客服 成功
	@GetMapping("/userinformation/customer_service")
	public String customer_service(Model model,HttpServletRequest request) {
//		QueListVO queListVO = new QueListVO(); // 創建QueListVO對象，如果需要的話
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO) session.getAttribute("loggingInUser");
		List<QueListVO> queListData1 = queSvc.getONE1StatQuestions(userVO);
		List<QueListVO> queListData0 = queSvc.getONEStat0Questions(userVO);
		model.addAttribute("queListVO", new QueListVO());
		model.addAttribute("queListData1", queListData1);
		model.addAttribute("queListData0", queListData0);

		return "front-end/userinformation/customer_service"; // view
	}


	// 註冊畫面 成功
	@GetMapping("/userinformation/register1")
	public String register1(ModelMap model) {
		UserVO userVO = new UserVO();
		model.addAttribute("userVO", userVO);
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

	
	@PostMapping("backReview")
	public String backReview(@RequestParam("userId") Integer userId, ModelMap model) {
	    // 接收請求參數 - 輸入格式的錯誤處理

	    // 開始審核資料
	    userSvc.reviewUserByComStat(userId);

	    // 審核完成，準備轉交
	    List<UserVO> list = userSvc.getAll();
	    model.addAttribute("userListData", list);
	    model.addAttribute("success", "審核成功");
	    return "back-end/member_admin"; // 審核完成後轉交listAllUser.html
	}
	
	
	
	
	// 聯絡我們
//	@GetMapping("/")      			
//	public String customer_service() {
//	return "front-end/customer_service"; // view
//	}

	// -----------------rptdlist-------------------------
	@GetMapping("/rptdlist/select_page")
	public String select_page_rptdlist(Model model) {
		return "back-end/rptdlist/select_page";
	}

	@GetMapping("/rptdlist/listAllRptdlist")
	public String listAllRptdlist(Model model) {
		return "back-end/rptdlist/listAllRptdlist";
	}

//	@ModelAttribute("rptdlistListData") // for select_page.html 第97 109行用 // for listAllEmp.html 第117 133行用
//	protected List<RptdlistVO> referenceListData_rptdlist(Model model) {
//
//		List<RptdlistVO> list = rptdlistSvc.getAll();
//		return list;
//	}

	// -------------------需求單-----------------------
//	@GetMapping("/userinformation/userpage")
//	public String userpage(Model model, HttpServletRequest request) {
//		HttpSession session = request.getSession();
//		UserVO userVO = (UserVO) session.getAttribute("loggingInUser");
//
//		if (userVO == null) {
//			return "redirect:/login"; // 如果使用者未登入，將其重定向到登入頁面
//		}
//
//		List<ReqOrderVO> list = reqOrderSvc.getAllReqOrderExceptMe(userVO.getUserId());
//		model.addAttribute("reqOrderListData", list);
//		model.addAttribute("comName", userVO.getComName()); // 將公司名稱添加到模型中
//		return "front-end/userinformation/userpage";
//	}
	
	@GetMapping("/userinformation/userpage")
	public String showUserPage(HttpSession session, Model model) {
	    UserVO userVO = (UserVO) session.getAttribute("loggingInUser");
	    if (userVO == null) {
	        // 使用者未登入，重定向到登入頁面
	        return "redirect:/login";
	    }

	    // 這裡加載需要在使用者頁面顯示的資料
	    model.addAttribute("userVO", userVO);
	    
	    // 假設你有方法來獲取相關資料列表
//	    List<ReqOrderVO> list = reqOrderSvc.getAllReqOrderExceptMe(userVO.getUserId());
	    List<QuoVO> list1 = quoSvc.getAllQuotationExceptMe(userVO.getUserId());
//	    model.addAttribute("reqOrderListData", list);
	    model.addAttribute("comName", userVO.getComName()); // 將公司名稱添加到模型中
	    model.addAttribute("quoListData", list1);

	    return "front-end/userinformation/userpage"; // 返回使用者頁面視圖名稱
	}


	@ModelAttribute("reqOrderListData")
	protected List<ReqOrderVO> referenceListData_reqorder(Model model, HttpServletRequest request,
	        HttpServletResponse response) {
	    HttpSession session = request.getSession();
	    UserVO userVO = (UserVO) session.getAttribute("loggingInUser");

	    if (userVO == null) {
	        return null;
	    } else {
	        List<ReqOrderVO> reqorder = reqOrderSvc.getAllReqOrderExceptMe(userVO.getUserId());
	        List<QuoVO> quoAll = quoSvc.getAll();
	        List<ReqOrderVO> list = new ArrayList<>();

	        for (ReqOrderVO reqOrderVO : reqorder) {
	            boolean shouldAddToList = true;
	            for (QuoVO quoVO : quoAll) {
	                if (quoVO.getReqOrderVO().getReqNum() == reqOrderVO.getReqNum() && quoVO.getUserVO().getUserId() == userVO.getUserId()) {
	                    shouldAddToList = false;
	                    break;
	                }
	            }
	            if (shouldAddToList) {
	                list.add(reqOrderVO);
	            }
	        }
	        return list;
	    }
	}

	// ----------------報價單--------------------
//		@GetMapping("/userinformation/quotation_list")
//		public String quotation_list(Model model, HttpServletRequest request) {
//			HttpSession session = request.getSession();
//		    UserVO userVO = (UserVO) session.getAttribute("loggingInUser");
//		    
//		    if (userVO == null) {
//		        return "redirect:/login"; // 如果使用者未登入，將其重定向到登入頁面
//		    }
//
//		    List<QuoVO> list = quoSvc.getOneStatQuotation(userVO);
//		    model.addAttribute("quoListData", list);
//		    model.addAttribute("comName", userVO.getComName()); // 將公司名稱添加到模型中
//			return "front-end/userinformation/quotation_list";
//		}

//	@ModelAttribute("quoListData") // for select_page.html 第97 109行用 // for listAllEmp.html 第117 133行用
//	protected List<QuoVO> referenceListData_quotation(Model model, HttpServletRequest request,
//			HttpServletResponse response) {
//		HttpSession session = request.getSession();
//		UserVO userVO = (UserVO) session.getAttribute("loggingInUser");
//
//		if (userVO == null) {
//			return null;
//		} else {
//			return quoSvc.getOneStatQuotation(userVO);
//		}


	
	@ModelAttribute("quoListData") // for select_page.html 第97 109行用 // for listAllEmp.html 第117 133行用
	protected List<QuoVO> referenceListData_quotation(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO) session.getAttribute("loggingInUser");


		if (userVO == null) {
			return null;
		} else {
			return quoSvc.getAllQuotationExceptMe(userVO.getUserId());
		}
	}

	
		// -------------------order------------------------
		

//		@ModelAttribute("orderListData")
//		protected List<OrderVO> referenceListData_order(Model model, HttpServletRequest request, HttpServletResponse response) {
//		    HttpSession session = request.getSession();
//		    UserVO userVO = (UserVO) session.getAttribute("loggingInUser");
//
//		    if (userVO == null) {
//		        return null;
//		    } else {
////		    	List<ReqOrderVO> list = reqOrderSvc.findByReqIsValid();
////				return list;
//		        return orderSvc.getOneStatOrder(userVO);
//		    }
//		}
//	}

		@ModelAttribute("orderListData")
		protected List<OrderVO> referenceListData_order(Model model, HttpServletRequest request, HttpServletResponse response) {
		    HttpSession session = request.getSession();
		    UserVO userVO = (UserVO) session.getAttribute("loggingInUser");

		    if (userVO == null) {
		        return null;
		    } else {
		    	List<OrderVO> list = orderSvc.getAll();
				return list;
		    }
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

	// ----------------member--------------------------
	



































	@ModelAttribute("userListAllData") // for select_page.html 第97 109行用 // for listAllEmp.html 第117 133行用
	protected List<UserVO> referenceListData_com(Model model) {

		List<UserVO> list = userSvc.getAll();
		return list;
	}

















	@ModelAttribute("userListData") // for select_page.html 第97 109行用 // for listAllEmp.html 第117 133行用
	protected List<UserVO> referenceListData_user(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO) session.getAttribute("loggingInUser");

		if (userVO == null) {
			return null;
		} else {
			return userSvc.getOneStatUser(userVO);
		}
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
		return "back-end/ad/addAd";
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

	// ------------------------ForumPost--------------------------------------

	@GetMapping("/forum/listOneForumPost/{fpNum}")
	public String listOneForumPost(@PathVariable("fpNum") Integer fpNum, Model model) {
		ForumPostVO forumPostVO = forumPostSvc.getOneForumPost(fpNum);
		if (forumPostVO != null) {
			model.addAttribute("forumPostVO", forumPostVO);
			return "front-end/forum/listOneForumPost";
		} else {
			model.addAttribute("errorMessage", "查無此文章資料");
			return "redirect:/forum/forumIndex"; // 或是重定向到一個錯誤頁面或文章列表頁面
		}
	}

	@GetMapping("/forum/forumIndex")
	public String listAllForumPost(Model model) {
		ForumPostVO forumPostVO = new ForumPostVO();
		forumPostVO = forumPostSvc.getLatestPost();
		List<ForumPostVO> sortedPosts = forumPostSvc.getAllForumPostsSortedByFpTime();
	    model.addAttribute("forumPostListData", sortedPosts);
		model.addAttribute("forumPostVO", forumPostVO);
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

	// ---------------------------------------------------------------------
	// @GetMapping("/notification/select_page")
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
//		model.addAttribute("UserVO", new UserVO());
		List<QueListVO> list = queSvc.getAll();
		return list;
	}

//	@ModelAttribute("queListData1")
//	protected List<QueListVO> referenceListData5(Model model, HttpServletRequest request, HttpServletResponse response)
//			throws IOException {
//		HttpSession session = request.getSession();
//		UserVO userVO = (UserVO) session.getAttribute("loggingInUser");
//
//		// 检查用户是否已登录
//		if (userVO == null) {
//			// 用户未登录，重定向到登录页面
//			return null; // 返回 null 告诉 Spring MVC 不需要处理这个请求了
//		} else {
//			// 用户已登录，获取数据并返回
//			List<QueListVO> list = queSvc.getONE1StatQuestions(userVO);
//			return list;
//		}
//	}
//	@ModelAttribute("queListData0") // for select_page.html 第97 109行用 // for listAllEmp.html 第117 133行用
//	protected List<QueListVO> referenceListData6(Model model, HttpServletRequest request, HttpServletResponse response)
//			throws IOException {
//		HttpSession session = request.getSession();
//		UserVO userVO = (UserVO) session.getAttribute("loggingInUser");
//
//		// 检查用户是否已登录
//		if (userVO == null) {
//			// 用户未登录，重定向到登录页面
//			return null; // 返回 null 告诉 Spring MVC 不需要处理这个请求了
//		} else {
//			// 用户已登录，获取数据并返回
//			List<QueListVO> list = queSvc.getONEStat0Questions(userVO);
//			return list;
//		}
//	}



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
	
	@ModelAttribute("newsListDatacheck") // for 最新消息
	protected List<NewsVO> referenceListData_checknews(Model model) {

		List<NewsVO> list = newsSvc.getAllAndHandleStatus();
		return list;
	}


//	------------------------------chat-----------------------------------------
//	@GetMapping("/chat/privatechat")
//   	public String chat(Model model) {
//   		return "front-end/chat/privatechat";
//   	}
	@GetMapping("/chat/privatechat")
public String chat(Model model) {
		return "front-end/chat/privatechat";
	}
	@PostMapping("/chat/chat.do")
	public String gochat(Model model) {
		return "front-end/chat/chat";
	}

	// --------------------------後臺管理----------------------------------------------------

	@GetMapping("/ad_order")
	public String ad_order() {
		return "back-end/ad_order"; // view
	}

	@GetMapping("/advertising")
	public String advertising() {
		return "back-end/advertising"; // view
	}

	@GetMapping("/arrived")
	public String arrived() {
		return "back-end/arrived"; // view
	}

	@GetMapping("/authorization_new_member")
	public String authorization_new_member() {
		return "back-end/authorization_new_member"; // view
	}

	@GetMapping("/authorization_update_member")
	public String authorization_update_member() {
		return "back-end/authorization_update_member"; // view
	}

	@GetMapping("/buy_list")
	public String buy_list() {
		return "back-end/buy_list"; // view
	}

	@GetMapping("/buy_operation_list")
	public String buy_operation_list() {
		return "back-end/buy_operation_list"; // view
	}

	@GetMapping("/buy_operation")
	public String buy_operation() {
		return "back-end/buy_operation"; // view
	}

	@GetMapping("/customer_completed")
	public String customer_completed() {
		return "back-end/customer_completed"; // view
	}

	@GetMapping("/customer_pending")
	public String customer_pending() {
		return "back-end/customer_pending"; // view
	}

	@GetMapping("/forum")
	public String forum() {
		return "back-end/forum"; // view
	}

	@GetMapping("/in_logistics")
	public String in_logistics() {
		return "back-end/in_logistics"; // view
	}

	@GetMapping("/industry_judge")
	public String industry_judge() {
		return "back-end/industry_judge"; // view
	}

	@GetMapping("/industry_update")
	public String industry_update() {
		return "back-end/industry_update"; // view
	}

	@GetMapping("/member_admin")
	public String member_admin() {
		return "back-end/member_admin"; // view
	}

	@GetMapping("/member")
	public String member() {
		return "back-end/member"; // view
	}

	@GetMapping("/msg_contact")
	public String msg_contact() {
		return "back-end/msg_contact"; // view
	}

	@GetMapping("/msg_history")
	public String msg_history() {
		return "back-end/msg_history"; // view
	}

	@GetMapping("/news")
	public String news() {
		return "back-end/news"; // view
	}

	@GetMapping("/order_completed")
	public String order_completed() {
		return "back-end/order_completed"; // view
	}

	@GetMapping("/order_error")
	public String order_error() {
		return "back-end/order_error"; // view
	}

	@GetMapping("/sign_in")
	public String login() {
		return "back-end/sign_in"; // view
	}
	
	@GetMapping("/open")
	public String open() {
		return "back-end/open"; // view
	}

// -------------------------------limitsale-----------------------------------

	@GetMapping("/limitSale/select_page")
	public String select_page(Model model) {
		return "back-end/limitSale/select_page";
	}

	@GetMapping("limitSale/listAllLimitSale")
	public String listAllLimitSale(Model model) {
		return "back-end/limitSale/listAllLimitSale";
	}

	@ModelAttribute("limitSaleListData") // for select_page.html 第行用 // for listAllUser.html 第行用
	protected List<LimitSaleVO> referenceListData_limitsale(Model model) {

		List<LimitSaleVO> list = limitSaleSvc.getAll();

		return list;
	}
  	
 // -------------------------------administrator-----------------------------------
	

//  	@GetMapping("/administrator/select_page")
//	public String select_page(Model model) {
//		return "back-end/administrator/select_page";
//	}

  	@GetMapping("/administrator/select_page")
	public String select_page1(Model model) {
		return "back-end/administrator/select_page";
	}

    
    @GetMapping("/administrator/listAllAdministrator")
	public String listAllAdministrator(Model model) {
		return "back-end/administrator/listAllAdministrator";
	}
    
    @ModelAttribute("administratorListData")  // for select_page.html 第97 109行用 // for listAllEmp.html 第117 133行用
	protected List<AdministratorVO> referenceListData(Model model) {
		
    	List<AdministratorVO> list = administratorSvc.getAll();
		return list;
	}

//	@ModelAttribute("limitSaleOneData") // for select_page.html 第行用 // for listAllUser.html 第行用
//	protected List<LimitSaleVO> referenceListData_limitsale1(Model model,HttpServletRequest request) {
//	HttpSession session = request.getSession();
//	UserVO userVO = (UserVO) session.getAttribute("loggingInUser");
//
//	if (userVO == null) {
//		// 用户未登录，重定向到登录页面
//		return null; // 返回 null 告诉 Spring MVC 不需要处理这个请求了
//	} else {
//		// 用户已登录，获取数据并返回
////    	Integer userid = userVO.getUserId();
//		List<LimitSaleVO> list = limitSaleSvc.getOneLimitSalebyUserid(userVO);
////        for (LimitSaleVO item : list) {
////            System.out.println(item.getLimSellerid());
////        }
//
//		return list;
//	}}


	// -------------------------------order-----------------------------------

 	// 訂單檢舉 成功
 	@GetMapping("/order/reports")
 	public String reports() {
 		return "front-end/order/reports"; // view
 	}

 	
	@ModelAttribute("orderAllListData")
	protected List<OrderVO> referenceListOrderData() {
		List<OrderVO> list = orderSvc.getAll();
		return list;
	}
	
	
@GetMapping("addLimitSale")
	public String addLimitSale(ModelMap model) {
		LimitSaleVO limitSaleVO = new LimitSaleVO();
		model.addAttribute("limitSaleVO", limitSaleVO);
		return "back-end/limitSale/addLimitSale";
	}}



