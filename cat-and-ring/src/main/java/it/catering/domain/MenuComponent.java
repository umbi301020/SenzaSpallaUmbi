package it.catering.domain;

import java.time.LocalDateTime;
import java.util.*;

// Pattern Composite - Menu composto da sezioni che contengono voci
public abstract class MenuComponent {
    public void add(MenuComponent component) {
        throw new UnsupportedOperationException();
    }
    
    public void remove(MenuComponent component) {
        throw new UnsupportedOperationException();
    }
    
    public MenuComponent getChild(int index) {
        throw new UnsupportedOperationException();
    }
    
    public String getName() {
        throw new UnsupportedOperationException();
    }
    
    public String getDescription() {
        throw new UnsupportedOperationException();
    }
    
    public void display() {
        throw new UnsupportedOperationException();
    }
}