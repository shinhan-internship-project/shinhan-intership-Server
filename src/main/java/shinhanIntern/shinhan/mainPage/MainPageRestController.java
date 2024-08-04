package shinhanIntern.shinhan.mainPage;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shinhanIntern.shinhan.mainPage.dto.PbUserDto;
import shinhanIntern.shinhan.mainPage.service.PbUserService;
import shinhanIntern.shinhan.utils.ApiUtils;
import shinhanIntern.shinhan.utils.ApiUtils.ApiResult;

@Slf4j
@RestController
@RequestMapping("/api/mainPage")
@AllArgsConstructor
public class MainPageRestController {
    private final PbUserService pbUserService;

    @GetMapping("/pbList")
    public ApiResult<List<PbUserDto>> getPbList(){
        try{
            List<PbUserDto> pbList = pbUserService.getPbAll();
            return ApiUtils.success(pbList);
        }catch(NullPointerException e){
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
