package dev.cathers.incidentscraper;


import java.util.List;
import javax.ws.rs.core.Response;

public final class App {

    public static final int DEFAULT_BAD_RESPONSE_LIMIT = 10;

    public static void main(String[] args) {
        Config config = new Config();
        List<Incident> incidents = IncidentScraper.init().parseIncidents();
        addToDatabase(incidents, DEFAULT_BAD_RESPONSE_LIMIT, new Config());
    }

    public static void addToDatabase(List<Incident> incidents, int badResponseLimit, Config config) {
        ApiAccess api = new ApiAccess(config);
        int badResponses = 0;
        for (Incident incident : incidents) {
            Response response = api.add(incident);
            System.out.println(response.toString());
            if (response.getStatus() != 200) {
                badResponses++;
            }
            response.close();
            if(badResponses > badResponseLimit){
                System.out.println("Bad response limit hit");
                break;
            }
        }
    }
}
