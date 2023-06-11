package com.project.anketa;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

import com.project.anketa.models.Anketa;
import com.project.anketa.models.repo.AnketaRepository;
import com.project.anketa.services.DatabaseHandler;
import com.project.anketa.services.EmailService;
import com.project.anketa.services.WordService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;


import java.util.Properties;

@Controller
public class HelloController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button loginButton;

    @FXML
    private Button excel_btn;

    @FXML
    private TextField name_field;

    @FXML
    private TextField number_field;

    @FXML
    private TextField email_field;

    @Autowired
    private DatabaseHandler databaseHandler;

    @Autowired
    private WordService wordService;

    @Autowired
    private EmailService emailSender;

    @Autowired
    private AnketaRepository anketaRepository;

    @FXML
    void initialize() {

        //обработчик кнопки "Зарегистрироваться"

        loginButton.setOnAction(actionEvent -> {

            String name = name_field.getText();
            String number = number_field.getText();
            String email = email_field.getText();

            DatabaseHandler handler = new DatabaseHandler();

            ResultSet resultSet = handler.result(number, email);


            try {
                if (resultSet.next()) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("danger.fxml"));
                    try {
                        loader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Parent root = loader.getRoot();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.showAndWait();
                } else {
                    System.out.println("Новый пользователь");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            //проверка номера телефона с заменой
            String correctNumber = number_field.getText().replaceAll("\\D+", "");

            System.out.print("Valid phone number: " + number_field.getText() + " -> " + correctNumber);
            correctNumber = correctNumber.replaceFirst("^(8?)(90)", "7$2");

            if (correctNumber.matches("^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{8}$")) {

                //проверка поля с ФИО
                if (name.trim().length() != 0 && name.matches("\\D+")) {

                    if (email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {

                        try {
                            wordService.toWord(name, correctNumber, email);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        signUpUser(name, correctNumber, email);

                        emailSender.emailSender(email);
                    }
                }
            }

        });
    }

    private void signUpUser(String name, String number, String email) {
        Date date = new Date();
        Anketa anketa = new Anketa(name, number, email, date);
        anketaRepository.save(anketa);
    }
}





