package it.catering.domain;

import java.time.LocalDateTime;
import java.util.*;

// Pattern GRASP: Information Expert - Event conosce i propri dati e servizi
public class Event {
    private String eventId;
    private String title;
    private String location;
    private LocalDateTime date;
    private String clientName;
    private List<Service> services;
    private EventStatus status;
    
    public Event(String eventId, String title, String location, LocalDateTime date, String clientName) {
        this.eventId = eventId;
        this.title = title;
        this.location = location;
        this.date = date;
        this.clientName = clientName;
        this.services = new ArrayList<>();
        this.status = EventStatus.PLANNING;
    }
    
    public void addService(Service service) {
        services.add(service);
    }
    
    public List<Service> getServices() { return new ArrayList<>(services); }
    public String getEventId() { return eventId; }
    public String getTitle() { return title; }
    public String getLocation() { return location; }
    public LocalDateTime getDate() { return date; }
    public String getClientName() { return clientName; }
    public EventStatus getStatus() { return status; }
    public void setStatus(EventStatus status) { this.status = status; }
}

enum EventStatus {
    PLANNING, APPROVED, IN_PROGRESS, COMPLETED, CANCELLED
}
