// Pattern GRASP: Information Expert - Cook conosce le proprie informazioni
public class Cook {
    private String cookId;
    private String name;
    private List<String> skills;
    private boolean available;
    
    public Cook(String cookId, String name) {
        this.cookId = cookId;
        this.name = name;
        this.skills = new ArrayList<>();
        this.available = true;
    }
    
    public void addSkill(String skill) {
        skills.add(skill);
    }
    
    public String getCookId() { return cookId; }
    public String getName() { return name; }
    public List<String> getSkills() { return new ArrayList<>(skills); }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
}