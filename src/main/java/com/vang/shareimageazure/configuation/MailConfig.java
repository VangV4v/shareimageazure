package com.vang.shareimageazure.configuation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    private static final String HOST = "smtp.gmail.com";
    private static final int PORT = 587;
    private static final String USERNAME = "replyminimicroservice@gmail.com";
    private static final String PASSWORD = "utxz djfo ieya egfu";
    private static final String KEY_MAIL_PROTOCOL = "mail.transport.protocol";
    private static final String VALUE_MAIL_PROTOCOL = "smtp";
    private static final String KEY_MAIL_AUTH = "mail.smtp.auth";
    private static final String VALUE_MAIL_AUTH = "true";
    private static final String KEY_MAIL_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
    private static final String VALUE_MAIL_STARTTLS_ENABLE = "true";
    private static final String KEY_MAIL_DEBUG = "mail.debug";
    private static final String VALUE_MAIL_DEBUG = "true";

    @Bean
    public JavaMailSender mailSender() {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(HOST);
        mailSender.setPort(PORT);

        mailSender.setUsername(USERNAME);
        mailSender.setPassword(PASSWORD);

        Properties props = mailSender.getJavaMailProperties();
        props.put(KEY_MAIL_PROTOCOL, VALUE_MAIL_PROTOCOL);
        props.put(KEY_MAIL_AUTH, VALUE_MAIL_AUTH);
        props.put(KEY_MAIL_STARTTLS_ENABLE, VALUE_MAIL_STARTTLS_ENABLE);
        props.put(KEY_MAIL_DEBUG, VALUE_MAIL_DEBUG);
        return mailSender;
    }
}
