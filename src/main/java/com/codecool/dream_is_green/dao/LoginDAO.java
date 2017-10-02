package com.codecool.dream_is_green.dao;

import com.codecool.dream_is_green.controller.AdminController;
import com.codecool.dream_is_green.controller.MentorController;
import com.codecool.dream_is_green.controller.StudentController;
import com.codecool.dream_is_green.model.AdminModel;
import com.codecool.dream_is_green.model.MentorModel;
import com.codecool.dream_is_green.model.StudentModel;
import com.codecool.dream_is_green.view.UIView;

public class LoginDAO {

    private static final String EXIT = "E";

    public static void loginIntoSystem() {

        UIView view = new UIView();
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

                MentorModel mentor = findMentor(login);
                AdminModel admin = findAdmin(login);
                StudentModel student = findStudent(login);

                if (mentor != null && mentor.getPassword().equals(password)) {
                    MentorController mentorController = new MentorController();
                    mentorController.startMentorController();

                } else if (admin != null && admin.getPassword().equals(password)) {
                    AdminController adminController = new AdminController();
                    adminController.startAdminController();

                } else if (student != null && student.getPassword().equals(password)) {
                    StudentController studentController = new StudentController();
                    studentController.startStudentController(student);

                } else {
                    view.printMessage("\nWrong login or password!\n");
                    view.pressToContinue();
                }
            }
        } while (!operation.equals(EXIT));
    }

    public static MentorModel findMentor(String login) {

        for (MentorModel mentor : DaoStart.getMentorDao().getObjectList()) {
            if(mentor.getLogin().equals(login)) {
                return mentor;
            }
        }
        return null;
    }

    public static StudentModel findStudent(String login) {

        for (StudentModel student : DaoStart.getStudentDao().getObjectList()) {
            if(student.getLogin().equals(login)) {
                return student;
            }
        }
        return null;
    }

    public static AdminModel findAdmin(String login) {

        for (AdminModel admin : DaoStart.getAdminDao().getObjectList()) {
            if(admin.getLogin().equals(login)) {
                return admin;
            }
        }
        return null;
    }

    public static void createDefaultUsers() {

        AdminModel admin = new AdminModel(12,"Janek", "Kowalski",
                "janek.ko@uo.com", "admin", "admin");

        MentorModel mentor = new MentorModel(10, "Michał", "Malinowski",
                "m.malinowski@gmail.com", "mentor", "mentor", "krk17");

        StudentModel student = new StudentModel(3,"Tomek", "Dupa",
                "tomcio.ko@uo.com", "student", "student", "2b");

        DaoStart.getAdminDao().addObject(admin);
        DaoStart.getMentorDao().addObject(mentor);
        DaoStart.getStudentDao().addObject(student);
    }
}
