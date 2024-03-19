package com.productinformation.model;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.model.UserVO;

@Service("productInformationService")
public class ProductInformationService {

    @Autowired
    ProductInformationRepository repository;

    public void addProductInformation(ProductInformationVO productInformationVO) {
        repository.save(productInformationVO);
    }

    public void updateProductInformation(ProductInformationVO productInformationVO) {
        repository.save(productInformationVO);
    }

    public void deleteProductInformation(Integer pinfoUserid) {
        if (repository.existsById(pinfoUserid)) {
            repository.deleteById(pinfoUserid);
        } else {
            throw new IllegalArgumentException("產品資訊不存在");
        }
    }

//    public ProductInformationVO getOneProductInformation(Integer pinfoUserid) {
//        Optional<ProductInformationVO> optional = repository.findById(pinfoUserid);
//        return optional.orElse(null);
//    }

    public List<ProductInformationVO> getAll() {
        return repository.findAll();
    }
    
    public ProductInformationVO getProductInformationByUserIdVo(Integer userId) {
        return (ProductInformationVO) repository.findByUserVOUserId(userId);
    }
    
    public List<ProductInformationVO> getProductInformationByUserId(Integer userId) {
        return repository.findByUserVOUserId(userId);
    }
    
	public ProductInformationVO getOnePinfoNum(Integer pinfoNum) {
		Optional<ProductInformationVO> optional = repository.findById(pinfoNum);
//		return optional.get();
		return optional.orElse(null);  // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
	}
    
    public ProductInformationVO getOneProductInformation(Integer pinfoUserid) {
        Optional<ProductInformationVO> optional = repository.findById(pinfoUserid);
        ProductInformationVO productInformation = optional.orElse(null);
        if (productInformation != null) {
            // 如果您的ProductInformationVO包含getUserVO()方法，则可以通过getUserVO()方法获取UserVO对象
            // 从UserVO对象中获取userid
        	Integer userId = productInformation.getUserVO().getUserId();
            // 通过userid查询相关的ProductInformationVO对象列表
//            List<ProductInformationVO> relatedProductInformationList = repository.findByUserVOUserId(userId);
            // 这里您可以根据需要处理相关的ProductInformationVO对象列表
        }
        return productInformation;
    }
    
}
