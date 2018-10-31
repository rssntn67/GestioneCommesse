package it.arsinfo.gc.vaadin;

import com.vaadin.annotations.Title;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SpringUI
@Title("Gestione Commesse")
public class GestioneCommesseUI extends UI {

    /**
     * 
     */
    private static final long serialVersionUID = 3896255437370683864L;
    public final static String URL_COMMESSE="commesse";
    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout layout = new VerticalLayout();
        layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        layout.addComponents(new Label("Benvenuti nel programma gestione commesse"),
                        new Link("Commesse", new ExternalResource(URL_COMMESSE)));
                       
        setContent(layout);
    }

}
