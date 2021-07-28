package main.pojo;

public class UserProgress {
    private int id;
    private String username;
    private int last_pos;

    public UserProgress(int id, String username, int last_pos) {
        this.id = id;
        this.username = username;
        this.last_pos=last_pos;
    }

    public UserProgress(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getLast_pos() {
        return last_pos;
    }

    public void setLast_pos(int last_pos) {
        this.last_pos = last_pos;
    }
}
