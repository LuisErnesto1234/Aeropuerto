package com.spring.aeropuerto.tarea.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Controller
public class ImagenController {

    @GetMapping("/uploads/{filename}")
    public ResponseEntity<byte[]> getImagen(@PathVariable String filename) throws IOException {
        byte[] imagen = Files.readAllBytes(Paths.get("./uploads/" + filename));
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(imagen);
    }
}