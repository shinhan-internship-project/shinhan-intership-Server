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

import java.security.Key;
import java.util.Date;

@Slf4j
@PropertySource(value = {"application.properties"})
@Component
public class JwtProvider {

    @Value("${JWT_SECRET_KEY}")
    private String JWT_SECRET_KEY;
    private Key key;
    private long tokenValidTime = 5 * 60 * 1000L; // 5min

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
        claims.put("email", user.getEmail());
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

}
