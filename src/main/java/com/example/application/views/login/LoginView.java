package com.example.application.views.login;

import com.example.application.security.AuthenticatedUser;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@AnonymousAllowed
@PageTitle("Login")
@Route("login")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private final LoginForm loginForm = new LoginForm();

    public LoginView(AuthenticatedUser authenticatedUser) {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        authenticatedUser.get().ifPresent(user ->
            getUI().ifPresent(ui -> ui.navigate(""))
        );

        loginForm.setAction("login");

        LoginI18n i18n = LoginI18n.createDefault();
        LoginI18n.Header header = new LoginI18n.Header();
        header.setTitle("Mi Aplicación");
        header.setDescription("Introduce tus credenciales");
        i18n.setHeader(header);
        loginForm.setI18n(i18n);

        Anchor enlaceRegistro = new Anchor("registro", "¿No tienes cuenta? Regístrate aquí");
        enlaceRegistro.getStyle().set("margin-top", "1rem");

        add(loginForm, enlaceRegistro);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (event.getLocation().getQueryParameters().getParameters().containsKey("error")) {
          
            UI.getCurrent().access(() -> {
                Notification.show("Usuario no encontrado o contraseña incorrecta", 5000, Notification.Position.TOP_CENTER);
            });
        }
    }


   
    
}



