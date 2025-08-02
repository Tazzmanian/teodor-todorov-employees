package com.employees.pairs.service;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface FileServiceUI {
    void populateModelWithPairs(RedirectAttributes model, MultipartFile file, char delimiter);
}
