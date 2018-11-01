package it.arsinfo.gc.vaadin;


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
import it.arsinfo.gc.entity.VariazioneCommessa;
import it.arsinfo.gc.repository.VariazioneCommessaDao;

public class VariazioneCommessaEditor extends GestioneCommesseEditor {

    /**
     * 
     */
    private static final long serialVersionUID = -6636202872081273998L;
    private VariazioneCommessa variazioneCommessa;
    private final ComboBox<Commessa> commessa = new ComboBox<Commessa>("Selezionare la Commessa");
    private final TextField descr = new TextField("Descrizione");
    private final TextField importo = new TextField("Importo");
    private final DateField inizio = new DateField("Inizio");
    private final DateField fine = new DateField("Fine");

    Button save = new Button("Save", VaadinIcons.CHECK);
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", VaadinIcons.TRASH);

    HorizontalLayout pri = new HorizontalLayout(commessa,importo,inizio,fine);
    HorizontalLayout ter = new HorizontalLayout(descr);
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    Binder<VariazioneCommessa> binder = new Binder<>(VariazioneCommessa.class);
    private VariazioneCommessaDao repo;
    public VariazioneCommessaEditor(VariazioneCommessaDao repo, List<Commessa> commesse) {
        super();
        this.repo = repo;
        addComponents(pri,ter,actions);
        setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        commessa.setItems(commesse);
        commessa.setItemCaptionGenerator(Commessa::getNome);

        binder.forField(commessa).asRequired().
        withValidator(an -> an != null, "Scegliere una Commessa" ).
        bind(VariazioneCommessa::getCommessa, VariazioneCommessa::setCommessa);
        binder.forField(descr).asRequired().bind("descr");
        binder.forField(importo).asRequired().withConverter(new StringToBigDecimalConverter("Conversione in Eur"))
        .bind("importo");
        
        binder.forField(inizio).asRequired().
        withConverter(new LocalDateToDateConverter()).bind("inizio");
        binder.forField(fine).asRequired().
        withConverter(new LocalDateToDateConverter()).bind("fine");
        
        setSpacing(true);

        save.addStyleName(ValoTheme.BUTTON_PRIMARY);
        delete.addStyleName(ValoTheme.BUTTON_DANGER);

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> edit(variazioneCommessa));
        setVisible(false);

    }

    private void delete() {
        repo.delete(variazioneCommessa);
        onchange();
    }

    private void save() {
        repo.save(variazioneCommessa);
        onchange();  
    }

    public void edit(VariazioneCommessa variazcomm) {
        if (variazcomm == null) {
            setVisible(false);
            return;
        }
        
        final boolean persisted = variazcomm.getId() != null;
        if (persisted) {
                // Find fresh entity for editing
                variazioneCommessa = repo.findById(variazcomm.getId()).get();
        }
        else {
            variazioneCommessa = variazcomm;
        }
        cancel.setVisible(persisted);
        binder.setBean(variazioneCommessa);

        setVisible(true);
        importo.focus();
    }

}
