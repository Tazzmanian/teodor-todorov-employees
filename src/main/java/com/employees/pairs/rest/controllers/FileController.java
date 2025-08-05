package com.employees.pairs.rest.controllers;

import com.employees.pairs.rest.services.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
//@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/files")
public class FileController {

    @Qualifier("RestFileService")
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("upload")
    public String loadFile(@RequestParam("file") MultipartFile file, @RequestParam(defaultValue = ";", name = "delimiter") char delimiter) {
        log.info(file.toString());
        return fileService.getPairs(file, delimiter);
    }
}
