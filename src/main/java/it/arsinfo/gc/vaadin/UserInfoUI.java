package it.arsinfo.gc.vaadin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.vaadin.annotations.Title;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;

import it.arsinfo.gc.entity.UserInfo;
import it.arsinfo.gc.repository.UserInfoDao;

@SpringUI(path=GestioneCommesseUI.URL_USER)
@Title("User")
public class UserInfoUI extends GestioneCommesseHeaderUI {

    Grid<UserInfo> grid;
    @Autowired
    UserInfoDao repo;
    /**
     * 
     */
    private static final long serialVersionUID = -659806613407638574L;

    @Override
    protected void init(VaadinRequest request) {
        super.init(request);
        Assert.notNull(repo, "repo must be not null");
        Button addNewBtn = new Button("Nuovo User", VaadinIcons.USERS);
        
        UserInfoEditor editor = new UserInfoEditor(repo);
        editor.setWidth("80%");
        HorizontalLayout actions = new HorizontalLayout(addNewBtn);
        grid = new Grid<>(UserInfo.class);
        grid.setWidth("80%");
        grid.setColumns("id", "username","role", "data");
        
        addComponents(editor,actions, grid);
        
        grid.asSingleSelect().addValueChangeListener(e -> {
                editor.edit(e.getValue());
        });

        addNewBtn.addClickListener(e -> editor.edit(new UserInfo()));

        editor.setChangeHandler(() -> {
                editor.setVisible(false);
                list();
        });
        list();
        
    }

    private void list() {
            grid.setItems(repo.findAll());
    }

    @Override
    protected Label getTitleLabel() {
        Label header = new Label("Users");
        header.addStyleName(ValoTheme.LABEL_H2);
        return header;
    }

}
