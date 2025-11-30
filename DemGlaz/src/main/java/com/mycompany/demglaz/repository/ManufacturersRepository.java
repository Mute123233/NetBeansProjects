package com.mycompany.demglaz.repository;

import com.mycompany.demglaz.entity.Manufacturers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManufacturersRepository extends JpaRepository<Manufacturers, Integer> {
    Manufacturers findByName(String name);
}