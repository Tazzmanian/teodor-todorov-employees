package com.employees.pairs.controller;

import com.employees.pairs.service.FileServiceRest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/files")
public class FileController {

    private final FileServiceRest fileService;

    @PostMapping("upload")
    public String loadFile(@RequestParam("file") MultipartFile file, @RequestParam(defaultValue = ";", name = "delimiter") char delimiter) {
        log.info(file.toString());
        return fileService.getPairs(file, delimiter);
    }
}
