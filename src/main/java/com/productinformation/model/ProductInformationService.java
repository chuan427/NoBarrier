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

    public void deleteProductInformation(Integer pinfoNum) {
        if (repository.existsById(pinfoNum)) {
            repository.deleteById(pinfoNum);
        } else {
            throw new IllegalArgumentException("產品資訊不存在");
        }
    }

    public ProductInformationVO getOneProductInformation(Integer pinfoNum) {
        Optional<ProductInformationVO> optional = repository.findById(pinfoNum);
        return optional.orElse(null);
    }

    public List<ProductInformationVO> getAll() {
        return repository.findAll();
    }
}
