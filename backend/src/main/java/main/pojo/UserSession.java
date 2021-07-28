package main.pojo;

public class UserSession {
    private String username;
    private long time;

    public UserSession(String username, long currentTimeMillis) {
        this.username=username;
        this.time = currentTimeMillis;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
