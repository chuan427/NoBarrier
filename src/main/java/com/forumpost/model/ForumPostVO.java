package com.forumpost.model;

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
//import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.forumreply.model.ForumReplyVO;
import com.forumreport.model.ForumReportVO;
import com.user.model.UserVO;

@Entity // 要加上@Entity才能成為JPA的一個Entity類別
@Table(name = "forumPost") // 代表這個class是對應到資料庫的實體table，目前對應的table是userInformation
public class ForumPostVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Integer fpNum;
	private UserVO userVO;
	private Integer fpCategory;
	private String fpTitle;
	private String fpContent;
	private byte[] fpImage;
	private Timestamp fpTime;
	private Timestamp fpUpdate;
	private Integer fpLike;
	private Integer fpStat;
	private Set<ForumReplyVO> forumReply = new HashSet<ForumReplyVO>();
	private Set<ForumReportVO> forumReport = new HashSet<ForumReportVO>();

//	private Integer fpIsValid;
//	private Integer fpUserid;

	public ForumPostVO() { 
	}

	@Id // @Id代表這個屬性是這個Entity的唯一識別屬性，並且對映到Table的主鍵
	@Column(name = "fpNum") // @Column指這個屬性是對應到資料庫Table的哪一個欄位 //【非必要，但當欄位名稱與屬性名稱不同時則一定要用】
	@GeneratedValue(strategy = GenerationType.IDENTITY) // @GeneratedValue的generator屬性指定要用哪個generator
														// //【strategy的GenerationType, 有四種值: AUTO, IDENTITY, SEQUENCE,
														// TABLE】
	public Integer getFpNum() {
		return this.fpNum;
	}

	public void setFpNum(Integer fpNum) {
		this.fpNum = fpNum;
	}

	@ManyToOne
	@JoinColumn(name = "fpUserid", referencedColumnName = "userId")
	public UserVO getUserVO() {
		return userVO;
	}

	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}

	@Column(name = "fpCategory")
	public Integer getFpCategory() {
		return this.fpCategory;
	}

	public void setFpCategory(Integer fpCategory) {
		this.fpCategory = fpCategory;
	}

	@Column(name = "fpTitle")
	@NotEmpty(message = "文章標題: 請勿空白")
	@Pattern(regexp = "^[\u4E00-\u9FA5a-zA-Z0-9\\p{P}]{0,50}$", message = "文章標題: 最多只能輸入 50 個字，包含中文、英文、數字和標點符號，不可以包含空格")
	public String getFpTitle() {
		return this.fpTitle;
	}

	public void setFpTitle(String fpTitle) {
		this.fpTitle = fpTitle;
	}

	@Column(name = "fpContent")
	@NotEmpty(message = "文章內容: 請勿空白")
//	@Pattern(regexp = "^[\u4E00-\u9FA5a-zA-Z0-9\\p{P}]{0,50}$", message = "文章標題: 最多只能輸入 50 個字，包含中文、英文、數字和標點符號，不可以包含空格")
	public String getFpContent() {
		return this.fpContent;
	}

	public void setFpContent(String fpContent) {
		this.fpContent = fpContent;
	}

	@Column(name = "fpStat")
	public Integer getFpStat() {
		return this.fpStat;
	}

	public void setFpStat(Integer fpStat) {
		this.fpStat = fpStat;
	}

	@Column(name = "fpImage")
	public byte[] getFpImage() {
		return fpImage;
	}

	public void setFpImage(byte[] fpImage) {
		this.fpImage = fpImage;
	}

	@Column(name = "fpTime" , updatable = false)
	public Timestamp getFpTime() {
		return fpTime;
	}

	public void setFpTime(Timestamp fpTime) {

		this.fpTime = fpTime;

	}

	@Column(name = "fpUpdate")
	public Timestamp getFpUpdate() {
		return fpUpdate;
	}

	public void setFpUpdate(Timestamp fpUpdate) {
		this.fpUpdate = fpUpdate;

	}

	@Column(name = "fpLike")
	public Integer getFpLike() {
		return fpLike;
	}

	public void setFpLike(Integer fpLike) {
		this.fpLike = fpLike;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "forumPostVO")
	@OrderBy("frFpnum asc")
	public Set<ForumReplyVO> getForumReply() {
		return forumReply;
	}

	public void setForumReply(Set<ForumReplyVO> forumReply) {
		this.forumReply = forumReply;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "forumPostVO")
	@OrderBy("frpFpnum asc")
	public Set<ForumReportVO> getForumReport() {
		return forumReport;
	}

	public void setForumReport(Set<ForumReportVO> forumReport) {
		this.forumReport = forumReport;
	}
	
	

//	@PrePersist
//	protected void onCreate() {
//		this.fpTime = LocalDateTime.now();
//	}
//
//	@PreUpdate
//	protected void onUpdate() {
//		this.fpTime = LocalDateTime.now();
//	}
//
//	@Column(name = "fpTime")
//  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//	public LocalDateTime getFpTime() {
//		return fpTime;
//	}

//	public Integer getFpUserid() {
//		return this.fpUserid;
//	}
//
//	public void setFpUserid(Integer fpUserid) {
//		this.fpUserid = fpUserid;
	
}
