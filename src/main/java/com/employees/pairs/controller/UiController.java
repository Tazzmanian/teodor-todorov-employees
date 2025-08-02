package com.employees.pairs.controller;


import com.employees.pairs.service.FileServiceUI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UiController {

    private final FileServiceUI fileService;

    @GetMapping("/")
    public String files (Model model) {
        return "file";
    }

    @PostMapping("/file/upload")
    public String uploadImage(RedirectAttributes model, @RequestParam("file") MultipartFile file,
                              @RequestParam(value = "delimiter", defaultValue = ";") char delimiter) {

        fileService.populateModelWithPairs(model, file, delimiter);

        return "redirect:/";
    }
}
