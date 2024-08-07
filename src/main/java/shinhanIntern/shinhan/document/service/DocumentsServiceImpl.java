package shinhanIntern.shinhan.document.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import shinhanIntern.shinhan.calendarPage.domain.Schedules;
import shinhanIntern.shinhan.calendarPage.domain.SchedulesRepository;
import shinhanIntern.shinhan.calendarPage.dto.ScheduleListDto;
import shinhanIntern.shinhan.document.domain.*;
import shinhanIntern.shinhan.document.dto.DocumentsDto;
import shinhanIntern.shinhan.document.dto.SendDocumentForm;
import shinhanIntern.shinhan.user.domain.UserRepository;
import shinhanIntern.shinhan.user.domain.Users;
import shinhanIntern.shinhan.user.dto.UsersDto;
import shinhanIntern.shinhan.utils.ApiUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
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
                    .id(document.getId())
                    .name(reqName)
                    .content(document.getContent())
                    .reservationDate(document.getReservationDate())
                    .build();
            documentsDtoList.add(dto);
        }
        documentsDtoList.sort(Comparator.comparing(DocumentsDto::getReservationDate));
        return documentsDtoList;
    }

    @Override
    public String saveDocument(SendDocumentForm sendDocumentForm) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
        // Parse the time string to LocalTime
        LocalTime getTime = LocalTime.parse(sendDocumentForm.getTime(), formatter);
        LocalDateTime newDateTime = LocalDateTime.of(sendDocumentForm.getDate(),getTime);

        boolean isScheduleExists = schedulesRepository.findByPbIdAndDayTime(sendDocumentForm.getPbId(),newDateTime).isPresent();
        if (isScheduleExists) {
            throw new DateTimeException("시간중복");
        }

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
                .scheduleDescription(sendDocumentForm.getContent())
                .build();

        documentsRepository.save(newDocument);
        schedulesRepository.save(newSchedule);

        return "상담신청완료";
    }

}
