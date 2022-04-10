package com.techroots.pdf.service;

import java.io.ByteArrayOutputStream;

public interface Html2PdfConverter {
    ByteArrayOutputStream htmlToPdf(String inputHTML) throws Exception;
}
