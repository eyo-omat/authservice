package com.eyo.authservice.email;

public interface EmailSender {

    void send(String to, String email);
}
