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
    public List<DocumentsDto> getDocuments(Long id) {
        List<Documents> documentsList = documentsRepository.findAllByPbId(id);
        List<DocumentsDto> documentsDtoList = new ArrayList<>();

        for (Documents document : documentsList) {
            String reqUserName = findByName(document.getUserId());

            DocumentsDto dto = DocumentsDto.builder()
                    .name(reqUserName)
                    .content(document.getContent())
                    .reservationDate(document.getReservationDate())
                    .build();
            documentsDtoList.add(dto);
        }

        return documentsDtoList;
    }
}
