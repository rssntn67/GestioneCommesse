package it.arsinfo.gc.vaadin;


import java.util.EnumSet;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.vaadin.data.Binder;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import it.arsinfo.gc.entity.UserInfo;
import it.arsinfo.gc.entity.UserInfo.Role;
import it.arsinfo.gc.repository.UserInfoDao;

public class UserInfoEditor extends GestioneCommesseEditor {

    private static String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,}$";
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    /**
     * 
     */
    private static final long serialVersionUID = -6636202872081273998L;
    private UserInfo userInfo;
    private final TextField username = new TextField("username");
    private final ComboBox<Role> role = new ComboBox<Role>("Selezionare il ruolo",EnumSet.allOf(Role.class));
    private final PasswordField password = new PasswordField("password");
    private final PasswordField confirm = new PasswordField("confirm");

    Button save = new Button("Save", VaadinIcons.CHECK);
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", VaadinIcons.TRASH);
    Button reset =  new Button("Reset Pass",VaadinIcons.ARCHIVE);
    Button resetP =  new Button("Reset Password",VaadinIcons.ARCHIVE);
    HorizontalLayout pri = new HorizontalLayout(username,role);
    VerticalLayout sec = new VerticalLayout(password,confirm,resetP);
    HorizontalLayout actions = new HorizontalLayout(save, reset, cancel, delete);

    Binder<UserInfo> binder = new Binder<>(UserInfo.class);
    private UserInfoDao repo;
    public UserInfoEditor(UserInfoDao repo) {
        super();
        this.repo = repo;
        addComponents(pri,sec,actions);
        setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        
        binder.forField(username).asRequired().bind("username");
        binder.forField(role).asRequired().bind("role");
        
        setSpacing(true);

        save.addStyleName(ValoTheme.BUTTON_PRIMARY);
        delete.addStyleName(ValoTheme.BUTTON_DANGER);

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> edit(userInfo));
        reset.addClickListener(e->password());
        resetP.addClickListener(e->resetPassword());

        setVisible(false);
    }

    private void delete() {
        repo.delete(userInfo);
        onchange();
    }

    private void password() {
        sec.setVisible(true);
        actions.setVisible(false);
    }
    
    private void resetPassword() {
        if (password.isEmpty()) {
            Notification.show("Il reset della Password è fallito",
                              "il campo password deve essere valorizzato",
                              Notification.Type.HUMANIZED_MESSAGE);
            return;
        }
        
        if (!password.getValue().matches(pattern)) {
            Notification.show("Il reset della Password è fallito",
                              "la password deve avere minimo 8 caratteri, contenere almeno un numero, almeno un carattere minuscolo, almeno un carattere maiuscolo e almeno nun carattere speciale",
                              Notification.Type.HUMANIZED_MESSAGE);
            return;
        }

        if (confirm.isEmpty())  {
            Notification.show("Il reset della Password è fallito",
                              "il campo confirm deve essere valorizzato",
                              Notification.Type.HUMANIZED_MESSAGE);
            return;
        }
        
        if (!confirm.getValue().equals(password.getValue())) {
            Notification.show("Il reset della Password è fallito",
                              "le password non corrispondono",
                              Notification.Type.HUMANIZED_MESSAGE);
            return;
        }
        userInfo.setPasswordHash(bCryptPasswordEncoder.encode(password.getValue()));
        repo.save(userInfo);
        onchange();  
        Notification.show("Password aggiornata",Notification.Type.HUMANIZED_MESSAGE);
    }

    private void save() {
        if (userInfo.getPasswordHash() ==  null) {
            userInfo.setPasswordHash(bCryptPasswordEncoder.encode("#DefaulT001!!"));
        }
        repo.save(userInfo);
        onchange();  
    }

    public void edit(UserInfo userd) {
        if (userd == null) {
            setVisible(false);
            return;
        }
        actions.setVisible(true);
        sec.setVisible(false);
        
        final boolean persisted = userd.getId() != null;
        if (persisted) {
            userInfo = repo.findById(userd.getId()).get();
        } else {
            userInfo = userd;
        }
        reset.setVisible(persisted);
        cancel.setVisible(persisted);
        binder.setBean(userInfo);

        setVisible(true);
        username.focus();
    }

}
