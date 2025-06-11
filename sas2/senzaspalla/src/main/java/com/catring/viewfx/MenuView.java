package com.catring.viewfx;

import com.catring.controller.MenuController;
import com.catring.model.*;
import com.catring.model.Menu;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

/**
 * VISTA UNIFICATA PER LA GESTIONE COMPLETA DEI MENU
 * VERSIONE CORRETTA con spaziature e dimensioni migliorate
 */
public class MenuView {
    
    private MenuController controller;
    private VBox layoutPrincipale;
    private Menu menuSelezionato;
    
    // Componenti per la creazione menu
    private TextField campoNomeMenu;
    private TextField campoDescrizioneMenu;
    private TextArea areaNoteMenu;
    private Button bottoneCreaMenu;
    private Button bottoneDettagliMenu;
    private Button bottoneDuplicaMenu;
    
    // Componenti per le sezioni
    private TextField campoTitoloSezione;
    private Button bottoneAggiungiSezione;
    private Button bottoneRimuoviSezione;
    
    // Componenti per le ricette - TUTTE E 3 LE COMBO BOX
    private ComboBox<String> comboSezioni; // Per aggiungere ricette
    private ComboBox<Ricetta> comboRicette; // Ricette disponibili da aggiungere
    
    // Per spostare ricette - COMPLETE CON 3 COMBO BOX
    private ComboBox<String> comboSezioniOrigine; // Sezione di origine
    private ComboBox<VoceMenu> comboRicetteDaSpostare; // Ricette nella sezione origine
    private ComboBox<String> comboSezioniDestinazione; // Sezione di destinazione
    
    // Per rimuovere ricette
    private ComboBox<VoceMenu> comboRicetteDaRimuovere; // Ricette nel menu da rimuovere
    
    // Pulsanti
    private Button bottoneAggiungiRicetta;
    private Button bottoneSpostaRicetta;
    private Button bottoneEliminaRicetta;
    
    // Tabella menu
    private TableView<Menu> tabellaMenu;
    
    // Liste per visualizzare contenuto menu
    private ListView<SezioniMenu> listaSezioni;
    private ListView<VoceMenu> listaVoci;
    
    // Componenti per finalizzazione
    private TextField campoNuovoTitolo;
    private TextArea areaNuoveNote;
    private Button bottoneAggiornaTitolo;
    private Button bottoneAggiungiAnnotazione;
    private Button bottoneGeneraTXT;
    private Button bottonePubblicaBacheca;
    private Button bottoneEliminaMenu;
    
    // Area di stato
    private Label labelStato;
    
    public MenuView(MenuController controller) {
        this.controller = controller;
        creaInterfaccia();
        collegaController();
    }
    
    /**
     * Crea l'interfaccia unificata per la gestione menu
     */
    private void creaInterfaccia() {
        layoutPrincipale = new VBox();
        layoutPrincipale.setSpacing(20);
        layoutPrincipale.setStyle("-fx-padding: 20px;");
        // Imposta dimensioni minime
        layoutPrincipale.setMinWidth(1300);
        layoutPrincipale.setPrefWidth(1500);
        
        // Intestazione
        Label titolo = new Label("Gestione Menu Completa");
        titolo.setStyle("-fx-font-weight: bold; -fx-font-size: 18px; -fx-text-fill: #2c3e50;");
        
        // ScrollPane per contenere tutto il contenuto
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");
        scrollPane.setPrefHeight(700);
        
        VBox contenutoCompleto = creaContenutoCompleto();
        scrollPane.setContent(contenutoCompleto);
        
        // Area di stato
        labelStato = new Label("Sistema pronto per la gestione menu");
        labelStato.setStyle("-fx-text-fill: #27ae60; -fx-padding: 15px; -fx-background-color: #f8f9fa; -fx-font-size: 14px;");
        labelStato.setMinHeight(40);
        
        layoutPrincipale.getChildren().addAll(titolo, scrollPane, labelStato);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
    }
    
    /**
     * Crea tutto il contenuto della vista unificata
     */
    private VBox creaContenutoCompleto() {
        VBox contenuto = new VBox();
        contenuto.setSpacing(25);
        contenuto.setMinWidth(1250);
        
        // Sezione 1: Creazione e gestione menu
        TitledPane sezioneCreazione = new TitledPane("1. Creazione e Gestione Menu", creaSezioneCreazione());
        sezioneCreazione.setExpanded(true);
        sezioneCreazione.setStyle("-fx-font-size: 14px;");
        
        // Sezione 2: Gestione contenuto menu
        TitledPane sezioneContenuto = new TitledPane("2. Gestione Contenuto Menu", creaSezioneContenuto());
        sezioneContenuto.setExpanded(true);
        sezioneContenuto.setStyle("-fx-font-size: 14px;");
        
        // Sezione 3: Finalizzazione e condivisione
        TitledPane sezioneFinalizzazione = new TitledPane("3. Finalizzazione e Condivisione", creaSezioneFinale());
        sezioneFinalizzazione.setExpanded(true);
        sezioneFinalizzazione.setStyle("-fx-font-size: 14px;");
        
        contenuto.getChildren().addAll(sezioneCreazione, sezioneContenuto, sezioneFinalizzazione);
        return contenuto;
    }
    
