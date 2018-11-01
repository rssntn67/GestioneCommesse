package it.arsinfo.gc.vaadin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.vaadin.annotations.Title;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

import it.arsinfo.gc.entity.Commessa;
import it.arsinfo.gc.entity.VariazioneCommessa;
import it.arsinfo.gc.repository.CommessaDao;
import it.arsinfo.gc.repository.VariazioneCommessaDao;

@SpringUI(path=GestioneCommesseUI.URL_VARIAZIONE_COMMESSE)
@Title("Variazione Commesse")
public class VariazioneCommessaUI extends GestioneCommesseHeaderUI {

    ComboBox<Commessa> filterCommessa;
    TextField filter = new TextField();

    Grid<VariazioneCommessa> grid;
    @Autowired
    VariazioneCommessaDao repo;
    @Autowired
    CommessaDao commessaDao;
    /**
     * 
     */
    private static final long serialVersionUID = -659806613407638574L;

    @Override
    protected void init(VaadinRequest request) {
        super.init(request);
        Assert.notNull(repo, "repo must be not null");
        Button addNewBtn = new Button("Nuova Variazione Commessa", VaadinIcons.PLUS);
        List<Commessa> comms = commessaDao.findAll();
        
        filterCommessa = new ComboBox<Commessa>("Selezionare la Commessa");
        filterCommessa.setItemCaptionGenerator(Commessa::getNome);
        filterCommessa.setItems(comms);
        filterCommessa.setEmptySelectionAllowed(true);
        filterCommessa.setPlaceholder("Cerca per Commessa");
        filterCommessa.addSelectionListener(e-> list(filter.getValue(),e.getSelectedItem().get()));

        VariazioneCommessaEditor editor = new VariazioneCommessaEditor(repo,comms);
        editor.setWidth("80%");
        HorizontalLayout actions = new HorizontalLayout(filter,filterCommessa,addNewBtn);
        grid = new Grid<>(VariazioneCommessa.class);
        grid.setWidth("80%");
        grid.setColumns("id", "commessa.nome", "importo","inizio","fine","descr");
        
        addComponents(editor,actions, grid);
        
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> list(e.getValue(),filterCommessa.getValue()));

        grid.asSingleSelect().addValueChangeListener(e -> {
                editor.edit(e.getValue());
        });

        addNewBtn.addClickListener(e -> editor.edit(new VariazioneCommessa()));

        editor.setChangeHandler(() -> {
                editor.setVisible(false);
                list(filter.getValue(),filterCommessa.getValue());
        });
        list(null,null);
        
    }

    private void list(String value, Commessa commessa) {
        if (StringUtils.isEmpty(value) && commessa == null) {
            grid.setItems(repo.findAll());
            return;
        }
        Set<VariazioneCommessa> listaitem = new HashSet<>();
        listaitem.addAll(repo.findByDescrStartsWithIgnoreCase(value));
        listaitem.addAll(repo.findByCommessa(commessa));
        grid.setItems(listaitem);    
    }

    @Override
    protected Label getTitleLabel() {
        Label header = new Label("Variazioni Commesse");
        header.addStyleName(ValoTheme.LABEL_H2);
        return header;
    }

}
