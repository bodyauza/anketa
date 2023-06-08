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
            String cleanedInput = number_field.getText().replaceAll("\\D+", "");

            System.out.print("Valid phone number: " + number_field.getText() + " -> " + cleanedInput);
            cleanedInput = cleanedInput.replaceFirst("^(8?)(90)", "7$2");

            if (cleanedInput.matches("^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{8}$")) {

                System.out.println("Hello number");

                //проверка поля с ФИО
                if (name.trim().length() != 0 && name.matches("\\D+")) {

                    System.out.println("hi");

                    if (email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {

                        try {
                            toWord(name, cleanedInput, email);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        signUpUser(name, cleanedInput, email);

                        emailSender(email);
                    }
                }
            }

        });
    }


    private void toWord(String name, String cleanedInput, String email) throws IOException {

        String[] nmbArray = new String[0];
        final ArrayList<String> numberandnames = new ArrayList(Arrays.asList(nmbArray));

        String[] nmbArray1 = new String[0];
        final ArrayList<String> numberandnames1 = new ArrayList(Arrays.asList(nmbArray1));

        String[] nmbArray2 = new String[0];
        final ArrayList<String> numberandnames2 = new ArrayList(Arrays.asList(nmbArray2));

        File f = new File("D:\\Anketa.docx");

        f.createNewFile();

        if (f.exists()) {

            System.out.println("\n Добавлено в базу");


            numberandnames.add(name);
            numberandnames1.add(cleanedInput);
            numberandnames2.add(email);

            //чтение файла
            FileInputStream fis;
            try {
                fis = new FileInputStream(f.getAbsolutePath());
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            XWPFDocument docxModel = null;
            try {
                docxModel = new XWPFDocument(fis);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            docxModel.createParagraph();

            String documentLine = docxModel.getDocument().toString();
            CTSectPr ctSectPr = docxModel.getDocument().getBody().addNewSectPr();
            XWPFParagraph bodyParagraph = docxModel.createParagraph();
            bodyParagraph.setAlignment(ParagraphAlignment.LEFT);

            //создание параграфа
            XWPFRun paragraphConfig = bodyParagraph.createRun();
            XWPFParagraph paragraph = docxModel.createParagraph();

            paragraphConfig.setItalic(true);
            paragraphConfig.setFontSize(20);
            paragraphConfig.setColor("170101");
            paragraphConfig.setFontSize(12);

            List<XWPFParagraph> paragraphs = docxModel.getParagraphs();

            paragraphConfig.setText(numberandnames.toString());
            paragraphConfig.setText(numberandnames1.toString());
            paragraphConfig.setText(numberandnames2.toString());

            try {
                fis.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            //изменение файла
            try {
                FileOutputStream outputStream = new FileOutputStream(f.getAbsolutePath());
                docxModel.write(outputStream);
            } catch (Exception var21) {
                throw new RuntimeException(var21);
            }

            //запуск Word
            Desktop desktop = null;
            if (Desktop.isDesktopSupported()) {
                desktop = Desktop.getDesktop();

                try {
                    desktop.open(new File(f.getAbsolutePath()));
                } catch (IOException var4) {
                    var4.printStackTrace();
                }
            }
        }
    }

    private void signUpUser(String name, String number, String email) {
        Date date = new Date();
        Anketa anketa = new Anketa(name, number, email, date);
        anketaRepository.save(anketa);
    }

    private void emailSender(String email) {

        String to = email;

        // Необходимо указать адрес электронной почты отправителя
        String from = "bogdanazino777@gmail.com";

        // Предполагая, что вы отправляете электронное письмо с gmail
        String host = "smtp.gmail.com";

        String port = "587";

        // Получить свойства системы
        Properties properties = System.getProperties();

        // Настроить почтовый сервер
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.debug", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                "bogdanazino777@gmail.com", "");// Адрес электронной почты и пароль отправителя
                    }
                });

        try {
            // Создание объекта MimeMessage по умолчанию
            Message message = new MimeMessage(session);

            // Установить От: поле заголовка
            message.setFrom(new InternetAddress(from));

            // Установить Кому: поле заголовка
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Установить тему: поле заголовка
            message.setSubject("Это тема письма!");

            // Теперь установите фактическое сообщение
            message.setText("Это актуальное сообщение");

            // Отправить сообщение
            Transport.send(message);
            System.out.println("Сообщение успешно отправлено....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}





