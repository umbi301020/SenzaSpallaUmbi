public class PublishedMenuState implements MenuState {
    @Override
    public void publish(Menu menu) {
        System.out.println("Il menu è già pubblicato");
    }
    
    @Override
    public void edit(Menu menu) {
        throw new IllegalStateException("Non puoi modificare un menu pubblicato. Creane una copia.");
    }
    
    @Override
    public void useInEvent(Menu menu) {
        menu.setStatus(it.catering.domain.MenuStatus.IN_USE);
        System.out.println("Menu ora in uso per l'evento");
    }
    
    @Override
    public String getStateName() {
        return "PUBLISHED";
    }
}
