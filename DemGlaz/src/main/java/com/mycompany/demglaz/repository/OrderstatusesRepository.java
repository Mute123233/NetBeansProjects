package com.mycompany.demglaz.repository;

import com.mycompany.demglaz.entity.Orderstatuses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderstatusesRepository extends JpaRepository<Orderstatuses, Integer> {
    Orderstatuses findByName(String name);
}