public class InUseMenuState implements MenuState {
    @Override
    public void publish(Menu menu) {
        System.out.println("Il menu è già in uso");
    }
    
    @Override
    public void edit(Menu menu) {
        throw new IllegalStateException("Non puoi modificare un menu in uso");
    }
    
    @Override
    public void useInEvent(Menu menu) {
        System.out.println("Il menu è già in uso");
    }
    
    @Override
    public String getStateName() {
        return "IN_USE";
    }
}