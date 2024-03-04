package com.notification.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "notification")
public class NotificationVO implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	private Integer notiNum;
	private Integer notiUserid;
	private Timestamp notiSenttime;
	private String notiContent;
	private Integer notiReadstat;
	private Integer notiIsValid;
	
	public NotificationVO() {
		
	}

	@Id
	@Column(name = "notiNum")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getNotiNum() {
		return this.notiNum;
	}

	public void setNotiNum(Integer notiNum) {
		this.notiNum = notiNum;
	}

	@Column(name = "notiUserid")
	public Integer getNotiUserid() {
		return this.notiUserid;
	}

	public void setNotiUserid(Integer notiUserid) {
		this.notiUserid = notiUserid;
	}

	@Column(name = "notiSenttime")
	public Timestamp getNotiSenttime() {
		return this.notiSenttime;
	}

	public void setNotiSenttime(Timestamp notiSenttime) {
		this.notiSenttime = notiSenttime;
	}

	@Column(name = "notiContent")
	@NotEmpty(message="通知訊息: 請勿空白")
	public String getNotiContent() {
		return this.notiContent;
	}

	public void setNotiContent(String notiContent) {
		this.notiContent = notiContent;
	}

	@Column(name = "notiReadstat")
	public Integer getNotiReadstat() {
		return this.notiReadstat;
	}

	public void setNotiReadstat(Integer notiReadstat) {
		this.notiReadstat = notiReadstat;
	}

	@Column(name = "notiIsValid")
	public Integer getNotiIsValid() {
		return this.notiIsValid;
	}

	public void setNotiIsValid(Integer notiIsValid) {
		this.notiIsValid = notiIsValid;
	}
	
}
