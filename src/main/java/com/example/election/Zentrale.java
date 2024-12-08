package com.example.election;

import java.util.ArrayList;
import java.util.List;

public class Zentrale {

    private List<ElectionData> data = new ArrayList<>();

    public void addElectionData(ElectionData electionData){
        data.add(electionData);
    }

    public List<ElectionData> getData(){
        return data;
    }
}
