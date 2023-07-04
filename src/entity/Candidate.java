package entity;

import java.util.HashMap;
import java.util.Map;

public class Candidate {

    private Map<Candidate,Double> candidateDoubleMap = new HashMap<>();
    private String name;
    private String photo;
    private int votes;
    private double percent;


    public Candidate(String name, String photo, int votes,double percent) {
        this.name = name;
        this.photo = photo;
        this.votes = votes;
        this.percent = percent;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
