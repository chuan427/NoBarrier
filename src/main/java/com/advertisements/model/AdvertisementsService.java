package com.advertisements.model;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ad.model.AdVO;

@Service("AdvertisementsService")
public class AdvertisementsService {

	@Autowired
	AdvertisementsRepository advertisementsrepository;

	@Transactional
	public void addAdvertisements(AdvertisementsVO advertisementsVO) {
		advertisementsrepository.save(advertisementsVO);
	}

	public void updateAdvertisements(AdvertisementsVO advertisementsVO) {
		advertisementsrepository.save(advertisementsVO);
	}

	public void deleteAdvertisements(Integer adsWhen) {
		if (advertisementsrepository.existsById(adsWhen))
			advertisementsrepository.deleteById(adsWhen);
	}


	public AdvertisementsVO getOneAdvertisements(Integer adsWhen) {
		Optional<AdvertisementsVO> optional = advertisementsrepository.findById(adsWhen);
//		return optional.get();
		return optional.orElse(null);  // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
	}

	public List<AdvertisementsVO> getAll() {
		return advertisementsrepository.findAll();
	}



}
