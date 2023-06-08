package com.project.anketa.models.repo;

import com.project.anketa.models.Anketa;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.PreparedStatement;
import java.util.List;

public interface AnketaRepository extends CrudRepository<Anketa, Long> {
    @Query("SELECT u FROM Anketa u WHERE u.number = :number and u.email = :email")
    PreparedStatement getUser(@Param("number") String number, @Param("email") String email);
}
