package com.heech.hellcoding.server.repository;

import com.heech.hellcoding.server.domain.Server;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryServerRepository implements ServerRepository {

    private static final Map<Long, Server> store = new HashMap<>();
    private static long sequence = 0L;

    /**
     * Server 저장
     * @param server
     * @return
     */
    @Override
    public Server save(Server server) {
        server.setId(++sequence);
        store.put(server.getId(), server);
        return server;
    }

    /**
     * Server 상세(Id)
     * @param id
     * @return
     */
    @Override
    public Optional<Server> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    /**
     * Server 상세(Name)
     * @param name
     * @return
     */
    @Override
    public Optional<Server> findByName(String name) {
        return store.values().stream()
                .filter(server -> server.getServerName().equals(name))
                .findAny();
    }

    /**
     * Server 수정
     * @param id
     * @param server
     */
    @Override
    public void update(Long id, Server server) {
        Server findServer = findById(id).get();
        findServer.setServerName(server.getServerName());
        findServer.setServerIp(server.getServerIp());
        findServer.setServerOs(server.getServerOs());
        findServer.setCpu(server.getCpu());
        findServer.setGpu(server.getGpu());
    }

    /**
     * Server 목록
     * @return
     */
    @Override
    public List<Server> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
