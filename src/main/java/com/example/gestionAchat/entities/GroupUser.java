package com.example.gestionAchat.entities;

import com.example.gestionAchat.domain.GroupUserPK;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A Role.
 */
@Entity
@Table(name = "pm_group_users")
public class GroupUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private GroupUserPK groupUserPK;

    @ManyToOne
    @JsonIgnoreProperties(value = "groupUserSet", allowSetters = true)
    @JoinColumn(name = "group_id", nullable = false, insertable = false, updatable = false)
    private Group group;

    @ManyToOne
    @JsonIgnoreProperties(value = "groupUserSet", allowSetters = true)
    @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    private User user;

    public GroupUser() {
    }

    public GroupUser(GroupUserPK groupUserPK, User user, Group group ) {
        this.groupUserPK = groupUserPK;
        this.group=group;
        this.user=user;
    }

    public GroupUserPK getGroupUserPK() {
        return groupUserPK;
    }

    public void setGroupUserPK(GroupUserPK groupUserPK) {
        this.groupUserPK = groupUserPK;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "GroupUser{" +
                "groupUserPK=" + groupUserPK +
                ", group=" + group +
                ", user=" + user +
                '}';
    }
}
