public class Shift {
    private String shiftId;
    private String date;
    private String timeSlot;
    private String location;
    private List<Cook> availableCooks;
    
    public Shift(String shiftId, String date, String timeSlot, String location) {
        this.shiftId = shiftId;
        this.date = date;
        this.timeSlot = timeSlot;
        this.location = location;
        this.availableCooks = new ArrayList<>();
    }
    
    public void addAvailableCook(Cook cook) {
        availableCooks.add(cook);
    }
    
    public String getShiftId() { return shiftId; }
    public String getDate() { return date; }
    public String getTimeSlot() { return timeSlot; }
    public String getLocation() { return location; }
    public List<Cook> getAvailableCooks() { return new ArrayList<>(availableCooks); }
}
