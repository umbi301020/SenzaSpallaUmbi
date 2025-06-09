package it.catering.catring.model.entities;

import java.util.*;

public class Preparazione {
    private String nome;
    private String descrizione;
    private User proprietario;
    private String autore;
    private List<Dose> ingredienti;
    private List<String> istruzioniAnticipo;
    private List<String> istruzioniUltimo;
    private int tempoPreparazione; // in minuti
    private double quantitaRisultante;
    private String unitaMisuraRisultato;
    private Set<String> tags;
    private boolean pubblicata;
    private Long id;
    
    public Preparazione(String nome, String descrizione, User proprietario) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.proprietario = proprietario;
        this.ingredienti = new ArrayList<>();
        this.istruzioniAnticipo = new ArrayList<>();
        this.istruzioniUltimo = new ArrayList<>();
        this.tags = new HashSet<>();
        this.pubblicata = false;
        this.id = System.currentTimeMillis();
    }
    
    // Getters e setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }
    public User getProprietario() { return proprietario; }
    public void setProprietario(User proprietario) { this.proprietario = proprietario; }
    public String getAutore() { return autore; }
    public void setAutore(String autore) { this.autore = autore; }
    public List<Dose> getIngredienti() { return ingredienti; }
    public void setIngredienti(List<Dose> ingredienti) { this.ingredienti = ingredienti; }
    public List<String> getIstruzioniAnticipo() { return istruzioniAnticipo; }
    public void setIstruzioniAnticipo(List<String> istruzioniAnticipo) { this.istruzioniAnticipo = istruzioniAnticipo; }
    public List<String> getIstruzioniUltimo() { return istruzioniUltimo; }
    public void setIstruzioniUltimo(List<String> istruzioniUltimo) { this.istruzioniUltimo = istruzioniUltimo; }
    public int getTempoPreparazione() { return tempoPreparazione; }
    public void setTempoPreparazione(int tempoPreparazione) { this.tempoPreparazione = tempoPreparazione; }
    public double getQuantitaRisultante() { return quantitaRisultante; }
    public void setQuantitaRisultante(double quantitaRisultante) { this.quantitaRisultante = quantitaRisultante; }
    public String getUnitaMisuraRisultato() { return unitaMisuraRisultato; }
    public void setUnitaMisuraRisultato(String unitaMisuraRisultato) { this.unitaMisuraRisultato = unitaMisuraRisultato; }
    public Set<String> getTags() { return tags; }
    public void setTags(Set<String> tags) { this.tags = tags; }
    public boolean isPubblicata() { return pubblicata; }
    public void setPubblicata(boolean pubblicata) { this.pubblicata = pubblicata; }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public void aggiungiIngrediente(Dose dose) {
        ingredienti.add(dose);
    }
    
    public void rimuoviIngrediente(Dose dose) {
        ingredienti.remove(dose);
    }
    
    public void aggiungiTag(String tag) {
        tags.add(tag);
    }
    
    public void rimuoviTag(String tag) {
        tags.remove(tag);
    }
    
    public void aggiungiIstruzioneAnticipo(String istruzione) {
        istruzioniAnticipo.add(istruzione);
    }
    
    public void aggiungiIstruzioneUltimo(String istruzione) {
        istruzioniUltimo.add(istruzione);
    }
    
    @Override
    public String toString() {
        return nome + (pubblicata ? " (Pubblicata)" : " (Bozza)");
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Preparazione that = (Preparazione) o;
        return Objects.equals(id, that.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
