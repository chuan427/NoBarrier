package com.industry.model;

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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.reqorder.model.ReqOrderVO;
import com.user.model.UserVO;

@Entity // 要加上@Entity才能成為JPA的一個Entity類別
@Table(name = "industry") // 代表這個class是對應到資料庫的實體table，目前對應的table是industry
public class IndustryVO implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer industryNum;
	private String industryName;
	private String industryDes;
	private Integer industryIsValid;
	private Set<UserVO> user = new HashSet<UserVO>();
	private Set<ReqOrderVO> reqOrder = new HashSet<ReqOrderVO>();
	
	public IndustryVO() {
		
	}

	@Id
	@Column(name = "industryNum")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getIndustryNum() {
		return this.industryNum;
	}

	public void setIndustryNum(Integer industryNum) {
		this.industryNum = industryNum;
	}

//	@ManyToOne
//	@JoinColumn(name = "industryNum", insertable = false, updatable = false)   // 指定用來join table的column
//	public UserVO getUserVO() {
//		return this.userVO;
//	}
//
//	public void setUserVO(UserVO userVO) {
//		this.userVO = userVO;
//	}
	
	
	
	@Column(name = "industryName")
	@NotEmpty(message="產業名稱: 請勿空白")
	@Pattern(regexp = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$", message = "產業名稱: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間")
	public String getIndustryName() {
		return this.industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	@Column(name = "industryDes")
	@NotEmpty(message="產業描述: 請勿空白")
	public String getIndustryDes() {
		return this.industryDes;
	}

	public void setIndustryDes(String industryDes) {
		this.industryDes = industryDes;
	}

	@Column(name = "industryIsValid")
	public Integer getIndustryIsValid() {
		return this.industryIsValid;
	}

	public void setIndustryIsValid(Integer industryIsValid) {
		this.industryIsValid = industryIsValid;
	}
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="industryVO")
	@OrderBy("comIndustry asc")
	public Set<UserVO> getUser() {
		return this.user;
	}

	public void setUser(Set<UserVO> user) {
		this.user = user;
	}
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="industryVO")
	@OrderBy("reqCategory asc")
	public Set<ReqOrderVO> getReqOrder() {
		return this.reqOrder;
	}

	public void setReqOrder(Set<ReqOrderVO> reqOrder) {
		this.reqOrder = reqOrder;
	}
	
}
