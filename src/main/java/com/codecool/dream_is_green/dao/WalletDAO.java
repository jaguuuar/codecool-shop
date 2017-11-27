package com.codecool.dream_is_green.dao;

import com.codecool.dream_is_green.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class WalletDAO extends AbstractDAO<WalletDAO> {

    public void loadCoolcoinsToWallet(StudentModel student) {

        Connection connection;
        Statement statement;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.createStatement();

            String query = "SELECT * FROM WalletTable WHERE user_id = '" + student.getUserID() + "'";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {

                Integer coolcoins = resultSet.getInt("coolcoins");

                student.getWallet().setCoolCoins(coolcoins);

            }
            resultSet.close();
            statement.close();

         } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadArtifactsToWallet(StudentModel student) {

        Connection connection;
        Statement statement;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.createStatement();

            String query = "SELECT * FROM StudentsWithArtifacts WHERE user_id = '" + student.getUserID() + "'";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {

                String artifactName = resultSet.getString("artifact_name");
                Integer price = resultSet.getInt("price");
                String artifactCategory = resultSet.getString("artifact_category");
                Integer state = resultSet.getInt("state");

                ArtifactCategoryModel artifactCategoryModel = new ArtifactCategoryModel(artifactCategory);
                ArtifactModel artifactModel = new ArtifactModel(artifactName, price, artifactCategoryModel);
                artifactModel.setIsUsed(state);

                student.getWallet().addBoughtArtifact(artifactModel);


            }
            resultSet.close();
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateCoolcoins(StudentModel student) {

        Connection connection;
        Statement statement;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.createStatement();
            connection.setAutoCommit(false);

            Integer coolcoins = student.getWallet().getCoolCoins();
            String query = "UPDATE WalletTable SET coolcoins = " + coolcoins +  " WHERE user_id = '" + student.getUserID() + "'";

            statement.executeUpdate(query);
            connection.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LinkedList<ArtifactModel> getStudentArtifacts(Integer userId) {

        LinkedList<ArtifactModel> studentArtifacts = new LinkedList<>();
        Connection connection;

        try {
            connection =  DatabaseConnection.getConnection();

            String selectSQL = "SELECT * FROM StudentsWithArtifacts WHERE user_Id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, userId);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String title = rs.getString("artifact_name");
                Integer price = rs.getInt("price");
                String category = rs.getString("artifact_category");
                Integer state = rs.getInt("state");

                ArtifactCategoryModel artifactCategory = new ArtifactCategoryModel(category);
                ArtifactModel artifact = new ArtifactModel(title, price, artifactCategory);
                artifact.setIsUsed(state);
                studentArtifacts.add(artifact);
            }

            preparedStatement.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return studentArtifacts;
    }

    public Integer getStudentCoolCoins(Integer userId) {

        Integer coolCoins = null;
        Connection connection;

        try {
            connection =  DatabaseConnection.getConnection();

            String selectSQL = "SELECT coolcoins FROM WalletTable WHERE user_Id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, userId);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Integer studentCoins = rs.getInt("coolcoins");
                coolCoins = studentCoins;
            }

            preparedStatement.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return coolCoins;
    }
}
