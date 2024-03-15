package com.quo.model;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.order.model.OrderVO;
import com.reqorder.model.ReqOrderVO;
import com.user.model.UserVO;

@Entity
@Table(name = "quotation")
public class QuoVO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "quoNum")
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment的@
	private Integer quoNum;

	@Column(name = "quoDate",columnDefinition = "DATE DEFAULT CURRENT_DATE" )
	private Date quoDate = Date.valueOf(LocalDate.now());

	@Column(name = "quoProdname")
	private String quoProdname;

	@Column(name = "quoUnitname")
	private String quoUnitname;

	@Column(name = "quoProdqty")
	private Integer quoProdqty;

	@Column(name = "quoUnitprice")
	private Integer quoUnitprice;

	@Column(name = "quoTotalprice")
	private Integer quoTotalprice;

	@Column(name = "quoInfo", columnDefinition = "text")
	private String quoInfo;

	// 訂單對報價一對一
	@OneToOne
	@MapsId
	@JoinColumn(name = "quoNum", referencedColumnName = "ordNum") // 先改為order的ID
	private OrderVO orderVO;
	
	@ManyToOne
	@JoinColumn(name = "quoReqnum", referencedColumnName = "reqNum")
	private ReqOrderVO reqOrderVO;
	
	@ManyToOne
	@JoinColumn(name = "quoUserid", referencedColumnName = "userid")
	private UserVO userVO;

	@Column(name = "quoIsValid")
	private Integer quoIsValid;
	
	@OneToOne
    @JoinColumn(name = "quoNum", referencedColumnName = "ordNum")
	private OrderVO orderVO;

	public QuoVO() {
	}

	public Integer getQuoNum() {
		return quoNum;
	}

	public void setQuoNum(Integer quoNum) {
		this.quoNum = quoNum;
	}

	public Date getQuoDate() {
		return quoDate;
	}

	public void setQuoDate(Date quoDate) {
		this.quoDate = quoDate;
	}

	public String getQuoProdname() {
		return quoProdname;
	}

	public void setQuoProdname(String quoProdname) {
		this.quoProdname = quoProdname;
	}

	public String getQuoUnitname() {
		return quoUnitname;
	}

	public void setQuoUnitname(String quoUnitname) {
		this.quoUnitname = quoUnitname;
	}

	public Integer getQuoProdqty() {
		return quoProdqty;
	}

	public void setQuoProdqty(Integer quoProdqty) {
		this.quoProdqty = quoProdqty;
	}

	public Integer getQuoUnitprice() {
		return quoUnitprice;
	}

	public void setQuoUnitprice(Integer quoUnitprice) {
		this.quoUnitprice = quoUnitprice;
	}

	public Integer getQuoTotalprice() {
		return quoTotalprice;
	}

	public void setQuoTotalprice(Integer quoTotalprice) {
		this.quoTotalprice = quoTotalprice;
	}

	public String getQuoInfo() {
		return quoInfo;
	}

	public void setQuoInfo(String quoInfo) {
		this.quoInfo = quoInfo;
	}

	public ReqOrderVO getReqOrderVO() {
		return reqOrderVO;
	}

	public void setReqOrderVO(ReqOrderVO reqOrderVO) {
		this.reqOrderVO = reqOrderVO;
	}

	public UserVO getUserVO() {
		return userVO;
	}

	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}

	public Integer getQuoIsValid() {
		return quoIsValid;
	}

	public void setQuoIsValid(Integer quoIsValid) {
		this.quoIsValid = quoIsValid;
	}

	public OrderVO getOrderVO() {
        return orderVO;
    }

    public void setOrderVO(OrderVO orderVO) {
        this.orderVO = orderVO;
    }
    

	@Override
	public String toString() {
		return "QuoVO [quoNum=" + quoNum + ", quoDate=" + quoDate + ", quoProdname=" + quoProdname + ", quoUnitname="
				+ quoUnitname + ", quoProdqty=" + quoProdqty + ", quoUnitprice=" + quoUnitprice + ", quoTotalprice="
				+ quoTotalprice + ", quoInfo=" + quoInfo + ", reqOrderVO=" + reqOrderVO + ", userVO=" + userVO
				+ ", quoIsValid=" + quoIsValid + "]";
	}

	

	
	
	
}
