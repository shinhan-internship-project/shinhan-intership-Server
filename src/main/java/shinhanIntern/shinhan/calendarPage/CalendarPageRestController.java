package shinhanIntern.shinhan.calendarPage;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import shinhanIntern.shinhan.calendarPage.dto.CalendarDto;
import shinhanIntern.shinhan.calendarPage.dto.CalendarReqForm;
import shinhanIntern.shinhan.calendarPage.dto.DayReqForm;
import shinhanIntern.shinhan.calendarPage.dto.SchedulesDto;
import shinhanIntern.shinhan.calendarPage.service.CalendarService;
import shinhanIntern.shinhan.utils.ApiUtils;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/calendar")
@AllArgsConstructor
public class CalendarPageRestController {

    private final CalendarService calendarService;
    @PostMapping("")
    public ApiUtils.ApiResult<List<CalendarDto>> getScheduleList(
            @Valid
            @RequestBody CalendarReqForm calendarReqForm
            ){
        try{
            List<CalendarDto> getSchedulesDtoList = calendarService.getCalendars(calendarReqForm);
            return ApiUtils.success(getSchedulesDtoList);
        }catch(NullPointerException e){
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/today")
    public ApiUtils.ApiResult<List<SchedulesDto>> getDaySchdules(
            @Valid
            @RequestBody DayReqForm dayReqForm
    ){
        try{
            List<SchedulesDto> getSchedulesDtoList = calendarService.getDaySchedules(dayReqForm);
            return ApiUtils.success(getSchedulesDtoList);
        }catch(NullPointerException e){
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}