package shinhanIntern.shinhan.mainPage.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Builder
@ToString
@Getter
@Setter
@JsonSerialize
@JsonDeserialize
@AllArgsConstructor
public class PbUserDto {
    @NonNull
    private Long id;

    @NonNull
    @Column(nullable = false)
    private String name;

    @Email
    @NotBlank(message = "이메일이 없습니다.")
    private String email;

    @NonNull
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Long cash;

    @Column(nullable = false)
    private int role;

    @Nullable
    private String photo;

    @Nullable
    private String category;

    @Nullable
    private String link;

    @Nullable
    private String pr;

}
