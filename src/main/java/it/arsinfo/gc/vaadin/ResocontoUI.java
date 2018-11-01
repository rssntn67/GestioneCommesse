package it.arsinfo.gc.vaadin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.vaadin.annotations.Title;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;

import it.arsinfo.gc.entity.Commessa;
import it.arsinfo.gc.entity.Resoconto;
import it.arsinfo.gc.entity.VoceCosto;
import it.arsinfo.gc.repository.CommessaDao;
import it.arsinfo.gc.repository.ResocontoDao;
import it.arsinfo.gc.repository.VoceCostoDao;

@SpringUI(path=GestioneCommesseUI.URL_RESOCONTI)
@Title("Resoconti")
public class ResocontoUI extends GestioneCommesseHeaderUI {

    ComboBox<Commessa> filterCommessa;
    ComboBox<VoceCosto> filterVoceCosto;

    Grid<Resoconto> grid;
    @Autowired
    ResocontoDao repo;
    @Autowired
    CommessaDao commessaDao;
    @Autowired 
    VoceCostoDao voceCostoDao;
    /**
     * 
     */
    private static final long serialVersionUID = -659806613407638574L;

    @Override
    protected void init(VaadinRequest request) {
        super.init(request);
        Assert.notNull(repo, "repo must be not null");
        Button addNewBtn = new Button("Aggiungi Resoconto", VaadinIcons.PLUS);
        List<Commessa> comms = commessaDao.findAll();
        List<VoceCosto> voces = voceCostoDao.findAll();
        
        filterCommessa = new ComboBox<Commessa>("Selezionare la Commessa");
        filterCommessa.setItemCaptionGenerator(Commessa::getNome);
        filterCommessa.setItems(comms);
        filterCommessa.setEmptySelectionAllowed(true);
        filterCommessa.setPlaceholder("Cerca per Commessa");
        filterCommessa.addSelectionListener(e-> list(e.getSelectedItem().get(),filterVoceCosto.getValue()));

        filterVoceCosto = new ComboBox<VoceCosto>("Selezionare la Voce di Costo");
        filterVoceCosto.setItemCaptionGenerator(VoceCosto::getVoce);
        filterVoceCosto.setItems(voces);
        filterVoceCosto.setEmptySelectionAllowed(true);
        filterVoceCosto.setPlaceholder("Cerca per Voce Costo");
        filterVoceCosto.addSelectionListener(e-> list(filterCommessa.getValue(),e.getSelectedItem().get()));

        ResocontoEditor editor = new ResocontoEditor(repo, comms,voces);
        editor.setWidth("80%");
        HorizontalLayout actions = new HorizontalLayout(filterCommessa,filterVoceCosto,addNewBtn);
        grid = new Grid<>(Resoconto.class);
        grid.setWidth("80%");
        grid.setColumns("id", "commessa.nome", "voceCosto.voce","importo","tipologia","data");
        
        addComponents(editor,actions, grid);
        
        grid.asSingleSelect().addValueChangeListener(e -> {
                editor.edit(e.getValue());
        });

        addNewBtn.addClickListener(e -> editor.edit(new Resoconto()));

        editor.setChangeHandler(() -> {
                editor.setVisible(false);
                list(filterCommessa.getValue(),filterVoceCosto.getValue());
        });
        list(null,null);
        
    }

    private void list(Commessa commessa, VoceCosto voceCosto) {
        if (commessa == null && voceCosto == null) {
            grid.setItems(repo.findAll());
            return;
        }
        Set<Resoconto> listaitem = new HashSet<>();
        if (commessa != null) {
            listaitem.addAll(repo.findByCommessa(commessa));
        }
        if (voceCosto != null) {
            listaitem.addAll(repo.findByVoceCosto(voceCosto));
        }
        grid.setItems(listaitem);    
    }

    @Override
    protected Label getTitleLabel() {
        Label header = new Label("Resoconti");
        header.addStyleName(ValoTheme.LABEL_H2);
        return header;
    }

}
