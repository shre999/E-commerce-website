package com.sport.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sport.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

}
