package com.codecool.dream_is_green.dao;

import com.codecool.dream_is_green.model.LevelModel;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;

public class LevelDAO extends AbstractDAO<LevelModel> {


    public void loadLevels() {

        Connection conn;
        Statement stat;

        try {

            conn = DatabaseConnection.getConnection();
            stat = conn.createStatement();


            String query = "SELECT * FROM LevelsTable";
            ResultSet result = stat.executeQuery(query);
            String name;
            int expRequired;

            while (result.next()) {

                name = result.getString("level_name");
                expRequired = result.getInt("exp_required");


                LevelModel level = new LevelModel(name, expRequired);
                this.addObject(level);
            }
            result.close();
            stat.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertLevel(String name, Integer expRequired)  {

        Connection conn;
        Statement stat;

        try {
            conn = DatabaseConnection.getConnection();
            stat = conn.createStatement();
            conn.setAutoCommit(false);

            String statement = String.format("INSERT INTO LevelTable (level_name, exp_required) VALUES ('%s', '%d');", name, expRequired);

            stat.executeUpdate(statement);

            stat.close();
            conn.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void deleteLevel(int id) {
//
//        Connection conn;
//
//        try {
//            conn = DatabaseConnection.getConnection();
//
//            String statement = "DELETE FROM MentorsTable WHERE user_id = ?";
//            PreparedStatement prepStmt = conn.prepareStatement(statement2);
//
//            prepStmt.setInt(1, id);
//            prepStmt.execute();
//            prepStmt.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    public void updateMentor(String phrase, int userID, String column, String table) {
//
//        Connection conn;
//        Statement stat;
//
//        try {
//            conn = DatabaseConnection.getConnection();
//            conn.setAutoCommit(false);
//
//            stat = conn.createStatement();
//            if (column.equals("class_name")) {
//                column = "class_name";
//
//            } else if (column.equals("email")) {
//                column = "email";
//
//            }
//            String statement = String.format("UPDATE %s set '%s' = '%s' where user_id=%d;", table, column, phrase, userID);
//            stat.executeUpdate(statement);
//            conn.commit();
//
//            stat.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}