package it.catering.catring.model.managers;

import it.catering.catring.model.entities.*;
import it.catering.catring.model.observers.*;
import java.util.*;

public class MenuManager implements Subject<MenuObserver> {
    private static MenuManager instance;
    private List<Menu> menus;
    private List<MenuObserver> observers;
    
    private MenuManager() {
        this.menus = new ArrayList<>();
        this.observers = new ArrayList<>();
    }
    
    public static synchronized MenuManager getInstance() {
        if (instance == null) {
            instance = new MenuManager();
        }
        return instance;
    }
    
    public Menu createMenu(String titolo, Chef chef) {
        Menu menu = new Menu(titolo, chef);
        menus.add(menu);
        notifyMenuCreated(menu);
        return menu;
    }
    
    public void updateMenu(Menu menu) {
        if (menus.contains(menu)) {
            notifyMenuUpdated(menu);
        }
    }
    
    public void deleteMenu(Menu menu) {
        if (menu.isUtilizzato()) {
            throw new IllegalStateException("Impossibile eliminare un menu utilizzato");
        }
        if (menus.remove(menu)) {
            notifyMenuDeleted(menu);
        }
    }
    
    public List<Menu> getMenusByChef(Chef chef) {
        return menus.stream()
                .filter(m -> m.getChef().equals(chef))
                .toList();
    }
    
    public List<Menu> getMenusDisponibili() {
        return menus.stream()
                .filter(m -> !m.isUtilizzato())
                .toList();
    }
    
    public Menu findMenuById(Long id) {
        return menus.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    
    public List<Menu> getAllMenus() {
        return new ArrayList<>(menus);
    }
    
    @Override
    public void addObserver(MenuObserver observer) {
        observers.add(observer);
    }
    
    @Override
    public void removeObserver(MenuObserver observer) {
        observers.remove(observer);
    }
    
    @Override
    public void notifyObservers() {
        // Implementazione generica
    }
    
    private void notifyMenuCreated(Menu menu) {
        for (MenuObserver observer : observers) {
            observer.onMenuCreated(menu);
        }
    }
    
    private void notifyMenuUpdated(Menu menu) {
        for (MenuObserver observer : observers) {
            observer.onMenuUpdated(menu);
        }
    }
    
    private void notifyMenuDeleted(Menu menu) {
        for (MenuObserver observer : observers) {
            observer.onMenuDeleted(menu);
        }
    }
}