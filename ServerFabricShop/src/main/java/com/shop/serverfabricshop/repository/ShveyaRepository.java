package com.shop.serverfabricshop.repository;

import com.shop.serverfabricshop.entity.Polzovateli;
import com.shop.serverfabricshop.entity.Shveya;
import com.shop.serverfabricshop.entity.Zakaznaposhiv;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Repository
public interface ShveyaRepository extends JpaRepository<Shveya, Integer> {

    Optional<Shveya> findByPolzovatelid_Id(Integer polzovatelId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Shveya s WHERE s.polzovatelid.id = :userId")
    void deleteByUserId(@Param("userId") Integer userId);

   

    boolean existsByPolzovatelid_Id(Integer polzovatelId);

    @Query("SELECT s FROM Shveya s WHERE s.polzovatelid.id = :userId")
    Optional<Shveya> findByPolzovatelidId(@Param("userId") Integer userId);

    List<Shveya> findByPolzovatelid(Polzovateli polzovatelid);

    @Modifying
    @Query("DELETE FROM Shveya s WHERE s.polzovatelid.id = :userId")
    void deleteByPolzovatelidId(@Param("userId") Integer userId);

    @Query("SELECT COUNT(s) > 0 FROM Shveya s WHERE s.polzovatelid.id = :userId")
    boolean existsByPolzovatelid(@Param("userId") Integer userId);
}
