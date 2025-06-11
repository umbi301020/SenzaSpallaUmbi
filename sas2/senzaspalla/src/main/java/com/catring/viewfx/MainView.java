package com.catring.viewfx;

import com.catring.controller.MenuController;
import com.catring.controller.EventoController;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * VISTA PRINCIPALE DELL'APPLICAZIONE - CORREZIONE FINALE
 * Risolve il problema dello scroll e delle dimensioni
 */
public class MainView {
    
    private Stage primaryStage;
    private Scene scene;
    private TabPane tabPane;
    
    // View specifiche per ogni funzionalità
    private EventiView eventiView;
    private MenuView menuView;
    private RicettarioView ricettarioView;
    private BachecaView bachecaView;
    
    // Controller
    private MenuController menuController;
    private EventoController eventoController;
    
    public MainView(Stage stage) {
        this.primaryStage = stage;
        this.menuController = new MenuController();
        this.eventoController = new EventoController();
        
        creaInterfaccia();
        configuraStage();
    }
    
    /**
     * Crea l'interfaccia principale
     */
    private void creaInterfaccia() {
        // Layout principale
        BorderPane layoutPrincipale = new BorderPane();
        
        // Intestazione compatta
        VBox intestazione = creaIntestazione();
        layoutPrincipale.setTop(intestazione);
        
        // Contenuto principale con tab e scroll
        tabPane = creaTabPane();
        layoutPrincipale.setCenter(tabPane);
        
        // Footer compatto
        HBox footer = creaFooter();
        layoutPrincipale.setBottom(footer);
        
        // Crea la scena con dimensioni flessibili
        scene = new Scene(layoutPrincipale, 1400, 800);
    }
    
    /**
     * Crea l'intestazione compatta
     */
    private VBox creaIntestazione() {
        VBox intestazione = new VBox();
        intestazione.setSpacing(5);
        intestazione.setStyle("-fx-padding: 10px 15px; -fx-background-color: #ecf0f1;");
        intestazione.setMaxHeight(60); // Limita l'altezza
        
        Label titolo = new Label("Cat & Ring - Sistema di Gestione Catering");
        titolo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        Label sottotitolo = new Label("Gestisci eventi, menu e ricette");
        sottotitolo.setStyle("-fx-font-size: 12px; -fx-text-fill: #7f8c8d;");
        
        intestazione.getChildren().addAll(titolo, sottotitolo);
        return intestazione;
    }
    
    /**
     * Crea il pannello con le tab principali - CON SCROLL
     */
    private TabPane creaTabPane() {
        TabPane tabs = new TabPane();
        tabs.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        
        // Tab Eventi
        Tab tabEventi = new Tab("Eventi");
        eventiView = new EventiView(eventoController);
        
        // IMPORTANTE: Ogni tab ha il suo ScrollPane
        ScrollPane scrollEventi = creaScrollPane(eventiView.getView());
        tabEventi.setContent(scrollEventi);
        
        // Tab Menu
        Tab tabMenu = new Tab("Menu");
        menuView = new MenuView(menuController);
        
        ScrollPane scrollMenu = creaScrollPane(menuView.getView());
        tabMenu.setContent(scrollMenu);
        
        // Tab Ricettario
        Tab tabRicettario = new Tab("Ricettario");
        ricettarioView = new RicettarioView(menuController);
        
        ScrollPane scrollRicettario = creaScrollPane(ricettarioView.getView());
        tabRicettario.setContent(scrollRicettario);
        
        // Tab Bacheca
        Tab tabBacheca = new Tab("Bacheca");
        bachecaView = new BachecaView(menuController);
        
        ScrollPane scrollBacheca = creaScrollPane(bachecaView.getView());
        tabBacheca.setContent(scrollBacheca);
        
        tabs.getTabs().addAll(tabEventi, tabMenu, tabRicettario, tabBacheca);
        return tabs;
    }
    
    /**
     * Crea uno ScrollPane configurato correttamente
     */
    private ScrollPane creaScrollPane(javafx.scene.Node contenuto) {
        ScrollPane scroll = new ScrollPane();
        scroll.setContent(contenuto);
        scroll.setFitToWidth(true);
        scroll.setFitToHeight(false); // IMPORTANTE: permetti scroll verticale
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroll.setStyle("-fx-background-color: transparent;");
        
        // Velocità scroll migliorata
        scroll.setOnScroll(event -> {
            double deltaY = event.getDeltaY() * 3; // Aumenta velocità scroll
            scroll.setVvalue(scroll.getVvalue() - deltaY / scroll.getContent().getBoundsInLocal().getHeight());
        });
        
        return scroll;
    }
    
    /**
     * Crea il footer compatto
     */
    private HBox creaFooter() {
        HBox footer = new HBox();
        footer.setStyle("-fx-padding: 8px 15px; -fx-background-color: #f8f9fa;");
        footer.setMaxHeight(25); // Limita l'altezza
        
        Region spazioVuoto = new Region();
        HBox.setHgrow(spazioVuoto, Priority.ALWAYS);
        
        Label copyright = new Label("Cat & Ring © 2024");
        copyright.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 10px;");
        
        footer.getChildren().addAll(spazioVuoto, copyright);
        return footer;
    }
    
    /**
     * Configura lo stage principale
     */
    private void configuraStage() {
        primaryStage.setTitle("Cat & Ring - Sistema di Gestione Catering");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        
        // Dimensioni più ragionevoli
        primaryStage.setMinWidth(1200);
        primaryStage.setMinHeight(700);
        
        // Dimensioni iniziali
        primaryStage.setWidth(1400);
        primaryStage.setHeight(800);
        
        // Centra la finestra
        primaryStage.centerOnScreen();
        
        // Massimizza automaticamente se lo schermo è grande
        if (javafx.stage.Screen.getPrimary().getVisualBounds().getWidth() >= 1600) {
            primaryStage.setMaximized(true);
        }
    }
    
    /**
     * Mostra la finestra principale
     */
    public void mostra() {
        primaryStage.show();
    }
    
    /**
     * Restituisce lo stage principale
     */
    public Stage getStage() {
        return primaryStage;
    }
    
    /**
     * Restituisce la scena principale
     */
    public Scene getScene() {
        return scene;
    }
}