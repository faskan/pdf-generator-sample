package com.techroots.pdf.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class Html2PdfConverterFactory {

    @Inject
    FlyingSaucerHtml2PdfConverter flyingSaucerHtml2PdfConver;

    public Html2PdfConverter defaultConverter() {
        return flyingSaucerHtml2PdfConver;
    }
}
