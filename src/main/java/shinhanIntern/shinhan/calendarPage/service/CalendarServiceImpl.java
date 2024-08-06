package shinhanIntern.shinhan.calendarPage.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import shinhanIntern.shinhan.calendarPage.domain.Schedules;
import shinhanIntern.shinhan.calendarPage.domain.SchedulesRepository;
import shinhanIntern.shinhan.calendarPage.dto.*;
import shinhanIntern.shinhan.user.domain.UserRepository;

import java.time.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class CalendarServiceImpl implements CalendarService {
    private final SchedulesRepository schedulesRepository;
    @Override
    public List<CalendarDto> getCalendars(CalendarReqForm calendarReqForm) {
        List<CalendarDto> calendarDtoList = new ArrayList<>();
        List<Schedules> schedulesList;

        Long userId = calendarReqForm.getId();
        YearMonth yearMonth = YearMonth.of(calendarReqForm.getYear(), calendarReqForm.getMonth());
        // 해당 월의 첫 날과 마지막 날 생성
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        if(calendarReqForm.getRole()==0)
            schedulesList = schedulesRepository.findAllByPbId(userId);
        else
            schedulesList = schedulesRepository.findAllByCustomId(userId);

        // Create a map to count schedules for each date
        Map<LocalDate, Integer> dateCountMap = new HashMap<>();

        for (Schedules schedule : schedulesList) {
            LocalDate scheduleDate = schedule.getDayTime().toLocalDate();
            if (checkInPeriod(startDate, endDate, scheduleDate)) {
                dateCountMap.put(scheduleDate, dateCountMap.getOrDefault(scheduleDate, 0) + 1);
            }
        }

        // Add only the dates with counts to the calendarDtoList
        for (Map.Entry<LocalDate, Integer> entry : dateCountMap.entrySet()) {
            LocalDate date = entry.getKey();
            int count = entry.getValue();
            calendarDtoList.add(new CalendarDto(date, count));
        }

        return calendarDtoList;
    }

    @Override
    public String saveSchedule(SaveScheduleForm saveScheduleForm) {
        Schedules newSchedule;
        LocalDateTime newDateTime = LocalDateTime.of(saveScheduleForm.getDate(), saveScheduleForm.getTime());

        boolean isScheduleExists = schedulesRepository.existsByDayTime(newDateTime);
        if (isScheduleExists) {
            throw new DateTimeException("시간중복");
        }

        if(saveScheduleForm.getRole()==0){
            newSchedule = Schedules.builder()
                    .pbId(saveScheduleForm.getId())
                    .dayTime(newDateTime)
                    .scheduleName(saveScheduleForm.getScheduleName())
                    .scheduleDescription(saveScheduleForm.getScheduleDescription())
                    .schedulePlace(saveScheduleForm.getSchedulePlace())
                    .build();
        }
        else{
            newSchedule = Schedules.builder()
                    .customId(saveScheduleForm.getId())
                    .dayTime(newDateTime)
                    .scheduleName(saveScheduleForm.getScheduleName())
                    .scheduleDescription(saveScheduleForm.getScheduleDescription())
                    .schedulePlace(saveScheduleForm.getSchedulePlace())
                    .build();
        }

        schedulesRepository.save(newSchedule);

        return "일정추가완료";
    }

    @Override
    public List<LocalTime> getEnableTime(CheckTimeForm checkTimeForm) {
        return null;
    }

    @Override
    public List<SchedulesDto> getDaySchedules(DayReqForm dayReqForm) {
        List<SchedulesDto> schedulesDtoList = new ArrayList<>();
        List<Schedules> schedulesList;

        Long userId = dayReqForm.getId();
        LocalDate todayDate = dayReqForm.getToday();

        if(dayReqForm.getRole()==0)
            schedulesList = schedulesRepository.findAllByPbId(userId);
        else
            schedulesList = schedulesRepository.findAllByCustomId(userId);

        for (Schedules schedule : schedulesList) {
            LocalDate tmpDate = schedule.getDayTime().toLocalDate();
            if(todayDate.isEqual(tmpDate))
                schedulesDtoList.add(new SchedulesDto(
                        schedule.getDayTime(),
                        schedule.getScheduleName(),
                        schedule.getScheduleDescription(),
                        schedule.getSchedulePlace(),
                        schedule.getPbId(),
                        schedule.getCustomId()
                ));
        }
        return schedulesDtoList;
    }


    public boolean checkInPeriod(LocalDate startDate, LocalDate endDate, LocalDate tmpDate){
        return (tmpDate.isEqual(startDate) || tmpDate.isAfter(startDate)) && (tmpDate.isEqual(endDate) || tmpDate.isBefore(endDate));
    }

}
