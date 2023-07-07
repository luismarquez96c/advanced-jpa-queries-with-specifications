package com.cazadordigital.jpa.specificationsdemo.web.controller;

import com.cazadordigital.jpa.specificationsdemo.persistence.entity.Category;
import com.cazadordigital.jpa.specificationsdemo.persistence.entity.Product;
import com.cazadordigital.jpa.specificationsdemo.persistence.repository.ProductRepository;
import com.cazadordigital.jpa.specificationsdemo.persistence.specification.ProductSpecificationProvider;
import com.cazadordigital.jpa.specificationsdemo.persistence.specification.SearchProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/v0")
    public List<Product> findAllv0()
    {
        System.out.println("V0");
        return productRepository.findAll();
    }

    @GetMapping("/v1")
    public List<Product> findAllv1(@RequestParam(required = false) String name,
                                 @RequestParam(required = false) BigDecimal minPrice,
                                 @RequestParam(required = false) BigDecimal maxPrice,
                                 @RequestParam(required = false) String category)
    {
        System.out.println("V1");
        SearchProductSpecification specification = new SearchProductSpecification(name, minPrice, maxPrice, category);
        return productRepository.findAll(specification);
    }

    @GetMapping("/v1.1")
    public List<Product> findAllv1_1(@RequestParam(required = false) BigDecimal minPrice,
                                 @RequestParam(required = false) BigDecimal maxPrice)
    {
        System.out.println("V1.1");
        SearchProductSpecification specification = new SearchProductSpecification(null, minPrice, maxPrice, null);
        return productRepository.findAll(specification);
    }


    @GetMapping("/v2")
    public List<Product> findAllv2(@RequestParam(required = false) String name,
                                   @RequestParam(required = false) BigDecimal minPrice,
                                   @RequestParam(required = false) BigDecimal maxPrice,
                                   @RequestParam(required = false) String category)
    {
        System.out.println("V2");
        return productRepository.findAllByNameContainingIgnoreCaseAndPriceLessThanEqualAndPriceGreaterThanEqualAndCategoryNameContainingIgnoreCase(name, maxPrice, minPrice, category);
    }

    @GetMapping("/v2.2")
    public List<Product> findAllv2_2(@RequestParam(required = false) String name,
                                   @RequestParam(required = false) BigDecimal minPrice,
                                   @RequestParam(required = false) BigDecimal maxPrice,
                                   @RequestParam(required = false) String category)
    {
        System.out.println("V2.2");

        if(StringUtils.hasText(name)){
            return productRepository.findAllByNameContainingIgnoreCase(name);
        }

        if(StringUtils.hasText(name) && StringUtils.hasText(category)){
            return productRepository.findAllByNameContainingIgnoreCaseAndCategoryNameContainingIgnoreCase(name, category);
        }

        if(StringUtils.hasText(name) && StringUtils.hasText(category) && minPrice != null && minPrice.equals(BigDecimal.ZERO) ){
            return productRepository.findAllByNameContainingIgnoreCaseAndCategoryNameIgnoreCaseAndPriceGreaterThanEqual(name, category, minPrice);
        }

        if(StringUtils.hasText(name) && StringUtils.hasText(category) && maxPrice != null && minPrice.equals(BigDecimal.ZERO) ){
            return productRepository.findAllByNameContainingIgnoreCaseAndCategoryNameIgnoreCaseAndPriceLessThanEqual(name, category, maxPrice);
        }

        return productRepository.findAllByNameContainingIgnoreCaseAndPriceLessThanEqualAndPriceGreaterThanEqualAndCategoryNameContainingIgnoreCase(name, maxPrice, minPrice, category);
    }

    @GetMapping("/v3")
    public List<Product> findAllv3(@RequestParam(required = false) String name,
                                 @RequestParam(required = false) BigDecimal minPrice,
                                 @RequestParam(required = false) BigDecimal maxPrice,
                                 @RequestParam(required = false) String category)
    {
        System.out.println("V3");

        Product productExample = new Product();
        productExample.setName(name);//Si :name es null entonces no se agregará su predicado al where

        Category categoryExample = new Category();
        categoryExample.setName(category);//Si :category es null entonces no se agregará su predicado al where

        productExample.setCategory(categoryExample);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase() )
                .withMatcher("category.name", ExampleMatcher.GenericPropertyMatchers.exact().ignoreCase());

        Example<Product> example = Example.of(productExample, matcher);

        return productRepository.findAll(example);
    }



    @GetMapping("/v4")
    public List<Product> findAllv4(@RequestParam(required = false) String name,
                                   @RequestParam(required = false) BigDecimal minPrice,
                                   @RequestParam(required = false) BigDecimal maxPrice
                                   )
    {
        System.out.println("V4");

//        Specification<Product> productSpecification = (root, query, cb) -> {
//            return cb.like(cb.lower(root.get("name")), "%".concat(name.toLowerCase()).concat("%") );
//        };
        return productRepository.findAll(ProductSpecificationProvider.priceGreaterThanEqualSpecification(minPrice));
//        return productRepository.findAllByNameContainingIgnoreCase(name);
//        return productRepository.findAll( (root, query, cb) -> cb.lessThanOrEqualTo(root.get("price"), maxPrice) );
//        return productRepository.findAll( (root, query, cb) -> cb.between(root.get("price"), minPrice, maxPrice) );
    }

}

