package shinhanIntern.shinhan.utils.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import shinhanIntern.shinhan.user.domain.Users;
import shinhanIntern.shinhan.user.dto.FindUserDto;
import shinhanIntern.shinhan.user.dto.UsersDto;

import java.security.Key;
import java.util.Date;

@Slf4j
@PropertySource(value = {"application.properties"})
@Component
public class JwtProvider {

    @Value("${JWT_SECRET_KEY}")
    private String JWT_SECRET_KEY;
    private Key key;
    private long tokenValidTime = 30 * 60 * 1000L; // 30min

    // 시크릿 키 초기화
    @PostConstruct
    protected void init() {
//        key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        byte[] keyBytes = Decoders.BASE64URL.decode(JWT_SECRET_KEY);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // 토큰 생성
    public String createToken(Users user) {
        Claims claims = Jwts.claims();
        Date now = new Date();

        // Custom claims
        claims.put("id", user.getId());
        claims.put("role", user.getRole());
        claims.put("name", user.getName());

        // header에 들어갈 내용 및 서명을 하기 위한 SECRET_KEY
        // payload에 들어갈 내용
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidTime))
                .signWith(key)
                .compact();
    }

    // 토큰에서 userinfo 가져오기
    public FindUserDto getUser(String token) {
        int role = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().get("role", Integer.class);
        String name = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().get("name", String.class);
        Long id = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().get("id", Long.class);

        FindUserDto getUserInfo = new FindUserDto(id, name, role);
        return getUserInfo;
    }

}
