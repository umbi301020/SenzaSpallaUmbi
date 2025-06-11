package com.catring.viewfx;

import com.catring.controller.EventoController;
import com.catring.model.Evento;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 * VISTA PER LA GESTIONE DEGLI EVENTI
 * Mostra la lista degli eventi assegnati e i loro dettagli
 * VERSIONE CORRETTA con spaziature migliorate
 */
public class EventiView {
    
    private EventoController controller;
    private VBox layoutPrincipale;
    
    // Componenti dell'interfaccia
    private ListView<Evento> listaEventi;
    private TextArea areaDettagli;
    private Button bottoneCaricaEventi;
    private Label labelStato;
    
    public EventiView(EventoController controller) {
        this.controller = controller;
        creaInterfaccia();
        collegaController();
    }
    
    /**
     * Crea l'interfaccia per la gestione eventi
     */
    private void creaInterfaccia() {
        layoutPrincipale = new VBox();
        layoutPrincipale.setSpacing(20);
        layoutPrincipale.setStyle("-fx-padding: 20px;");
        layoutPrincipale.setMinWidth(1200);
        layoutPrincipale.setPrefWidth(1400);
        
        // Intestazione
        Label titolo = new Label("Eventi Assegnati");
        titolo.setStyle("-fx-font-weight: bold; -fx-font-size: 18px; -fx-text-fill: #2c3e50;");
        
        Label descrizione = new Label("Visualizza e gestisci gli eventi che ti sono stati assegnati");
        descrizione.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 14px;");
        
        // Pulsanti di azione
        HBox pannelloAzioni = creaPannelloAzioni();
        
        // Contenuto principale
        HBox contenutoPrincipale = creaContenutoPrincipale();
        
        // Area di stato
        labelStato = new Label("Sistema pronto. Seleziona 'Carica Eventi' per visualizzare gli eventi assegnati");
        labelStato.setStyle("-fx-text-fill: #27ae60; -fx-padding: 15px; -fx-background-color: #f8f9fa; -fx-font-size: 14px;");
        labelStato.setMinHeight(50);
        
        layoutPrincipale.getChildren().addAll(titolo, descrizione, pannelloAzioni, contenutoPrincipale, labelStato);
    }
    
    /**
     * Crea il pannello con i pulsanti di azione
     */
    private HBox creaPannelloAzioni() {
        HBox pannello = new HBox();
        pannello.setSpacing(15);
        pannello.setStyle("-fx-padding: 10px 0;");
        
        bottoneCaricaEventi = new Button("Carica Eventi Assegnati");
        bottoneCaricaEventi.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-padding: 12px 20px; -fx-font-size: 14px;");
        bottoneCaricaEventi.setPrefWidth(200);
        
        // Spazio vuoto per centrare visivamente
        Region spazio = new Region();
        HBox.setHgrow(spazio, Priority.ALWAYS);
        
        Label info = new Label("Gli eventi vengono assegnati automaticamente dal sistema");
        info.setStyle("-fx-text-fill: #666; -fx-font-size: 12px; -fx-font-style: italic;");
        
        pannello.getChildren().addAll(bottoneCaricaEventi, spazio, info);
        return pannello;
    }
    
    /**
     * Crea il contenuto principale con lista eventi e dettagli
     */
    private HBox creaContenutoPrincipale() {
        HBox contenuto = new HBox();
        contenuto.setSpacing(25);
        contenuto.setMinHeight(500);
        contenuto.setPrefHeight(600);
        
        // Pannello sinistro - Lista eventi
        VBox pannelloLista = creaPannelloListaEventi();
        
        // Pannello destro - Dettagli evento
        VBox pannelloDettagli = creaPannelloDettagli();
        
        contenuto.getChildren().addAll(pannelloLista, pannelloDettagli);
        return contenuto;
    }
    
