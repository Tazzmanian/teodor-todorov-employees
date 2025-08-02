package com.employees.pairs.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileServiceRest {
    String getPairs(MultipartFile file, char delimiter);
}
