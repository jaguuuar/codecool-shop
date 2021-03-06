package com.codecool.dream_is_green.model;

public class MailBoxModel extends User {

    private String content;
    private String header;
    private Integer idRecipient;
    private Integer read;
    private Integer idMail;

    public MailBoxModel(int userID, String name, String surname, String email,
                        String login, String password, String header, String content, Integer idRecipient, Integer read, Integer idMail) {
        super(userID, name, surname, email, login, password);
        this.content = content;
        this.header = header;
        this.idRecipient = idRecipient;
        this.read = read;
        this.idMail = idMail;
    }

    public String getContent() {
        return content;
    }

    public String getHeader() {
        return header;
    }

    public Integer getIdRecipient() {
        return idRecipient;
    }

    public Integer getRead() {
        return read;
    }

    public Integer getIdMail() {

        return idMail;
    }

}
