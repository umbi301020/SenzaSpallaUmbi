package it.catering.catring.model.managers;

import it.catering.catring.model.entities.*;
import java.util.*;

public class RicettarioManager {
    private static RicettarioManager instance;
    private List<Preparazione> preparazioni;
    private List<Ricetta> ricette;
    private List<Ingrediente> ingredientiBase;
    
    private RicettarioManager() {
        this.preparazioni = new ArrayList<>();
        this.ricette = new ArrayList<>();
        this.ingredientiBase = new ArrayList<>();
        initializeIngredientiBase();
    }
    
    public static synchronized RicettarioManager getInstance() {
        if (instance == null) {
            instance = new RicettarioManager();
        }
        return instance;
    }
    
    private void initializeIngredientiBase() {
        ingredientiBase.addAll(Arrays.asList(
            new Ingrediente("Farina 00", "g"),
            new Ingrediente("Uova", "pz"),
            new Ingrediente("Latte", "ml"),
            new Ingrediente("Burro", "g"),
            new Ingrediente("Olio EVO", "ml"),
            new Ingrediente("Sale", "g"),
            new Ingrediente("Pepe", "g"),
            new Ingrediente("Pomodoro", "g"),
            new Ingrediente("Mozzarella", "g"),
            new Ingrediente("Parmigiano", "g"),
            new Ingrediente("Basilico", "g"),
            new Ingrediente("Aglio", "spicchi"),
            new Ingrediente("Cipolla", "g"),
            new Ingrediente("Carote", "g"),
            new Ingrediente("Sedano", "g")
        ));
    }
    
    public Ricetta createRicetta(String nome, String descrizione, User proprietario, int numeroPortate) {
        Ricetta ricetta = new Ricetta(nome, descrizione, proprietario, numeroPortate);
        ricette.add(ricetta);
        return ricetta;
    }
    
    public Preparazione createPreparazione(String nome, String descrizione, User proprietario) {
        Preparazione preparazione = new Preparazione(nome, descrizione, proprietario);
        preparazioni.add(preparazione);
        return preparazione;
    }
    
    public List<Ricetta> getRicettePubblicate() {
        return ricette.stream()
                .filter(Preparazione::isPubblicata)
                .toList();
    }
    
    public List<Preparazione> getPreparazioniPubblicate() {
        return preparazioni.stream()
                .filter(Preparazione::isPubblicata)
                .toList();
    }
    
    public List<Ricetta> getRicetteByProprietario(User proprietario) {
        return ricette.stream()
                .filter(r -> r.getProprietario().equals(proprietario))
                .toList();
    }
    
    public List<Preparazione> getPreparazioniByProprietario(User proprietario) {
        return preparazioni.stream()
                .filter(p -> p.getProprietario().equals(proprietario))
                .toList();
    }
    
    public List<Ricetta> cercaRicettePerTag(String tag) {
        return ricette.stream()
                .filter(r -> r.isPubblicata() && r.getTags().contains(tag))
                .toList();
    }
    
    public List<Ricetta> cercaRicettePerNome(String nome) {
        return ricette.stream()
                .filter(r -> r.isPubblicata() && 
                        r.getNome().toLowerCase().contains(nome.toLowerCase()))
                .toList();
    }
    
    public List<Ingrediente> getIngredientiBase() {
        return new ArrayList<>(ingredientiBase);
    }
    
    public void aggiungiIngredienteBase(Ingrediente ingrediente) {
        if (!ingredientiBase.contains(ingrediente)) {
            ingredientiBase.add(ingrediente);
        }
    }
    
    public boolean canDelete(Preparazione preparazione) {
        // Verifica se la preparazione è utilizzata in altre ricette o menu
        boolean usataInRicette = ricette.stream()
                .anyMatch(r -> r.getIngredienti().stream()
                        .anyMatch(dose -> dose.getIngrediente().getNome().equals(preparazione.getNome())));
        
        return !usataInRicette && !preparazione.isPubblicata();
    }
    
    public boolean canModify(Preparazione preparazione, User user) {
        return preparazione.getProprietario().equals(user) && !preparazione.isPubblicata();
    }
    
    public Set<String> getAllTags() {
        Set<String> allTags = new HashSet<>();
        ricette.forEach(r -> allTags.addAll(r.getTags()));
        preparazioni.forEach(p -> allTags.addAll(p.getTags()));
        return allTags;
    }
}