package it.catering.controller;

import it.catering.domain.*;
import it.catering.persistence.*;
import it.catering.factory.*;
import it.catering.strategy.*;
import it.catering.observer.*;
import java.util.*;

// Pattern GRASP: Controller - Coordina le operazioni del sistema
public class MenuController {
    private final MenuRepository menuRepository;
    private final RecipeRepository recipeRepository;
    private final CateringEntityFactory entityFactory;
    
    public MenuController() {
        this.menuRepository = MenuRepository.getInstance();
        this.recipeRepository = RecipeRepository.getInstance();
        this.entityFactory = new StandardCateringEntityFactory();
    }
    
    public String createNewMenu(String eventId, String title) {
        String menuId = "MENU_" + System.currentTimeMillis();
        Menu menu = entityFactory.createMenu(menuId, title);
        menuRepository.save(menu);
        return menuId;
    }
    
    public void defineMenuSections(String menuId, List<String> sectionNames) {
        Menu menu = menuRepository.findById(menuId);
        if (menu == null) {
            throw new IllegalArgumentException("Menu non trovato");
        }
        
        for (String sectionName : sectionNames) {
            MenuSection section = new MenuSection(sectionName);
            menu.add(section);
        }
        menuRepository.save(menu);
    }
    
    public void addRecipeToSection(String menuId, String recipeId, String sectionName) {
        Menu menu = menuRepository.findById(menuId);
        Recipe recipe = recipeRepository.findById(recipeId);
        
        if (menu == null || recipe == null) {
            throw new IllegalArgumentException("Menu o ricetta non trovati");
        }
        
        // Trova la sezione
        MenuSection targetSection = null;
        for (MenuComponent component : menu.getSections()) {
            if (component instanceof MenuSection section && 
                section.getName().equals(sectionName)) {
                targetSection = section;
                break;
            }
        }
        
        if (targetSection != null) {
            MenuItem menuItem = new MenuItem(recipe.getName(), recipe);
            targetSection.add(menuItem);
            menuRepository.save(menu);
        }
    }
    
    public void moveRecipeBetweenSections(String menuId, String recipeId, 
                                        String sourceSectionName, String destinationSectionName) {
        Menu menu = menuRepository.findById(menuId);
        if (menu == null) {
            throw new IllegalArgumentException("Menu non trovato");
        }
        
        MenuSection sourceSection = findSection(menu, sourceSectionName);
        MenuSection destinationSection = findSection(menu, destinationSectionName);
        
        if (sourceSection != null && destinationSection != null) {
            MenuItem itemToMove = findMenuItem(sourceSection, recipeId);
            if (itemToMove != null) {
                sourceSection.remove(itemToMove);
                destinationSection.add(itemToMove);
                menuRepository.save(menu);
            }
        }
    }
    
    public void deleteRecipesFromSection(String menuId, String sectionName, List<String> recipeIds) {
        Menu menu = menuRepository.findById(menuId);
        if (menu == null) return;
        
        MenuSection section = findSection(menu, sectionName);
        if (section != null) {
            for (String recipeId : recipeIds) {
                MenuItem item = findMenuItem(section, recipeId);
                if (item != null) {
                    section.remove(item);
                }
            }
            menuRepository.save(menu);
        }
    }
    
    public void insertMenuTitle(String menuId, String title) {
        Menu menu = menuRepository.findById(menuId);
        if (menu != null) {
            menu.setTitle(title);
            menuRepository.save(menu);
        }
    }
    
    public void annotateAdditionalInformation(String menuId, String info) {
        Menu menu = menuRepository.findById(menuId);
        if (menu != null) {
            menu.setAdditionalInfo(info);
            menuRepository.save(menu);
        }
    }
    
    public String confirmMenuCreation(String menuId) {
        Menu menu = menuRepository.findById(menuId);
        if (menu != null) {
            menu.setStatus(MenuStatus.PUBLISHED);
            menuRepository.save(menu);
            return "Menu creato e salvato con ID: " + menuId;
        }
        return null;
    }
    
    public Menu selectExistingMenu(String menuId) {
        return menuRepository.findById(menuId);
    }
    
    public List<Recipe> consultRecipeBook() {
        return recipeRepository.findByStatus(RecipeStatus.PUBLISHED);
    }
    
    public void deleteMenu(String menuId) {
        Menu menu = menuRepository.findById(menuId);
        if (menu != null && menu.getStatus() != MenuStatus.IN_USE) {
            // Implementazione semplificata - in un sistema reale si rimuoverebbe dal repository
            menu.setStatus(MenuStatus.DRAFT);
            System.out.println("Menu eliminato: " + menuId);
        }
    }
    
    // Metodi di utilità privati
    private MenuSection findSection(Menu menu, String sectionName) {
        return menu.getSections().stream()
                .filter(component -> component instanceof MenuSection)
                .map(component -> (MenuSection) component)
                .filter(section -> section.getName().equals(sectionName))
                .findFirst()
                .orElse(null);
    }
    
    private MenuItem findMenuItem(MenuSection section, String recipeId) {
        return section.getItems().stream()
                .filter(component -> component instanceof MenuItem)
                .map(component -> (MenuItem) component)
                .filter(item -> item.getRecipe().getRecipeId().equals(recipeId))
                .findFirst()
                .orElse(null);
    }
}