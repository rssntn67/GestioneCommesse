package it.arsinfo.gc.vaadin;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Link;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class GestioneCommesseHeaderUI extends UI {

    /**
     * 
     */
    private static final long serialVersionUID = 679939857396325190L;
    private VerticalLayout layout = new VerticalLayout();

    @Override
    protected void init(VaadinRequest request) {
        HorizontalLayout header = new HorizontalLayout();
        header.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        header.addComponents(
                            new Link("Commesse", new ExternalResource(GestioneCommesseUI.URL_COMMESSE)));
        layout.addComponent(header);
        
        setContent(layout);
    }
    
    protected void addComponents(Component... components ) {
        layout.addComponents(components);
    }

}
