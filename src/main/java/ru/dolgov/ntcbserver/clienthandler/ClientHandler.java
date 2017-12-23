package ru.dolgov.ntcbserver.clienthandler;

public interface ClientHandler {

    Client getClientByImei(long imei);

    void saveClientStatus(Client client);
}
