package com.shop.serverfabricshop.repository;

import com.shop.serverfabricshop.entity.Polzovateli;
import com.shop.serverfabricshop.entity.Upravlyayushchiy;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Repository
public interface UpravlyayushchiyRepository extends JpaRepository<Upravlyayushchiy, Integer> {

    Optional<Upravlyayushchiy> findByPolzovatelid_Id(Integer polzovatelId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Upravlyayushchiy u WHERE u.polzovatelid.id = :userId")
    void deleteByUserId(@Param("userId") Integer userId);

    @Query("SELECT u FROM Upravlyayushchiy u WHERE u.polzovatelid.id = :userId")
    Optional<Upravlyayushchiy> findByPolzovatelidId(@Param("userId") Integer userId);

    List<Upravlyayushchiy> findByPolzovatelid(Polzovateli polzovatelid);

    @Modifying
    @Query("DELETE FROM Upravlyayushchiy u WHERE u.polzovatelid.id = :userId")
    void deleteByPolzovatelidId(@Param("userId") Integer userId);

    @Query("SELECT COUNT(u) > 0 FROM Upravlyayushchiy u WHERE u.polzovatelid.id = :userId")
    boolean existsByPolzovatelid(@Param("userId") Integer userId);
}
