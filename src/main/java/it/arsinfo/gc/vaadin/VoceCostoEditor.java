package it.arsinfo.gc.vaadin;


import com.vaadin.data.Binder;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

import it.arsinfo.gc.entity.VoceCosto;
import it.arsinfo.gc.repository.VoceCostoDao;

public class VoceCostoEditor extends GestioneCommesseEditor {

    /**
     * 
     */
    private static final long serialVersionUID = -6636202872081273998L;
    private VoceCosto voceCosto;
    private final TextField voce = new TextField("Voce di Costo");
    private final TextField descr = new TextField("Descrizione");

    Button save = new Button("Save", VaadinIcons.CHECK);
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", VaadinIcons.TRASH);

    HorizontalLayout pri = new HorizontalLayout(voce);
    HorizontalLayout ter = new HorizontalLayout(descr);
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    Binder<VoceCosto> binder = new Binder<>(VoceCosto.class);
    private VoceCostoDao repo;
    public VoceCostoEditor(VoceCostoDao repo) {
        super();
        this.repo = repo;
        addComponents(pri,ter,actions);
        setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        
        binder.forField(voce).asRequired().bind("voce");
        binder.forField(descr).asRequired().bind("descr");
        
        setSpacing(true);

        save.addStyleName(ValoTheme.BUTTON_PRIMARY);
        delete.addStyleName(ValoTheme.BUTTON_DANGER);

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> edit(voceCosto));
        setVisible(false);

    }

    private void delete() {
        repo.delete(voceCosto);
        onchange();
    }

    private void save() {
        repo.save(voceCosto);
        onchange();  
    }

    public void edit(VoceCosto voco) {
        if (voco == null) {
            setVisible(false);
            return;
        }
        
        final boolean persisted = voco.getId() != null;
        if (persisted) {
                // Find fresh entity for editing
                voceCosto = repo.findById(voco.getId()).get();
        }
        else {
            voceCosto = voco;
        }
        cancel.setVisible(persisted);
        binder.setBean(voceCosto);

        setVisible(true);
        voce.focus();
    }

}
