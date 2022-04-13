package com.heech.hellcoding.server.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Server {

    private Long id;
    private String serverName;
    private String serverIp;
    private String serverOs;
    private String cpu;
    private String gpu;

    public Server() {
    }

    public Server(String serverName, String serverIp, String serverOs, String cpu, String gpu) {
        this.serverName = serverName;
        this.serverIp = serverIp;
        this.serverOs = serverOs;
        this.cpu = cpu;
        this.gpu = gpu;
    }
}
