package com.example.application.views.login;

import com.example.application.security.AuthenticatedUser;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@AnonymousAllowed
@PageTitle("Login")
@Route("login")
public class LoginView extends VerticalLayout {

    public LoginView(AuthenticatedUser authenticatedUser) {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        authenticatedUser.get().ifPresent(user ->
            getUI().ifPresent(ui -> ui.navigate(""))
        );

        // Formulario de login
        LoginForm loginForm = new LoginForm();
        loginForm.setAction("login");

        // Personalizar texto del formulario
        LoginI18n i18n = LoginI18n.createDefault();
        LoginI18n.Header header = new LoginI18n.Header();
        header.setTitle("Mi Aplicación");
        header.setDescription("Introduce tus credenciales");
        i18n.setHeader(header);
        loginForm.setI18n(i18n);

        // Mostrar error si ?error
        getUI().ifPresent(ui -> {
            boolean hasError = ui.getInternals().getActiveViewLocation()
                .getQueryParameters().getParameters().containsKey("error");
            loginForm.setError(hasError);
        });

        // Enlace a registro
        Anchor enlaceRegistro = new Anchor("registro", "¿No tienes cuenta? Regístrate aquí");
        enlaceRegistro.getStyle().set("margin-top", "1rem");

        add(loginForm, enlaceRegistro);
    }
}