    /**
     * Crea la sezione per creazione e gestione menu
     */
    private HBox creaSezioneCreazione() {
        HBox sezione = new HBox();
        sezione.setSpacing(20);
        sezione.setMinHeight(250);
        
        // Pannello creazione menu
        VBox pannelloCreazione = creaPannelloCreazione();
        
        // Pannello lista menu
        VBox pannelloLista = creaPannelloListaMenu();
        
        sezione.getChildren().addAll(pannelloCreazione, pannelloLista);
        return sezione;
    }
    
    /**
     * Crea il pannello per la creazione di menu
     */
    private VBox creaPannelloCreazione() {
        VBox pannello = new VBox();
        pannello.setSpacing(15);
        pannello.setPrefWidth(450);
        pannello.setMinWidth(400);
        pannello.setStyle("-fx-background-color: #ecf0f1; -fx-padding: 20px; -fx-border-radius: 8px;");
        
        Label etichetta = new Label("Crea Nuovo Menu");
        etichetta.setStyle("-fx-font-weight: bold; -fx-text-fill: #34495e; -fx-font-size: 14px;");
        
        GridPane griglia = new GridPane();
        griglia.setHgap(15);
        griglia.setVgap(15);
        
        Label labelNome = new Label("Nome:");
        labelNome.setStyle("-fx-font-size: 12px;");
        campoNomeMenu = new TextField();
        campoNomeMenu.setPromptText("Es: Menu Matrimonio");
        campoNomeMenu.setPrefWidth(250);
        
        Label labelDescrizione = new Label("Descrizione:");
        labelDescrizione.setStyle("-fx-font-size: 12px;");
        campoDescrizioneMenu = new TextField();
        campoDescrizioneMenu.setPromptText("Breve descrizione del menu");
        campoDescrizioneMenu.setPrefWidth(250);
        
        Label labelNote = new Label("Note:");
        labelNote.setStyle("-fx-font-size: 12px;");
        areaNoteMenu = new TextArea();
        areaNoteMenu.setPrefRowCount(3);
        areaNoteMenu.setPrefWidth(250);
        areaNoteMenu.setPromptText("Note aggiuntive (opzionale)");
        
        griglia.add(labelNome, 0, 0);
        griglia.add(campoNomeMenu, 1, 0);
        griglia.add(labelDescrizione, 0, 1);
        griglia.add(campoDescrizioneMenu, 1, 1);
        griglia.add(labelNote, 0, 2);
        griglia.add(areaNoteMenu, 1, 2);
        
        HBox pannelloPulsanti = new HBox();
        pannelloPulsanti.setSpacing(10);
        
        bottoneCreaMenu = new Button("Crea Menu");
        bottoneCreaMenu.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-padding: 10px 20px; -fx-font-size: 12px;");
        bottoneCreaMenu.setPrefWidth(100);
        
        bottoneDettagliMenu = new Button("Dettagli");
        bottoneDettagliMenu.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-padding: 10px 20px; -fx-font-size: 12px;");
        bottoneDettagliMenu.setPrefWidth(80);
        
        bottoneDuplicaMenu = new Button("Duplica");
        bottoneDuplicaMenu.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white; -fx-padding: 10px 20px; -fx-font-size: 12px;");
        bottoneDuplicaMenu.setPrefWidth(80);
        
        pannelloPulsanti.getChildren().addAll(bottoneCreaMenu, bottoneDettagliMenu, bottoneDuplicaMenu);
        
        pannello.getChildren().addAll(etichetta, griglia, pannelloPulsanti);
        return pannello;
    }
    
