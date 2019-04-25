package it.arsinfo.gc.ui.vaadin;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import it.arsinfo.gc.entity.UserInfo;
import it.arsinfo.gc.entity.UserInfo.Role;
import it.arsinfo.gc.repository.UserInfoDao;
import it.arsinfo.gc.ui.security.SecurityUtils;

public abstract class GCUI extends UI {

    @Autowired
    private UserInfoDao userInfoDao;

    private UserInfo loggedInUser;

    /**
     * 
     */
    public final static String APP_URL = "/";
    public final static String URL_LOGIN = "/login.html";
    public final static String URL_LOGIN_PROCESSING = "/login";
    public final static String URL_LOGIN_FAILURE = "/login.html?error";
    public final static String URL_LOGOUT = "/login.html?logout";
    public final static String URL_USER="/user";
    public final static String URL_COMMESSE="/commesse";
    public final static String URL_RESOCONTI="/resoconti";
    public final static String URL_VOCECOSTO="/vocecosto";
    public final static String URL_VARIAZIONE_COMMESSE="/variazionecommesse";
    public final static String URL_RESET = "/reset";
    
    private static final long serialVersionUID = 679939857396325190L;
    private VerticalLayout layout = new VerticalLayout();

    @Override
    protected void init(VaadinRequest request) {
        loggedInUser = SecurityUtils.getCurrentUser(userInfoDao);
        layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        layout.addComponents(new HorizontalLayout(getHeaderLinks()),
                             getTitleLabel());
        setContent(layout);
    }

    protected abstract Label getTitleLabel();

    protected void addComponents(Component... components ) {
        layout.addComponents(components);
    }
    
    public Link[] getHeaderLinks() {
        List<Link> links = new ArrayList<Link>();
        UserInfo loggedInUser = SecurityUtils.getCurrentUser(userInfoDao);
        links.add(new Link("Commesse", new ExternalResource(URL_COMMESSE)));
        links.add(new Link("Resoconti", new ExternalResource(URL_RESOCONTI)));
        links.add(new Link("Variazioni Commesse", new ExternalResource(URL_VARIAZIONE_COMMESSE)));
        links.add(new Link("Voci di Costo", new ExternalResource(URL_VOCECOSTO)));
        if (loggedInUser.getRole() == Role.ADMIN ) {
            links.add(new Link("Amministrazione Utenti", new ExternalResource(URL_USER)));
        } 
        if (!loggedInUser.isLocked()) {
            links.add(new Link("Reset Password", new ExternalResource(URL_RESET)));
        }
        links.add(new Link(String.format("Logout: %s",loggedInUser.getUsername()),
                         new ExternalResource(URL_LOGOUT)));
        return links.toArray((new Link[links.size()]));
    }
    
    public UserInfo getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(UserInfo loggedInUser) {
        this.loggedInUser = loggedInUser;
    }


}
