package com.rptdlist.model;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.order.model.OrderVO;

@Entity
@Table(name = "reportedList")
public class RptdlistVO implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "rptdNum")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer rptdNum;
	
	@Column(name = "rptdTitle")
	private String rptdTitle;
	
//	@OneToOne(mappedBy="rptdlistVO",cascade=CascadeType.ALL)
//	@PrimaryKeyJoinColumn 
//	private OrderVO orderVO;
	
	@Column(name = "rptdOrdernum")
	private Integer rptdOrdernum;
	
	@Column(name = "rptdDate")
	private Date rptdDate;
	
	@Column(name = "rptdContent", columnDefinition = "text")
	private String rptdContent;
	
	@Column(name = "rptdStat")
	private Integer rptdStat;
	
	@Column(name = "rptdIsValid")
	private Integer rptdIsValid;
	
	
	
	public RptdlistVO() {
	}

	public Integer getRptdNum() {
		return rptdNum;
	}

	public void setRptdNum(Integer rptdNum) {
		this.rptdNum = rptdNum;
	}

	public String getRptdTitle() {
		return rptdTitle;
	}

	public void setRptdTitle(String rptdTitle) {
		this.rptdTitle = rptdTitle;
	}

	public Integer getRptdOrdernum() {
		return rptdOrdernum;
	}

	public void setRptdOrdernum(Integer rptdOrdernum) {
		this.rptdOrdernum = rptdOrdernum;
	}

	public Date getRptdDate() {
		return rptdDate;
	}

	public void setRptdDate(Date rptdDate) {
		this.rptdDate = rptdDate;
	}

	public String getRptdContent() {
		return rptdContent;
	}

	public void setRptdContent(String rptdContent) {
		this.rptdContent = rptdContent;
	}

	public Integer getRptdStat() {
		return rptdStat;
	}

	public void setRptdStat(Integer rptdStat) {
		this.rptdStat = rptdStat;
	}

	public Integer getRptdIsValid() {
		return rptdIsValid;
	}

	public void setRptdIsValid(Integer rptdIsValid) {
		this.rptdIsValid = rptdIsValid;
	}

//	public OrderVO getOrderVO() {
//		return orderVO;
//	}
//
//	public void setOrderVO(OrderVO orderVO) {
//		this.orderVO = orderVO;
//	}

	
}
