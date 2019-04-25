package it.arsinfo.gc.ui.vaadin;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;

public class GCButton extends GCChangeHandler {

    /**
     * 
     */
    private static final long serialVersionUID = -4810859843481273558L;
    private final Button button;

    public GCButton(String caption, VaadinIcons icon) {
        button = new Button(caption, icon);
        button.addClickListener(e -> onchange());
        addComponents(button);

    }
    
    public Button getButton() {
        return button;
    }

}
