package shinhanIntern.shinhan.document.service;

import shinhanIntern.shinhan.document.dto.DocumentsDto;

import java.util.List;

public interface DocumentsService {

    List<DocumentsDto> getDocuments(Long id, int role);
}
