package com.techroots.pdf.service;

import com.techroots.pdf.vo.PdfRequestVo;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.ByteArrayOutputStream;

@ApplicationScoped
public class PdfService {

    @Inject
    FileStorageService awsS3Service;

    @Inject
    HTMLTemplateParser htmlTemplateParser;

    @Inject
    Html2PdfConverterFactory html2PdfConverterFactory;

    public String createNewPdf(PdfRequestVo pdfRequestVo) throws Exception {
        ByteArrayOutputStream outputStream = outputStream(pdfRequestVo.getTemplateName(), pdfRequestVo.getPdfRequestBody());
        awsS3Service.saveFile(pdfRequestVo.getFileName(), outputStream);
        return pdfRequestVo.getFileName();
    }

    private ByteArrayOutputStream outputStream(String template, Object pdfRequest) throws Exception {
        String html = htmlTemplateParser.parseHtmlTemplate(template, pdfRequest);
        return html2PdfConverterFactory.defaultConverter().htmlToPdf(html);
    }
}
