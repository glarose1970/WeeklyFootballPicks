package com.inkedapparelonline.weeklyfootballpicks.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;

public class Player {

    private String Name;
    private String Company;
    private int WinTotal;
    private int LossTotal;
    private List<Team> Picks;

    public Player() {
    }

    public Player(String name, String company) {
        Name = name;
        Company = company;
    }

    public Player(String name, String company, List<Team> picks) {
        Name = name;
        Company = company;
        Picks = picks;
    }

    public Player(String name, String company, int winTotal, int lossTotal, List<Team> picks) {
        Name = name;
        Company = company;
        WinTotal = winTotal;
        LossTotal = lossTotal;
        Picks = picks;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        Company = company;
    }

    public int getWinTotal() {
        return WinTotal;
    }

    public void setWinTotal(int winTotal) {
        WinTotal = winTotal;
    }

    public int getLossTotal() {
        return LossTotal;
    }

    public void setLossTotal(int lossTotal) {
        LossTotal = lossTotal;
    }

    public List<Team> getPicks() {
        return Picks;
    }

    public void setPicks(List<Team> picks) {
        Picks = picks;
    }
}
