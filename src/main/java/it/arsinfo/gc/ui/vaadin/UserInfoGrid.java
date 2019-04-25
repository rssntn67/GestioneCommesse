package it.arsinfo.gc.ui.vaadin;

import com.vaadin.ui.Grid;

import it.arsinfo.gc.entity.UserInfo;


public class UserInfoGrid extends GCGrid<UserInfo> {


    /**
     * 
     */
    private static final long serialVersionUID = -2113139265485731072L;

    public UserInfoGrid(String gridname) {
        super(new Grid<>(UserInfo.class),gridname);

        setColumns("username","role","data");

    }
    
}
