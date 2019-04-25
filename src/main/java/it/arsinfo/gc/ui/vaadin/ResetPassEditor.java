package it.arsinfo.gc.ui.vaadin;


import org.springframework.security.crypto.password.PasswordEncoder;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationResult;
import com.vaadin.data.Validator;
import com.vaadin.data.ValueContext;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;import com.vaadin.ui.PasswordField;

import it.arsinfo.gc.entity.UserInfo;
import it.arsinfo.gc.repository.UserInfoDao;
import it.arsinfo.gc.ui.security.SecurityUtils;


public class ResetPassEditor extends GCEditor<UserInfo> {

    /**
     * 
     */
    private static final long serialVersionUID = 3564093851452528938L;
    /**
     * 
     */
    private final PasswordField password = new PasswordField("password");
    private final PasswordField confirm = new PasswordField("confirm");
    private final Button resetPassword = new Button("Reset Password");

    public ResetPassEditor(UserInfoDao repo, PasswordEncoder passwordEncoder) {
        super(repo, new Binder<>(UserInfo.class));
        addComponents(new HorizontalLayout(password,confirm),
                      resetPassword
                      );
        
        getBinder().forField(password).withValidator(passwordValidator)
        .bind(
              bean -> "",
                 (bean, value) -> {
                                 if (value.isEmpty()) {
                                     } else {
                                 bean.setPasswordHash(passwordEncoder.encode(value));
                                     }
                                 });
        resetPassword.addClickListener(e->resetPassword());
    }
    
    private void resetPassword() {
        if (!password.isEmpty() && !SecurityUtils.verify(password.getValue())) {
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

        super.save();
        Notification.show("Password aggiornata",Notification.Type.HUMANIZED_MESSAGE);
        password.clear();
        confirm.clear();
    }

    @Override 
    public void save() {
        return;
    }

    @Override
    public void focus(boolean persisted, UserInfo obj) {
        resetPassword.setEnabled(!obj.isLocked());
    }
    
    private Validator<String> passwordValidator = new Validator<String>() {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        BeanValidator passwordBeanValidator = new BeanValidator(UserInfo.class, "passwordHash");

        @Override
        public ValidationResult apply(String value, ValueContext context) {
            if (value.isEmpty()) {
                return ValidationResult.ok();
            }
            
            if (!SecurityUtils.verify(password.getValue())) {
                Notification.show("Il reset della Password è fallito",
                                  "la password deve avere minimo 8 caratteri, contenere almeno un numero, almeno un carattere minuscolo, almeno un carattere maiuscolo e almeno nun carattere speciale",
                                  Notification.Type.HUMANIZED_MESSAGE);
                return ValidationResult.error("la password deve avere minimo 8 caratteri, contenere almeno un numero, almeno un carattere minuscolo, almeno un carattere maiuscolo e almeno nun carattere speciale");
            }
            return passwordBeanValidator.apply(value, context);
        }
};


}
