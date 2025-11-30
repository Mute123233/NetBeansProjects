package com.mycompany.demglaz.repository;

import com.mycompany.demglaz.entity.Suppliers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuppliersRepository extends JpaRepository<Suppliers, Integer> {
    Suppliers findByName(String name);
}