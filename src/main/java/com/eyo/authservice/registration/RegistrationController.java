package com.eyo.authservice.registration;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

    private RegistrationService registrationService;

    @PostMapping
    public String register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);

    }

    @GetMapping("/confirm/{token}")
    public String confirmToken(@PathVariable String token) {
        return registrationService.confirmToken(token);
    }
}
