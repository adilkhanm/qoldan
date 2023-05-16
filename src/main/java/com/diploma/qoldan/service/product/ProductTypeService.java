package com.diploma.qoldan.service.product;

import com.diploma.qoldan.dto.product.ProductTypeDto;
import com.diploma.qoldan.exception.product.ProductTypeExistsException;
import com.diploma.qoldan.exception.product.ProductTypeNotFoundException;
import com.diploma.qoldan.mapper.product.ProductTypeMapper;
import com.diploma.qoldan.model.product.ProductType;
import com.diploma.qoldan.repository.product.ProductTypeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductTypeService {

    private final ProductTypeRepo repo;
    private final ProductTypeMapper mapper;

    public List<ProductTypeDto> getProductTypes() {
        List<ProductType> productTypeList = repo.findAll();
        return productTypeList
                .stream()
                .map(mapper::mapTypeToDto)
                .toList();
    }

    @Transactional
    public Long createProductType(ProductTypeDto productTypeDto) throws ProductTypeExistsException {
        ProductType productType = repo.findByTitle(productTypeDto.getTitle());
        if (productType != null) {
            throw new ProductTypeExistsException("");
        }

        productType = mapper.mapDtoToType(productTypeDto);
        repo.save(productType);
        return productType.getId();
    }

    @Transactional
    public void updateProductType(ProductTypeDto productTypeDto) throws ProductTypeNotFoundException {
        ProductType productType = repo.findById(productTypeDto.getId());
        if (productType == null) {
            throw new ProductTypeNotFoundException("");
        }

        productType = mapper.mapDtoToType(productTypeDto);
        repo.save(productType);
    }

    @Transactional
    public void deleteProductTypeById(Long productTypeId) throws ProductTypeNotFoundException {
        ProductType productType = repo.findById(productTypeId);
        if (productType == null) {
            throw new ProductTypeNotFoundException("");
        }

        repo.delete(productType);
    }

    ProductType findTypeByTitle(String title) throws ProductTypeNotFoundException {
        ProductType productType = repo.findByTitle(title);
        if (productType == null)
            throw new ProductTypeNotFoundException("");
        return productType;
    }
}
