package com.toll.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.toll.demo.model.Fastag;

public interface FastagRepository extends JpaRepository<Fastag, String> {
}