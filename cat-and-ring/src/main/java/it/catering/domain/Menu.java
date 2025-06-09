// Pattern GRASP: Information Expert - Menu conosce le proprie caratteristiche
public class Menu extends MenuComponent {
    private String menuId;
    private String title;
    private String additionalInfo;
    private List<MenuComponent> sections;
    private MenuCharacteristics characteristics;
    private MenuStatus status;
    
    public Menu(String menuId, String title) {
        this.menuId = menuId;
        this.title = title;
        this.sections = new ArrayList<>();
        this.characteristics = new MenuCharacteristics();
        this.status = MenuStatus.DRAFT;
    }
    
    @Override
    public void add(MenuComponent component) {
        sections.add(component);
    }
    
    @Override
    public void remove(MenuComponent component) {
        sections.remove(component);
    }
    
    @Override
    public MenuComponent getChild(int index) {
        return sections.get(index);
    }
    
    @Override
    public String getName() {
        return title;
    }
    
    @Override
    public void display() {
        System.out.println("=== " + title + " ===");
        if (additionalInfo != null) {
            System.out.println("Info: " + additionalInfo);
        }
        for (MenuComponent section : sections) {
            section.display();
        }
    }
    
    public String getMenuId() { return menuId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAdditionalInfo() { return additionalInfo; }
    public void setAdditionalInfo(String additionalInfo) { this.additionalInfo = additionalInfo; }
    public List<MenuComponent> getSections() { return new ArrayList<>(sections); }
    public MenuCharacteristics getCharacteristics() { return characteristics; }
    public MenuStatus getStatus() { return status; }
    public void setStatus(MenuStatus status) { this.status = status; }
}