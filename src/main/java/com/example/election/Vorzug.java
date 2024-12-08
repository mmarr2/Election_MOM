package com.example.election;

public class Vorzug {

    private String name;
    private int listennummer;

    private int votes;

    public Vorzug(){
        this("John Doe", 3, 1);
    }

    public Vorzug(String name, int listennummer, int votes){
        this.name = name;
        this.listennummer = listennummer;
        this.votes = votes;
    }

    public String getName() {
        return name;
    }

    public int getListennummer() {
        return listennummer;
    }

    public int getVotes(){
        return votes;
    }
}