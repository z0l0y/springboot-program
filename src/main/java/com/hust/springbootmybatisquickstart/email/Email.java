package com.hust.springbootmybatisquickstart.email;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {
    public static void main(String[] args) {
        // 实例化EmailService，并配置相关信息
        EmailService emailService = new EmailService();
        emailService.setSmtpHost("smtp.qq.com");  // 设置SMTP服务器地址
        emailService.setSmtpPort(465);  // 设置SMTP端口号
        emailService.setFromEmail("");  // 设置发件人邮箱
        emailService.setEmailPassword("");  // 设置邮箱密码

        // 调用sendConfirmationEmail方法发送邮件
        String recipientEmail = "";  // 收件人邮箱地址
        String subject = "注册验证";
        String body = "您以成功注册！";
        emailService.sendConfirmationEmail(recipientEmail, subject, body);
    }
}

class EmailService {
    private String smtpHost;
    private int smtpPort;
    private String fromEmail;
    private String emailPassword;

    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    public void setSmtpPort(int smtpPort) {
        this.smtpPort = smtpPort;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public void setEmailPassword(String emailPassword) {
        this.emailPassword = emailPassword;
    }

    public void sendConfirmationEmail(String recipientEmail, String subject, String body) {
        Properties props = new Properties();
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, emailPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

            System.out.println("邮件发送成功");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}