package com.eyo.authservice.registration;

import com.eyo.authservice.appuser.AppUser;
import com.eyo.authservice.appuser.AppUserRole;
import com.eyo.authservice.appuser.AppUserService;
import com.eyo.authservice.registration.token.ConfirmationToken;
import com.eyo.authservice.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private EmailValidator emailValidator;
    private ConfirmationTokenService confirmationTokenService;

    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());


        if (!isValidEmail) {
            throw new IllegalStateException("email not valid: " + request.getEmail());
        }
        return appUserService.signUpUser(new AppUser(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPassword(),
                AppUserRole.USER
        ));
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }
        confirmationTokenService.setConfirmedAt(token);
        appUserService.enableAppUser(confirmationToken.getAppUser().getEmail());
        return "confirmed";
    }
}
