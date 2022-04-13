package com.heech.hellcoding.server.repository;

import com.heech.hellcoding.server.domain.Server;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemoryServerRepositoryTest {

    MemoryServerRepository serverRepository = new MemoryServerRepository();

    @AfterEach
    void afterEach() {
        serverRepository.clearStore();
    }

    @Test
    void save() {
        //given
        Server server = new Server("serverA", "127.0.0.1", "ubuntu", "intel", "nvidia");

        //when
        Server savedServer = serverRepository.save(server);

        //then
        Server findServer = serverRepository.findById(server.getId()).get();
        assertThat(findServer).isEqualTo(savedServer);

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