    /**
     * Crea il pannello con la lista dei menu
     */
    private VBox creaPannelloListaMenu() {
        VBox pannello = new VBox();
        pannello.setSpacing(10);
        pannello.setPrefWidth(400);
        pannello.setMinWidth(350);
        
        Label etichetta = new Label("Menu Esistenti");
        etichetta.setStyle("-fx-font-weight: bold; -fx-text-fill: #34495e; -fx-font-size: 14px;");
        
        tabellaMenu = new TableView<>();
        tabellaMenu.setPrefHeight(200);
        tabellaMenu.setMinHeight(180);
        tabellaMenu.setStyle("-fx-border-color: #bdc3c7; -fx-border-radius: 5px;");
        
        TableColumn<Menu, String> colonnaNome = new TableColumn<>("Nome");
        colonnaNome.setPrefWidth(150);
        colonnaNome.setMinWidth(120);
        colonnaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        
        TableColumn<Menu, String> colonnaDescrizione = new TableColumn<>("Descrizione");
        colonnaDescrizione.setPrefWidth(200);
        colonnaDescrizione.setMinWidth(150);
        colonnaDescrizione.setCellValueFactory(new PropertyValueFactory<>("descrizione"));
        
        tabellaMenu.getColumns().add(colonnaNome);
        tabellaMenu.getColumns().add(colonnaDescrizione);
        
        pannello.getChildren().addAll(etichetta, tabellaMenu);
        return pannello;
    }
    
    /**
     * Crea la sezione per gestione contenuto menu
     */
    private HBox creaSezioneContenuto() {
        HBox sezione = new HBox();
        sezione.setSpacing(20);
        sezione.setMinHeight(400);
        
        // Pannello gestione sezioni e ricette
        VBox pannelloGestione = creaPannelloGestioneContenuto();
        
        // Pannello visualizzazione contenuto
        VBox pannelloVisualizzazione = creaPannelloVisualizzazioneContenuto();
        
        sezione.getChildren().addAll(pannelloGestione, pannelloVisualizzazione);
        return sezione;
    }
    
    /**
     * Crea il pannello per gestire sezioni e ricette
     */
    private VBox creaPannelloGestioneContenuto() {
        VBox pannello = new VBox();
        pannello.setSpacing(20);
        pannello.setPrefWidth(600);
        pannello.setMinWidth(550);
        pannello.setStyle("-fx-background-color: #ecf0f1; -fx-padding: 20px; -fx-border-radius: 8px;");
        
        Label etichetta = new Label("Gestione Sezioni e Ricette");
        etichetta.setStyle("-fx-font-weight: bold; -fx-text-fill: #34495e; -fx-font-size: 14px;");
        
        // Gestione sezioni
        VBox sezioneSezioni = creaSezioneGestioneSezioni();
        
        // Gestione ricette
        VBox sezioneRicette = creaSezioneGestioneRicette();
        
        pannello.getChildren().addAll(etichetta, sezioneSezioni, sezioneRicette);
        return pannello;
    }
    
    /**
     * Crea la sezione per gestire le sezioni del menu
     */
    private VBox creaSezioneGestioneSezioni() {
        VBox sezione = new VBox();
        sezione.setSpacing(12);
        
        Label label = new Label("Gestione Sezioni:");
        label.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50; -fx-font-size: 13px;");
        
        HBox pannelloSezione = new HBox();
        pannelloSezione.setSpacing(10);
        
        campoTitoloSezione = new TextField();
        campoTitoloSezione.setPromptText("Es: Antipasti, Primi, Dolci...");
        campoTitoloSezione.setPrefWidth(200);
        HBox.setHgrow(campoTitoloSezione, Priority.ALWAYS);
        
        bottoneAggiungiSezione = new Button("Aggiungi");
        bottoneAggiungiSezione.setStyle("-fx-background-color: #e67e22; -fx-text-fill: white; -fx-padding: 8px 15px;");
        bottoneAggiungiSezione.setPrefWidth(80);
        
        bottoneRimuoviSezione = new Button("Rimuovi");
        bottoneRimuoviSezione.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-padding: 8px 15px;");
        bottoneRimuoviSezione.setPrefWidth(80);
        
        pannelloSezione.getChildren().addAll(campoTitoloSezione, bottoneAggiungiSezione, bottoneRimuoviSezione);
        
        sezione.getChildren().addAll(label, pannelloSezione);
        return sezione;
    }
    
