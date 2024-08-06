package shinhanIntern.shinhan.mainPage.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import shinhanIntern.shinhan.mainPage.domain.Awards;
import shinhanIntern.shinhan.mainPage.domain.PbAwardRepository;
import shinhanIntern.shinhan.mainPage.domain.PbPortpolioRepository;
import shinhanIntern.shinhan.mainPage.domain.PbUserRepository;
import shinhanIntern.shinhan.mainPage.domain.Portfolios;
import shinhanIntern.shinhan.mainPage.dto.PbDetailDto;
import shinhanIntern.shinhan.mainPage.dto.PbUserDto;
import shinhanIntern.shinhan.user.domain.Users;

@Service
@AllArgsConstructor
public class PbUserServiceImpl implements PbUserService {
    private final PbUserRepository pbUserRepository;
    private final PbAwardRepository pbAwardRepository;
    private final PbPortpolioRepository pbPortpolioRepository;

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

        List<PbUserDto> resultList = new ArrayList<>();

        return pbList.stream()
            .map(this::toPbUserDto)  // convertToDto 메서드를 사용하여 변환
            .collect(Collectors.toList());  // 변환된 리스트를 수집
    }

    @Override
    public PbDetailDto getPbDetail(Long pbId) {
        Users pbUser = pbUserRepository.findById(pbId)
            .orElseThrow(()-> new NullPointerException("User not found"));
        List<Portfolios> portpolios = pbPortpolioRepository.findAllByPbId(pbId);
        List<Awards> awards = pbAwardRepository.findAllByPbId(pbId);


        return new PbDetailDto(pbUser, portpolios, awards);
    }


    public PbUserDto toPbUserDto(Users user) {
        PbUserDto pbUserDto = new PbUserDto(
            user.getId(), user.getName(), user.getEmail(),user.getPassword(), user.getCash(), user.getRole(), user.getPhoto(), user.getCategory()
        );
        return pbUserDto;
    }
}
