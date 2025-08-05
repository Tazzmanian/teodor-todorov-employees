package com.employees.pairs.rest.services;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String getPairs(MultipartFile file, char delimiter);
}
