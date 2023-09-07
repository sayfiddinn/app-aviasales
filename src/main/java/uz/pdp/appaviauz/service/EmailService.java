package uz.pdp.appaviauz.service;

import jakarta.mail.MessagingException;
import uz.pdp.appaviauz.payload.EmailDetails;

public interface EmailService {
    void sendSimpleMail(EmailDetails details);

    void sendSimpleMailForAuth(EmailDetails details, String code) throws MessagingException;
}
