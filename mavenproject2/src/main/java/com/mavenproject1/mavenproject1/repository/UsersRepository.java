/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mavenproject1.mavenproject1.repository;

import com.mavenproject1.mavenproject1.entity.Abonent;
import com.mavenproject1.mavenproject1.entity.Operator;
import com.mavenproject1.mavenproject1.entity.Users;
import java.util.List;
import java.util.Optional;
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
public interface UsersRepository extends JpaRepository<Users, Integer> {

    @Query("SELECT u.idUser, u.surname, u.name, u.patronymic, u.login, u.phone, u.status "
            + "FROM Users u "
            + "ORDER BY u.surname, u.name")
    List<Object[]> findAllUsersBasic();

    @Query("SELECT u.surname, u.name, u.login, "
            + "CASE WHEN o.idUser IS NOT NULL THEN 'Оператор' "
            + "WHEN s.idUser IS NOT NULL THEN 'Супервизор' "
            + "WHEN t.idUser IS NOT NULL THEN 'Техник' "
            + "ELSE 'Нет роли' END as role "
            + "FROM Users u "
            + "LEFT JOIN Operator o ON o.idUser = u "
            + "LEFT JOIN Supervisor s ON s.idUser = u "
            + "LEFT JOIN Technician t ON t.idUser = u")
    List<Object[]> findUsersWithRoles();

    @Query("SELECT u.status, COUNT(u) "
            + "FROM Users u "
            + "GROUP BY u.status")
    List<Object[]> findUserStatusStatistics();

    @Query("SELECT u.surname, u.name, u.login, u.phone, u.status, "
            + "CASE WHEN o.idUser IS NOT NULL THEN 'Оператор' "
            + "WHEN s.idUser IS NOT NULL THEN 'Супервизор' "
            + "WHEN t.idUser IS NOT NULL THEN 'Техник' "
            + "ELSE 'Нет роли' END as role, "
            + "COUNT(DISTINCT a.idAbonent) as abonentCount "
            + "FROM Users u "
            + "LEFT JOIN Operator o ON o.idUser = u "
            + "LEFT JOIN Supervisor s ON s.idUser = u "
            + "LEFT JOIN Technician t ON t.idUser = u "
            + "LEFT JOIN Abonent a ON a.idOperator = o "
            + "GROUP BY u.idUser")
    List<Object[]> findFullUserHierarchy();

    Users findByLogin(String login);

    @Query("SELECT u FROM Users u WHERE u.login = :login AND u.password = :password")
    Optional<Users> findByLoginAndPassword(@Param("login") String login, @Param("password") String password);

    // Проверить существование пользователя по логину
    boolean existsByLogin(String login);

    @Query("SELECT a FROM Abonent a WHERE a.idOperator = :operator")
    List<Abonent> findByIdOperator(@Param("operator") Operator operator);

    public Optional<Users> findByLoginAndPasswordAndCode(String Login, String Password, String Code);

}
