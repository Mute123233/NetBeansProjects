package com.mycompany.demglaz.repository;

import com.mycompany.demglaz.entity.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Integer> {
    Categories findByName(String name);
}