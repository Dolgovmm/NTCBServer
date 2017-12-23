package ru.dolgov.ntcbserver.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		String fxmlFile = "/fxml/main.fxml";
		FXMLLoader loader = new FXMLLoader();
		Parent root = loader.load(getClass().getResourceAsStream(fxmlFile));
		primaryStage.setTitle("Server");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}

}