    /**
     * Crea la sezione per gestire le ricette - VERSIONE COMPLETA CON 3 COMBO BOX
     */
    private VBox creaSezioneGestioneRicette() {
        VBox sezione = new VBox();
        sezione.setSpacing(15);
        
        Label label = new Label("Gestione Ricette:");
        label.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50; -fx-font-size: 13px;");
        
        // SEZIONE 1: Aggiungere ricette
        Label labelAggiungi = new Label("Aggiungi ricetta:");
        labelAggiungi.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50; -fx-font-size: 12px;");
        
        HBox pannelloAggiungi = new HBox();
        pannelloAggiungi.setSpacing(10);
        
        comboSezioni = new ComboBox<>();
        comboSezioni.setPromptText("Sezione");
        comboSezioni.setPrefWidth(130);
        
        comboRicette = new ComboBox<>();
        comboRicette.setPromptText("Ricetta");
        comboRicette.setPrefWidth(180);
        
        bottoneAggiungiRicetta = new Button("Aggiungi");
        bottoneAggiungiRicetta.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-padding: 8px 15px;");
        bottoneAggiungiRicetta.setPrefWidth(80);
        
        pannelloAggiungi.getChildren().addAll(comboSezioni, comboRicette, bottoneAggiungiRicetta);
        
        // SEZIONE 2: Spostare ricette - CON 3 COMBO BOX
        Label labelSposta = new Label("Sposta ricetta:");
        labelSposta.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50; -fx-font-size: 12px;");
        
        // Prima riga: sezione origine
        HBox pannelloSposta1 = new HBox();
        pannelloSposta1.setSpacing(10);
        
        Label labelDa = new Label("Da sezione:");
        labelDa.setPrefWidth(80);
        comboSezioniOrigine = new ComboBox<>();
        comboSezioniOrigine.setPromptText("Sezione origine");
        comboSezioniOrigine.setPrefWidth(160);
        
        pannelloSposta1.getChildren().addAll(labelDa, comboSezioniOrigine);
        
        // Seconda riga: ricetta da spostare
        HBox pannelloSposta2 = new HBox();
        pannelloSposta2.setSpacing(10);
        
        Label labelRicetta = new Label("Ricetta:");
        labelRicetta.setPrefWidth(80);
        comboRicetteDaSpostare = new ComboBox<>();
        comboRicetteDaSpostare.setPromptText("Scegli ricetta");
        comboRicetteDaSpostare.setPrefWidth(220);
        
        pannelloSposta2.getChildren().addAll(labelRicetta, comboRicetteDaSpostare);
        
        // Terza riga: sezione destinazione e pulsante
        HBox pannelloSposta3 = new HBox();
        pannelloSposta3.setSpacing(10);
        
        Label labelA = new Label("Verso sezione:");
        labelA.setPrefWidth(80);
        comboSezioniDestinazione = new ComboBox<>();
        comboSezioniDestinazione.setPromptText("Sezione destinazione");
        comboSezioniDestinazione.setPrefWidth(140);
        
        bottoneSpostaRicetta = new Button("Sposta");
        bottoneSpostaRicetta.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white; -fx-padding: 8px 15px;");
        bottoneSpostaRicetta.setPrefWidth(80);
        
        pannelloSposta3.getChildren().addAll(labelA, comboSezioniDestinazione, bottoneSpostaRicetta);
        
        // SEZIONE 3: Rimuovere ricette
        Label labelRimuovi = new Label("Rimuovi ricetta:");
        labelRimuovi.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50; -fx-font-size: 12px;");
        
        HBox pannelloRimuovi = new HBox();
        pannelloRimuovi.setSpacing(10);
        
        comboRicetteDaRimuovere = new ComboBox<>();
        comboRicetteDaRimuovere.setPromptText("Scegli ricetta da rimuovere");
        comboRicetteDaRimuovere.setPrefWidth(220);
        
        bottoneEliminaRicetta = new Button("Rimuovi");
        bottoneEliminaRicetta.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-padding: 8px 15px;");
        bottoneEliminaRicetta.setPrefWidth(80);
        
        pannelloRimuovi.getChildren().addAll(comboRicetteDaRimuovere, bottoneEliminaRicetta);
        
        // Configura le combo box delle ricette
        configuraComboPRicette();
        
        sezione.getChildren().addAll(
            label,
            labelAggiungi, pannelloAggiungi,
            labelSposta, pannelloSposta1, pannelloSposta2, pannelloSposta3,
            labelRimuovi, pannelloRimuovi
        );
        return sezione;
    }
    
