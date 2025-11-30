/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.shop.serverfabricshop.repository;

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
public interface ZakazNaPoshivRepository extends JpaRepository<Zakaznaposhiv, Integer> {

    List<Zakaznaposhiv> findByIdShveya_Id(Integer shveyaId);

    // Альтернативный вариант с @Query
    @Query("SELECT z FROM Zakaznaposhiv z WHERE z.idShveya.id = :shveyaId")
    List<Zakaznaposhiv> findByShveyaId(@Param("shveyaId") Integer shveyaId);
}
