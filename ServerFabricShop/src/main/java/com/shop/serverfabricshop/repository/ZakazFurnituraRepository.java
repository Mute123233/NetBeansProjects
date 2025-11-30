/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.shop.serverfabricshop.repository;

import com.shop.serverfabricshop.entity.Zakazfurnitura;
import com.shop.serverfabricshop.entity.Zakaznaposhiv;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author 8279@stud.pgt.su
 */
@Repository
public interface ZakazFurnituraRepository extends JpaRepository<Zakazfurnitura, Integer> {

    List<Zakazfurnitura> findByIdZakaz(Zakaznaposhiv zakaz);

    List<Zakazfurnitura> findByIdZakaz_Id(Integer zakazId);

    // Альтернативный вариант с @Query
    @Query("SELECT z FROM Zakazfurnitura z WHERE z.idZakaz.id = :zakazId")
    List<Zakazfurnitura> findByZakazId(@Param("zakazId") Integer zakazId);
}
