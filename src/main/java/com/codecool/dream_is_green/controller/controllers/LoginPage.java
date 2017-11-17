package com.codecool.dream_is_green.controller.controllers;

import com.codecool.dream_is_green.dao.MentorDAO;
import com.codecool.dream_is_green.dao.SessionDAO;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.HttpCookie;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LoginPage implements HttpHandler {

    UUID sessionId;
    HttpCookie cookie = null;

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");

        if (cookieStr != null) {  // SessionCookie already exists
            cookie = HttpCookie.parse(cookieStr).get(0);
        } else {
            sessionId = UUID.randomUUID();
            cookie = new HttpCookie("SessionId", String.valueOf(sessionId));
            httpExchange.getResponseHeaders().add("Set-cookie", "first=" + cookie.getValue() + "; Max-Age=300");
        }
        String method = httpExchange.getRequestMethod();

        // Send a form if it wasn't submitted yet.
        if(method.equals("GET")){
            String response = this.getLoginTemplate();

            httpExchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
        // If the form was submitted, retrieve it's content.
        if(method.equals("POST")){
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();

            Map<String, String> inputs = parseFormData(formData);

            String username = inputs.get("username");
            String password = inputs.get("password");

            MentorDAO mentorDAO = new MentorDAO();
            String currentPassword = mentorDAO.getUserPassword(username);
            System.out.println(currentPassword);

            if (password.equals(currentPassword)) {
                SessionDAO.insertSession(cookie.getValue(), username);
                httpExchange.getResponseHeaders().set("Location", "/admin");
                httpExchange.sendResponseHeaders(302,-1);

            } else {
                httpExchange.getResponseHeaders().set("Location", "/login");
                httpExchange.sendResponseHeaders(302,-1);
            }
        }
    }

    /**
     * web.LoginPage data is sent as a urlencoded string. Thus we have to parse this string to get data that we want.
     * See: https://en.wikipedia.org/wiki/POST_(HTTP)
     */
    private static Map<String, String> parseFormData(String formData) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<>();
        String[] pairs = formData.split("&");
        for(String pair : pairs){
            String[] keyValue = pair.split("=");
            // We have to decode the value because it's urlencoded. see: https://en.wikipedia.org/wiki/POST_(HTTP)#Use_for_submitting_web_forms
            String value = new URLDecoder().decode(keyValue[1], "UTF-8");
            map.put(keyValue[0], value);
        }
        return map;
    }

    private String getLoginTemplate() {

        JtwigTemplate template = JtwigTemplate.classpathTemplate("static/templates/login/login_Page.twig");
        JtwigModel model = JtwigModel.newModel();

        String response = template.render(model);

        return response;
    }

}
