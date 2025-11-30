package com.mycompany.demglaz.repository;

import com.mycompany.demglaz.entity.Orderitems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderitemsRepository extends JpaRepository<Orderitems, Integer> {
}