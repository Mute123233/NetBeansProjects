package com.shop.serverfabricshop.repository;

import com.shop.serverfabricshop.entity.Polzovateli;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PolzovateliRepository extends JpaRepository<Polzovateli, Integer> {

    Optional<Polzovateli> findByLoginAndParol(String login, String parol);

    Optional<Polzovateli> findByLogin(String login);

    Optional<Polzovateli> findByLoginAndIdNot(String login, Integer id);
}
