#!/bin/bash

echo "=== Correzione scrolling per tutte le view ==="

# Array dei file da correggere
declare -a files=(
    "src/main/java/it/catering/catring/view/MenuManagementView.java"
    "src/main/java/it/catering/catring/view/CompitoManagementView.java"
    "src/main/java/it/catering/catring/view/MieiCompitiView.java"
    "src/main/java/it/catering/catring/view/SupervisioneView.java"
)

# Backup dei file originali
echo "1. Creazione backup dei file originali..."
for file in "${files[@]}"; do
    if [ -f "$file" ]; then
        cp "$file" "${file}.scroll_backup"
        echo "   Backup creato: ${file}.scroll_backup"
    fi
done

echo ""
echo "2. Applicazione correzioni ScrollPane..."

# Correzione per ogni file
for file in "${files[@]}"; do
    if [ -f "$file" ]; then
        echo "   Correggendo: $file"
        
        # Sostituisce "public VBox getView()" con "public ScrollPane getView()"
        sed -i 's/public VBox getView()/public ScrollPane getView()/' "$file"
        
        # Sostituisce il return statement
        sed -i 's/return mainContent;/ScrollPane scrollPane = new ScrollPane(mainContent);\
        scrollPane.setFitToWidth(true);\
        scrollPane.setFitToHeight(true);\
        scrollPane.setStyle("-fx-background-color: transparent;");\
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);\
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);\
        return scrollPane;/' "$file"
        
        echo "   ✅ $file corretto"
    else
        echo "   ⚠️  File non trovato: $file"
    fi
done


echo ""
echo "=== Correzione scrolling completata ==="