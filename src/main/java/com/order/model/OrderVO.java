package com.order.model;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.limitsale.model.LimitSaleVO;
import com.quo.model.QuoVO;
import com.reqorder.model.ReqOrderVO;
import com.rptdlist.model.RptdlistVO;
//import com.rptdlist.model.RptdlistVO;
import com.user.model.UserVO;


	@Entity // 要加上@Entity才能成為JPA的一個Entity類別
	@Table(name = "`order`") // 代表這個class是對應到資料庫的實體table，目前對應的table是userInformation
	public class OrderVO implements java.io.Serializable {
		private static final long serialVersionUID = 1L; 
		private Integer ordNum;
		private Date ordDate;
		private String ordProdname;
		private Integer ordProdqty;
		private String ordUnitname;
		private Integer ordProdprice;
		private Integer ordTotalamount;
		private Integer ordBuyerid;
//		private Integer ordSellerid;
		private Integer ordStat;
		private Integer ordTranStat;
		private Integer ordPaystat;
		private Double ordRatstar;
		private String ordComment;
		private Integer ordLimnum;
		private Integer ordReqnum;
		private Integer ordQuonum;
		private Integer ordIsValid;
		private UserVO userVO;
		private LimitSaleVO limitsaleVO;
		private ReqOrderVO reqOrderVO;
		private QuoVO quoVO;
		private RptdlistVO rptdlistVO;
		public OrderVO() { 
		}
		
		//一對一 使用原版mappedBy
		@OneToOne(mappedBy="orderVO",cascade=CascadeType.ALL)
		@PrimaryKeyJoinColumn 
		public QuoVO getQuoVO() {
			return quoVO;
		}
		public void setQuoVO(QuoVO quoVO) {
			this.quoVO = quoVO;
		}
		
		
		//訂單與需求是一對一 用@PrimaryKeyJoinColumn寫法 
		@OneToOne(mappedBy="orderVO",cascade=CascadeType.ALL)
		@PrimaryKeyJoinColumn 
		public ReqOrderVO getReqOrderVO() {
				return reqOrderVO;
		}
		public void setReqOrderVO(ReqOrderVO reqOrderVO) {
				this.reqOrderVO = reqOrderVO;
		}
		

		@OneToOne
		@MapsId 
		@JoinColumn(name="ordNum", referencedColumnName = "rptdOrdernum")
		public RptdlistVO getRptdlistVO() {
			return rptdlistVO;
		}

		public void setRptdlistVO(RptdlistVO rptdlistVO) {
			this.rptdlistVO = rptdlistVO;
		}

		@OneToOne(mappedBy="orderVO",cascade=CascadeType.ALL)
		@PrimaryKeyJoinColumn 
		public LimitSaleVO getLimitsaleVO() {
			return limitsaleVO;
		}
		public void setLimitsaleVO(LimitSaleVO limitsaleVO) {
			this.limitsaleVO = limitsaleVO;
		}
		

		@ManyToOne           //此VO資料庫對應的欄位                //參照的資料庫欄位
		@JoinColumn(name = "ordSellerid",referencedColumnName = "userid")
		public UserVO getUserVO() {
			return userVO;
		}


		public void setUserVO(UserVO userVO) {
				this.userVO = userVO;
		}
		
		//以上是關聯的 getter & setter
		
		@Id //@Id代表這個屬性是這個Entity的唯一識別屬性，並且對映到Table的主鍵 
		@Column(name = "ordNum")  //@Column指這個屬性是對應到資料庫Table的哪一個欄位   //【非必要，但當欄位名稱與屬性名稱不同時則一定要用】
		@GeneratedValue(strategy = GenerationType.IDENTITY) //@GeneratedValue的generator屬性指定要用哪個generator //【strategy的GenerationType, 有四種值: AUTO, IDENTITY, SEQUENCE, TABLE】
		public Integer getOrdNum() {
			return this.ordNum;
		}
		public void setOrdNum(Integer ordNum) {
			this.ordNum = ordNum;
		}
		@Column(name = "ordDate")
//		@NotNull(message="下單日期: 請勿空白")	
//		@Future(message="日期必須是在今日(不含)之後")
//		@Past(message="日期必須是在今日(含)之前")
//		@DateTimeFormat(pattern="yyyy-MM-dd") 
//		@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") 
		public Date getOrdDate() {
			return this.ordDate;
		}
		public void setOrdDate(Date ordDate) {
			this.ordDate = ordDate;
		}
		@Column(name = "ordProdname")
//		@NotEmpty(message="商品名稱: 請勿空白")
//		@Pattern(regexp = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$", message = "商品名稱: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間")
		public String getOrdProdname() {
			return this.ordProdname;
		}
		public void setOrdProdname(String ordProdname) {
			this.ordProdname = ordProdname;
		}		
		@Column(name = "ordProdqty")
//		@NotEmpty(message="商品數量: 請勿空白")
//		@Size(min=2,max=50,message="商品數量: 長度必需在{min}到{max}之間")
		public Integer getOrdProdqty() {
			return this.ordProdqty;
		}
		public void setOrdProdqty(Integer ordProdqty) {
			this.ordProdqty = ordProdqty;
		}
		@Column(name = "ordUnitname")
//		@NotEmpty(message="品項單位: 請勿空白")
//		@Size(min=2,max=50,message="公司密碼: 長度必需在{min}到{max}之間")
		public String getOrdUnitname() {
			return this.ordUnitname;
		}
		public void setOrdUnitname(String ordUnitname) {
			this.ordUnitname = ordUnitname;
		}
		
		@Column(name = "ordProdprice")
//		@NotEmpty(message="商品價格: 請勿空白")
//		@Size(min=2,max=8,message="商品價格: 長度必需在{min}到{max}之間")
		public Integer getOrdProdprice() {
			return this.ordProdprice;
		}
		public void setOrdProdprice(Integer ordProdprice) {
			this.ordProdprice = ordProdprice;
		}
		@Column(name = "ordTotalamount")
//		@NotEmpty(message="總金額: 請勿空白")
//		@Size(min=2,max=255,message="總金額: 長度必需在{min}到{max}之間")
		public Integer getOrdTotalamount() {
			return this.ordTotalamount;
		}
		public void setOrdTotalamount(Integer ordTotalamount) {
			this.ordTotalamount = ordTotalamount;
		}
		@Column(name = "ordBuyerid")
//		@NotEmpty(message="買家編號: 請勿空白")
//		@Size(min=2,max=20,message="買家編號: 長度必需在{min}到{max}之間")
		public Integer getOrdBuyerid() {
			return this.ordBuyerid;
		}
		public void setOrdBuyerid(Integer ordBuyerid) {
			this.ordBuyerid = ordBuyerid;
		}
		
//		@Column(name = "ordSellerid")
////		@NotEmpty(message="賣家編號: 請勿空白")
//		public Integer getOrdSellerid() {
//			return this.ordSellerid;
//		}
//		public void setOrdSellerid(Integer ordSellerid) {
//			this.ordSellerid = ordSellerid;
//		}
		@Column(name = "ordStat")
//		@NotEmpty(message="訂單狀態: 請勿空白")
		public Integer getOrdStat() {
			return this.ordStat;
		}
		public void setOrdStat(Integer ordStat) {
			this.ordStat = ordStat;
		}
		@Column(name = "ordTranStat")
//		@NotEmpty(message="物流狀態: 請勿空白")
		public Integer getOrdTranStat() {
			return this.ordTranStat;
		}
		public void setOrdTranStat(Integer ordTranStat) {
			this.ordTranStat = ordTranStat;
		}
		@Column(name = "ordPaystat")
//		@NotEmpty(message="交易狀態: 請勿空白")
//		@Size(min=10,max=50,message="交易狀態: 長度必需在{min}到{max}之間")
		public Integer getOrdPaystat() {
			return this.ordPaystat;
		}
		public void setOrdPaystat(Integer ordPaystat) {
			this.ordPaystat = ordPaystat;
		}
		@Column(name = "ordRatstar")
//		@NotEmpty(message="公司狀態: 請勿空白")
//		@Size(min=1,max=3,message="公司狀態: 長度必需在{min}到{max}之間")
		public Double getOrdRatstar() {
			return this.ordRatstar;
		}
		public void setOrdRatstar(Double ordRatstar) {
			this.ordRatstar = ordRatstar;
		}
		
		@Column(name = "ordComment")
//		@NotEmpty(message="訂單描述: 請勿空白")
//		@Size(min=2,max=255,message="訂單描述: 長度必需在{min}到{max}之間")
		public String getOrdComment() {
			return this.ordComment;
		}
		public void setOrdComment(String ordComment) {
			this.ordComment = ordComment;
		}
		@Column(name = "ordLimnum")
//		@NotNull(message="限時特賣單號: 請勿空白")
		public Integer getOrdLimnum() {
			return this.ordLimnum;
		}
		public void setOrdLimnum(Integer ordLimnum) {
			this.ordLimnum = ordLimnum;
		}
		@Column(name = "ordReqnum")
//		@NotEmpty(message="需求單號: 請勿空白")
//		@Size(min=2,max=255,message="關於我們敘述: 長度必需在{min}到{max}之間")
		public Integer getOrdReqnum() {
			return this.ordReqnum;
		}
		public void setOrdReqnum(Integer ordReqnum) {
			this.ordReqnum = ordReqnum;
		}
		@Column(name = "ordQuonum")
//		@NotEmpty(message="報價單號: 請勿空白")
		public Integer getOrdQuonum() {
			return this.ordQuonum;
		}
		public void setOrdQuonum(Integer ordQuonum) {
			this.ordQuonum = ordQuonum;
		}

		@Column(name = "ordIsValid")
//		@NotEmpty(message="訂單表格有效狀態: 請勿空白")
//		@Size(min=1,max=3,message="公司帳號狀態: 長度必需在{min}到{max}之間")
		public Integer getOrdIsValid() {
			return this.ordIsValid;
		}
		
		public void setOrdIsValid(Integer ordIsValid) {
			this.ordIsValid = ordIsValid;
		}
		
		
	}



