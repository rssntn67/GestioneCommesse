package it.arsinfo.gc.vaadin;


import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public abstract class GestioneCommesseHeaderUI extends UI {

    /**
     * 
     */
    public final static String URL_USER="user";
    public final static String URL_COMMESSE="commesse";
    public final static String URL_RESOCONTI="resoconti";
    public final static String URL_VOCECOSTO="vocecosto";
    public final static String URL_VARIAZIONE_COMMESSE="variazionecommesse";
    
    private static final long serialVersionUID = 679939857396325190L;
    private VerticalLayout layout = new VerticalLayout();

    @Override
    protected void init(VaadinRequest request) {
        layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        layout.addComponents(new HorizontalLayout(getHeaderLinks()),
                             getTitleLabel());
        setContent(layout);
    }

    protected abstract Label getTitleLabel();

    protected void addComponents(Component... components ) {
        layout.addComponents(components);
    }
    
    public Link[] getHeaderLinks() {
        Link[] links =  {
            new Link("User", new ExternalResource(URL_USER)),
            new Link("Commesse", new ExternalResource(URL_COMMESSE)),
            new Link("Resoconti", new ExternalResource(URL_RESOCONTI)),
            new Link("Variazioni Commesse", new ExternalResource(URL_VARIAZIONE_COMMESSE)),
            new Link("Voci di Costo", new ExternalResource(URL_VOCECOSTO))
        };
        return links;
    }

}
