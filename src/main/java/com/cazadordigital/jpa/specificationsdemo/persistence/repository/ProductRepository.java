package com.cazadordigital.jpa.specificationsdemo.persistence.repository;

import com.cazadordigital.jpa.specificationsdemo.persistence.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    /**
     * Find all products that meet the follow the next predicates:
     * product.name like %:name% with ignore case
     * price <= :maxPrice
     * price >= :minPrice
     * category.name = :categoryName with ignore case
     */
    List<Product> findAllByNameContainingIgnoreCaseAndPriceLessThanEqualAndPriceGreaterThanEqualAndCategoryNameContainingIgnoreCase(String name, BigDecimal minPrice, BigDecimal maxPrice, String categoryName);

    /**
     * Find all products that meet the follow the next predicates:
     * price <= :maxPrice
     */
    List<Product> findAllByPriceLessThanEqual(BigDecimal maxPrice);

    /**
     * Find all products that meet the follow the next predicates:
     * product.name like %:name% with ignore case
     */
    List<Product> findAllByNameContainingIgnoreCase(String name);

    /**
     * Find all products that meet the follow the next predicates:
     * product.name like %:name% with ignore case
     * category.name = :categoryName with ignore case
     */
    List<Product> findAllByNameContainingIgnoreCaseAndCategoryNameContainingIgnoreCase(String name, String categoryName);

    /**
     * Find all products that meet the follow the next predicates:
     * price >= :minPrice
     * price <= :maxPrice
     */
    List<Product> findAllByPriceLessThanEqualAndPriceGreaterThanEqual(BigDecimal maxPrice, BigDecimal minPrice);

    /**
     * Find all products that meet the follow the next predicates:
     * product.name like %:name% with ignore case
     * category.name = :categoryName with ignore case
     * price <= :maxPrice
     */
    List<Product> findAllByNameContainingIgnoreCaseAndCategoryNameIgnoreCaseAndPriceLessThanEqual(String name, String categoryName, BigDecimal maxPrice);

    /**
     * Find all products that meet the follow the next predicates:
     * product.name like %:name% with ignore case
     * category.name = :categoryName with ignore case
     * price >= :minPrice
     */
    List<Product> findAllByNameContainingIgnoreCaseAndCategoryNameIgnoreCaseAndPriceGreaterThanEqual(String name, String categoryName, BigDecimal minPrice);


}
