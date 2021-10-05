package com.example.gestionAchat.entities;

import java.util.List;


public class listePrivillegeUser {
    private String Application;
    private String user;
    private List<String> Roles;
    private String Profiles;
    private List<String> orgaPrivillege;
    public listePrivillegeUser() {
        super();
    }

    public listePrivillegeUser(String application, String user, List<String> roles, String profiles,
                               List<String> orgaPrivillege) {
        super();
        Application = application;
        this.user = user;
        Roles = roles;
        Profiles = profiles;
        this.orgaPrivillege = orgaPrivillege;
    }

    /**
     * @return the application
     */
    public String getApplication() {
        return Application;
    }

    /**
     * @param application the application to set
     */
    public void setApplication(String application) {
        Application = application;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the roles
     */
    public List<String> getRoles() {
        return Roles;
    }

    /**
     * @param roles the roles to set
     */
    public void setRoles(List<String> roles) {
        Roles = roles;
    }

    /**
     * @return the profiles
     */
    public String getProfiles() {
        return Profiles;
    }

    /**
     * @param profiles the profiles to set
     */
    public void setProfiles(String profiles) {
        Profiles = profiles;
    }

    /**
     * @return the orgaPrivillege
     */
    public List<String> getOrgaPrivillege() {
        return orgaPrivillege;
    }

    /**
     * @param orgaPrivillege the orgaPrivillege to set
     */
    public void setOrgaPrivillege(List<String> orgaPrivillege) {
        this.orgaPrivillege = orgaPrivillege;
    }


}
