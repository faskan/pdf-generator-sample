package com.techroots.pdf.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techroots.pdf.vo.PdfRequestVo;
import io.quarkus.amazon.lambda.http.model.AwsProxyRequest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.SecurityContext;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

import static java.lang.String.format;

@ApplicationScoped
public class PdfRequestMapper {

    private static final String DEFAULT_TEMPLATE = "https://raw.githubusercontent.com/faskan/mbiz-invoice-templates/main/default-qute-template.html";

    @Inject
    ObjectMapper objectMapper;

    public PdfRequestVo toPdfRequestVo(AwsProxyRequest proxyRequest, SecurityContext securityContext) throws IOException {
        return PdfRequestVo.builder()
                .fileName(fileName(getFileNameFromHeaders(proxyRequest), getUserId(securityContext)))
                .templateName(getTemplateFromHeaders(proxyRequest))
                .pdfRequestBody(getPdfRequestBody(proxyRequest))
                .build();
    }

    private Object getPdfRequestBody(AwsProxyRequest requestEvent) throws IOException {
        if (requestEvent.isBase64Encoded()) {
            byte[] decode = Base64.getDecoder().decode(requestEvent.getBody());
            return objectMapper.readerFor(Object.class).readValue(new String(decode));
        }
        return objectMapper.readerFor(Object.class).readValue(requestEvent.getBody());
    }

    private String getTemplateFromHeaders(AwsProxyRequest proxyRequest) {
        if (proxyRequest.getMultiValueHeaders().containsKey("x-template")) {
            return proxyRequest.getMultiValueHeaders().get("x-template").get(0);
        }
        return DEFAULT_TEMPLATE;
    }

    private Optional<String> getUserId(SecurityContext context) {
        if (context.getUserPrincipal() != null &&
                context.getUserPrincipal().getName() != null) {
            return Optional.of(context.getUserPrincipal().getName());
        }
        return Optional.empty();
    }

    private String getFileNameFromHeaders(AwsProxyRequest requestEvent) {
        if (requestEvent.getMultiValueHeaders().containsKey("x-filename")) {
            return requestEvent.getMultiValueHeaders().get("x-filename").get(0);
        }
        return "defaultFileName";
    }

    private String fileName(String fileName, Optional<String> _userId) {
        return _userId.isPresent() ? format("%s/%s.pdf", _userId.get(), fileName)
                : fileName;
    }
}
