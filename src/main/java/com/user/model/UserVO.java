package com.user.model;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
//import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.ad.model.AdVO;
import com.forumpost.model.ForumPostVO;
import com.industry.model.IndustryVO;
import com.order.model.OrderVO;
import com.productinformation.model.ProductInformationVO;
import com.questionList.model.QueListVO;
import com.quo.model.QuoVO;
import com.reqorder.model.ReqOrderVO;

	@Entity // 要加上@Entity才能成為JPA的一個Entity類別
	@Table(name = "userInformation") // 代表這個class是對應到資料庫的實體table，目前對應的table是userInformation
	public class UserVO implements java.io.Serializable {
		private static final long serialVersionUID = 1L; 
		
		private Integer userId;
		private IndustryVO industryVO;
		private String comName;
		private String comAccount;
		private String comPassword;
		private String comUniNumber;
		private String comAddress;
		private String comMail;
		private String comPhone;
		private String comBank;
		private String accountNumber;
		private Date comRegDate;
		private String comContactPerson;
		private String comContactPhone;
		private Integer comStat;
		private byte[] comImage1;
		private byte[] comImage2;
		private byte[] comImage3;
		private byte[] comImage4;
		private byte[] comAboutImage;
		private String comAboutContent;
		private Double comRatStars;
		private Integer comRatCount;
		private Integer comIsValid;
		
		private Set<ReqOrderVO> reqOrder = new HashSet<ReqOrderVO>();
		private Set<QuoVO> quotations = new HashSet<QuoVO>();
		private Set<ProductInformationVO> productInformation = new HashSet<ProductInformationVO>();
		private Set<QueListVO> quelists = new HashSet<QueListVO>();
		private Set<ForumPostVO> forumPost = new HashSet<ForumPostVO>();
		private Set<AdVO> ad = new HashSet<AdVO>();
		private Set<OrderVO> orders = new HashSet<OrderVO>();



		public UserVO() { // 必需有一個不傳參數建構子(JavaBean基本知識)
		}
		
		
		//--------------------------------------------

		@Id //@Id代表這個屬性是這個Entity的唯一識別屬性，並且對映到Table的主鍵 
		@Column(name = "userid")  //@Column指這個屬性是對應到資料庫Table的哪一個欄位   //【非必要，但當欄位名稱與屬性名稱不同時則一定要用】
		@GeneratedValue(strategy = GenerationType.IDENTITY) //@GeneratedValue的generator屬性指定要用哪個generator //【strategy的GenerationType, 有四種值: AUTO, IDENTITY, SEQUENCE, TABLE】
		public Integer getUserId() {
			return this.userId;
		}
		public void setUserId(Integer userId) {
			this.userId = userId;
		}
		
		
		//--------------------------------------------

		@ManyToOne
	    @JoinColumn(name = "comIndustry", referencedColumnName = "industryNum", insertable=false, updatable=false)
		public IndustryVO getIndustryVO() {
			return this.industryVO;
		}

		public void setIndustryVO(IndustryVO industryVO) {
			this.industryVO = industryVO;
		}
			
		
		//--------------------------------------------

		@Column(name = "comName")
		@NotEmpty(message="公司名稱: 請勿空白")
		@Pattern(regexp = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,30}$", message = "公司名稱: 只能是中、英文字母、數字和_ , 且長度必需在2到30之間")
		public String getComName() {
			return this.comName;
		}
		public void setComName(String comName) {
			this.comName = comName;
		}	
		
		//--------------------------------------------

		@Column(name = "comAccount")
		@NotEmpty(message="公司帳號: 請勿空白")
		@Size(min=2,max=50,message="公司帳號: 長度必需在{min}到{max}之間")
		public String getComAccount() {
			return this.comAccount;
		}
		public void setComAccount(String comAccount) {
			this.comAccount = comAccount;
		}
		
		//--------------------------------------------

		@Column(name = "comPassword")
		@NotEmpty(message="公司密碼: 請勿空白")
		@Size(min=2,max=100,message="公司密碼: 長度必需在{min}到{max}之間")
		public String getComPassword() {
			return this.comPassword;
		}
		public void setComPassword(String comPassword) {
			this.comPassword = comPassword;
		}
		
		
		//--------------------------------------------

		@Column(name = "comUniNumber")
		@NotEmpty(message="公司統編: 請勿空白")
		@Size(min=8,max=8,message="公司統編: 格式錯誤，長度為{max}")
		public String getComUniNumber() {
			return this.comUniNumber;
		}
		public void setComUniNumber(String comUniNumber) {
			this.comUniNumber = comUniNumber;
		}
		
		//--------------------------------------------

		@Column(name = "comAddress")
		@NotEmpty(message="公司地址: 請勿空白")
		@Size(min=2,max=255,message="公司地址: 長度必需在{min}到{max}之間")
		public String getComAddress() {
			return this.comAddress;
		}
		public void setComAddress(String comAddress) {
			this.comAddress = comAddress;
		}
		
		//--------------------------------------------

		@Column(name = "comMail")
		@NotEmpty(message="公司信箱: 請勿空白")
//		@Email
//		@Length
		public String getComMail() {
			return this.comMail;
		}
		public void setComMail(String comMail) {
			this.comMail = comMail;
		}
		
		//--------------------------------------------

		@Column(name = "comPhone")
		@NotEmpty(message="公司電話: 請勿空白")
		@Size(min=2,max=20,message="公司密碼: 長度必需在{min}到{max}之間")
		public String getComPhone() {
			return this.comPhone;
		}
		public void setComPhone(String comPhone) {
			this.comPhone = comPhone;
		}
		
		//--------------------------------------------

		@Column(name = "comBank")
		@Size(min=2,max=50,message="銀行代碼: 長度必需在{min}到{max}之間")
		public String getComBank() {
			return this.comBank;
		}
		public void setComBank(String comBank) {
			this.comBank = comBank;
		}
		
		@Column(name = "accountNumber")
		@Size(min=2,max=50,message="銀行帳號: 長度必需在{min}到{max}之間")
		public String getAccountNumber() {
			return accountNumber;
		}


		public void setAccountNumber(String accountNumber) {
			this.accountNumber = accountNumber;
		}

		
		//--------------------------------------------

		@Column(name = "comRegdate")
//		@NotNull(message="雇用日期: 請勿空白")	
//		@Future(message="日期必須是在今日(不含)之後")
//		@Past(message="日期必須是在今日(含)之前")
//		@DateTimeFormat(pattern="yyyy-MM-dd") 
//		@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") 
		public Date getComRegDate() {
			return this.comRegDate;
		}
		public void setComRegDate(Date comRegDate) {
			this.comRegDate = comRegDate;
		}
		
		//--------------------------------------------

		@Column(name = "comContactperson")
		@NotEmpty(message="公司聯絡人: 請勿空白")
		public String getComContactPerson() {
			return this.comContactPerson;
		}
		public void setComContactPerson(String comContactPerson) {
			this.comContactPerson = comContactPerson;
		}
		
		//--------------------------------------------

		@Column(name = "comContactphone")
		@NotEmpty(message="公司聯絡人電話: 請勿空白")
		@Size(min=7,max=50,message="請符合電話公司聯絡人電話格式")
		public String getComContactPhone() {
			return this.comContactPhone;
		}
		public void setComContactPhone(String comContactPhone) {
			this.comContactPhone = comContactPhone;
		}
		
		//--------------------------------------------

		@Column(name = "comStat")
		public Integer getComStat() {
			return this.comStat;
		}
		public void setComStat(Integer comStat) {
			this.comStat = comStat;
		}
		
		//--------------------------------------------

		@Column(name = "comImage1")
		public byte[] getComImage1() {
			return comImage1;
		}
		public void setComImage1(byte[] comImage1) {
			this.comImage1 = comImage1;
		}
		
		//--------------------------------------------

		@Column(name = "comImage2")
		public byte[] getComImage2() {
			return comImage2;
		}
		public void setComImage2(byte[] comImage2) {
			this.comImage2 = comImage2;
		}
		
		//--------------------------------------------

		@Column(name = "comImage3")
		public byte[] getComImage3() {
			return comImage3;
		}
		public void setComImage3(byte[] comImage3) {
			this.comImage3 = comImage3;
		}
		
		//--------------------------------------------

		@Column(name = "comImage4")
		public byte[] getComImage4() {
			return comImage4;
		}
		public void setComImage4(byte[] comImage4) {
			this.comImage4 = comImage4;
		}
		
		//--------------------------------------------

		@Column(name = "comAboutimage")
		public byte[] getComAboutImage() {
			return comAboutImage;
		}
		public void setComAboutImage(byte[] comAboutImage) {
			this.comAboutImage = comAboutImage;
		}
		
		//--------------------------------------------

		@Column(name = "comAboutcontent")
		public String getComAboutContent() {
			return this.comAboutContent;
		}
		public void setComAboutContent(String comAboutContent) {
			this.comAboutContent = comAboutContent;
		}
		
		//--------------------------------------------

		@Column(name = "comRatstars")
		public Double getComRatStars() {
			return this.comRatStars;
		}
		public void setComRatStars(Double comRatStars) {
			this.comRatStars = comRatStars;
		}
		
		//--------------------------------------------

		@Column(name = "comRatcount")
		public Integer getComRatCount() {
			return this.comRatCount;
		}
		public void setComRatCount(Integer comRatCount) {
			this.comRatCount = comRatCount;
		}
		
		//--------------------------------------------

		@Column(name = "comIsValid")
		public Integer getComIsValid() {
			return comIsValid;
		}
		
		public void setComIsValid(Integer comIsValid) {
			this.comIsValid = comIsValid;
		}
		
		//--------------------------------------------
		
		@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="userVO")
		@OrderBy("reqNum asc")
		public Set<ReqOrderVO> getReqOrder() {
			return this.reqOrder;
		}

		public void setReqOrder(Set<ReqOrderVO> reqOrder) {
			this.reqOrder = reqOrder;
		}
		
		//--------------------------------------------
		
		@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="userVO")
		@OrderBy("pinfoNum asc")
		public Set<ProductInformationVO> getProductInformation() {
			return this.productInformation;
		}

		public void setProductInformation(Set<ProductInformationVO> productInformation) {
			this.productInformation = productInformation;
		}

		//--------------------------------------------
		
		@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="userVO")
		@OrderBy("quoNum asc")
		public Set<QuoVO> getQuotations() {
			return quotations;
		}

		public void setQuotations(Set<QuoVO> quotations) {
			this.quotations = quotations;
		}
		
		//--------------------------------------------
		
		@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "userVO")
		@OrderBy("fpUserid asc")
		public Set<ForumPostVO> getForumPost() {
			return forumPost;
		}

		public void setForumPost(Set<ForumPostVO> forumPost) {
			this.forumPost = forumPost;
		}
		
		
		
		
