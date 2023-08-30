package com.example.Pastebin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/texts")
public class TextController {


    private TextRepository textRepository;

    @Autowired
    public TextController(TextRepository textRepository) {
        this.textRepository = textRepository;
    }

    @GetMapping("")
    public String getText(Model model) {
        model.addAttribute("texts", new Text());
        return "index";
    }

    @PostMapping("")
    public String getResult() {
        return "index";
    }

    @GetMapping("/{id}")
    public String getAText(Model model, @PathVariable("id") Long id) {
        Text text = textRepository.findById(id).orElseThrow();
        model.addAttribute("text", text);
        return "value";
    }

    @PostMapping("/{id}")
    public String createAText(Model model, @PathVariable("id") Long id) {
        Text text = textRepository.findById(id).orElseThrow();
        model.addAttribute("text", text);
        return "value";
    }

    @GetMapping("/list")
    public String display(Model model) {
        model.addAttribute("texts", textRepository.findAll());
        return "display";
    }

    public void createTitleValue(Text text) {
        String title = "";
        String[] name = text.getName().split("\\s+");
        int nrWords = name.length;
        if (nrWords >= 10) {
            for (int i = 0; i < 10; ++i) {
                title += name[i].charAt(0);
            }
            text.setTitle(title);
        } else {
            text.setTitle(text.getName());
        }
    }

    @PostMapping("/list")
    public String postDisplay(@Valid Text text, Model model) {
        createTitleValue(text);
        textRepository.save(text);
        model.addAttribute("texts", textRepository.findAll());
        return "display";
    }

    @PostMapping("/result")
    public String postTheValue(@Valid Text text, Model model) {
        model.addAttribute("text", text);
        createTitleValue(text);
        textRepository.save(text);
        return "redirect:/texts/" + text.getId() + "";
    }
}
