package shinhanIntern.shinhan.mainPage.service;

import java.util.List;
import shinhanIntern.shinhan.mainPage.domain.PbListView;
import shinhanIntern.shinhan.mainPage.dto.PbDetailDto;
import shinhanIntern.shinhan.mainPage.dto.PbUserDto;

public interface PbUserService {

    public PbDetailDto getPbDetail(Long pbId);

    public List<PbUserDto> getPbAll();

    List<PbListView> getPbView();

    List<PbListView> getPbViewToCategory(int category);
}
