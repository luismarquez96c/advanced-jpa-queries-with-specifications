package com.cazadordigital.jpa.specificationsdemo.persistence.specification;

import com.cazadordigital.jpa.specificationsdemo.persistence.entity.Product;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProductSpecificationProvider {

    public static Specification<Product> priceLessThanEqualSpecification(BigDecimal maxPrice){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice) );
    }

    public static Specification<Product> priceGreaterThanEqualSpecification(BigDecimal minPrice){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice) );
    }

}
