package com.productinformation.model;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public ProductInformationVO getOneProductInformation(Integer pinfoUserid) {
        Optional<ProductInformationVO> optional = repository.findById(pinfoUserid);
        return optional.orElse(null);
    }

    public List<ProductInformationVO> getAll() {
        return repository.findAll();
    }
}
