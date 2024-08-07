package shinhanIntern.shinhan.mainPage.domain;


import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Builder
public class PbListView {
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
}
