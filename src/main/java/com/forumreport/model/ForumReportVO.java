package com.forumreport.model;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.forumpost.model.ForumPostVO;
import com.forumreply.model.ForumReplyVO;
import com.user.model.UserVO;

@Entity // 要加上@Entity才能成為JPA的一個Entity類別
@Table(name = "forumReport") // 代表這個class是對應到資料庫的實體table，目前對應的table是userInformation
public class ForumReportVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer frpNum; 
	private UserVO userVO; 
	private ForumPostVO forumPostVO; 
	private ForumReplyVO forumReplyVO; 
	private Integer frpType; 

	private Timestamp frpTime; 
	private Integer frpStat; 
	private Timestamp frpDealtime; 
	private Integer frpIsValid; 

//	private Integer frpUserid;
//	private Integer frpFpNum;
//	private Integer frpFrNum;

	public ForumReportVO() {
	}

	@Id
	@Column(name = "frpNum")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getFrpNum() {
		return frpNum;
	}

	public void setFrpNum(Integer frpNum) {
		this.frpNum = frpNum;
	}

	@ManyToOne
	@JoinColumn(name = "frpUserid", referencedColumnName = "userId")
	public UserVO getUserVO() {
		return userVO;
	}

	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}

	@ManyToOne
	@JoinColumn(name = "frpFpnum", referencedColumnName = "fpNum")
	public ForumPostVO getForumPostVO() {
		return forumPostVO;
	}

	public void setForumPostVO(ForumPostVO forumPostVO) {
		this.forumPostVO = forumPostVO;
	}

	@ManyToOne
	@JoinColumn(name = "frpFrNum", referencedColumnName = "frNum")
	public ForumReplyVO getForumReplyVO() {
		return forumReplyVO;
	}

	public void setForumReplyVO(ForumReplyVO forumReplyVO) {
		this.forumReplyVO = forumReplyVO;
	}

	@Column(name = "frpType")
	public Integer getFrpType() {
		return frpType;
	}

	public void setFrpType(Integer frpType) {
		this.frpType = frpType;
	}

	@Column(name = "frpTime", updatable = false)
	public Timestamp getFrpTime() {
		return frpTime;
	}

	public void setFrpTime(Timestamp frpTime) {
		this.frpTime = frpTime;
	}

	@Column(name = "frpStat")
	public Integer getFrpStat() {
		return frpStat;
	}

	public void setFrpStat(Integer frpStat) {
		this.frpStat = frpStat;
	}

	@Column(name = "frpDealtime")
	public Timestamp getFrpDealtime() {
		return frpDealtime;
	}

	public void setFrpDealtime(Timestamp frpDealtime) {
		this.frpDealtime = frpDealtime;
	}

	@Column(name = "frpIsValid")
	public Integer getFrpIsValid() {
		return frpIsValid;
	}

	public void setFrpIsValid(Integer frpIsValid) {
		this.frpIsValid = frpIsValid;
	}

//	@Column(name = "frpFrnum")
//	public Integer getFrpFrnum() {
//		return frpFrnum;
//	}
//
//	public void setFrpFrnum(Integer frpFrnum) {
//		this.frpFrnum = frpFrnum;
//	}

//	public Integer getFrpUserid() {
//		return frpUserid;
//	}
//
//	public void setFrpUserid(Integer frpUserid) {
//		this.frpUserid = frpUserid;
//	}

//	public Integer getFrpFpnum() {
//		return frpFpnum;
//	}

//	public void setFrpFpnum(Integer frpFpnum) {
//		this.frpFpnum = frpFpnum;
//	}

}
