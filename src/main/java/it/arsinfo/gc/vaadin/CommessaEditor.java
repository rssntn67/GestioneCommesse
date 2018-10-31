package it.arsinfo.gc.vaadin;


import java.util.EnumSet;

import com.vaadin.data.Binder;
import com.vaadin.data.converter.LocalDateToDateConverter;
import com.vaadin.data.converter.StringToBigDecimalConverter;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

import it.arsinfo.gc.entity.Commessa;
import it.arsinfo.gc.entity.Commessa.Tipologia;
import it.arsinfo.gc.repository.CommessaDao;

public class CommessaEditor extends GestioneCommesseEditor {

    /**
     * 
     */
    private static final long serialVersionUID = -6636202872081273998L;
    private Commessa commessa;
    private final TextField nome = new TextField("Nome");
    private final TextField descr = new TextField("Descrizione");
    private final TextField importo = new TextField("Importo");
    private final DateField inizio = new DateField("Inizio");
    private final DateField fine = new DateField("Fine");
    private final ComboBox<Tipologia> tipologia = new ComboBox<Tipologia>("Tipologia", EnumSet.allOf(Tipologia.class));

    Button save = new Button("Save", VaadinIcons.CHECK);
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", VaadinIcons.TRASH);

    HorizontalLayout pri = new HorizontalLayout(nome,importo,tipologia);
    HorizontalLayout sec = new HorizontalLayout(inizio,fine);
    HorizontalLayout ter = new HorizontalLayout(descr);
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    Binder<Commessa> binder = new Binder<>(Commessa.class);
    private CommessaDao repo;
    public CommessaEditor(CommessaDao repo) {
        super();
        this.repo = repo;
        addComponents(pri,sec,ter,actions);
        setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        
        binder.forField(nome).asRequired().bind("nome");
        binder.forField(descr).asRequired().bind("descr");
        binder.forField(importo).asRequired().withConverter(new StringToBigDecimalConverter("Conversione in Eur"))
        .bind("importo");
        
        binder.forField(inizio).asRequired().
        withConverter(new LocalDateToDateConverter()).bind("inizio");
        binder.forField(fine).asRequired().
        withConverter(new LocalDateToDateConverter()).bind("fine");
        binder.forField(tipologia).asRequired().bind("tipologia");
        
        setSpacing(true);

        save.addStyleName(ValoTheme.BUTTON_PRIMARY);
        delete.addStyleName(ValoTheme.BUTTON_DANGER);

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> edit(commessa));
        setVisible(false);

    }

    private void delete() {
        repo.delete(commessa);
        onchange();
    }

    private void save() {
        repo.save(commessa);
        onchange();  
    }

    public void edit(Commessa comm) {
        if (comm == null) {
            setVisible(false);
            return;
        }
        
        final boolean persisted = comm.getId() != null;
        if (persisted) {
                // Find fresh entity for editing
                commessa = repo.findById(comm.getId()).get();
        }
        else {
                commessa = comm;
        }
        cancel.setVisible(persisted);
        binder.setBean(commessa);

        setVisible(true);
        nome.focus();
    }

}
