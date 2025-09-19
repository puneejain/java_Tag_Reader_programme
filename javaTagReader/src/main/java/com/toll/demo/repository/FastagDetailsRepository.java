package com.toll.demo.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.toll.demo.entity.FastagDetails;

public interface FastagDetailsRepository extends JpaRepository<FastagDetails, String> {
    Optional<FastagDetails> findByTagId(String tagId);
}