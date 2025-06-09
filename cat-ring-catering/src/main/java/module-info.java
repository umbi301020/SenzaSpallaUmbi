// File: src/main/java/module-info.java
module catring {
    // JavaFX dependencies with transitive to avoid accessibility warnings
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
    requires java.desktop;
    
    // Export main application package
    exports it.catering.catring;
    
    // Export controller package
    exports it.catering.catring.controller;
    
    // Export view packages
    exports it.catering.catring.view;
    exports it.catering.catring.view.components;
    
    // Export model main package
    exports it.catering.catring.model;
    
    // Export all model sub-packages to make entities accessible
    exports it.catering.catring.model.entities;
    exports it.catering.catring.model.factories;
    exports it.catering.catring.model.managers;
    exports it.catering.catring.model.observers;
    exports it.catering.catring.model.states;
    exports it.catering.catring.model.strategies;
}