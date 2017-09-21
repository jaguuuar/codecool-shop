package com.codecool.dream_is_green.dao;

import com.codecool.dream_is_green.model.MentorModel;

import java.sql.*;

public class MentorDAO extends AbstractDAO<MentorModel> {


    public void loadMentors() {

        Connection conn;
        Statement stat;

        try {

            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:quest_store.db");
            stat = conn.createStatement();


            String query = "SELECT * FROM MentorsTable JOIN UsersTable ON UsersTable.user_id = MentorsTable.user_id";
            ResultSet result = stat.executeQuery(query);
            String name, surname, email, login, password, className;
            int userID;

            while(result.next()) {

                name = result.getString("name");
                surname = result.getString("surname");
                email = result.getString("email");
                login = result.getString("login");
                password = result.getString("password");
                userID = result.getInt("user_id");
                className = result.getString("class_name");

                System.out.println("dupa" + surname);
                MentorModel mentor = new MentorModel(userID, name, surname, email, login, password, className);
                this.addObject(mentor);
            }
            result.close();
            stat.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}