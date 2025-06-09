package it.catering.visitor;

import it.catering.domain.*;

// Pattern Visitor - Visitatore per analizzare i menu
public interface MenuVisitor {
    void visit(Menu menu);
    void visit(MenuSection section);
    void visit(MenuItem item);
}

