package net.honux.qna2020.web;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class User extends AbstractEntity {

    @Column(nullable = false, length = 64, unique = true)
    @JsonProperty
    private String email;

    @Column(nullable = false, length = 64)
    @JsonProperty
    private String name;

    @Column(nullable = false, length = 32)
    @JsonIgnore
    private String password;

    @JsonProperty
    private int totalAccess;

    public int getTotalAccess() {return totalAccess; }

    public void increaseAccess() {
        totalAccess++;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void update(User updateUser) {
        this.email = updateUser.email;
        this.name = updateUser.name;
        if(updateUser.password != null && updateUser.password.length() >= 1) {
            this.password = updateUser.password;
        }
    }

    public boolean notMatchPassword(String password) {
        return !this.password.equals(password);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + this.getId() + '\'' +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

