package com.codecool.dream_is_green.controller;

import com.codecool.dream_is_green.dao.*;
import com.codecool.dream_is_green.model.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.URI;
import java.util.*;

public class AdminController implements HttpHandler {

    Integer countMail;
    private static CookieManager cookie = new CookieManager();

    public void handle(HttpExchange httpExchange) throws IOException {
        URI uri = httpExchange.getRequestURI();
        URIModel uriModel = parseURI(uri.getPath());
        String userAction = uriModel.getUserAction();
        MailController mailController = new MailController();
        countMail = mailController.checkMail(10);

        if (userAction == null) {
            index(httpExchange);
        } else if (userAction.equals("add_class")) {
            addClass(httpExchange);
        } else if (userAction.equals("show_classes")) {
            showClasses(httpExchange);
        } else if (userAction.equals("add_mentor")) {
            addMentor(httpExchange);
        } else if (userAction.equals("show_mentors")) {
            showMentors(httpExchange);
        } else if (userAction.equals("add_level")) {
            addLevel(httpExchange);
        } else if (userAction.equals("show_levels")) {
            showLevels(httpExchange);
        } else if (userAction.equals("logout")) {
            clearCookie(httpExchange);
        } else if (userAction.equals("mail")) {
            mailController = new MailController();
            mailController.showReadMail(httpExchange, 10);
        }
    }

    private void index(HttpExchange httpExchange) throws IOException {

        cookie.redirectIfCookieNull(httpExchange);
        String sessionId = cookie.getSessionId(httpExchange);
        SessionDAO sessionDAO = new SessionDAO();
        SessionModel session = sessionDAO.getSession(sessionId);

        if (session != null) {

            String userType = session.getUserType();

            if(userType.equals("admin")) {

                JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/main.twig");
                JtwigModel model = JtwigModel.newModel();
                model.with("title", "Home admin");
                model.with("menu", "classpath:/templates/admin/menu_admin.twig");
                model.with("main", "classpath:/templates/admin/admin_home.twig");
                String response = template.render(model);

                httpExchange.sendResponseHeaders(200, response.length());
                OutputStream os = httpExchange.getResponseBody();
                os.write(response.getBytes());
                os.close();

            } else {
                httpExchange.getResponseHeaders().set("Location", "/" + userType);
                httpExchange.sendResponseHeaders(302,-1);
            }

        } else {
            httpExchange.getResponseHeaders().set("Location", "/login");
            httpExchange.sendResponseHeaders(302,-1);
        }

    }

    private void addMentor(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();

        if (method.equals("POST")) {
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();
            MentorDAO mentorDAO = new MentorDAO();
            FormDataController<PreUserModel> preUser = new FormDataController<>();
            mentorDAO.insertMentor(preUser.parseFormData(formData, "preUser"));
            httpExchange.getResponseHeaders().set("Location", "/admin/show_mentors");
            httpExchange.sendResponseHeaders(302, -1);
        }

        if (method.equals("GET")) {
            ClassDAO classDAO = new ClassDAO();
            classDAO.loadClasses();
            LinkedList<ClassModel> classes = classDAO.getObjectList();
            ResponseController<ClassModel> responseController = new ResponseController<>();
            responseController.sendResponse(httpExchange, countMail, classes, "classModels",
                    "Add mentor", "admin_add_mentor");
        }
    }

    private void addClass(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();

        if (method.equals("POST")) {
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();
            FormDataController<ClassModel> classModel = new FormDataController<>();
            ClassDAO classDao = new ClassDAO();
            classDao.insertClass(classModel.parseFormData(formData, "class"));
            httpExchange.getResponseHeaders().set("Location", "/admin/show_classes");
            httpExchange.sendResponseHeaders(302, -1);
        }

        if (method.equals("GET")) {
            ResponseController<ClassModel> responseController = new ResponseController<>();
            responseController.sendResponse(httpExchange, "Add class", "admin_add_class");
        }
    }

    private void addLevel(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();

        if (method.equals("POST")) {
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();
            FormDataController<LevelModel> level = new FormDataController<>();
            LevelDAO levelDAO = new LevelDAO();
            levelDAO.insertLevel(level.parseFormData(formData, "level"));
            httpExchange.getResponseHeaders().set("Location", "/admin/show_levels");
            httpExchange.sendResponseHeaders(302, -1);
        }

        if (method.equals("GET")) {
            ResponseController<LevelModel> responseController = new ResponseController<>();
            responseController.sendResponse(httpExchange,"Add level","admin_create_level");
        }
    }

    private void showMentors(HttpExchange httpExchange) throws IOException {
        MentorDAO mentorDAO = new MentorDAO();
        mentorDAO.loadMentors();
        LinkedList<MentorModel> mentors = mentorDAO.getObjectList();
        ResponseController<MentorModel> responseController = new ResponseController<>();
        responseController.sendResponse(httpExchange, countMail, mentors, "mentorModels",
                "Show mentors", "admin_show_mentors");
    }

    private void showLevels(HttpExchange httpExchange) throws IOException {
        LevelDAO levelDAO = new LevelDAO();
        levelDAO.loadLevels();
        LinkedList<LevelModel> levels = levelDAO.getObjectList();
        ResponseController<LevelModel> responseController = new ResponseController<>();
        responseController.sendResponse(httpExchange, countMail, levels, "levelModels",
                "Show levels", "admin_show_levels");
    }

    private void showClasses(HttpExchange httpExchange) throws IOException {
        ClassDAO classDAO = new ClassDAO();
        classDAO.loadClasses();
        LinkedList<ClassModel> classes = classDAO.getObjectList();
        ResponseController<ClassModel> responseController = new ResponseController<>();
        responseController.sendResponse(httpExchange, countMail, classes, "classModels",
                "Show classes", "admin_show_classes");
    }

    private void clearCookie(HttpExchange httpExchange) throws IOException {
        cookie.cleanCookie(httpExchange);

        httpExchange.getResponseHeaders().set("Location", "/login");
        httpExchange.sendResponseHeaders(302,-1);
    }

    private URIModel parseURI (String uri) {
        String[] pairs = uri.split("/");
        URIModel uriModel = new URIModel();

        if (pairs.length == 3) {
            uriModel = new URIModel(pairs[2]);
        }
        return uriModel;
    }
}