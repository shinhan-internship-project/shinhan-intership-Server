package shinhanIntern.shinhan.document.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import shinhanIntern.shinhan.calendarPage.domain.Schedules;
import shinhanIntern.shinhan.calendarPage.domain.SchedulesRepository;
import shinhanIntern.shinhan.document.domain.*;
import shinhanIntern.shinhan.document.dto.DocumentsDto;
import shinhanIntern.shinhan.document.dto.SendDocumentForm;
import shinhanIntern.shinhan.user.domain.UserRepository;
import shinhanIntern.shinhan.user.domain.Users;
import shinhanIntern.shinhan.user.dto.UsersDto;
import shinhanIntern.shinhan.utils.ApiUtils;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Slf4j
@Service
@AllArgsConstructor
public class DocumentsServiceImpl implements DocumentsService {
    private final DocumentsRepository documentsRepository;
    private final UserRepository userRepository;
    private final SchedulesRepository schedulesRepository;

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

    @Override
    public String saveDocument(SendDocumentForm sendDocumentForm) {
        try{
            OffsetDateTime newDateTime = createOffsetDateTime(sendDocumentForm.getDate(), sendDocumentForm.getTime());

            Documents newDocument = Documents.builder()
                    .userId(sendDocumentForm.getUserId())
                    .pbId(sendDocumentForm.getPbId())
                    .content(sendDocumentForm.getContent())
                    .reservationDate(newDateTime)
                    .build();

            Schedules newSchedule = Schedules.builder()
                    .customId(sendDocumentForm.getUserId())
                    .pbId(sendDocumentForm.getPbId())
                    .dayTime(newDateTime)
                    .scheduleName("상담신청예약")
                    .build();

            documentsRepository.save(newDocument);
            schedulesRepository.save(newSchedule);

            return "상담신청완료";
        }catch (Exception e){
            return "신청오류";
        }
    }

    public OffsetDateTime createOffsetDateTime(LocalDate date, LocalTime time) {
        LocalDateTime localDateTime = LocalDateTime.of(date, time);
        ZoneOffset offset = ZoneOffset.UTC; // 필요한 경우 적절한 ZoneOffset 사용
        return OffsetDateTime.of(localDateTime, offset);
    }
}
