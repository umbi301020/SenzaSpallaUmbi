// Simulazione di un servizio PDF esterno con interfaccia diversa
public class LegacyPDFGenerator {
    public String createDocument(String title, String content, String format) {
        return "PDF_DATA_" + title + "_" + format;
    }
    
    public boolean emailDocument(String documentData, String emailList) {
        System.out.println("Email inviata a: " + emailList);
        return true;
    }
}