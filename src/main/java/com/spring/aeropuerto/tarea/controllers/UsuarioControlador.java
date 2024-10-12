package com.spring.aeropuerto.tarea.controllers;

import com.spring.aeropuerto.tarea.entity.Usuario;
import com.spring.aeropuerto.tarea.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class UsuarioControlador {

    private static String rutaSubida = "uploads/";

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/usuarios")
    public String listar(Model model) {
        model.addAttribute("usuarios", usuarioService.listarTodos());
        return "usuarios";
    }

    @GetMapping("/usuarios/nuevo")
    public String nuevo(Model model){
        model.addAttribute("usuario", new Usuario());
        return "formulario_usuario";
    }

    @PostMapping("/usuarios")
    public String guardarUsuario(@ModelAttribute("usuario") Usuario usuario,
                                  @RequestParam("file") MultipartFile file,
                                  RedirectAttributes redirectAttributes) {
        if (!file.isEmpty()) {
            try {
                // Guardar la imagen en el servidor
                byte[] bytes = file.getBytes();
                Path path = Paths.get(rutaSubida + file.getOriginalFilename());
                Files.write(path, bytes);

                // Guardar la URL de la imagen en el producto
                usuario.setImagenUrl("/" + rutaSubida + file.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        usuarioService.guardar(usuario);
        redirectAttributes.addFlashAttribute("mensaje", "Usuario guardado exitosamente");
        return "redirect:/usuarios";
    }

    @GetMapping("/usuarios/editar/{id}")
    public String editarUsuario(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioService.obtenerPorId(id);
        model.addAttribute("usuario", usuario);
        return "formulario_usuario";
    }

    @GetMapping("/usuarios/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        usuarioService.eliminar(id);
        redirectAttributes.addFlashAttribute("mensaje", "Usuario eliminado exitosamente");
        return "redirect:/usuarios";
    }

}

