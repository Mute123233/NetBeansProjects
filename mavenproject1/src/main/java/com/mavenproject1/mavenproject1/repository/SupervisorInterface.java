package com.mavenproject1.mavenproject1.repository;

import com.mavenproject1.mavenproject1.entity.Supervisor;
import com.mavenproject1.mavenproject1.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface SupervisorInterface extends JpaRepository<Supervisor, Integer> {
    
    // Найти супервизора по пользователю
    Optional<Supervisor> findByIdUser(Users user);
    
    // Найти супервизора по ID пользователя
    @Query("SELECT s FROM Supervisor s WHERE s.idUser.idUser = :userId")
    Optional<Supervisor> findByIdUser_IdUser(@Param("userId") Integer userId);
    
    // Удалить супервизора по пользователю
    @Modifying
    @Transactional
    @Query("DELETE FROM Supervisor s WHERE s.idUser = :user")
    void deleteByIdUser(@Param("user") Users user);
    
    // Удалить супервизора по ID пользователя
    @Modifying
    @Transactional
    @Query("DELETE FROM Supervisor s WHERE s.idUser.idUser = :userId")
    void deleteByIdUser_IdUser(@Param("userId") Integer userId);
    
    // Проверить существование супервизора по пользователю
    boolean existsByIdUser(Users user);
    
    // Проверить существование супервизора по ID пользователя
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Supervisor s WHERE s.idUser.idUser = :userId")
    boolean existsByIdUser_IdUser(@Param("userId") Integer userId);
}