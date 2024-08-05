package shinhanIntern.shinhan.document.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import shinhanIntern.shinhan.document.domain.*;
import shinhanIntern.shinhan.document.dto.DocumentsDto;
import shinhanIntern.shinhan.user.domain.UserRepository;
import shinhanIntern.shinhan.user.domain.Users;
import shinhanIntern.shinhan.user.dto.UsersDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DocumentsServiceImpl implements DocumentsService {
    private final DocumentsRepository documentsRepository;
    private final UserRepository userRepository;

    public Users findByEmail(String email) {
        Users user = userRepository.findByEmail(email)
            .orElseThrow(()-> new NullPointerException("User not found"));
        return user;
    }

    public String findByName(Long id) {
        Users foundUser = userRepository.findById(id)
                .orElseThrow(()-> new NullPointerException("User not found"));
        String foundName = foundUser.getName();
        return foundName;
    }

    @Override
    public List<DocumentsDto> getDocuments(Long id, int role) {
        List<Documents> documentsList;
        if(role==0)
            documentsList = documentsRepository.findAllByPbId(id);
        else
            documentsList = documentsRepository.findAllByUserId(id);
        List<DocumentsDto> documentsDtoList = new ArrayList<>();

        for (Documents document : documentsList) {
            String reqName;
            if(role==0)
                reqName = findByName(document.getUserId());
            else
                reqName = findByName(document.getPbId());

            DocumentsDto dto = DocumentsDto.builder()
                    .name(reqName)
                    .content(document.getContent())
                    .reservationDate(document.getReservationDate())
                    .build();
            documentsDtoList.add(dto);
        }

        return documentsDtoList;
    }
}
