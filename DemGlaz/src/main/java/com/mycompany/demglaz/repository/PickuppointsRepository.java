package com.mycompany.demglaz.repository;

import com.mycompany.demglaz.entity.Pickuppoints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PickuppointsRepository extends JpaRepository<Pickuppoints, Integer> {
}