package it.arsinfo.gc.ui.vaadin;


import java.util.EnumSet;
import java.util.List;

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
import it.arsinfo.gc.entity.Resoconto;
import it.arsinfo.gc.entity.Resoconto.Tipologia;
import it.arsinfo.gc.entity.VoceCosto;
import it.arsinfo.gc.repository.ResocontoDao;

public class ResocontoEditor extends GCChangeHandler {

    /**
     * 
     */
    private static final long serialVersionUID = -6636202872081273998L;
    private Resoconto resoconto;
    private final ComboBox<Commessa> commessa = new ComboBox<Commessa>("Selezionare la Commessa");
    private final ComboBox<VoceCosto> voceCosto = new ComboBox<VoceCosto>("Selezionare la Voce Costo");
    private final TextField importo = new TextField("Importo");
    private final DateField data = new DateField("Data");
    private final ComboBox<Tipologia> tipologia = new ComboBox<Tipologia>("Tipologia", EnumSet.allOf(Tipologia.class));

    Button save = new Button("Save", VaadinIcons.CHECK);
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", VaadinIcons.TRASH);

    HorizontalLayout pri = new HorizontalLayout(commessa,voceCosto,importo);
    HorizontalLayout sec = new HorizontalLayout(tipologia,data);
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    Binder<Resoconto> binder = new Binder<>(Resoconto.class);
    private ResocontoDao repo;
    public ResocontoEditor(ResocontoDao repo, List<Commessa> commesse, List<VoceCosto> vc) {
        super();
        this.repo = repo;
        addComponents(pri,sec,actions);
        setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        
        setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        commessa.setItems(commesse);
        commessa.setItemCaptionGenerator(Commessa::getNome);

        voceCosto.setItems(vc);
        voceCosto.setItemCaptionGenerator(VoceCosto::getVoce);

        binder.forField(commessa).asRequired().
        withValidator(an -> an != null, "Scegliere una Commessa" ).
        bind(Resoconto::getCommessa, Resoconto::setCommessa);

        binder.forField(voceCosto).asRequired().
        withValidator(an -> an != null, "Scegliere una Voce di Costo" ).
        bind(Resoconto::getVoceCosto, Resoconto::setVoceCosto);

        binder.forField(importo).asRequired().withConverter(new StringToBigDecimalConverter("Conversione in Eur"))
        .bind("importo");
        
        binder.forField(data).asRequired().
        withConverter(new LocalDateToDateConverter()).bind("data");
        
        binder.forField(tipologia).asRequired().bind("tipologia");
        
        setSpacing(true);

        save.addStyleName(ValoTheme.BUTTON_PRIMARY);
        delete.addStyleName(ValoTheme.BUTTON_DANGER);

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> edit(resoconto));
        setVisible(false);

    }

    private void delete() {
        repo.delete(resoconto);
        onchange();
    }

    private void save() {
        repo.save(resoconto);
        onchange();  
    }

    public void edit(Resoconto rscnt) {
        if (rscnt == null) {
            setVisible(false);
            return;
        }
        
        final boolean persisted = rscnt.getId() != null;
        if (persisted) {
                // Find fresh entity for editing
                resoconto = repo.findById(rscnt.getId()).get();
        }
        else {
            resoconto = rscnt;
        }
        cancel.setVisible(persisted);
        binder.setBean(resoconto);

        setVisible(true);
        commessa.focus();
    }

}
