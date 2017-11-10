package ru.dolgov.ntcbserver.main;

import ru.dolgov.ntcbserver.server.Server;

public class Main {
    public static void main(String[] args) {
        try {
            new Server(9000).run();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
