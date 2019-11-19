package dev.cathers.incidentscraper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Incident {

    private String area;
    private String date;
    private String time;
    private String type;
    private String address;
    private String location;

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy h:mm:ss a");

    public Incident(String date, String time, String type, String address, String location, String area) {
        this.area = normalizeString(area);
        LocalDateTime datetime = LocalDateTime.parse(date + " " + time, formatter);
        this.date = datetime.toLocalDate().toString();
        this.time = datetime.toLocalTime().toString();
        this.type = normalizeString(type);
        this.address = normalizeString(address);
        this.location = normalizeString(location);

    }

    public String getArea() {
        return area;
    }

    private String normalizeString(String value) {
        return value.replaceAll("[\'\"]", "");
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDate() {
        return date;
    }

    public String getTime() { return time; }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Incident [address=" + address + ", area=" + area + ", date=" + date + ", location=" + location
            + ", type=" + type + "]";
    }

}
