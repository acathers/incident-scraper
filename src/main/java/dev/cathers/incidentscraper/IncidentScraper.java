package dev.cathers.incidentscraper;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.naming.directory.InvalidSearchFilterException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class IncidentScraper {

    private static String userAgent;
    private static String url;
    private static String referrer;
    private static String headerInfo;
    private static Connection.Response response;

    private IncidentScraper() {
        userAgent = "Mozilla/5.0 (Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36";
        referrer = "http://www.google.com";
        url = "http://www11.honolulu.gov/hpdtraffic/MainPrograms/frmMain.asp?sSearch=All+Incidents&sSort=I_tTimeCreate";
        headerInfo = "Date Time Type Address Location Area";
    }

    public static IncidentScraper init() {
        IncidentScraper scraper = new IncidentScraper();
        try {
            response = Jsoup.connect(url).userAgent(userAgent).referrer(referrer).timeout(12000).followRedirects(true).execute();
        } catch (IOException e) {
            System.out.println("Unable to connect to " + url);
        }
        return scraper;
    }

    public List<Incident> parseIncidents() {
        List<Incident> incidents = new ArrayList<>();
        try {
            Document doc = response.parse();
            Elements tableRows = doc.select("tr");
            int index = IntStream.range(0, tableRows.size()).filter(i -> tableRows.get(i).text().equals(headerInfo)).findFirst().orElseThrow(InvalidSearchFilterException::new) + 1;

            for (Element tableRow : tableRows.subList(index, tableRows.size())) {
                String date = tableRow.child(0).text();
                String time = tableRow.child(1).text();
                String type = tableRow.child(2).text();
                String address = tableRow.child(3).text();
                String location = tableRow.child(4).text();
                String area = tableRow.child(5).text();
                incidents.add(new Incident(date, time, type, address, location, area));
            }

        } catch (IOException e) {
            System.out.println("Unable to read response");
        } catch (InvalidSearchFilterException e) {
            System.out.println("Unable to find header index for parsing");

        }
        return incidents;
    }


}
