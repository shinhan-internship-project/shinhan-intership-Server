package shinhanIntern.shinhan.calendarPage.service;

import shinhanIntern.shinhan.calendarPage.dto.*;

import java.util.List;

public interface CalendarService {

    List<SchedulesDto> getDaySchedules(DayReqForm dayReqForm);

    List<CalendarDto> getCalendars(CalendarReqForm calendarReqForm);

    String saveSchedule(SaveScheduleForm saveScheduleForm);
}
