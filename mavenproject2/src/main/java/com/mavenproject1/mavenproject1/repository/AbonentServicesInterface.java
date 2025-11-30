/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mavenproject1.mavenproject1.repository;

import com.mavenproject1.mavenproject1.entity.Abonent;
import com.mavenproject1.mavenproject1.entity.AbonentServices;
import com.mavenproject1.mavenproject1.entity.Services;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author 8277
 */
@Repository
public interface AbonentServicesInterface extends JpaRepository<AbonentServices, Integer> {

    List<AbonentServices> findByIdServices_IdServices(Integer idServices);

    @Query("SELECT a.surname, a.name, a.patronymic, s.name as serviceName, abs.startDate "
            + "FROM AbonentServices abs "
            + "JOIN abs.idAbonent a "
            + "JOIN abs.idServices s")
    List<Object[]> findAbonentsWithServices();

}
