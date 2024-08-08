package shinhanIntern.shinhan.mainPage.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.micrometer.common.lang.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Builder
public class PbListViewNew {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private Long cash;

    @NotNull
    private int role;

    @NotNull
    private String category;

    @NotNull
    private String categoryDetail;

    @Nullable
    private String photo;

    @Nullable
    private String link;

    @Nullable
    private String pr;

    @Nullable
    private String certificate;

    @NotNull
    private String investType;

    @NotNull
    private int officeId;

    @NotNull
    private String officeName;

    @NotNull
    private String officeAddress;

    @NotNull
    private Double officeLatitude;

    @NotNull
    private Double officeLongitude;

    @NotNull
    private String officeRegion;

    @NotNull
    private Double officeDistance;
}
