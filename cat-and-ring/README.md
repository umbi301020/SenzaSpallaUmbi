# Cat & Ring - Sistema di Gestione Catering

Sistema per la gestione di menù e compiti della cucina per servizio catering.

## Requisiti
- Java 21
- Maven 3.9.9
- Visual Studio Code

## Compilazione
```bash
mvn clean compile
```

## Esecuzione
```bash
mvn exec:java -Dexec.mainClass="it.catering.CateringApplication"
```

## Test
```bash
mvn test
```

## Pattern Implementati
- **GRASP**: Controller, Creator, Information Expert, Low Coupling & High Cohesion, Separation of Concerns
- **GoF Creazionali**: Abstract Factory, Singleton
- **GoF Strutturali**: Adapter, Composite, Decorator
- **GoF Comportamentali**: Observer, State, Strategy, Visitor
