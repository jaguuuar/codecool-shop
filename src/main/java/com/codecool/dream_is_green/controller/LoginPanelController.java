package com.codecool.dream_is_green.controller;

import com.codecool.dream_is_green.dao.StudentDAO;
import com.codecool.dream_is_green.model.StudentModel;
import com.codecool.dream_is_green.view.UIView;
import com.codecool.dream_is_green.dao.LoginPanelDAO;


public class LoginPanelController {

    private static final String EXIT = "E";
    private UIView view = new UIView();

    public void loginIntoSystem() {
        String operation;

        do {
            view.clearScreen();
            operation = view.getInput("~~~ Welcome in CODECOOL SHOP :)\n" +
                    "~~~ Login into system: press ENTER to continue\n" +
                    "~~~ Exit: press E\n");
            operation = operation.toUpperCase();

            if (!operation.equals(EXIT)) {
                String login = view.getInput("Enter login: ");
                String password = view.getInput("Enter password: ");
                chooseUserPanel(login, password);
            }

        } while (!operation.equals(EXIT));
    }


    private void chooseUserPanel(String login, String password) {

        LoginPanelDAO loginDAO = new LoginPanelDAO();
        String[] userDetails = loginDAO.getUserDetails(login);
        String user_type = userDetails[0];
        String user_password = userDetails[1];

        if (!user_type.equals("") && user_password.equals(password)) {

            if (user_type.equals("mentor")) {
                MentorController mentorController = new MentorController();
                mentorController.startMentorController();

            } else if (user_type.equals("admin")) {
                AdminController adminController = new AdminController();
                adminController.startAdminController();

            } else if (user_type.equals("student")) {
                StudentController studentController = new StudentController();
                StudentDAO studentDAO = new StudentDAO();
                StudentModel student = studentDAO.getStudent(login);
                studentController.startStudentController(student);
            }

        } else {
            view.printMessage("\nWrong login or password\n");
            view.pressToContinue();
        }
    }
}
