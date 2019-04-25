package it.arsinfo.gc.ui.vaadin;

import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;

@SpringUI
@Title("Gestione Commesse")
public class HomeUI extends GCUI {

    /**
     * 
     */
    private static final long serialVersionUID = 3896255437370683864L;
    @Override
    protected void init(VaadinRequest request) {
        super.init(request);
        addComponents(getHeaderLinks());
    }
    
    @Override
    protected Label getTitleLabel() {
        Label header = new Label("Benvenuti nel programma gestione commesse");
        header.addStyleName(ValoTheme.LABEL_H2);
        return header;
    }

    
}
