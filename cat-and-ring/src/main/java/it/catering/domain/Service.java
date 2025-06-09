package it.catering.domain;

import java.time.LocalDateTime;
import java.util.*;


// Pattern GRASP: Information Expert - Service gestisce il proprio menù
public class Service {
    private String serviceId;
    private String serviceName;
    private String timeSlot;
    private Menu menu;
    
    public Service(String serviceId, String serviceName, String timeSlot) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.timeSlot = timeSlot;
    }
    
    public void setMenu(Menu menu) { this.menu = menu; }
    
    public String getServiceId() { return serviceId; }
    public String getServiceName() { return serviceName; }
    public String getTimeSlot() { return timeSlot; }
    public Menu getMenu() { return menu; }
}
