// Pattern Composite - Sezione del menu
public class MenuSection extends MenuComponent {
    private String sectionName;
    private List<MenuComponent> items;
    
    public MenuSection(String sectionName) {
        this.sectionName = sectionName;
        this.items = new ArrayList<>();
    }
    
    @Override
    public void add(MenuComponent component) {
        items.add(component);
    }
    
    @Override
    public void remove(MenuComponent component) {
        items.remove(component);
    }
    
    @Override
    public MenuComponent getChild(int index) {
        return items.get(index);
    }
    
    @Override
    public String getName() {
        return sectionName;
    }
    
    @Override
    public void display() {
        System.out.println("\n--- " + sectionName + " ---");
        for (MenuComponent item : items) {
            item.display();
        }
    }
    
    public List<MenuComponent> getItems() { return new ArrayList<>(items); }
}
