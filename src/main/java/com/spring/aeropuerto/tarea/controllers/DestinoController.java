package com.spring.aeropuerto.tarea.controllers;

import com.spring.aeropuerto.tarea.entity.Destino;
import com.spring.aeropuerto.tarea.entity.Usuario;
import com.spring.aeropuerto.tarea.service.DestinoService;
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
public class DestinoController {

    private static String rutaSubida = "uploads/";

    @Autowired
    private DestinoService destinoService;

    @GetMapping("/destinos")
    public String listar(Model model){
        model.addAttribute("destinos", destinoService.listarDestinos());
        return "destinos";
    }

    @GetMapping("/destinos/nuevo")
    public String nuevo(Model model){
        model.addAttribute("destino", new Destino());
        return "formulario_destino";
    }

    @PostMapping("/destinos")
    public String guardarDestino(@ModelAttribute("destino") Destino destino,
                                 @RequestParam("file") MultipartFile file,
                                 RedirectAttributes redirectAttributes) {
        if (!file.isEmpty()) {
            try {
                // Guardar la imagen en el servidor
                byte[] bytes = file.getBytes();
                Path path = Paths.get(rutaSubida + file.getOriginalFilename());
                Files.write(path, bytes);

                // Guardar la URL de la imagen en el producto
                destino.setImagenUrl("/" + rutaSubida + file.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        destinoService.guardarDestino(destino);
        redirectAttributes.addFlashAttribute("mensaje", "Destino guardado exitosamente");
        return "redirect:/destinos";
    }

    @GetMapping("/destinos/editar/{id}")
    public String editarDestino(@PathVariable Long id, Model model) {
        Destino destino = destinoService.obtenerDestinoPorId(id);
        model.addAttribute("destino", destino);
        return "formulario_destino";
    }

    @GetMapping("/destinos/eliminar/{id}")
    public String eliminarDestino(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        destinoService.eliminarDestino(id);
        redirectAttributes.addFlashAttribute("mensaje", "Destino eliminado exitosamente");
        return "redirect:/destinos";
    }

}
