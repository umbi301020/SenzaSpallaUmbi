package it.catering.adapter;

import it.catering.domain.Menu;
import java.util.Map;
import java.util.HashMap;

// Interfaccia per sistemi esterni di generazione PDF
public interface ExternalPDFService {
    byte[] generatePDF(Map<String, Object> data);
    boolean sendEmail(byte[] pdfData, String[] recipients, String subject);
}

