package it.arsinfo.gc.ui.vaadin;

import it.arsinfo.gc.entity.UserInfo;

public class UserInfoAdd extends GCAdd<UserInfo> {

    /**
     * 
     */
    private static final long serialVersionUID = -7367794517035138478L;

    public UserInfoAdd(String caption) {
        super(caption);
    }
    
    @Override
    public UserInfo generate() {
        return new UserInfo();
    }

}
