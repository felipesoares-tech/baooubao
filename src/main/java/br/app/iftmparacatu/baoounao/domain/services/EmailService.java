package br.app.iftmparacatu.baoounao.domain.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class EmailService {


    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom("baoounaoiftm@hotmail.com"); // Certifique-se de usar o mesmo e-mail autenticado
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true); // O segundo parâmetro "true" indica que o conteúdo é HTML

        mailSender.send(message);
    }
    public void enviarEmailDeConfirmacao(String destinatario, String nome, String urlDeConfirmacao) throws MessagingException {
        String assunto = "Confirme seu E-mail";
        String corpoEmail = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "</head>\n" +
                "<body style=\"margin: 0; padding: 0; background-color: #f4f4f4; font-family: Arial, sans-serif;\">\n" +
                "    <table align=\"center\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"\n" +
                "        style=\"max-width: 600px; background-color: #ffffff; margin: 20px auto; border-radius: 8px; overflow: hidden;\">\n" +
                "        <tr>\n" +
                "            <td style=\"padding: 20px; text-align: center; background-color: #28B76B; color: #ffffff;\">\n" +
                "                <h1 style=\"margin: 0; font-size: 24px;\">Confirme seu E-mail</h1>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td style=\"padding: 20px;\">\n" +
                "                <div style=\"display: flex; justify-content: center; flex-direction: column; align-items: center;\">\n" +
                "                    <h1 style=\"margin: 0; font-size: 24px;\">Bão ou Não</h1>\n" +
                "                    <img src=\"https://baoounao.iftmparacatu.app.br/assets/images/BaoOuNao.png\" alt=\"logo\"\n" +
                "                        style=\"width: 60px;\">\n" +
                "                </div>\n" +
                "                <p style=\"margin: 0; font-size: 16px; line-height: 1.5;\">Olá " + nome + ",</p>\n" +
                "                <p style=\"margin: 20px 0; font-size: 16px; line-height: 1.5;\">Obrigado por se cadastrar em nosso site!\n" +
                "                    Para completar seu cadastro, por favor, confirme seu e-mail clicando no botão abaixo.</p>\n" +
                "                <p style=\"margin: 20px 0; text-align: center;\">\n" +
                "                    <a href=\"" + urlDeConfirmacao + "\"\n" +
                "                        style=\"display: inline-block; padding: 10px 20px; font-size: 16px; color: #ffffff; background-color: #28B76B; text-decoration: none; border-radius: 4px;\">Confirmar\n" +
                "                        E-mail</a>\n" +
                "                </p>\n" +
                "                <p style=\"margin: 20px 0; font-size: 16px; line-height: 1.5;\">Se você não se cadastrou em nosso site,\n" +
                "                    por favor ignore este e-mail.</p>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td style=\"padding: 20px; text-align: center; background-color: #f4f4f4;\">\n" +
                "                <p style=\"margin: 0; font-size: 14px; color: #888888;\">&copy; 2024 Bão ou Não. Todos os direitos\n" +
                "                    reservados.</p>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "    </table>\n" +
                "</body>\n" +
                "</html>";

        sendEmail(destinatario, assunto, corpoEmail);
    }

    public void sendEmailWithAttachment(String to, String subject, String body) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body);

        FileSystemResource file = new FileSystemResource(new File("anexo.jpg"));
        helper.addAttachment("anexo.jpg", file);

        mailSender.send(message);
    }
}
