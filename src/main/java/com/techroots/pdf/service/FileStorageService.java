package com.techroots.pdf.service;

import java.io.ByteArrayOutputStream;

public interface FileStorageService {
    void saveFile(String fileName, ByteArrayOutputStream outputStream);
}
