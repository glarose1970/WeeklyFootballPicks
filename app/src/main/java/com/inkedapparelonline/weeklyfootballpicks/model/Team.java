package com.inkedapparelonline.weeklyfootballpicks.model;

public class Team {

    private String Name;
    private String Abr;
    private String Record;
    private String ImgLocation;
    private String Score;

    public Team() {
    }

    public Team(String name, String imgLocation) {
        Name = name;
        ImgLocation = imgLocation;
    }

    public Team(String name, String record, String score) {
        Name = name;
        Record = record;
        Score = score;
    }

    public Team(String name, String record, String imgLocation, String score) {
        Name = name;
        Record = record;
        ImgLocation = imgLocation;
        Score = score;
    }

    public Team(String name, String abr, String record, String imgLocation, String score) {
        Name = name;
        Abr = abr;
        Record = record;
        ImgLocation = imgLocation;
        Score = score;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String score) {
        Score = score;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAbr() {
        return Abr;
    }

    public void setAbr(String abr) {
        Abr = abr;
    }

    public String getRecord() {
        return Record;
    }

    public void setRecord(String record) {
        Record = record;
    }

    public String getImgLocation() {
        return ImgLocation;
    }

    public void setImgLocation(String imgLocation) {
        ImgLocation = imgLocation;
    }
}