    /**
     * Configura la visualizzazione delle combo box delle ricette
     */
    private void configuraComboPRicette() {
        // Combo ricette per aggiungere
        comboRicette.setCellFactory(listView -> new ListCell<Ricetta>() {
            @Override
            protected void updateItem(Ricetta ricetta, boolean empty) {
                super.updateItem(ricetta, empty);
                if (empty || ricetta == null) {
                    setText(null);
                } else {
                    setText(ricetta.getNome() + " (" + ricetta.getTempoPreparazione() + " min)");
                }
            }
        });
        
        comboRicette.setButtonCell(new ListCell<Ricetta>() {
            @Override
            protected void updateItem(Ricetta ricetta, boolean empty) {
                super.updateItem(ricetta, empty);
                if (empty || ricetta == null) {
                    setText(null);
                } else {
                    setText(ricetta.getNome() + " (" + ricetta.getTempoPreparazione() + " min)");
                }
            }
        });
        
        // Combo ricette per spostare
        comboRicetteDaSpostare.setCellFactory(listView -> new ListCell<VoceMenu>() {
            @Override
            protected void updateItem(VoceMenu voce, boolean empty) {
                super.updateItem(voce, empty);
                if (empty || voce == null) {
                    setText(null);
                } else {
                    setText(voce.getNomeVisuale());
                }
            }
        });
        
        comboRicetteDaSpostare.setButtonCell(new ListCell<VoceMenu>() {
            @Override
            protected void updateItem(VoceMenu voce, boolean empty) {
                super.updateItem(voce, empty);
                if (empty || voce == null) {
                    setText(null);
                } else {
                    setText(voce.getNomeVisuale());
                }
            }
        });
        
        // Combo ricette per rimuovere (uguale a quella per spostare)
        comboRicetteDaRimuovere.setCellFactory(listView -> new ListCell<VoceMenu>() {
            @Override
            protected void updateItem(VoceMenu voce, boolean empty) {
                super.updateItem(voce, empty);
                if (empty || voce == null) {
                    setText(null);
                } else {
                    setText(voce.getNomeVisuale());
                }
            }
        });
        
        comboRicetteDaRimuovere.setButtonCell(new ListCell<VoceMenu>() {
            @Override
            protected void updateItem(VoceMenu voce, boolean empty) {
                super.updateItem(voce, empty);
                if (empty || voce == null) {
                    setText(null);
                } else {
                    setText(voce.getNomeVisuale());
                }
            }
        });
    }
    
    /**
     * Crea il pannello per visualizzare il contenuto del menu
     */
    private VBox creaPannelloVisualizzazioneContenuto() {
        VBox pannello = new VBox();
        pannello.setSpacing(15);
        pannello.setPrefWidth(400);
        pannello.setMinWidth(350);
        
        Label etichetta = new Label("Contenuto Menu Selezionato");
        etichetta.setStyle("-fx-font-weight: bold; -fx-text-fill: #34495e; -fx-font-size: 14px;");
        
        Label labelSezioni = new Label("Sezioni:");
        labelSezioni.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50; -fx-font-size: 12px;");
        
        listaSezioni = new ListView<>();
        listaSezioni.setPrefHeight(150);
        listaSezioni.setMinHeight(120);
        listaSezioni.setStyle("-fx-border-color: #bdc3c7; -fx-border-radius: 5px;");
        
        listaSezioni.setCellFactory(listView -> new ListCell<SezioniMenu>() {
            @Override
            protected void updateItem(SezioniMenu sezione, boolean empty) {
                super.updateItem(sezione, empty);
                if (empty || sezione == null) {
                    setText(null);
                } else {
                    setText(sezione.getTitolo() + " (" + sezione.getVoci().size() + " ricette)");
                }
            }
        });
        
        Label labelRicette = new Label("Ricette nella sezione:");
        labelRicette.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50; -fx-font-size: 12px;");
        
        listaVoci = new ListView<>();
        listaVoci.setPrefHeight(150);
        listaVoci.setMinHeight(120);
        listaVoci.setStyle("-fx-border-color: #bdc3c7; -fx-border-radius: 5px;");
        
        listaVoci.setCellFactory(listView -> new ListCell<VoceMenu>() {
            @Override
            protected void updateItem(VoceMenu voce, boolean empty) {
                super.updateItem(voce, empty);
                if (empty || voce == null) {
                    setText(null);
                } else {
                    setText(voce.getNomeVisuale());
                }
            }
        });
        
        pannello.getChildren().addAll(etichetta, labelSezioni, listaSezioni, labelRicette, listaVoci);
        return pannello;
    }
    
    /**
     * Crea la sezione per finalizzazione e condivisione
     */
    private VBox creaSezioneFinale() {
        VBox sezione = new VBox();
        sezione.setSpacing(20);
        
        HBox contenutoFinale = new HBox();
        contenutoFinale.setSpacing(20);
        
        // Pannello personalizzazione
        VBox pannelloPersonalizzazione = creaPannelloPersonalizzazione();
        
        // Pannello azioni finali
        VBox pannelloAzioni = creaPannelloAzioniFinali();
        
        contenutoFinale.getChildren().addAll(pannelloPersonalizzazione, pannelloAzioni);
        sezione.getChildren().add(contenutoFinale);
        
        return sezione;
    }
    
