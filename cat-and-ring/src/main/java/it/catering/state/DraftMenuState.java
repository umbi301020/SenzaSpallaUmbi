public class DraftMenuState implements MenuState {
    @Override
    public void publish(Menu menu) {
        menu.setStatus(it.catering.domain.MenuStatus.PUBLISHED);
        System.out.println("Menu pubblicato con successo");
    }
    
    @Override
    public void edit(Menu menu) {
        System.out.println("Menu modificato");
    }
    
    @Override
    public void useInEvent(Menu menu) {
        throw new IllegalStateException("Non puoi usare un menu in bozza per un evento");
    }
    
    @Override
    public String getStateName() {
        return "DRAFT";
    }
}