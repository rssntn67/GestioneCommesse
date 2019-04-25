package it.arsinfo.gc.ui.vaadin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;

import it.arsinfo.gc.entity.UserInfo;
import it.arsinfo.gc.repository.UserInfoDao;


@SpringUI(path=GCUI.URL_USER)
@Title("Amministrazione Utenti")
public class UserInfoUI extends GCUI {

    Grid<UserInfo> grid;
    @Autowired
    private UserInfoDao userInfoDao;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    /**
     * 
     */
    private static final long serialVersionUID = -659806613407638574L;

    @Override
    protected void init(VaadinRequest request) {
        super.init(request);
        UserInfoAdd add = new UserInfoAdd("Aggiungi Utente");
        UserInfoGrid grid = new UserInfoGrid("Users");
        UserInfoEditor editor = new UserInfoEditor(userInfoDao,passwordEncoder);
        
        addComponents(add,editor, grid);
        editor.setVisible(false);
        
        add.setChangeHandler(() -> {
            editor.edit(add.generate());
        });

        grid.setChangeHandler(() ->{
            if (grid.getSelected() == null) {
                editor.setVisible(false);
            } else {
                editor.edit(grid.getSelected());
            }

        }) ;
        editor.setChangeHandler(() -> {
                editor.setVisible(false);
                grid.populate(userInfoDao.findAll());
        });
        grid.populate(userInfoDao.findAll());
        
    }

    @Override
    protected Label getTitleLabel() {
        Label header = new Label("Admin user");
        header.addStyleName(ValoTheme.LABEL_H2);
        return header;
    }

}
