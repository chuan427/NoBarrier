package com.addday;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.advertisements.model.AdvertisementsVO;


public class AdDate {

	private byte[] adImageadd;
	private Integer adPriceadd;
	private List<AdvertisementsVO> advertisements = new ArrayList<>();
	

	
	public AdDate() { // 必需有一個不傳參數建構子(JavaBean基本知識)
	}
	

	public byte[] getAdImageadd() {
		return adImageadd;
	}

	public void setAdImageadd(byte[] adImageadd) {
		this.adImageadd = adImageadd;
	}
	

	public Integer getAdPriceadd() {
		return adPriceadd;
	}

	public void setAdPriceadd(Integer adPriceadd) {
		this.adPriceadd = adPriceadd;
	}



	public List<AdvertisementsVO> getAdvertisements() {
		return advertisements;
	}

	public void setAdvertisements(List<AdvertisementsVO> advertisements) {
		this.advertisements = advertisements;
	}

	

}
