package com.cazadordigital.jpa.specificationsdemo.persistence.repository;

import com.cazadordigital.jpa.specificationsdemo.persistence.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {
}
