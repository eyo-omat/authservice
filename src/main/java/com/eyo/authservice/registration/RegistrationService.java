package com.eyo.authservice.registration;

import com.eyo.authservice.appuser.AppUser;
import com.eyo.authservice.appuser.AppUserRole;
import com.eyo.authservice.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private EmailValidator emailValidator;

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
}
