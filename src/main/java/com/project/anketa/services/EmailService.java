package com.project.anketa.services;

import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailService {
    public void emailSender(String email) {

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
