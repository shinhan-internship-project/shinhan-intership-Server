package shinhanIntern.shinhan.calendarPage.service;

import shinhanIntern.shinhan.calendarPage.dto.*;

import java.time.LocalTime;
import java.util.List;

public interface CalendarService {

    List<SchedulesDto> getDaySchedules(DayReqForm dayReqForm);

    List<CalendarDto> getCalendars(CalendarReqForm calendarReqForm);

    String saveSchedule(SaveScheduleForm saveScheduleForm);

    List<LocalTime> getEnableTime(CheckTimeForm checkTimeForm);
}
