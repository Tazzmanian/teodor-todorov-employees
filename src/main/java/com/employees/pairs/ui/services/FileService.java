package com.employees.pairs.ui.services;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface FileService {
    void populateModelWithPairs(RedirectAttributes model, MultipartFile file, char delimiter);
}
