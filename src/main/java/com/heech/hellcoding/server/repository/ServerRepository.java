package com.heech.hellcoding.server.repository;

import com.heech.hellcoding.server.domain.Server;

import java.util.List;
import java.util.Optional;

public interface ServerRepository {

    Server save(Server server);

    Optional<Server> findById(Long id);

    Optional<Server> findByName(String name);

    void update(Long id, Server server);

    List<Server> findAll();
}
