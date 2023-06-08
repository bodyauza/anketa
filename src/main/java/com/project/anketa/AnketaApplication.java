package com.project.anketa;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class AnketaApplication /*extends Application*/ {
	/*@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(AnketaApplication.class.getResource("hello-view.fxml"));
		stage.setResizable(false);
		Scene scene = new Scene((Parent) fxmlLoader.load(), 700, 500);
		stage.setTitle("Hello!");
		stage.setScene(scene);
		stage.show();
	}*/

	public static void main(String[] args) {
		SpringApplication.run(AnketaApplication.class, args);
		/*launch();*/
	}
}
