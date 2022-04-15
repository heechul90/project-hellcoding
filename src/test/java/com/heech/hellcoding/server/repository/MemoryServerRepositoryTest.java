package com.heech.hellcoding.server.repository;

import com.heech.hellcoding.server.domain.Server;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

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
        //given
        Server server = new Server("serverA", "127.0.0.1", "ubuntu", "intel", "nvidia");
        Server savedServer = serverRepository.save(server);

        //when
        Server findServer = serverRepository.findById(savedServer.getId()).get();

        //then
        assertThat(findServer).isEqualTo(server);
    }

    @Test
    void findByName() {
        //given
        Server server = new Server("serverA", "127.0.0.1", "ubuntu", "intel", "nvidia");
        Server savedServer = serverRepository.save(server);

        //when
        Optional<Server> findServer = serverRepository.findByName(savedServer.getServerName());

        //then
        assertThat(findServer.get()).isEqualTo(server);
    }

    @Test
    void update() {
    }

    @Test
    void findAll() {
    }
}