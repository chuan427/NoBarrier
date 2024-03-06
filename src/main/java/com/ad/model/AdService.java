package com.ad.model;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.addday.AdDate;
import com.advertisements.model.AdvertisementsRepository;
import com.advertisements.model.AdvertisementsVO;


@Service("adService")
public class AdService {

	@Autowired
	AdRepository repository;

	@Autowired
	AdvertisementsRepository advertisementsrepository;

	public void addEmp(AdVO adVO) {
		repository.save(adVO);
	}

	public void updateEmp(AdVO adVO) {
		repository.save(adVO);
	}

	public void deleteEmp(Integer adOrdernum) {
		if (repository.existsById(adOrdernum))
			repository.deleteByAd_ordernum(adOrdernum);
//		    repository.deleteById(empno);
	}

	public AdVO getOneEmp(Integer adOrdernum) {
		Optional<AdVO> optional = repository.findById(adOrdernum);
//		return optional.get();
		return optional.orElse(null); // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
	}

	public List<AdVO> getAll() {
		return repository.findAll();
	}
	

	public void addAdAndAdvertisements(AdDate addate) {
        AdVO advo;
        advo = new AdVO();
        advo.setAdPrice(addate.getAdPriceadd());
        advo.setAdImage(addate.getAdImageadd());
        advo.setAdDuration(addate.getAdvertisements().size());
        
        
        List<AdvertisementsVO> preparedToSaveEmployees = addate.getAdvertisements();
        preparedToSaveEmployees.forEach(advertisements -> advertisements.setAdVO(advo));
        advo.setAdvertisements(preparedToSaveEmployees);
		repository.save(advo);
		
	}


}
