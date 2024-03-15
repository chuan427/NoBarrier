package com.reqorder.model;

import java.sql.Date;
import java.time.LocalDate;
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
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.industry.model.IndustryVO;
import com.order.model.OrderVO;
import com.quo.model.QuoVO;
import com.user.model.UserVO;

@Entity // 要加上@Entity才能成為JPA的一個Entity類別
@Table(name = "reqorder") // 代表這個class是對應到資料庫的實體table，目前對應的table是reqOrder
//@Where(clause = "reqIsValid=1")
public class ReqOrderVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private Integer reqNum;
	private UserVO userVO;
	private IndustryVO industryVO;
	private OrderVO orderVO; 
	private Date reqOrderdate = Date.valueOf(LocalDate.now());
	private String reqProdname;
	private String reqUnitname;
	private Integer reqProdqty;
	private byte[] reqProdimage;
	private String reqDes;
//	private Integer reqCategory;
//	private Integer reqUserid;
	private Integer reqIsValid;
	private OrderVO orderVO;
	private Set<QuoVO> quotations = new HashSet<QuoVO>();
	public ReqOrderVO() {

	}

	@Id
	@Column(name = "reqNum")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getReqNum() {
		return this.reqNum;
	}

	public void setReqNum(Integer reqNum) {
		this.reqNum = reqNum;
	}
	
	@MapsId 
	@JoinColumn(name="reqNum", referencedColumnName = "ordNum")
	public OrderVO getOrderVO() {
		return orderVO;
	}

	public void setOrderVO(OrderVO orderVO) {
		this.orderVO = orderVO;
	}

	@ManyToOne
	@JoinColumn(name = "reqUserid", referencedColumnName = "userid")   // 指定用來join table的column
	public UserVO getUserVO() {
		return this.userVO;
	}

	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}
	
	
	@ManyToOne
	@JoinColumn(name = "reqCategory", referencedColumnName = "industryNum")   // 指定用來join table的column
	public IndustryVO getIndustryVO() {
		return this.industryVO;
	}

	public void setIndustryVO(IndustryVO industryVO) {
		this.industryVO = industryVO;
	}
	
	@Column(name = "reqOrderdate")
	public Date getReqOrderdate() {
		return this.reqOrderdate;
	}

	public void setReqOrderdate(Date reqOrderdate) {
		this.reqOrderdate = reqOrderdate;
	}
	
	@Column(name = "reqProdname")
	@NotEmpty(message="商品名稱: 請勿空白")
	@Pattern(regexp = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$", message = "商品名稱: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間")
	public String getReqProdname() {
		return this.reqProdname;
	}

	public void setReqProdname(String reqProdname) {
		this.reqProdname = reqProdname;
	}

	@Column(name = "reqUnitname")
	@NotEmpty(message="單位: 請勿空白")
	@Pattern(regexp = "^[(\u4e00-\u9fa5)(a-zA-Z_)]{1,10}$", message = "單位: 只能是中、英文字母和_ , 且長度必需在1到10之間")
	public String getReqUnitname() {
		return this.reqUnitname;
	}

	public void setReqUnitname(String reqUnitname) {
		this.reqUnitname = reqUnitname;
	}

	@Column(name = "reqProdqty")
	public Integer getReqProdqty() {
		return this.reqProdqty;
	}

	public void setReqProdqty(Integer reqProdqty) {
		this.reqProdqty = reqProdqty;
	}

	@Column(name = "reqProdimage")
	public byte[] getReqProdimage() {
		return this.reqProdimage;
	}

	public void setReqProdimage(byte[] reqProdimage) {
		this.reqProdimage = reqProdimage;
	}

	@Column(name = "reqDes")
	@NotEmpty(message="文字敘述: 請勿空白")
	public String getReqDes() {
		return this.reqDes;
	}

	public void setReqDes(String reqDes) {
		this.reqDes = reqDes;
	}

//	@Column(name = "reqCategory")
//	public Integer getReqCategory() {
//		return this.reqCategory;
//	}
//
//	public void setReqCategory(Integer reqCategory) {
//		this.reqCategory = reqCategory;
//	}
//
//	@Column(name = "reqUserid")
//	public Integer getReqUserid() {
//		return this.reqUserid;
//	}
//
//	public void setReqUserid(Integer reqUserid) {
//		this.reqUserid = reqUserid;
//	}

	@Column(name = "reqIsValid")
	public Integer getReqIsValid() {
		return this.reqIsValid;
	}

	public void setReqIsValid(Integer reqIsValid) {
		this.reqIsValid = reqIsValid;
	}

	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="reqOrderVO")
	@OrderBy("quoNum asc")
	public Set<QuoVO> getQuotations() {
		return quotations;
	}

	public void setQuotations(Set<QuoVO> quotations) {
		this.quotations = quotations;
	}
	
	
}
