/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mavenproject1.mavenproject1.repository;

import com.mavenproject1.mavenproject1.entity.Operator;
import com.mavenproject1.mavenproject1.entity.Users;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author 8277
 */
@Repository
public interface OperatorInterface extends JpaRepository<Operator, Integer> {
    // Найти оператора по пользователю

    Optional<Operator> findByIdUser(Users user);

    @Query("SELECT o.idUser.surname, o.idUser.name, COUNT(a) as abonentCount "
            + "FROM Operator o "
            + "LEFT JOIN Abonent a ON a.idOperator = o "
            + "GROUP BY o.idUser")
    List<Object[]> findOperatorsWithAbonentCount();

    // Найти оператора по ID пользователя
    @Query("SELECT o FROM Operator o WHERE o.idUser.idUser = :userId")
    Optional<Operator> findByIdUser_IdUser(@Param("userId") Integer userId);

    // Удалить оператора по пользователю
    @Modifying
    @Transactional
    @Query("DELETE FROM Operator o WHERE o.idUser = :user")
    void deleteByIdUser(@Param("user") Users user);

    // Удалить оператора по ID пользователя
    @Modifying
    @Transactional
    @Query("DELETE FROM Operator o WHERE o.idUser.idUser = :userId")
    void deleteByIdUser_IdUser(@Param("userId") Integer userId);

    // Проверить существование оператора по пользователю
    boolean existsByIdUser(Users user);

    // Проверить существование оператора по ID пользователя
    @Query("SELECT CASE WHEN COUNT(o) > 0 THEN true ELSE false END FROM Operator o WHERE o.idUser.idUser = :userId")
    boolean existsByIdUser_IdUser(@Param("userId") Integer userId);
}
