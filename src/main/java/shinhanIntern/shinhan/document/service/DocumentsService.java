package shinhanIntern.shinhan.document.service;

import shinhanIntern.shinhan.document.dto.DocumentsDto;
import shinhanIntern.shinhan.document.dto.SendDocumentForm;

import java.util.List;

public interface DocumentsService {

    List<DocumentsDto> getDocuments(Long id, int role);

    String saveDocument(SendDocumentForm sendDocumentForm);
}
