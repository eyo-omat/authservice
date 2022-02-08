package com.eyo.authservice.registration.token;

import com.eyo.authservice.appuser.AppUser;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class ConfirmationToken {

    @Id
    @SequenceGenerator(
            name = "confirmation_token_sequence",
            sequenceName = "confirmation_token_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "confirmation_token_sequence")
    private Long id;

    @NonNull
    @Column(nullable = false)
    private String token;

    @NonNull
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @NonNull
    private LocalDateTime expiresAt;

    public LocalDateTime confirmedAt;

    @NonNull
    @ManyToOne
    @JoinColumn(nullable = false, name = "app_user_id")
    private AppUser appUser;
}
