/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mavenproject1.mavenproject1.repository;

import com.mavenproject1.mavenproject1.entity.Applications;
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
public interface ApplicationInterface extends JpaRepository<Applications, Integer> {

    @Query("SELECT app.idApplication, a.surname, a.name, a.patronymic, at.name as applicationType, app.applicationsDate "
            + "FROM Applications app "
            + "JOIN app.idAbonent a "
            + "JOIN app.idApplicationType at")
    List<Object[]> findApplicationsWithTypes();
}
