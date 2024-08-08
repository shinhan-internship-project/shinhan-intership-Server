package shinhanIntern.shinhan.mainPage;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import shinhanIntern.shinhan.mainPage.domain.PbListView;
import shinhanIntern.shinhan.mainPage.domain.SearchDto;
import shinhanIntern.shinhan.mainPage.domain.PbListViewNew;
import shinhanIntern.shinhan.mainPage.dto.PbDetailDto;
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
    public ApiResult<Page<PbListViewNew>> list(
            @RequestParam(value = "distance", defaultValue = "false") boolean isDistance,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<PbListViewNew> pbListViewNew = pbUserService.getPbView(isDistance, pageable);
            return ApiUtils.success(pbListViewNew);
        } catch (NullPointerException e) {
            return ApiUtils.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/pbList/{category}")
    public ApiResult<Page<PbListViewNew>> listToCategory(
            @PathVariable int category,
		    @RequestParam(value = "distance", defaultValue = "false") boolean isDistance,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        try{
            Pageable pageable = PageRequest.of(page, size);
            Page<PbListViewNew> pbListViewNew = pbUserService.getPbViewToCategory(category,isDistance,pageable);
            return ApiUtils.success(pbListViewNew);
        }catch(NullPointerException e){
            return ApiUtils.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/pbListNoUseThis")
    public ApiResult<List<PbUserDto>> getPbList(){
        try{
            List<PbUserDto> pbList = pbUserService.getPbAll();
            return ApiUtils.success(pbList);
        }catch(NullPointerException e){
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/{pbId}")
    public ApiResult<PbDetailDto> getPbById(@PathVariable("pbId") Long pbId){
        try{
            PbDetailDto result = pbUserService.getPbDetail(pbId);
            return ApiUtils.success(result);
        }catch(NullPointerException e){
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/searchPb")
    public ApiResult<List<PbListViewNew>> searchPb(@RequestBody SearchDto searchDto){
        try{
            List<PbListViewNew> searchedList = pbUserService.searchKeyword(searchDto.getKeyword());
            return ApiUtils.success(searchedList);
        }catch (NullPointerException e){
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
