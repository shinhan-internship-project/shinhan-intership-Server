package shinhanIntern.shinhan.mainPage.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shinhanIntern.shinhan.mainPage.domain.PbListView;
import shinhanIntern.shinhan.mainPage.domain.PbListViewNew;
import shinhanIntern.shinhan.mainPage.dto.PbDetailDto;
import shinhanIntern.shinhan.mainPage.dto.PbUserDto;

public interface PbUserService {

    public PbDetailDto getPbDetail(Long pbId);

    public List<PbUserDto> getPbAll();

//    List<PbListView> getPbView(boolean isDistance);

    Page<PbListViewNew> getPbViewToCategory(int category, boolean isDistance,Pageable pageable, int type);


    List<PbListViewNew> searchKeyword(String keyword);

    Page<PbListViewNew> getPbView(boolean isDistance, Pageable pageable,int type);
}
