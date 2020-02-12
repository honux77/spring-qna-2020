package net.honux.qna2020.web;

import com.sun.javafx.geom.transform.Identity;

import javax.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 64, unique = true)
    private String email;
    @Column(nullable = false, length = 64)
    private String name;
    @Column(nullable = false, length = 32)
    private String password;

    public Long getId() { return id; }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
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
        this.password = updateUser.password;
    }
    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }


}
