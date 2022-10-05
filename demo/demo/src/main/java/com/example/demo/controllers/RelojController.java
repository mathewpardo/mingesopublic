package com.example.demo.controllers;

import com.example.demo.services.RelojService;
import com.example.demo.services.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;

@Controller
@RequestMapping(value = "/reloj")
public class RelojController {

    @Autowired
    private UploadService uploadService;
    @Autowired
    private RelojService relojService;

    @GetMapping("/")
    public String home(){
        return "home";
    }

    @PostMapping("/importar")
    public String carga(@RequestParam("archivos") MultipartFile file, RedirectAttributes ms){
        if(Objects.equals(file.getOriginalFilename(),"DATOS.TXT")) {
            uploadService.save(file);
            relojService.read(file);
            ms.addFlashAttribute("mensaje", "Archivo guardado correctamente");
        }else{
            ms.addFlashAttribute("mensaje", "Archivo no ha podido ser guardado");
        }
        return "redirect:/planillas/ver";
    }
}