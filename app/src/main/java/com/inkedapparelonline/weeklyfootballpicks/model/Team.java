package com.inkedapparelonline.weeklyfootballpicks.model;

public class Team {

    private String Name;
    private String Abr;
    private String Record;
    private String ImgLocation;
    private String Score;
    boolean isChecked;
    boolean isEnabled;

    public Team() {
    }

    public Team(String name, String imgLocation, boolean is_checked, boolean is_enabled) {
        Name = name;
        ImgLocation = imgLocation;
        isChecked = is_checked;
        isEnabled = is_enabled;
    }

    public Team(String name, String record, String score) {
        Name = name;
        Record = record;
        Score = score;
    }

    public Team(String name, String record, String imgLocation, String score, boolean is_checked, boolean is_enabled) {
        Name = name;
        Record = record;
        ImgLocation = imgLocation;
        Score = score;
        isChecked = is_checked;
        isEnabled = is_enabled;
    }

    public Team(String name, String abr, String record, String imgLocation, String score) {
        Name = name;
        Abr = abr;
        Record = record;
        ImgLocation = imgLocation;
        Score = score;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
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
