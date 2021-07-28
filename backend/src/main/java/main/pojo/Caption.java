package main.pojo;

public class Caption {
    private int id;
    private int pos;
    private String eng;
    private String chn;
    private String se;
    private int season;
    private int episode;

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public int getEpisode() {
        return episode;
    }

    public void setEpisode(int episode) {
        this.episode = episode;
    }

    public int getId() {
        return id;
    }

    public int getPos() {
        return pos;
    }

    public String getEng() {
        return eng;
    }

    public String getChn() {
        return chn;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public void setEng(String eng) {
        this.eng = eng;
    }

    public void setChn(String chn) {
        this.chn = chn;
    }

    public String getSe() {
        return se;
    }

    public void setSe(String se) {
        this.se = se;
    }
}
