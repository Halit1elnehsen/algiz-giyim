package com.ecommerce.repository;

import com.ecommerce.entity.DressVariant;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DressVariantRepository extends JpaRepository<DressVariant, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT v FROM DressVariant v WHERE v.id = :id")
    Optional<DressVariant> findByIdForUpdate(@Param("id") Long id);
}
