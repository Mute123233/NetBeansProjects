package com.shop.serverfabricshop.repository;

import com.shop.serverfabricshop.entity.Polzovateli;
import com.shop.serverfabricshop.entity.Prodavec;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Repository
public interface ProdavecRepository extends JpaRepository<Prodavec, Integer> {

    Optional<Prodavec> findByPolzovatelid_Id(Integer polzovatelId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Prodavec p WHERE p.polzovatelid.id = :userId")
    void deleteByUserId(@Param("userId") Integer userId);

    @Query("SELECT p FROM Prodavec p WHERE p.polzovatelid.id = :userId")
    Optional<Prodavec> findByPolzovatelidId(@Param("userId") Integer userId);

    List<Prodavec> findByPolzovatelid(Polzovateli polzovatelid);

    @Modifying
    @Query("DELETE FROM Prodavec p WHERE p.polzovatelid.id = :userId")
    void deleteByPolzovatelidId(@Param("userId") Integer userId);

    @Query("SELECT COUNT(p) > 0 FROM Prodavec p WHERE p.polzovatelid.id = :userId")
    boolean existsByPolzovatelid(@Param("userId") Integer userId);

}
