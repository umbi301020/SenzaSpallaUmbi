// Servizio moderno per gestire PDF dei menu
public class MenuPDFService {
    private ExternalPDFService pdfService;
    
    public MenuPDFService(ExternalPDFService pdfService) {
        this.pdfService = pdfService;
    }
    
    public byte[] generateMenuPDF(Menu menu) {
        Map<String, Object> menuData = new HashMap<>();
        menuData.put("title", menu.getTitle());
        menuData.put("content", generateMenuContent(menu));
        menuData.put("additionalInfo", menu.getAdditionalInfo());
        
        return pdfService.generatePDF(menuData);
    }
    
    public boolean shareMenuPDF(byte[] pdfData, String[] recipients) {
        String subject = "Menu per il vostro evento";
        return pdfService.sendEmail(pdfData, recipients, subject);
    }
    
    private String generateMenuContent(Menu menu) {
        StringBuilder content = new StringBuilder();
        content.append("MENU: ").append(menu.getTitle()).append("\n\n");
        
        for (var section : menu.getSections()) {
            if (section instanceof it.catering.domain.MenuSection menuSection) {
                content.append(menuSection.getName()).append(":\n");
                for (var item : menuSection.getItems()) {
                    if (item instanceof it.catering.domain.MenuItem menuItem) {
                        content.append("- ").append(menuItem.getName()).append("\n");
                    }
                }
                content.append("\n");
            }
        }
        
        return content.toString();
    }
}  