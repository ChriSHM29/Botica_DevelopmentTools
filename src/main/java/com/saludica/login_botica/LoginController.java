package com.saludica.login_botica;

import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private record Cuenta(String clave, String rol) {}

    private static final Map<String, Cuenta> USUARIOS = Map.of(
        "admin", new Cuenta("admin123", "Administrador"),
        "caja01", new Cuenta("caja123", "Cajero"),
        "alm01", new Cuenta("alm123", "Almacenero")
    );

    @GetMapping("/login")
    public String mostrarFormulario() {
        return "login";
    }

    @PostMapping("/login")
    public String validar(
        @RequestParam("usuario") String usuario,
        @RequestParam("clave") String clave,
        Model model
    ) {
        Cuenta cuenta = USUARIOS.get(usuario);

        if (cuenta == null || !cuenta.clave().equals(clave)) {
            model.addAttribute("error", "Credenciales incorrectas");
            return "login";
        }

        model.addAttribute("usuario", usuario);
        model.addAttribute("rol", cuenta.rol());
        return "bienvenida";
    }
}