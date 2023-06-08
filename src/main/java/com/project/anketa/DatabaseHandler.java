package com.project.anketa;



import com.project.anketa.models.Anketa;
import com.project.anketa.models.repo.AnketaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

@Service
public class DatabaseHandler {

    @Autowired
    AnketaRepository anketaRepository;

    public ResultSet result(String number, String email) {

        ResultSet resultSet = null;

        try {
            PreparedStatement prSt = anketaRepository.getUser(number, email);
            resultSet = prSt.executeQuery();
        } catch (SQLException var5) {
            throw new RuntimeException(var5);
        }
        return resultSet;
    }

}