    /**
     * Crea il pannello con la lista degli eventi
     */
    private VBox creaPannelloListaEventi() {
        VBox pannello = new VBox();
        pannello.setSpacing(15);
        pannello.setPrefWidth(450);
        pannello.setMinWidth(400);
        
        Label etichetta = new Label("Lista Eventi Assegnati");
        etichetta.setStyle("-fx-font-weight: bold; -fx-text-fill: #34495e; -fx-font-size: 16px;");
        
        listaEventi = new ListView<>();
        listaEventi.setPrefHeight(400);
        listaEventi.setMinHeight(350);
        listaEventi.setStyle("-fx-border-color: #bdc3c7; -fx-border-radius: 8px; -fx-border-width: 2px;");
        
        // Personalizza la visualizzazione degli eventi con più dettagli
        listaEventi.setCellFactory(listView -> new ListCell<Evento>() {
            @Override
            protected void updateItem(Evento evento, boolean empty) {
                super.updateItem(evento, empty);
                if (empty || evento == null) {
                    setText(null);
                    setStyle("");
                } else {
                    // Testo su più righe per maggiore leggibilità
                    setText(evento.getLuogo() + "\n" + 
                           evento.getDataInizio() + " → " + evento.getDataFine() + "\n" +
                           "Tipo: " + evento.getTipo() + " | Persone: " + evento.getNumeroPersone());
                    
                    // Stile per celle più grandi
                    setStyle("-fx-padding: 10px; -fx-font-size: 12px;");
                    setPrefHeight(80);
                }
            }
        });
        
        Label infoEventi = new Label("Clicca su un evento per vedere i dettagli completi");
        infoEventi.setStyle("-fx-font-size: 12px; -fx-text-fill: #666; -fx-font-style: italic;");
        
        // Statistiche rapide
        Label labelStats = new Label("Statistiche:");
        labelStats.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50; -fx-font-size: 14px;");
        
        Label statsInfo = new Label("Eventi caricati: 0 | In corso: 0 | Completati: 0");
        statsInfo.setId("statsLabel"); // Per aggiornamenti dinamici
        statsInfo.setStyle("-fx-font-size: 12px; -fx-text-fill: #27ae60;");
        
        pannello.getChildren().addAll(etichetta, listaEventi, infoEventi, labelStats, statsInfo);
        return pannello;
    }
    
    /**
     * Crea il pannello con i dettagli dell'evento selezionato
     */
    private VBox creaPannelloDettagli() {
        VBox pannello = new VBox();
        pannello.setSpacing(15);
        HBox.setHgrow(pannello, Priority.ALWAYS);
        pannello.setMinWidth(500);
        
        Label etichetta = new Label("Dettagli Evento Selezionato");
        etichetta.setStyle("-fx-font-weight: bold; -fx-text-fill: #34495e; -fx-font-size: 16px;");
        
        areaDettagli = new TextArea();
        areaDettagli.setPrefHeight(400);
        areaDettagli.setMinHeight(350);
        areaDettagli.setEditable(false);
        areaDettagli.setStyle("-fx-border-color: #bdc3c7; -fx-border-radius: 8px; -fx-border-width: 2px; -fx-font-size: 13px; -fx-font-family: 'Courier New', monospace;");
        areaDettagli.setWrapText(true);
        areaDettagli.setText("Seleziona un evento dalla lista per vedere i dettagli completi\n\nI dettagli includeranno:\n• Informazioni sul cliente\n• Servizi previsti\n• Note speciali\n• Numero di ospiti\n• Requisiti particolari");
        
        // Pannello con informazioni aggiuntive
        VBox pannelloInfo = new VBox();
        pannelloInfo.setSpacing(10);
        pannelloInfo.setStyle("-fx-background-color: #f8f9fa; -fx-padding: 15px; -fx-border-radius: 8px;");
        
        Label labelInfo = new Label("Informazioni Utili:");
        labelInfo.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50; -fx-font-size: 14px;");
        
        Label infoDettagli1 = new Label("• I dettagli includono informazioni complete su cliente e servizi");
        infoDettagli1.setStyle("-fx-font-size: 12px; -fx-text-fill: #666;");
        
        Label infoDettagli2 = new Label("• Ogni evento mostra il numero di ospiti previsti");
        infoDettagli2.setStyle("-fx-font-size: 12px; -fx-text-fill: #666;");
        
        Label infoDettagli3 = new Label("• Le note speciali contengono requisiti particolari");
        infoDettagli3.setStyle("-fx-font-size: 12px; -fx-text-fill: #666;");
        
        pannelloInfo.getChildren().addAll(labelInfo, infoDettagli1, infoDettagli2, infoDettagli3);
        
        pannello.getChildren().addAll(etichetta, areaDettagli, pannelloInfo);
        return pannello;
    }
    
