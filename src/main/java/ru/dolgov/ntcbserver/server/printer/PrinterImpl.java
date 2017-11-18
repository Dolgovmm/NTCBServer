package ru.dolgov.ntcbserver.server.printer;

import javafx.scene.control.TextArea;

public class PrinterImpl implements Printer {
    private TextArea textArea;

    public PrinterImpl(TextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void print(String str) {
        textArea.appendText(str + "\n");
    }
}
