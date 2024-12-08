package com.example.kafka.controller;
import com.example.election.ElectionData;
import com.example.election.ElectionService;
import com.example.election.Zentrale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import com.example.kafka.producer.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class ElectionController {

    private final ElectionService electionService;

    @Autowired
    private MessageProducer messageProducer;
    private Zentrale zentrale = new Zentrale();

    public ElectionController(ElectionService electionService) {
        this.electionService = electionService;
    }

    @RequestMapping("/")
    public String electionMain() {
        return "<h1>Election Data</h1>" +
                "<br><a href='http://localhost:8080/election/12345/json'>JSON-Anzeige</a><br/>" +
                "\n<a href='http://localhost:8080/election/34567/xml'>XML-Anzeige</a><br/>";
    }

    @RequestMapping(value = "/election/{regionID}/xml", produces = MediaType.APPLICATION_XML_VALUE)
    public ElectionData electionXML(@PathVariable String regionID) {
        return getElectionDataByRegionID(regionID);
    }

    @RequestMapping(value = "/election/{regionID}/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ElectionData electionJSON(@PathVariable String regionID) {
        return getElectionDataByRegionID(regionID);
    }

    private ElectionData getElectionDataByRegionID(String regionID) {
        List<ElectionData> elections = electionService.getData();
        for (ElectionData election : elections) {
            if (election.getRegionID().equals(regionID)) {
                return election;
            }
        }
        return null;
    }


    @RequestMapping(value = "/getData", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Zentrale getAll(){
        return zentrale;
    }

    @PostMapping(value = "/send/{regionID}")
    public String sendElectionData(@PathVariable String regionID) {
        zentrale.addElectionData(getElectionDataByRegionID(regionID));
        messageProducer.sendMessage("election", getElectionDataByRegionID(regionID));
        return "Election Data for " + regionID + " sent!";
    }

    @PostMapping(value = "/send/{regionID}/xml", produces = MediaType.APPLICATION_XML_VALUE)
    public ElectionData sendElectionDataXML(@PathVariable String regionID) {
        zentrale.addElectionData(getElectionDataByRegionID(regionID));
        messageProducer.sendMessage("election", getElectionDataByRegionID(regionID));
        return getElectionDataByRegionID(regionID);
    }


    @PostMapping(value = "/send/{regionID}/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ElectionData sendElectionDataJSON(@PathVariable String regionID) {
        zentrale.addElectionData(getElectionDataByRegionID(regionID));
        messageProducer.sendMessage("election", getElectionDataByRegionID(regionID));
        return getElectionDataByRegionID(regionID);
    }

}