    /**
     * Crea il pannello per personalizzare il menu
     */
    private VBox creaPannelloPersonalizzazione() {
        VBox pannello = new VBox();
        pannello.setSpacing(15);
        pannello.setPrefWidth(450);
        pannello.setMinWidth(400);
        pannello.setStyle("-fx-background-color: #ecf0f1; -fx-padding: 20px; -fx-border-radius: 8px;");
        
        Label etichetta = new Label("Personalizza Menu");
        etichetta.setStyle("-fx-font-weight: bold; -fx-text-fill: #34495e; -fx-font-size: 14px;");
        
        // Modifica titolo
        Label labelTitolo = new Label("Nuovo titolo:");
        labelTitolo.setStyle("-fx-font-size: 12px;");
        HBox pannelloTitolo = new HBox();
        pannelloTitolo.setSpacing(10);
        
        campoNuovoTitolo = new TextField();
        campoNuovoTitolo.setPromptText("Nuovo titolo del menu");
        campoNuovoTitolo.setPrefWidth(200);
        HBox.setHgrow(campoNuovoTitolo, Priority.ALWAYS);
        
        bottoneAggiornaTitolo = new Button("Aggiorna");
        bottoneAggiornaTitolo.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-padding: 8px 15px;");
        bottoneAggiornaTitolo.setPrefWidth(80);
        
        pannelloTitolo.getChildren().addAll(campoNuovoTitolo, bottoneAggiornaTitolo);
        
        // Aggiungi note
        Label labelNote = new Label("Note aggiuntive:");
        labelNote.setStyle("-fx-font-size: 12px;");
        areaNuoveNote = new TextArea();
        areaNuoveNote.setPromptText("Inserisci note speciali...");
        areaNuoveNote.setPrefRowCount(4);
        areaNuoveNote.setPrefWidth(350);
        
        bottoneAggiungiAnnotazione = new Button("Salva Note");
        bottoneAggiungiAnnotazione.setStyle("-fx-background-color: #16a085; -fx-text-fill: white; -fx-padding: 8px 15px;");
        bottoneAggiungiAnnotazione.setPrefWidth(100);
        
        pannello.getChildren().addAll(etichetta, labelTitolo, pannelloTitolo, labelNote, areaNuoveNote, bottoneAggiungiAnnotazione);
        return pannello;
    }
    
    /**
     * Crea il pannello con le azioni finali
     */
    private VBox creaPannelloAzioniFinali() {
        VBox pannello = new VBox();
        pannello.setSpacing(15);
        pannello.setPrefWidth(350);
        pannello.setMinWidth(300);
        pannello.setStyle("-fx-background-color: #ecf0f1; -fx-padding: 20px; -fx-border-radius: 8px;");
        
        Label etichetta = new Label("Azioni Finali");
        etichetta.setStyle("-fx-font-weight: bold; -fx-text-fill: #34495e; -fx-font-size: 14px;");
        
        Label labelCondivisione = new Label("Condividi menu:");
        labelCondivisione.setStyle("-fx-text-fill: #2c3e50; -fx-font-weight: bold; -fx-font-size: 12px;");
        
        bottoneGeneraTXT = new Button("Genera TXT");
        bottoneGeneraTXT.setPrefWidth(220);
        bottoneGeneraTXT.setMinWidth(200);
        bottoneGeneraTXT.setStyle("-fx-background-color: #34495e; -fx-text-fill: white; -fx-padding: 12px;");
        
        bottonePubblicaBacheca = new Button("Pubblica su Bacheca");
        bottonePubblicaBacheca.setPrefWidth(220);
        bottonePubblicaBacheca.setMinWidth(200);
        bottonePubblicaBacheca.setStyle("-fx-background-color: #8e44ad; -fx-text-fill: white; -fx-padding: 12px;");
        
        Separator separatore = new Separator();
        
        Label labelGestione = new Label("Gestione:");
        labelGestione.setStyle("-fx-text-fill: #2c3e50; -fx-font-weight: bold; -fx-font-size: 12px;");
        
        bottoneEliminaMenu = new Button("Elimina Menu");
        bottoneEliminaMenu.setPrefWidth(220);
        bottoneEliminaMenu.setMinWidth(200);
        bottoneEliminaMenu.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-padding: 12px;");
        
        pannello.getChildren().addAll(etichetta, labelCondivisione, bottoneGeneraTXT, bottonePubblicaBacheca, separatore, labelGestione, bottoneEliminaMenu);
        return pannello;
    }
    
