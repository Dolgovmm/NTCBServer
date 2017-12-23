package ru.dolgov.ntcbserver.clienthandler;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ClientHandlerImpl implements ClientHandler {
    private List<Client> clients;

    public ClientHandlerImpl() {
        clients = new ArrayList<>();
        Client client = new Client();
        client.setImei(100000000000000L);
        clients.add(client);
    }

    @Override
    public Client getClientByImei(long imei) {
        for (Client client: clients) {
            if (client.getImei() == imei) {
                return client;
            }
        }
        return null;
    }

    @Override
    public void saveClientStatus(Client client) {
        saveToFile(client);
    }

    private void saveToFile(Client client) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("log.txt"))) {
            writer.write(client.toString());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
