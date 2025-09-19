package com.toll.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.toll.demo.model.Fastag;
import com.toll.demo.repository.FastagRepository;

@Service
public class FastagService {
    private final FastagRepository repo;

    public FastagService(FastagRepository repo) {
        this.repo = repo;
    }

    public Fastag save(Fastag fastag) {
        return repo.save(fastag);
    }

    public Fastag getById(String tagId) {
        return repo.findById(tagId).orElse(null);
    }

    public List<Fastag> getAll() {
        return repo.findAll();
    }
}