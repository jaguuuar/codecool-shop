package com.codecool.dream_is_green.controller;

import com.codecool.dream_is_green.dao.TeamDao;
import com.codecool.dream_is_green.model.SessionModel;
import com.codecool.dream_is_green.model.StudentModel;
import com.codecool.dream_is_green.model.TeamShoppingModel;
import com.codecool.dream_is_green.model.LevelModel;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

public class ResponseController<T> {

    public void sendResponse(HttpExchange httpExchange, SessionModel session, Integer counterMail, LinkedList<T> objectsList,
                             String objectModels, String title,
                             String menuPath, String pagePath) throws IOException {

        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/main.twig");
        JtwigModel model = JtwigModel.newModel();

        model.with(objectModels, objectsList);
        model.with("session", session);
        model.with("title", title);
        model.with("menu", "classpath:/templates/" + menuPath);
        model.with("main", "classpath:/templates/" + pagePath);
        model.with("counterMail", counterMail);
        String response = template.render(model);

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    public void sendQuestResponse(HttpExchange httpExchange, SessionModel session, Integer counterMail, LinkedList<T> objectsList,
                                  LinkedList<String> foundObject, String objectModels, String title,
                                  String menuPath, String pagePath) throws IOException {

        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/main.twig");
        JtwigModel model = JtwigModel.newModel();

        model.with("studentWithQuest", foundObject);
        model.with(objectModels, objectsList);
        model.with("title", title);
        model.with("session", session);
        model.with("menu", "classpath:/templates/" + menuPath);
        model.with("main", "classpath:/templates/" + pagePath);
        model.with("counterMail", counterMail);
        String response = template.render(model);

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    public void sendResponse(HttpExchange httpExchange, SessionModel session, Integer counterMail, String title,
                             String menuPath, String pagePath) throws IOException {

        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/main.twig");
        JtwigModel model = JtwigModel.newModel();

        model.with("title", title);
        model.with("session", session);
        model.with("menu", "classpath:/templates/" + menuPath);
        model.with("main", "classpath:/templates/" + pagePath);
        model.with("counterMail", counterMail);
        String response = template.render(model);

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    public void sendResponseTeaamShop(HttpExchange httpExchange, SessionModel session, Integer countMail, LinkedList<T> objectsList,
                                      Integer state, Integer voted, TeamDao teamDao, String teamName, Integer walletTeam,
                                      List<StudentModel> members) throws IOException {

        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/main.twig");
        JtwigModel model = JtwigModel.newModel();
        model.with("artifactModels", objectsList);
        model.with("title", "Team shop");
        model.with("session", session);
        model.with("counterMail", countMail);
        model.with("teamName", teamName);
        model.with("walletTeam", walletTeam);
        model.with("members", members);
        model.with("voted", voted);
        model.with("menu", "classpath:/templates/student/student_menu.twig");
        model.with("main", "classpath:/templates/student/student_team_shop.twig");
        model.with("data1", "classpath:/templates/student/data.twig");
        model.with("state", state );
        String titleArt = teamDao.getObjectList().get(0).getArtifactModel().getTitle();

        if (titleArt.length() == 0) {
            model.with("titleArt", "empty");
            model.with("price", "0");
        } else {
            model.with("titleArt", titleArt);
            model.with("price", teamDao.getObjectList().get(0).getArtifactModel().getPrice());
        }

        String response = template.render(model);

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    public void sendResponseCreateTeam(HttpExchange httpExchange, SessionModel session, Integer countMail, LinkedList<T> objectsList,
                                      Integer state) throws IOException {

        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/main.twig");
        JtwigModel model = JtwigModel.newModel();
        model.with("studentModels", objectsList);
        model.with("title", "Create Team");
        model.with("session", session);
        model.with("counterMail", countMail);
        model.with("menu", "classpath:/templates/mentor/menu_mentor.twig");
        model.with("main", "classpath:/templates/mentor/mentor_create_team.twig");
        model.with("state", state );

        String response = template.render(model);

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
  
    public void sendResponseCreateTeam(HttpExchange httpExchange, SessionModel session, Integer countMail, LinkedList<T> objectsList,
                                      Integer state) throws IOException {

        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/main.twig");
        JtwigModel model = JtwigModel.newModel();

        model.with("mailModels", objectsList);
        model.with("title", "Show Mail");
        model.with("menu", "classpath:/templates/admin/menu_admin.twig");
        model.with("main", "classpath:/templates/admin/admin_mail.twig");
        model.with("response", responsee);
        model.with("counterMail", countMail);
        String response = template.render(model);

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    public void sendResponseEmptyCreateTeam(HttpExchange httpExchange, SessionModel session, Integer countMail, Integer state, LinkedList<TeamShoppingModel> teams) throws IOException {

        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/main.twig");
        JtwigModel model = JtwigModel.newModel();
        model.with("title", "Full Team");
        model.with("session", session);
        model.with("counterMail", countMail);
        model.with("menu", "classpath:/templates/mentor/menu_mentor.twig");
        model.with("main", "classpath:/templates/mentor/mentor_full_team.twig");
        model.with("state", state);
        model.with("teams", teams);

        String response = template.render(model);

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    public void sendResponseWallet(HttpExchange httpExchange, SessionModel session, Integer counterMail, LinkedList<T> objectsList,
                             String objectModels, String title, Integer coolCoins,
                             String menuPath, String pagePath) throws IOException {

        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/main.twig");
        JtwigModel model = JtwigModel.newModel();

        model.with(objectModels, objectsList);
        model.with("title", title);
        model.with("session", session);
        model.with("coolCoins", coolCoins);
        model.with("menu", "classpath:/templates/" + menuPath);
        model.with("main", "classpath:/templates/" + pagePath);
        model.with("counterMail", counterMail);
        String response = template.render(model);

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    public void sendResponseLevel(HttpExchange httpExchange, SessionModel session, Integer counterMail, String title,
                                  Integer studentExp, LevelModel studentLevel, LevelModel previousLevel, LevelModel nextLevel,
                                  String menuPath, String pagePath) throws IOException {

        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/main.twig");
        JtwigModel model = JtwigModel.newModel();

        model.with("studentLevel", studentLevel);
        model.with("previousLevel", previousLevel);
        model.with("nextLevel", nextLevel);
        model.with("studentExp", studentExp);
        model.with("title", title);
        model.with("session", session);
        model.with("menu", "classpath:/templates/" + menuPath);
        model.with("main", "classpath:/templates/" + pagePath);
        model.with("counterMail", counterMail);
        String response = template.render(model);

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    public void sendBuyArtifactResponse(HttpExchange httpExchange, SessionModel session, Integer counterMail, LinkedList<T> objectsList,
                             Integer currentCoolCoins, String objectModels, String title,
                             String menuPath, String pagePath) throws IOException {

        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/main.twig");
        JtwigModel model = JtwigModel.newModel();

        model.with(objectModels, objectsList);
        model.with("title", title);
        model.with("session", session);
        model.with("currentCoolCoins", currentCoolCoins);
        model.with("menu", "classpath:/templates/" + menuPath);
        model.with("main", "classpath:/templates/" + pagePath);
        model.with("counterMail", counterMail);
        String response = template.render(model);

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
