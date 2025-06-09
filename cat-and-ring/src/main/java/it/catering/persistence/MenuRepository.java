// Pattern Singleton - Un solo repository per i menu
public class MenuRepository {
    private static MenuRepository instance;
    private final Map<String, Menu> menus;
    
    private MenuRepository() {
        this.menus = new ConcurrentHashMap<>();
    }
    
    public static MenuRepository getInstance() {
        if (instance == null) {
            synchronized (MenuRepository.class) {
                if (instance == null) {
                    instance = new MenuRepository();
                }
            }
        }
        return instance;
    }
    
    public void save(Menu menu) {
        menus.put(menu.getMenuId(), menu);
    }
    
    public Menu findById(String menuId) {
        return menus.get(menuId);
    }
    
    public List<Menu> findAll() {
        return new ArrayList<>(menus.values());
    }
    
    public List<Menu> findByStatus(MenuStatus status) {
        return menus.values().stream()
                .filter(menu -> menu.getStatus() == status)
                .toList();
    }
}
