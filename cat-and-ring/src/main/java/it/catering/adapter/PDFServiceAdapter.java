// Pattern Adapter - Adatta il servizio legacy all'interfaccia moderna
public class PDFServiceAdapter implements ExternalPDFService {
    private LegacyPDFGenerator legacyGenerator;
    
    public PDFServiceAdapter(LegacyPDFGenerator legacyGenerator) {
        this.legacyGenerator = legacyGenerator;
    }
    
    @Override
    public byte[] generatePDF(Map<String, Object> data) {
        String title = (String) data.get("title");
        String content = (String) data.get("content");
        
        String pdfData = legacyGenerator.createDocument(title, content, "PDF");
        return pdfData.getBytes(); // Conversione semplificata
    }
    
    @Override
    public boolean sendEmail(byte[] pdfData, String[] recipients, String subject) {
        String emailList = String.join(",", recipients);
        String documentData = new String(pdfData);
        return legacyGenerator.emailDocument(documentData, emailList);
    }
}