//		@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="userVO")
//		@OrderBy("industryNum asc")
//		public Set<IndustryVO> getIndustry() {
//			return this.industry;
//		}
//
//		public void setIndustry(Set<IndustryVO> industry) {
//			this.industry = industry;
//		}
		@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="userVO")
		//註1:【現在是設定成 cascade="all" lazy="false" inverse="true"之意】
		//註2:【mappedBy="多方的關聯屬性名"：用在雙向關聯中，把關係的控制權反轉】【deptVO是EmpVO的屬性】
		//註3:【原預設為@OneToMany(fetch=FetchType.LAZY, mappedBy="deptVO")之意】--> 【是指原為  lazy="true"  inverse="true"之意】
		//FetchType.EAGER : Defines that data must be eagerly fetched
		//FetchType.LAZY  : Defines that data can be lazily fetched
		public Set<QueListVO> getQueLists() {
			return this.quelists;
		}

		public void setQueLists(Set<QueListVO> quelists) {
			this.quelists = quelists;
		}
		
		@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="userVO")
		public Set<AdVO> getAd() {
			return ad;
		}


		public void setAd(Set<AdVO> ad) {
			this.ad = ad;
		}
		
		@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "userVO")
//		@OrderBy("userId asc") 
		public Set<OrderVO> getOrders() {
			return this.orders;
		}
		
		public void setOrders(Set<OrderVO> orders) {
			this.orders = orders;
		}
	}

