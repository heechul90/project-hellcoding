package com.heech.hellcoding.server.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemoryServerRepositoryTest {

    MemoryServerRepository serverRepository = new MemoryServerRepository();

    @AfterEach
    void afterEach() {
        serverRepository.clearStore();
    }

    @Test
    void save() {
    }

    @Test
    void findById() {
    }

    @Test
    void findByName() {
    }

    @Test
    void update() {
    }

    @Test
    void findAll() {
    }
}