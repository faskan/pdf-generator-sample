package com.techroots.pdf.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PdfRequestVo {
    private Object pdfRequestBody;
    private String templateName;
    private String fileName;
}
