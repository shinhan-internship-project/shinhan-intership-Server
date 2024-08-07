package shinhanIntern.shinhan.mainPage.service;

import java.util.*;
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
    public List<PbListView> getPbView(boolean isDistance) {
        List<PbListView> pbListView = pbViewListRepository.findAll();
        if (pbListView.isEmpty()) {
            throw new NullPointerException("User not found");
        }

        if(isDistance){
            double currentLat = 37.52158432691758;
            double currentLon = 126.92291854507867;
            pbListView.sort(Comparator.comparingDouble(pb ->
                    calculateDistance(currentLat, currentLon, pb.getOfficeLatitude(), pb.getOfficeLongitude())
            ));
        }
        return pbListView;
    }

    @Override
    public List<PbListView> getPbViewToCategory(int category, boolean isDistance) {
        String categoryString = CATEGORY_MAP.get(category);
        List<PbListView> pbListView = pbViewListRepository.findAllByCategory(categoryString);
        if (pbListView.isEmpty()) {
            throw new NullPointerException("User not found");
        }

        if(isDistance){
            double currentLat = 37.52158432691758;
            double currentLon = 126.92291854507867;
            pbListView.sort(Comparator.comparingDouble(pb ->
                    calculateDistance(currentLat, currentLon, pb.getOfficeLatitude(), pb.getOfficeLongitude())
            ));
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

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int EARTH_RADIUS = 6371; // 지구 반경 (단위: km)

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }
}
