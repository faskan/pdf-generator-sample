package com.techroots.pdf.service;

import io.quarkus.qute.Engine;
import io.quarkus.qute.Template;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

@ApplicationScoped
public class HTMLTemplateParser {

    @Inject
    Engine engine;

    public String parseHtmlTemplate(String templateUrl, Object pdfRequest) throws IOException { //quarkus qute template
        String out = new Scanner(new URL(templateUrl).openStream(), "UTF-8").useDelimiter("\\A").next();
        Template template = engine.parse(out);
        return template.data("invoice", pdfRequest).render();
    }
}
