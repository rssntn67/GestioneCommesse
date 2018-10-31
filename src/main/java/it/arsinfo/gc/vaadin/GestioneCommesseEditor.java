package it.arsinfo.gc.vaadin;

import com.vaadin.ui.VerticalLayout;

public abstract class GestioneCommesseEditor extends VerticalLayout {

    private ChangeHandler changeHandler;
    
    public interface ChangeHandler {
        void onChange();
    }
    
    public void setChangeHandler(ChangeHandler object) {
        changeHandler = object;
    }
    
    public void onchange() {
        changeHandler.onChange();
    }

}
