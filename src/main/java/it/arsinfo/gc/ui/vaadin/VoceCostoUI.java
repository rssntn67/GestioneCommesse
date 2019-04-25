package it.arsinfo.gc.ui.vaadin;

import java.util.HashSet;
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
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

import it.arsinfo.gc.entity.VoceCosto;
import it.arsinfo.gc.repository.VoceCostoDao;

@SpringUI(path=GCUI.URL_VOCECOSTO)
@Title("Voci di Costo")
public class VoceCostoUI extends GCUI {

    Grid<VoceCosto> grid;
    @Autowired
    VoceCostoDao repo;
    /**
     * 
     */
    private static final long serialVersionUID = -659806613407638574L;

    @Override
    protected void init(VaadinRequest request) {
        super.init(request);
        Assert.notNull(repo, "repo must be not null");
        Button addNewBtn = new Button("Nuova Voce di Costo", VaadinIcons.PLUS);
        TextField filter = new TextField();
        
        VoceCostoEditor editor = new VoceCostoEditor(repo);
        editor.setWidth("80%");
        HorizontalLayout actions = new HorizontalLayout(filter,addNewBtn);
        grid = new Grid<>(VoceCosto.class);
        grid.setWidth("80%");
        grid.setColumns("id", "voce","descr");
        
        addComponents(editor,actions, grid);
        
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> list(e.getValue()));

        grid.asSingleSelect().addValueChangeListener(e -> {
                editor.edit(e.getValue());
        });

        addNewBtn.addClickListener(e -> editor.edit(new VoceCosto()));

        editor.setChangeHandler(() -> {
                editor.setVisible(false);
                list(filter.getValue());
        });
        list(null);
        
    }

    private void list(String value) {
        if (StringUtils.isEmpty(value)) {
            grid.setItems(repo.findAll());
            return;
        }
        Set<VoceCosto> listaitem = new HashSet<>();
        listaitem.addAll(repo.findByDescrStartsWithIgnoreCase(value));
        listaitem.addAll(repo.findByVoceStartsWithIgnoreCase(value));
        grid.setItems(listaitem);    
    }

    @Override
    protected Label getTitleLabel() {
        Label header = new Label("Voci di Costo");
        header.addStyleName(ValoTheme.LABEL_H2);
        return header;
    }

}
