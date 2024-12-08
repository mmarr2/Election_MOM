package com.example.election;

import com.example.election.*;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class ElectionService {

    private List<ElectionData> data;

    public ElectionService(){
        this.data = Arrays.asList(election("12345"), election("23456"), election("34567"));
    }

    public List<ElectionData> getData(){
        return data;
    }


    public int randomVote(){
        return new Random().nextInt(400) + 100;
    }

    public int vorzugVote(){
        return new Random().nextInt(30) + 1;
    }

    public int randomListenNr(){
        return new Random().nextInt(9) + 1;
    }

    public ElectionData election(String regionId){
        List<Party> parties = Arrays.asList(
                new Party("OEVP", randomVote(), getVorzug()),
                new Party("SPOE", randomVote(), getVorzug()),
                new Party("FPOE", randomVote(), getVorzug()),
                new Party("GRUENE", randomVote(), getVorzug()),
                new Party("NEOS", randomVote(), getVorzug())
        );

        return new ElectionData(regionId, "Linz Bahnhof", "Bahnhofsstraße 27/9", "Linz", "Austria", parties);
    }


    public List<Vorzug> getVorzug(){
        return Arrays.asList(
            new Vorzug(getVorname() + " " + getNachname(), randomListenNr(), vorzugVote()),
            new Vorzug(getVorname() + " " + getNachname(), randomListenNr(), vorzugVote()),
            new Vorzug(getVorname() + " " + getNachname(), randomListenNr(), vorzugVote()),
            new Vorzug(getVorname() + " " + getNachname(), randomListenNr(), vorzugVote()),
            new Vorzug(getVorname() + " " + getNachname(), randomListenNr(), vorzugVote())

        );
    }

    public String getVorname(){
        String[] namen = new String[]{"Matthias", "Katharina", "Johanna", "Alexander", "Helene", "Simon", "Clara", "Florian", "Annika", "Sebastian", "Theresa", "Markus", "Marie", "Andreas", "Franziska"};
        return namen[new Random().nextInt(14)];
    }

    public String getNachname(){
        String[] namen = new String[]{"Schmidt", "Meyer", "Schäfer", "Koch", "Krause", "Jäger", "Lehmann", "Schmitt", "Peters", "Wagner", "Fuchs", "Scherer", "Klein", "Hartwig", "Becker"};
        return namen[new Random().nextInt(14)];
    }
}
