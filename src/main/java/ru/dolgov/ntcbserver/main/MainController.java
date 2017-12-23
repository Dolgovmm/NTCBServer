package ru.dolgov.ntcbserver.main;

import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import ru.dolgov.ntcbserver.server.Server;

public class MainController {
    private Server server;

    public TextArea textArea;

    public void onStartButton(ActionEvent actionEvent) {
        server = new Server("127.0.0.1", 9000);
        new Thread(() -> {
            try {
                server.run();
            } catch (Exception e) {
                e.printStackTrace();
                server.shutdown();
            }
        }).start();
    }

    public void onStopButton(ActionEvent actionEvent) {
        server.shutdown();
    }
}
