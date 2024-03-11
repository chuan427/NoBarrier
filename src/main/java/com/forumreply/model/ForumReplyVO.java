package com.forumreply.model;

import java.sql.Timestamp;
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

import com.forumpost.model.ForumPostVO;
import com.forumreport.model.ForumReportVO;
import com.user.model.UserVO;

@Entity // 要加上@Entity才能成為JPA的一個Entity類別
@Table(name = "forumReply") // 代表這個class是對應到資料庫的實體table，目前對應的table是userInformation
public class ForumReplyVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Integer frNum;
	private ForumPostVO forumPostVO;
	private UserVO userVO;
	private String frContent;
	private byte[] frImage;
	private Timestamp frTime;
	private Timestamp frUpdate;
	private Integer frLike;
	private Integer frStat;
	private Set<ForumReportVO> forumReport = new HashSet<ForumReportVO>();

//	private Integer frUserid; //與user關聯
//	private Integer frFpnum;  //與ForumPost關聯


	public ForumReplyVO() { // 必需有一個不傳參數建構子(JavaBean基本知識)
	}

	@Id // @Id代表這個屬性是這個Entity的唯一識別屬性，並且對映到Table的主鍵
	@Column(name = "frNum") // @Column指這個屬性是對應到資料庫Table的哪一個欄位 //【非必要，但當欄位名稱與屬性名稱不同時則一定要用】
	@GeneratedValue(strategy = GenerationType.IDENTITY) // @GeneratedValue的generator屬性指定要用哪個generator
														// //【strategy的GenerationType, 有四種值: AUTO, IDENTITY, SEQUENCE,
														// TABLE】
	public Integer getFrNum() {
		return this.frNum;
	}

	public void setFrNum(Integer frNum) {
		this.frNum = frNum;
	}

	@ManyToOne
	@JoinColumn(name = "frFpnum", referencedColumnName = "fpNum")
	public ForumPostVO getForumPostVO() {
		return forumPostVO;
	}

	public void setForumPostVO(ForumPostVO forumPostVO) {
		this.forumPostVO = forumPostVO;
	}

	@ManyToOne
	@JoinColumn(name = "frUserid", referencedColumnName = "userId")
	public UserVO getUserVO() {
		return userVO;
	}

	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}

	@Column(name = "frContent")
	@NotEmpty(message = "回文內容: 請勿空白")
	public String getFrContent() {
		return this.frContent;
	}

	public void setFrContent(String frContent) {
		this.frContent = frContent;
	}

	@Column(name = "frLike")
	public Integer getFrLike() {
		return this.frLike;
	}

	public void setFrLike(Integer frLike) {
		this.frLike = frLike;
	}

	@Column(name = "frImage")
//	@NotEmpty(message="特賣商品圖片: 請上傳圖片") --> 由UserController.java 第60行處理錯誤信息
	public byte[] getFrImage() {
		return frImage;
	}

	public void setFrImage(byte[] frImage) {
		this.frImage = frImage;
	}

	@Column(name = "frTime" ,  updatable = false)
	public void setFrTime(Timestamp frTime) {
		this.frTime = frTime;
	}

	public Timestamp getFrTime() {
		return this.frTime;
	}

	@Column(name = "frUpdate")
	public Timestamp getFrUpdate() {
		return this.frUpdate;
	}

	public void setFrUpdate(Timestamp frUpdate) {
		this.frUpdate = frUpdate;
	}

	public Integer getFrStat() {
		return frStat;
	}

	public void setFrStat(Integer frStat) {
		this.frStat = frStat;
	}

	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "forumReplyVO")
	@OrderBy("frpFrnum asc")
	public Set<ForumReportVO> getForumReport() {
		return forumReport;
	}

	public void setForumReport(Set<ForumReportVO> forumReport) {
		this.forumReport = forumReport;
	}

	
	
	
	
	
	
	
//	@PrePersist
//	protected void onCreate() {
//		this.frTime = LocalDateTime.now();
//		
//	}
//
//	@PreUpdate
//	protected void onUpdate() {
//		this.frTime = LocalDateTime.now();
//	}
//
//	public LocalDateTime getFrTime() {
//		return frTime;
//	}
//
//	public void setFrTime(LocalDateTime frTime) {
//		this.frTime = frTime;
//	}

//	public Integer getFrUserid() {
//	return this.frUserid;
//	}
//
//	public void setFrUserid(Integer frUserid) {
//	this.frUserid = frUserid;
//	}

//	@Column(name = "frFpnum")
//	public Integer getFrFpnum() {
//		return frFpnum;
//	}
//
//	public void setFrFpnum(Integer frFpnum) {
//		this.frFpnum = frFpnum;
//	}

}