    /**
     * Collega i componenti dell'interfaccia al controller - VERSIONE COMPLETA CORRETTA
     */
    private void collegaController() {
        // Imposta i componenti nel controller
        controller.setComponentiMenu(
            campoNomeMenu, campoDescrizioneMenu, areaNoteMenu,
            campoTitoloSezione, comboSezioni, comboRicette,
            tabellaMenu, listaSezioni, listaVoci
        );
        
        controller.setComponentiFinale(
            campoNuovoTitolo, areaNuoveNote, labelStato
        );
        
        // Collega eventi creazione menu
        bottoneCreaMenu.setOnAction(e -> controller.handleCreaMenu());
        bottoneDettagliMenu.setOnAction(e -> controller.handleSelezionaMenu());
        bottoneDuplicaMenu.setOnAction(e -> controller.handleDuplicaMenu());
        
        // Collega eventi gestione contenuto
        bottoneAggiungiSezione.setOnAction(e -> {
            controller.handleAggiungiSezione();
            // AGGIORNA TUTTE LE COMBO BOX quando si aggiunge una sezione
            aggiornaComboBoxSezioni(menuSelezionato);
        });
        
        bottoneRimuoviSezione.setOnAction(e -> {
            controller.handleRimuoviSezione();
            // AGGIORNA TUTTE LE COMBO BOX quando si rimuove una sezione
            aggiornaComboBoxSezioni(menuSelezionato);
            aggiornaComboBoxRicette(menuSelezionato);
        });
        
        bottoneAggiungiRicetta.setOnAction(e -> {
            controller.handleAggiungiRicetta();
            // AGGIORNA TUTTE LE COMBO BOX quando si aggiunge una ricetta
            aggiornaComboBoxRicette(menuSelezionato);
        });
        
        // SPOSTA RICETTA - CON 3 COMBO BOX
        bottoneSpostaRicetta.setOnAction(e -> handleSpostaRicettaCompleta());
        
        // ELIMINA RICETTA - dalla combo box
        bottoneEliminaRicetta.setOnAction(e -> {
            handleEliminaRicettaDaCombo();
            // AGGIORNA TUTTE LE COMBO BOX quando si elimina una ricetta
            aggiornaComboBoxRicette(menuSelezionato);
        });
        
        // Collega eventi finalizzazione
        bottoneAggiornaTitolo.setOnAction(e -> controller.handleAggiornaTitolo());
        bottoneAggiungiAnnotazione.setOnAction(e -> controller.handleAggiungiAnnotazione());
        bottoneGeneraTXT.setOnAction(e -> controller.handleGeneraTXT());
        bottonePubblicaBacheca.setOnAction(e -> controller.handlePubblicaBacheca());
        bottoneEliminaMenu.setOnAction(e -> controller.handleEliminaMenu());
        
        // Listener per selezioni - VERSIONE CORRETTA
        tabellaMenu.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                menuSelezionato = newSelection;
                controller.handleSelezionaMenuDaTabella(newSelection);
                
                // AGGIORNA TUTTE LE COMBO BOX quando si seleziona un menu
                aggiornaComboBoxSezioni(newSelection);
                aggiornaComboBoxRicette(newSelection);
                
                System.out.println("DEBUG: Menu selezionato: " + newSelection.getNome());
                System.out.println("DEBUG: Sezioni trovate: " + newSelection.getSezioni().size());
            }
        });
        
        listaSezioni.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                controller.handleSelezionaSezione(newSelection);
            }
        });
        
        // LISTENER IMPORTANTE: Aggiorna combo ricette quando cambia sezione origine
        comboSezioniOrigine.setOnAction(e -> {
            String sezioneSelezionata = comboSezioniOrigine.getValue();
            if (sezioneSelezionata != null && menuSelezionato != null) {
                aggiornaComboRicettePerSezione(sezioneSelezionata);
                System.out.println("DEBUG: Sezione origine selezionata: " + sezioneSelezionata);
            }
        });
    }
    
    /**
     * Gestisce lo spostamento della ricetta - VERSIONE COMPLETA CON 3 COMBO BOX
     */
    private void handleSpostaRicettaCompleta() {
        String sezioneOrigine = comboSezioniOrigine.getValue();
        VoceMenu ricettaSelezionata = comboRicetteDaSpostare.getValue();
        String sezioneDestinazione = comboSezioniDestinazione.getValue();
        
        if (sezioneOrigine == null) {
            mostraErrore("Sezione origine non selezionata", "Scegli la sezione di origine");
            return;
        }
        
        if (ricettaSelezionata == null) {
            mostraErrore("Ricetta non selezionata", "Scegli una ricetta da spostare");
            return;
        }
        
        if (sezioneDestinazione == null) {
            mostraErrore("Sezione destinazione non selezionata", "Scegli la sezione di destinazione");
            return;
        }
        
        if (sezioneOrigine.equals(sezioneDestinazione)) {
            mostraErrore("Sezioni uguali", "La sezione di origine e destinazione devono essere diverse");
            return;
        }
        
        // Chiama il controller per spostare la ricetta
        controller.handleSpostaRicettaConSezioni(sezioneOrigine, sezioneDestinazione, ricettaSelezionata);
        
        // Aggiorna le combo box
        aggiornaComboBoxRicette(menuSelezionato);
        
        // Pulisce le selezioni
        comboSezioniOrigine.setValue(null);
        comboRicetteDaSpostare.setValue(null);
        comboSezioniDestinazione.setValue(null);
        
        System.out.println("DEBUG: Ricetta spostata da " + sezioneOrigine + " a " + sezioneDestinazione);
    }
    
    /**
     * Gestisce l'eliminazione della ricetta dalla combo box
     */
    private void handleEliminaRicettaDaCombo() {
        VoceMenu ricettaSelezionata = comboRicetteDaRimuovere.getValue();
        
        if (ricettaSelezionata == null) {
            mostraErrore("Ricetta non selezionata", "Scegli una ricetta da rimuovere");
            return;
        }
        
        if (confermaAzione("Conferma rimozione", "Rimuovere '" + ricettaSelezionata.getNomeVisuale() + "' dal menu?")) {
            controller.handleEliminaRicetta(ricettaSelezionata);
            comboRicetteDaRimuovere.setValue(null);
        }
    }
    
    /**
     * Aggiorna tutte le combo box delle sezioni - VERSIONE CORRETTA
     */
    private void aggiornaComboBoxSezioni(Menu menu) {
        // Pulisce tutte le combo box delle sezioni
        comboSezioni.getItems().clear();
        comboSezioniOrigine.getItems().clear();
        comboSezioniDestinazione.getItems().clear();
        
        if (menu != null) {
            // Usa il controller per ottenere i titoli delle sezioni
            ObservableList<String> titoli = controller.getTitoliSezioniMenuSelezionato();
            
            comboSezioni.getItems().addAll(titoli);
            comboSezioniOrigine.getItems().addAll(titoli);
            comboSezioniDestinazione.getItems().addAll(titoli);
            
            System.out.println("DEBUG: Combo sezioni aggiornate con " + titoli.size() + " sezioni");
        }
    }
    
    /**
     * Aggiorna tutte le combo box delle ricette - VERSIONE CORRETTA
     */
    private void aggiornaComboBoxRicette(Menu menu) {
        // Pulisce le combo box delle ricette
        comboRicetteDaSpostare.getItems().clear();
        comboRicetteDaRimuovere.getItems().clear();
        
        if (menu != null) {
            // Usa il controller per ottenere tutte le voci del menu
            ObservableList<VoceMenu> voci = controller.getVociMenuSelezionato();
            
            comboRicetteDaRimuovere.getItems().addAll(voci);
            
            System.out.println("DEBUG: Combo ricette aggiornate con " + voci.size() + " ricette");
        }
    }
    
    /**
     * NUOVO METODO: Aggiorna combo ricette per una sezione specifica
     */
    private void aggiornaComboRicettePerSezione(String titoloSezione) {
        comboRicetteDaSpostare.getItems().clear();
        
        if (menuSelezionato != null && titoloSezione != null) {
            // Trova la sezione e aggiungi solo le sue ricette
            for (SezioniMenu sezione : menuSelezionato.getSezioni()) {
                if (sezione.getTitolo().equals(titoloSezione)) {
                    comboRicetteDaSpostare.getItems().addAll(sezione.getVoci());
                    System.out.println("DEBUG: Trovate " + sezione.getVoci().size() + " ricette in " + titoloSezione);
                    break;
                }
            }
        }
    }
    
    /**
     * Mostra un messaggio di errore
     */
    private void mostraErrore(String titolo, String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titolo);
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }
    
    /**
     * Chiede conferma per una azione
     */
    private boolean confermaAzione(String titolo, String messaggio) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titolo);
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        return alert.showAndWait().get() == ButtonType.OK;
    }
    
    /**
     * Restituisce il nodo principale della vista
     */
    public Node getView() {
        return layoutPrincipale;
    }
    
    /**
     * Aggiorna il messaggio di stato
     */
    public void aggiornaStato(String messaggio) {
        if (labelStato != null) {
            labelStato.setText(messaggio);
        }
    }
}