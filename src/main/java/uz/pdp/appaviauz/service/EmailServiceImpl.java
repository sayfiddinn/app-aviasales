package uz.pdp.appaviauz.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import uz.pdp.appaviauz.exception.ConflictException;
import uz.pdp.appaviauz.payload.EmailDetails;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    @Value("${spring.mail.username}")
    String sender;
    final JavaMailSender javaMailSender;

    @Override
    public void sendSimpleMail(EmailDetails details) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(sender);
        mailMessage.setTo(details.getRecipient());
        mailMessage.setText(details.getMsgBody());
        mailMessage.setSubject(details.getSubject());

        javaMailSender.send(mailMessage);

    }

    @Override
    public void sendSimpleMailForAuth(EmailDetails details, String code) throws MessagingException {
//        String htmlContent = "<div style=\"text-align: center\">" +
//                "<h1>Hisobingiz deyarli tayyor</h1><br>\n" +
//                "<p>Hisobingizni faollashtirish uchun uni yaratishni boshlagan sahifaga\n" +
//                "    <br> ushbu tasdiq kodini nusxalab qoʻying:</p>\n" +
//                "<h1 style=\"font-size: 50px\"><mark> &ensp;" + code + "&ensp;</mark></h1>\n" +
//                "<p>Quyidagi havolani bosish orqali hisobingizni faollashtirishingiz mumkin:</p>\n" +
//                "<a href=\"" + details.getMsgBody() + "\">\n" +
//                "    <h2><mark> Profilni faollashtirish</mark></h2> </a><br>\n" +
//                "<p>Agar bu email bilan hisob yarata olmasangiz, bizga xabar bering.</p><br></div>";
        String htmlContent = "<div style=\"text-align: center;align-items: center\">\n" +
                "<h4 style=\"font-size:24px;font-weight:bold;line-height:1.2;\n" +
                "margin:0 0 32px 0;color:#002f34;\">Hisobingiz deyarli tayyor </h4>\n" +
                "<p>Hisobingizni faollashtirish uchun uni yaratishni boshlagan sahifaga\n" +
                "<br> ushbu tasdiq kodini nusxalab qoʻying:</p><h1 style=\"\n" +
                "display:inline-block;background:#e9fcfb;padding:18px;border-radius:4px;\n" +
                "font-weight:bold;letter-spacing:8px;width: 20%;\">" + code + "</h1>\n" +
                "<p>Quyidagi havolani bosish orqali hisobingizni faollashtirishingiz mumkin:</p>\n" +
                "<a href=\"" + details.getMsgBody() + "\"\n" +
                "style=\"color:#ffffff;display:inline-block;background-color:#002f34;\n" +
                "padding:15px 18px;font-weight:bold;border-radius:4px;width:15%\">\n" +
                "Profilni faollashtirish </a><br><p>Agar bu email bilan \n" +
                "hisobini yarata olmasangiz, bizga xabar bering.</p><br></div>";
        MimeMessage message = javaMailSender.createMimeMessage();
        message.setFrom(new InternetAddress(sender));
        message.setRecipients(MimeMessage.RecipientType.TO, details.getRecipient());
        message.setSubject("Hisobingizni faollashtiring");
        message.setContent(htmlContent, "text/html; charset=utf-8");
        javaMailSender.send(message);
    }
}