    /**
     * Collega i componenti dell'interfaccia al controller
     */
    private void collegaController() {
        // Imposta la lista eventi nel controller
        controller.setListaEventi(listaEventi);
        controller.setAreaDettagli(areaDettagli);
        controller.setLabelStato(labelStato);
        
        // Collega il pulsante al controller
        bottoneCaricaEventi.setOnAction(e -> {
            controller.handleAggiornaEventi();
            aggiornaStatistiche();
        });
        
        // Listener per la selezione degli eventi
        listaEventi.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                controller.handleSelezionaEvento(newSelection);
                evidenziaEventoSelezionato();
            } else {
                // Gestione quando nessun evento è selezionato
                if (areaDettagli != null) {
                    areaDettagli.setText("Nessun evento selezionato\n\nSeleziona un evento dalla lista per vedere i dettagli");
                }
            }
        });
    }
    
    /**
     * Evidenzia visivamente l'evento selezionato
     */
    private void evidenziaEventoSelezionato() {
        Evento eventoSelezionato = listaEventi.getSelectionModel().getSelectedItem();
        if (eventoSelezionato != null) {
            // Aggiorna il titolo dell'area dettagli
            String titoloDettagli = "Dettagli: " + eventoSelezionato.getLuogo() + " (" + eventoSelezionato.getTipo() + ")";
            // Trova il label dei dettagli e aggiornalo se necessario
            aggiornaStato("Evento selezionato: " + eventoSelezionato.getLuogo());
        }
    }
    
    /**
     * Aggiorna le statistiche degli eventi
     */
    private void aggiornaStatistiche() {
        if (controller.getEventiList() != null) {
            int totaleEventi = controller.getEventiList().size();
            
            // Calcola eventi in corso (per semplicità, eventi futuri)
            long eventiInCorso = controller.getEventiList().stream()
                    .filter(e -> e.getDataInizio().isAfter(java.time.LocalDate.now()) || 
                               e.getDataInizio().isEqual(java.time.LocalDate.now()))
                    .count();
            
            long eventiCompletati = totaleEventi - eventiInCorso;
            
            // Trova e aggiorna il label delle statistiche
            String statsText = String.format("Eventi caricati: %d | In corso: %d | Completati: %d", 
                                            totaleEventi, eventiInCorso, eventiCompletati);
            
            // Aggiorna il label se esiste (questo richiederebbe un riferimento al label)
            // Per ora aggiorniamo lo stato principale
            aggiornaStato("Statistiche aggiornate - " + statsText);
        }
    }
    
    /**
     * Restituisce il nodo principale della vista
     */
    public Node getView() {
        return layoutPrincipale;
    }
    
    /**
     * Restituisce la lista degli eventi (per il controller)
     */
    public ListView<Evento> getListaEventi() {
        return listaEventi;
    }
    
    /**
     * Restituisce l'area dei dettagli (per il controller)
     */
    public TextArea getAreaDettagli() {
        return areaDettagli;
    }
    
    /**
     * Aggiorna il messaggio di stato
     */
    public void aggiornaStato(String messaggio) {
        if (labelStato != null) {
            labelStato.setText(messaggio);
        }
    }
    
    /**
     * Metodo per ricaricare i dati quando la vista diventa visibile
     */
    public void attivaVista() {
        // Carica automaticamente i dati quando la vista diventa attiva
        if (controller != null) {
            controller.caricaDatiIniziali();
            aggiornaStatistiche();
        }
    }
    
    /**
     * Pulisce la selezione e resetta la vista
     */
    public void pulisciSelezione() {
        if (listaEventi != null) {
            listaEventi.getSelectionModel().clearSelection();
        }
        if (areaDettagli != null) {
            areaDettagli.setText("Seleziona un evento dalla lista per vedere i dettagli completi");
        }
    }
}