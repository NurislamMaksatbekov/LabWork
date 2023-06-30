package entity;

import java.util.Objects;

public class User {

    private int id;

    private String name;

    private String email;

    private String password;

    private int votes;


    public User(int id, String name, String email, String password, int votes) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.votes = votes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        if (votes > 1) {
            throw new IllegalArgumentException("you can't votes more than 1.");
        }
        this.votes = votes;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", votes=" + votes +
                '}';
    }
}
