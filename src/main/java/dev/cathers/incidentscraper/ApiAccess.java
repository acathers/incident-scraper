package dev.cathers.incidentscraper;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


public class ApiAccess {
    private Client client;
    private WebTarget target;

    public ApiAccess(Config config) {

        client = ClientBuilder.newClient();
        client.register(HttpAuthenticationFeature.basic(config.getUsername(), config.getPassword()));
        target = client.target(config.getApiUrl());

    }
    // sends the post request to the api to add an Incident, returns a Reponse
    public Response add(Incident incident) {
        return target.request(MediaType.APPLICATION_JSON).post(Entity.entity(incident, MediaType.APPLICATION_JSON));
    }



}
