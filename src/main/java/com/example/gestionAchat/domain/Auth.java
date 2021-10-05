package com.example.gestionAchat.domain;

public class Auth {

    private String username;
    private String password;
    private String application;

    public Auth(String username, String password, String application) {
        super();
        this.username = username;
        this.password = password;
        this.application = application;
    }

    public Auth(){};

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }
}
