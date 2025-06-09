package it.catering.catring.view;

import it.catering.catring.controller.ApplicationController;
import it.catering.catring.controller.MenuController;
import it.catering.catring.model.entities.*;
import it.catering.catring.model.entities.Menu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;

public class MenuManagementView {
    private MenuController menuController;
    private VBox mainContent;
    private ListView<Menu> menuListView;
    private VBox menuDetailsPane;
    private Menu selectedMenu;
    
    public MenuManagementView() {
        this.menuController = ApplicationController.getInstance().getMenuController();
        createView();
        loadMenus();
    }
    
    public ScrollPane getView() {
        ScrollPane scrollPane = new ScrollPane(mainContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle("-fx-background-color: transparent;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        return scrollPane;
    }
    
    private void createView() {
        mainContent = new VBox(20);
        mainContent.setPadding(new Insets(20));
        mainContent.setStyle("-fx-background-color: white;");
        
        // Header
        HBox header = new HBox(20);
        header.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        
        Label titleLabel = new Label("Gestione Menu");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        Button newMenuButton = new Button("Nuovo Menu");
        newMenuButton.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-padding: 10; -fx-background-radius: 5;");
        newMenuButton.setOnAction(e -> showCreateMenuDialog());
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        header.getChildren().addAll(titleLabel, spacer, newMenuButton);
        
        // Content area
        HBox contentArea = new HBox(20);
        contentArea.setAlignment(javafx.geometry.Pos.TOP_LEFT);
        
        // Menu list
        VBox menuListPane = createMenuListPane();
        
        // Menu details
        menuDetailsPane = createMenuDetailsPane();
        
        contentArea.getChildren().addAll(menuListPane, menuDetailsPane);
        
        mainContent.getChildren().addAll(header, new Separator(), contentArea);
    }
    
    private VBox createMenuListPane() {
        VBox pane = new VBox(10);
        pane.setPrefWidth(300);
        pane.setStyle("-fx-background-color: #f8f9fa; -fx-padding: 15; -fx-background-radius: 5;");
        
        Label listTitle = new Label("I Miei Menu");
        listTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        menuListView = new ListView<>();
        menuListView.setPrefHeight(400);
        menuListView.getSelectionModel().selectedItemProperty().addListener((obs, oldMenu, newMenu) -> {
            selectedMenu = newMenu;
            updateMenuDetails();
        });
        
        pane.getChildren().addAll(listTitle, menuListView);
        return pane;
    }
    
    private VBox createMenuDetailsPane() {
        VBox pane = new VBox(15);
        pane.setPrefWidth(600);
        pane.setStyle("-fx-background-color: #f8f9fa; -fx-padding: 15; -fx-background-radius: 5;");
        
        Label detailsTitle = new Label("Dettagli Menu");
        detailsTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        Label noSelectionLabel = new Label("Seleziona un menu dalla lista per visualizzare i dettagli");
        noSelectionLabel.setStyle("-fx-text-fill: #7f8c8d; -fx-font-style: italic;");
        
        pane.getChildren().addAll(detailsTitle, noSelectionLabel);
        return pane;
    }
    
    private void updateMenuDetails() {
        updateMenuDetailsWithButtons();
    }
    
    private void updateMenuDetailsWithButtons() {
        menuDetailsPane.getChildren().clear();
        
        Label detailsTitle = new Label("Dettagli Menu");
        detailsTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        menuDetailsPane.getChildren().add(detailsTitle);
        
        if (selectedMenu == null) {
            Label noSelectionLabel = new Label("Seleziona un menu dalla lista per visualizzare i dettagli");
            noSelectionLabel.setStyle("-fx-text-fill: #7f8c8d; -fx-font-style: italic;");
            menuDetailsPane.getChildren().add(noSelectionLabel);
            return;
        }
        
        // Menu info
        VBox menuInfo = new VBox(10);
        menuInfo.setStyle("-fx-background-color: white; -fx-padding: 15; -fx-background-radius: 5;");
        
        Label menuTitleLabel = new Label("Titolo: " + selectedMenu.getTitolo());
        menuTitleLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        menuInfo.getChildren().add(menuTitleLabel);
        
        if (selectedMenu.getDescrizione() != null && !selectedMenu.getDescrizione().trim().isEmpty()) {
            Label descLabel = new Label("Descrizione: " + selectedMenu.getDescrizione());
            descLabel.setWrapText(true);
            menuInfo.getChildren().add(descLabel);
        }
        
        // Stato menu
        Label statoLabel = new Label("Stato: " + (selectedMenu.isUtilizzato() ? "UTILIZZATO" : "DISPONIBILE"));
        statoLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: " + 
                           (selectedMenu.isUtilizzato() ? "#e74c3c" : "#27ae60") + ";");
        menuInfo.getChildren().add(statoLabel);
        
        // Caratteristiche menu
        VBox caratteristiche = new VBox(5);
        caratteristiche.getChildren().add(new Label("Caratteristiche:"));
        
        if (selectedMenu.isCuocoRichiesto()) caratteristiche.getChildren().add(new Label("• Cuoco richiesto durante il servizio"));
        if (selectedMenu.isSoloPiattiFreddi()) caratteristiche.getChildren().add(new Label("• Solo piatti freddi"));
        if (selectedMenu.isCucinaRichiesta()) caratteristiche.getChildren().add(new Label("• Cucina richiesta nella sede"));
        if (selectedMenu.isAdeguatoBuffet()) caratteristiche.getChildren().add(new Label("• Adeguato per buffet"));
        if (selectedMenu.isFingerFood()) caratteristiche.getChildren().add(new Label("• Finger food"));
        
        if (caratteristiche.getChildren().size() == 1) {
            caratteristiche.getChildren().add(new Label("  Nessuna caratteristica particolare"));
        }
        
        menuInfo.getChildren().add(caratteristiche);
        
        // Sezioni e ricette
        VBox sezioniArea = new VBox(10);
        sezioniArea.setStyle("-fx-background-color: white; -fx-padding: 15; -fx-background-radius: 5;");
        
        Label sezioniTitle = new Label("Sezioni e Ricette:");
        sezioniTitle.setStyle("-fx-font-weight: bold;");
        sezioniArea.getChildren().add(sezioniTitle);
        
        if (selectedMenu.getSezioni().isEmpty()) {
            Label noSezioniLabel = new Label("Nessuna sezione presente. Aggiungi una sezione per iniziare.");
            noSezioniLabel.setStyle("-fx-text-fill: #7f8c8d; -fx-font-style: italic;");
            sezioniArea.getChildren().add(noSezioniLabel);
        } else {
            for (SezioneMenu sezione : selectedMenu.getSezioni()) {
                VBox sezioneBox = new VBox(5);
                sezioneBox.setStyle("-fx-border-color: #e9ecef; -fx-border-width: 1; -fx-padding: 10; -fx-background-radius: 3;");
                
                Label sezioneLabel = new Label(sezione.getNome());
                sezioneLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #495057;");
                sezioneBox.getChildren().add(sezioneLabel);
                
                if (sezione.getVoci().isEmpty()) {
                    Label noVociLabel = new Label("  Nessuna ricetta in questa sezione");
                    noVociLabel.setStyle("-fx-text-fill: #6c757d; -fx-font-style: italic;");
                    sezioneBox.getChildren().add(noVociLabel);
                } else {
                    for (VoceMenu voce : sezione.getVoci()) {
                        Label voceLabel = new Label("  • " + voce.getNomeVoce());
                        if (!voce.getNomeVoce().equals(voce.getRicetta().getNome())) {
                            voceLabel.setText(voceLabel.getText() + " (" + voce.getRicetta().getNome() + ")");
                        }
                        sezioneBox.getChildren().add(voceLabel);
                    }
                }
                
                sezioniArea.getChildren().add(sezioneBox);
            }
        }
        
        // Action buttons
        HBox buttonBox = createActionButtons();
        
        menuDetailsPane.getChildren().addAll(menuInfo, sezioniArea, buttonBox);
    }
    
    private HBox createActionButtons() {
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        
        Button editButton = new Button("Modifica Info");
        editButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-padding: 8 15; -fx-background-radius: 5;");
        editButton.setOnAction(e -> showEditMenuDialog());
        editButton.disableProperty().bind(menuListView.getSelectionModel().selectedItemProperty().isNull()
            .or(javafx.beans.binding.Bindings.createBooleanBinding(() -> 
                selectedMenu != null && selectedMenu.isUtilizzato(), 
                menuListView.getSelectionModel().selectedItemProperty())));
        
        Button addSectionButton = new Button("Aggiungi Sezione");
        addSectionButton.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white; -fx-padding: 8 15; -fx-background-radius: 5;");
        addSectionButton.setOnAction(e -> showAddSectionDialog());
        addSectionButton.disableProperty().bind(menuListView.getSelectionModel().selectedItemProperty().isNull()
            .or(javafx.beans.binding.Bindings.createBooleanBinding(() -> 
                selectedMenu != null && selectedMenu.isUtilizzato(), 
                menuListView.getSelectionModel().selectedItemProperty())));
        
        Button addRicettaButton = new Button("Aggiungi Ricetta");
        addRicettaButton.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-padding: 8 15; -fx-background-radius: 5;");
        addRicettaButton.setOnAction(e -> showAddRicettaDialog());
        addRicettaButton.disableProperty().bind(menuListView.getSelectionModel().selectedItemProperty().isNull()
            .or(javafx.beans.binding.Bindings.createBooleanBinding(() -> 
                selectedMenu != null && selectedMenu.isUtilizzato(), 
                menuListView.getSelectionModel().selectedItemProperty())));
        
        Button removeRicettaButton = new Button("Rimuovi Ricetta");
        removeRicettaButton.setStyle("-fx-background-color: #e67e22; -fx-text-fill: white; -fx-padding: 8 15; -fx-background-radius: 5;");
        removeRicettaButton.setOnAction(e -> showRemoveRicettaDialog());
        removeRicettaButton.disableProperty().bind(menuListView.getSelectionModel().selectedItemProperty().isNull()
            .or(javafx.beans.binding.Bindings.createBooleanBinding(() -> 
                selectedMenu != null && selectedMenu.isUtilizzato(), 
                menuListView.getSelectionModel().selectedItemProperty())));
        
        Button deleteButton = new Button("Elimina Menu");
        deleteButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-padding: 8 15; -fx-background-radius: 5;");
        deleteButton.setOnAction(e -> deleteMenu());
        deleteButton.disableProperty().bind(menuListView.getSelectionModel().selectedItemProperty().isNull()
            .or(javafx.beans.binding.Bindings.createBooleanBinding(() -> 
                selectedMenu != null && selectedMenu.isUtilizzato(), 
                menuListView.getSelectionModel().selectedItemProperty())));
        
        buttonBox.getChildren().addAll(editButton, addSectionButton, addRicettaButton, 
                                      removeRicettaButton, deleteButton);
        return buttonBox;
    }
    
    private void showAddRicettaDialog() {
        if (selectedMenu == null) {
            showAlert("Errore", "Seleziona prima un menu");
            return;
        }
        
        if (selectedMenu.getSezioni().isEmpty()) {
            showAlert("Errore", "Aggiungi prima almeno una sezione al menu");
            return;
        }
        
        Dialog<Boolean> dialog = new Dialog<>();
        dialog.setTitle("Aggiungi Ricetta");
        dialog.setHeaderText("Aggiungi una ricetta al menu");
        
        ButtonType addButtonType = new ButtonType("Aggiungi", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
        // ComboBox per sezioni
        ComboBox<SezioneMenu> sezioneCombo = new ComboBox<>();
        sezioneCombo.setItems(FXCollections.observableArrayList(selectedMenu.getSezioni()));
        sezioneCombo.setConverter(new javafx.util.StringConverter<SezioneMenu>() {
            @Override
            public String toString(SezioneMenu sezione) {
                return sezione != null ? sezione.getNome() : "";
            }
            
            @Override
            public SezioneMenu fromString(String string) {
                return null;
            }
        });
        
        // ComboBox per ricette
        ComboBox<Ricetta> ricettaCombo = new ComboBox<>();
        List<Ricetta> ricetteDisponibili = menuController.getRicetteDisponibili();
        ricettaCombo.setItems(FXCollections.observableArrayList(ricetteDisponibili));
        ricettaCombo.setConverter(new javafx.util.StringConverter<Ricetta>() {
            @Override
            public String toString(Ricetta ricetta) {
                return ricetta != null ? ricetta.getNome() : "";
            }
            
            @Override
            public Ricetta fromString(String string) {
                return null;
            }
        });
        
        TextField nomePersonalizzatoField = new TextField();
        nomePersonalizzatoField.setPromptText("Nome personalizzato (opzionale)");
        
        grid.add(new Label("Sezione:"), 0, 0);
        grid.add(sezioneCombo, 1, 0);
        grid.add(new Label("Ricetta:"), 0, 1);
        grid.add(ricettaCombo, 1, 1);
        grid.add(new Label("Nome personalizzato:"), 0, 2);
        grid.add(nomePersonalizzatoField, 1, 2);
        
        dialog.getDialogPane().setContent(grid);
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                SezioneMenu sezione = sezioneCombo.getValue();
                Ricetta ricetta = ricettaCombo.getValue();
                
                if (sezione == null || ricetta == null) {
                    showAlert("Errore", "Seleziona sezione e ricetta");
                    return false;
                }
                
                try {
                    String nomePersonalizzato = nomePersonalizzatoField.getText().trim();
                    if (nomePersonalizzato.isEmpty()) {
                        nomePersonalizzato = ricetta.getNome();
                    }
                    
                    menuController.addRicettaToSezione(selectedMenu, sezione.getNome(), 
                                                     ricetta, nomePersonalizzato);
                    return true;
                } catch (Exception e) {
                    showAlert("Errore", e.getMessage());
                    return false;
                }
            }
            return false;
        });
        
