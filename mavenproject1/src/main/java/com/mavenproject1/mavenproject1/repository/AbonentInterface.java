/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mavenproject1.mavenproject1.repository;

import com.mavenproject1.mavenproject1.entity.Abonent;
import com.mavenproject1.mavenproject1.entity.Operator;
import com.mavenproject1.mavenproject1.entity.Services;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author 8277
 */
@Repository
public interface AbonentInterface extends JpaRepository<Abonent, Integer> {

    @Query("SELECT a.idAbonent, a.surname, a.name, a.patronymic, a.downloadDate, "
            + "a.availabilityBlocker, a.debt, u.surname as operatorSurname, u.name as operatorName "
            + "FROM Abonent a "
            + "LEFT JOIN a.idOperator o "
            + "LEFT JOIN o.idUser u "
            + "ORDER BY a.surname, a.name")
    List<Object[]> findAllAbonentsWithOperator();

    @Query("SELECT a.availabilityBlocker, COUNT(a) as count, "
            + "SUM(CAST(a.debt AS double)) as totalDebt "
            + "FROM Abonent a "
            + "GROUP BY a.availabilityBlocker")
    List<Object[]> findBlockedStatistics();

    @Query("SELECT a.availabilityBlocker, COUNT(a), SUM(CAST(a.debt AS double)) "
            + "FROM Abonent a "
            + "GROUP BY a.availabilityBlocker")
    List<Object[]> findDebtStatistics();

    @Query("SELECT a.surname, a.name, a.patronymic, s.name as serviceName, o.idUser.surname as operatorSurname "
            + "FROM Abonent a "
            + "LEFT JOIN a.abonentServicesCollection abs "
            + "LEFT JOIN abs.idServices s "
            + "LEFT JOIN a.idOperator o "
            + "ORDER BY a.surname")
    List<Object[]> findFullAbonentInfo();

    @Query("SELECT a FROM Abonent a WHERE a.idOperator = :operator")
    List<Abonent> findByIdOperator(@Param("operator") Operator operator);

    long countByAvailabilityBlocker(int value);

    @Query("SELECT a FROM Abonent a JOIN a.abonentServicesCollection s JOIN s.idServices serv WHERE serv.name = 'Интернет'")
    List<Abonent> findAbonentsByInternetService();

}
