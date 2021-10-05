package com.example.gestionAchat.entities.dto;


import javax.validation.constraints.NotNull;

public class AuthUser {
    @NotNull
    private String username;
    @NotNull
    private String password;
    private String application;


    public AuthUser() {
        super();
    }

    public AuthUser(String username, String password, String application) {
        super();
        this.username = username;
        this.password = password;
        this.application = application;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the application
     */
    public String getApplication() {
        return application;
    }

    /**
     * @param application the application to set
     */
    public void setApplication(String application) {
        this.application = application;
    }


}
