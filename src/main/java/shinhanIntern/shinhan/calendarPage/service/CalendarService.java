package shinhanIntern.shinhan.calendarPage.service;

import shinhanIntern.shinhan.calendarPage.dto.CalendarDto;
import shinhanIntern.shinhan.calendarPage.dto.CalendarReqForm;
import shinhanIntern.shinhan.calendarPage.dto.DayReqForm;
import shinhanIntern.shinhan.calendarPage.dto.SchedulesDto;

import java.util.List;

public interface CalendarService {

    List<SchedulesDto> getDaySchedules(DayReqForm dayReqForm);

    List<CalendarDto> getCalendars(CalendarReqForm calendarReqForm);
}
