package shinhanIntern.shinhan.mainPage.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import shinhanIntern.shinhan.mainPage.domain.Awards;
import shinhanIntern.shinhan.mainPage.domain.PbAwardRepository;
import shinhanIntern.shinhan.mainPage.domain.PbListView;
import shinhanIntern.shinhan.mainPage.domain.PbPortpolioRepository;
import shinhanIntern.shinhan.mainPage.domain.PbUserRepository;
import shinhanIntern.shinhan.mainPage.domain.PbViewListRepository;
import shinhanIntern.shinhan.mainPage.domain.Portpolios;
import shinhanIntern.shinhan.mainPage.dto.PbDetailDto;
import shinhanIntern.shinhan.mainPage.dto.PbUserDto;
import shinhanIntern.shinhan.user.domain.OfficeRepository;
import shinhanIntern.shinhan.user.domain.Offices;
import shinhanIntern.shinhan.user.domain.Users;

@Service
@AllArgsConstructor
public class PbUserServiceImpl implements PbUserService {
    private final PbUserRepository pbUserRepository;
    private final PbAwardRepository pbAwardRepository;
    private final PbPortpolioRepository pbPortpolioRepository;
    private final OfficeRepository officeRepository;
    private final PbViewListRepository pbViewListRepository;
    private static final Map<Integer, String> CATEGORY_MAP = new HashMap<>();
    static {
        CATEGORY_MAP.put(0, "증권");
        CATEGORY_MAP.put(1, "연금");
        CATEGORY_MAP.put(2, "채권");
        CATEGORY_MAP.put(3, "파생");
    }

    public Users findByEmail() {
        Users user = pbUserRepository.findByEmail("test@naver.com")
            .orElseThrow(()-> new NullPointerException("User not found"));
        return user;
    }

    @Override
    public List<PbUserDto> getPbAll() {
        List<Users> pbList = pbUserRepository.findAllByRole(0);

        if (pbList.isEmpty()) {
            throw new NullPointerException("User not found");
        }

        return pbList.stream()
            .map(user -> {
                Offices office = officeRepository.findById(user.getOfficeId())
                    .orElseThrow(()-> new NullPointerException("오피스 아이디 잘못됨")); // officeId를 이용해 오피스 정보 조회
                PbUserDto dto = toPbUserDto(user, office);
                return dto;
            })
            .collect(Collectors.toList());
    }

    @Override
    public List<PbListView> getPbView() {
        List<PbListView> pbListView = pbViewListRepository.findAll();
        if (pbListView.isEmpty()) {
            throw new NullPointerException("User not found");
        }
        return pbListView;
    }

    @Override
    public List<PbListView> getPbViewToCategory(int category) {
        String categoryString = CATEGORY_MAP.get(category);
        List<PbListView> pbListView = pbViewListRepository.findAllByCategory(categoryString);
        if (pbListView.isEmpty()) {
            throw new NullPointerException("User not found");
        }
        return pbListView;
    }

    @Override
    public PbDetailDto getPbDetail(Long pbId) {
        Users pbUser = pbUserRepository.findById(pbId)
            .orElseThrow(()-> new NullPointerException("User not found"));
        List<Portpolios> portpolios = pbPortpolioRepository.findAllByPbId(pbId);
        List<Awards> awards = pbAwardRepository.findAllByPbId(pbId);
        Offices office = officeRepository.findById(pbUser.getOfficeId())
            .orElseThrow(()-> new NullPointerException("Office not found"));

        return new PbDetailDto(pbUser, portpolios, awards, office);
    }


    public PbUserDto toPbUserDto(Users user, Offices office) {
        PbUserDto pbUserDto = new PbUserDto(
            user.getId(), user.getName(), user.getEmail(),user.getPassword(), user.getCash(), user.getRole(), user.getPhoto(), user.getCategory(), office
        );
        return pbUserDto;
    }
}
