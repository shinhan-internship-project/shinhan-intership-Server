package shinhanIntern.shinhan.document;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shinhanIntern.shinhan.calendarPage.dto.SchedulesDto;
import shinhanIntern.shinhan.document.dto.DocumentsDto;
import shinhanIntern.shinhan.document.dto.SendDocumentForm;
import shinhanIntern.shinhan.document.service.DocumentsService;
import shinhanIntern.shinhan.utils.ApiUtils;
import shinhanIntern.shinhan.utils.ApiUtils.ApiResult;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/documents")
@AllArgsConstructor
public class DocumentRestController {
    private final DocumentsService documentsService;

    @GetMapping("/pb/{id}")
    public ApiResult<List<DocumentsDto>> getPbDocumentsList(@PathVariable("id") Long pbId){
        try{
            List<DocumentsDto> documentsDtoList = documentsService.getDocuments(pbId,0);
            return ApiUtils.success(documentsDtoList);
        }catch(NullPointerException e){
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/customer/{id}")
    public ApiResult<List<DocumentsDto>> getCustomerDocumentsList(@PathVariable("id") Long customerId){
        try{
            List<DocumentsDto> documentsDtoList = documentsService.getDocuments(customerId,1);
            return ApiUtils.success(documentsDtoList);
        }catch(NullPointerException e){
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/send")
    public ApiUtils.ApiResult<String> sendDocument(
            @Valid
            @RequestBody SendDocumentForm sendDocumentForm
    ){
        try{
            String message = documentsService.saveDocument(sendDocumentForm);
            return ApiUtils.success(message);
        }catch(Exception e){
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
