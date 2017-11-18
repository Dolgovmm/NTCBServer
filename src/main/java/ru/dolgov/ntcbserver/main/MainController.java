package ru.dolgov.ntcbserver.main;

import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import ru.dolgov.ntcbserver.server.Server;
import ru.dolgov.ntcbserver.server.printer.Printer;
import ru.dolgov.ntcbserver.server.printer.PrinterImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainController {
    private Server server;
    private Printer printer;

    public TextArea textArea;

    public void onStartButton(ActionEvent actionEvent) {
        server = new Server("localhost", 9000);
        printer = new PrinterImpl(textArea);
        server.setPrinter(printer);
        if (printer != null) {
            printer.print(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")) + " сервер запущен");
        }
        new Thread(() -> {
            try {
                server.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void onStopButton(ActionEvent actionEvent) {
        if (printer != null) {
            printer.print(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")) + " сервер остановлен");
        }
        server.shutdown();
    }
}
