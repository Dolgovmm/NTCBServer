package ru.dolgov.ntcbserver.main;

import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import ru.dolgov.ntcbserver.server.Server;

public class MainController {
    private Server server;

    public TextArea textArea;

    public void onStartButton(ActionEvent actionEvent) {
        server = new Server("localhost", 9000);
        new Thread(() -> {
            try {
                server.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void addMessageToTextArea() {
        String str = server.getLogFromList();
        if (str != null) {
            textArea.appendText(str);
        }
    }

    public void onStopButton(ActionEvent actionEvent) {
        server.shutdown();
    }
}
