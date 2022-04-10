package com.techroots.pdf;

import com.techroots.pdf.mapper.PdfRequestMapper;
import com.techroots.pdf.service.PdfService;
import com.techroots.pdf.vo.PdfRequestVo;
import io.quarkus.amazon.lambda.http.model.AwsProxyRequest;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

@Path("/pdf")
public class PdfGenerationResource {

    @Inject
    PdfService pdfService;
    @Inject
    PdfRequestMapper pdfRequestMapper;

    @POST
    @Path("/generate")
    @Produces(MediaType.APPLICATION_JSON)
    public GeneratePdfResponse generate(@Context AwsProxyRequest request,
                           @Context SecurityContext securityContext) {
        try {
            PdfRequestVo pdfRequestVo = pdfRequestMapper.toPdfRequestVo(request, securityContext);
            return GeneratePdfResponse.builder()
                    .filePath(pdfService.createNewPdf(pdfRequestVo))
                    .status("OK")
                    .build();
        } catch (Exception e) {
            return GeneratePdfResponse.builder()
                    .status("NOK")
                    .build();
        }
    }

}