        dialog.showAndWait().ifPresent(result -> {
            if (result) {
                updateMenuDetails();
            }
        });
    }
    
    private void showRemoveRicettaDialog() {
        if (selectedMenu == null || selectedMenu.getSezioni().isEmpty()) {
            showAlert("Errore", "Nessuna ricetta da rimuovere");
            return;
        }
        
        // Raccogli tutte le voci di menu
        List<VoceMenu> tutteLeVoci = new ArrayList<>();
        for (SezioneMenu sezione : selectedMenu.getSezioni()) {
            tutteLeVoci.addAll(sezione.getVoci());
        }
        
        if (tutteLeVoci.isEmpty()) {
            showAlert("Info", "Nessuna ricetta presente nel menu");
            return;
        }
        
        ChoiceDialog<VoceMenu> dialog = new ChoiceDialog<>(tutteLeVoci.get(0), tutteLeVoci);
        dialog.setTitle("Rimuovi Ricetta");
        dialog.setHeaderText("Seleziona la ricetta da rimuovere");
        dialog.setContentText("Ricetta:");
        
        dialog.getDialogPane().getContent().setStyle("-fx-pref-width: 400px;");
        
        dialog.showAndWait().ifPresent(voceSelezionata -> {
            try {
                // Trova la sezione che contiene questa voce
                for (SezioneMenu sezione : selectedMenu.getSezioni()) {
                    if (sezione.getVoci().contains(voceSelezionata)) {
                        menuController.removeRicettaFromSezione(selectedMenu, 
                                                              sezione.getNome(), voceSelezionata);
                        updateMenuDetails();
                        break;
                    }
                }
            } catch (Exception e) {
                showAlert("Errore", e.getMessage());
            }
        });
    }
    
    private void showCreateMenuDialog() {
        Dialog<Menu> dialog = new Dialog<>();
        dialog.setTitle("Nuovo Menu");
        dialog.setHeaderText("Crea un nuovo menu");
        
        ButtonType createButtonType = new ButtonType("Crea", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
        TextField titleField = new TextField();
        titleField.setPromptText("Titolo del menu");
        
        TextArea descriptionArea = new TextArea();
        descriptionArea.setPromptText("Descrizione (opzionale)");
        descriptionArea.setPrefRowCount(3);
        
        grid.add(new Label("Titolo:"), 0, 0);
        grid.add(titleField, 1, 0);
        grid.add(new Label("Descrizione:"), 0, 1);
        grid.add(descriptionArea, 1, 1);
        
        dialog.getDialogPane().setContent(grid);
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == createButtonType) {
                try {
                    return menuController.createMenu(titleField.getText(), descriptionArea.getText());
                } catch (Exception e) {
                    showAlert("Errore", e.getMessage());
                    return null;
                }
            }
            return null;
        });
        
        dialog.showAndWait().ifPresent(menu -> {
            loadMenus();
            menuListView.getSelectionModel().select(menu);
        });
    }
    
    private void showEditMenuDialog() {
        if (selectedMenu == null) return;
        
        Dialog<Boolean> dialog = new Dialog<>();
        dialog.setTitle("Modifica Menu");
        dialog.setHeaderText("Modifica caratteristiche del menu");
        
        ButtonType saveButtonType = new ButtonType("Salva", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);
        
        VBox content = new VBox(10);
        content.setPadding(new Insets(20));
        
        CheckBox cuocoRichiestoBox = new CheckBox("Cuoco richiesto durante il servizio");
        cuocoRichiestoBox.setSelected(selectedMenu.isCuocoRichiesto());
        
        CheckBox soloPiattiFreddiBox = new CheckBox("Solo piatti freddi");
        soloPiattiFreddiBox.setSelected(selectedMenu.isSoloPiattiFreddi());
        
        CheckBox cucinaRichiestaBox = new CheckBox("Cucina richiesta nella sede");
        cucinaRichiestaBox.setSelected(selectedMenu.isCucinaRichiesta());
        
        CheckBox adeguatoBuffetBox = new CheckBox("Adeguato per buffet");
        adeguatoBuffetBox.setSelected(selectedMenu.isAdeguatoBuffet());
        
        CheckBox fingerFoodBox = new CheckBox("Finger food");
        fingerFoodBox.setSelected(selectedMenu.isFingerFood());
        
        content.getChildren().addAll(cuocoRichiestoBox, soloPiattiFreddiBox, cucinaRichiestaBox, adeguatoBuffetBox, fingerFoodBox);
        
        dialog.getDialogPane().setContent(content);
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                try {
                    menuController.updateMenuInfo(selectedMenu, 
                        cuocoRichiestoBox.isSelected(),
                        soloPiattiFreddiBox.isSelected(),
                        cucinaRichiestaBox.isSelected(),
                        adeguatoBuffetBox.isSelected(),
                        fingerFoodBox.isSelected());
                    return true;
                } catch (Exception e) {
                    showAlert("Errore", e.getMessage());
                    return false;
                }
            }
            return false;
        });
        
        dialog.showAndWait().ifPresent(result -> {
            if (result) {
                updateMenuDetails();
            }
        });
    }
    
    private void showAddSectionDialog() {
        if (selectedMenu == null) return;
        
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Nuova Sezione");
        dialog.setHeaderText("Aggiungi una nuova sezione al menu");
        dialog.setContentText("Nome sezione:");
        
        dialog.showAndWait().ifPresent(sectionName -> {
            if (!sectionName.trim().isEmpty()) {
                try {
                    menuController.addSezioneToMenu(selectedMenu, sectionName.trim());
                    updateMenuDetails();
                } catch (Exception e) {
                    showAlert("Errore", e.getMessage());
                }
            }
        });
    }
    
    private void deleteMenu() {
        if (selectedMenu == null) return;
        
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Conferma Eliminazione");
        confirmAlert.setHeaderText("Eliminare il menu selezionato?");
        confirmAlert.setContentText("Questa azione non può essere annullata.");
        
        confirmAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    menuController.deleteMenu(selectedMenu);
                    loadMenus();
                    selectedMenu = null;
                    updateMenuDetails();
                } catch (Exception e) {
                    showAlert("Errore", e.getMessage());
                }
            }
        });
    }
    
    private void loadMenus() {
        try {
            List<Menu> menus = menuController.getMenusByCurrentChef();
            ObservableList<Menu> menuList = FXCollections.observableArrayList(menus);
            menuListView.setItems(menuList);
        } catch (Exception e) {
            showAlert("Errore", "Impossibile caricare i menu: " + e.getMessage());
        }
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (!title.equals("Errore")) {
            alert.setAlertType(Alert.AlertType.INFORMATION);
        }
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}