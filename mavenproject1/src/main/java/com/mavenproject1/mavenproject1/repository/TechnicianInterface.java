package com.mavenproject1.mavenproject1.repository;

import com.mavenproject1.mavenproject1.entity.Technician;
import com.mavenproject1.mavenproject1.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface TechnicianInterface extends JpaRepository<Technician, Integer> {
    
    // Найти техника по пользователю
    Optional<Technician> findByIdUser(Users user);
    
    // Найти техника по ID пользователя
    @Query("SELECT t FROM Technician t WHERE t.idUser.idUser = :userId")
    Optional<Technician> findByIdUser_IdUser(@Param("userId") Integer userId);
    
    // Удалить техника по пользователю
    @Modifying
    @Transactional
    @Query("DELETE FROM Technician t WHERE t.idUser = :user")
    void deleteByIdUser(@Param("user") Users user);
    
    // Удалить техника по ID пользователя
    @Modifying
    @Transactional
    @Query("DELETE FROM Technician t WHERE t.idUser.idUser = :userId")
    void deleteByIdUser_IdUser(@Param("userId") Integer userId);
    
    // Проверить существование техника по пользователю
    boolean existsByIdUser(Users user);
    
    // Проверить существование техника по ID пользователя
    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM Technician t WHERE t.idUser.idUser = :userId")
    boolean existsByIdUser_IdUser(@Param("userId") Integer userId);
}