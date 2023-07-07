package com.cazadordigital.jpa.specificationsdemo.web.controller;

import com.cazadordigital.jpa.specificationsdemo.persistence.entity.Category;
import com.cazadordigital.jpa.specificationsdemo.persistence.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/v0")
    public List<Category> findAllv0(){
        return categoryRepository.findAll();
    }

    @GetMapping("/v1")
    public List<Category> findAllv1(){

        Specification<Category> categoryWithoutProductsSpecification = (root, query, criteriaBuilder) -> {
            return criteriaBuilder.isEmpty(root.get("products"));
        };

        return categoryRepository.findAll(categoryWithoutProductsSpecification);
    }

}
