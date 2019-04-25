package it.arsinfo.gc.ui.vaadin;

import com.vaadin.icons.VaadinIcons;

import it.arsinfo.gc.entity.GCEntity;

public abstract class GCAdd<T extends GCEntity>
        extends GCButton {

    /**
     * 
     */
    private static final long serialVersionUID = 5891798901681193924L;

    public GCAdd(String caption) {
        super(caption, VaadinIcons.PLUS);

    }

    public abstract T generate();
    
}
