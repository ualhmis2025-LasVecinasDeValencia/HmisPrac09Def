package com.example.application.views.registro;

import com.example.application.data.User;
import com.example.application.data.UserRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



@AnonymousAllowed

@PageTitle("Registro")
@Route(value = "registro")
public class RegistroView extends VerticalLayout {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final TextField usuario = new TextField("Usuario");
    private final PasswordField password = new PasswordField("Contraseña");
    private final Button registrarBtn = new Button("Registrar");

    @Autowired
    public RegistroView(UserRepository userRepository) {
        this.userRepository = userRepository;

        registrarBtn.addClickListener(e -> registrarUsuario());

        add(usuario, password, registrarBtn);
    }

    private void registrarUsuario() {
        String usuarioValor = usuario.getValue().trim();
        String passwordValor = password.getValue().trim();

        if (usuarioValor.isEmpty() || passwordValor.isEmpty()) {
           Notification n1 = Notification.show("Debe rellenar usuario y contraseña");
           n1.setPosition(Notification.Position.TOP_CENTER); // o BOTTOM_START, MIDDLE, etc.

            return;
        }

        boolean existe = userRepository.findAll().stream()
                .anyMatch(u -> u.getUsername().equalsIgnoreCase(usuarioValor));

        if (existe) {
          
        	Notification n = Notification.show("El usuario ya existe");
        	n.setPosition(Notification.Position.TOP_CENTER); // o BOTTOM_START, MIDDLE, etc.
        	
        	
        } else {
            User nuevo = new User();
            nuevo.setUsername(usuarioValor);
            nuevo.setHashedPassword(passwordEncoder.encode(passwordValor)); // encriptado
            userRepository.save(nuevo);
            Notification n2 = Notification.show("Usuario registrado correctamente");
            n2.setPosition(Notification.Position.TOP_CENTER); // o BOTTOM_START, MIDDLE, etc.

            usuario.clear();
            password.clear();
        }
    }
}
