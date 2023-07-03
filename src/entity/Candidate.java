package entity;

public class Candidate {
    private String name;
    private String photo;
    private int votes;
    private double procent;


    public Candidate(String name, String photo, int votes,double procent) {
        this.name = name;
        this.photo = photo;
        this.votes = votes;
        this.procent = procent;
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
