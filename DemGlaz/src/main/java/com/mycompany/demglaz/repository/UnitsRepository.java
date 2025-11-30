package com.mycompany.demglaz.repository;

import com.mycompany.demglaz.entity.Units;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitsRepository extends JpaRepository<Units, Integer> {
    Units findByName(String name);
}