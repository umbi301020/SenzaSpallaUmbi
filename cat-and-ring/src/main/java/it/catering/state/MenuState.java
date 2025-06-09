package it.catering.state;

import it.catering.domain.Menu;

// Pattern State - Stato del menu
public interface MenuState {
    void publish(Menu menu);
    void edit(Menu menu);
    void useInEvent(Menu menu);
    String getStateName();
}