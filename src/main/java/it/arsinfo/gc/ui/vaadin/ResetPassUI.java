package it.arsinfo.gc.ui.vaadin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;

import it.arsinfo.gc.repository.UserInfoDao;

@SpringUI(path=GCUI.URL_RESET)
@Title("Reset Password")
public class ResetPassUI extends GCUI {

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
        ResetPassEditor editor = new ResetPassEditor(userInfoDao,passwordEncoder);
        
        addComponents(editor);
        
        editor.setChangeHandler(() -> {
        });
        
        editor.edit(getLoggedInUser());
        
    }

    @Override
    protected Label getTitleLabel() {
        Label header = new Label("Reset Pass");
        header.addStyleName(ValoTheme.LABEL_H2);
        return header;
    }
}
