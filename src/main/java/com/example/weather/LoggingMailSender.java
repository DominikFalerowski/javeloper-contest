package com.example.weather;

class LoggingMailSender implements MailSender {

    @Override
    public void sendMail(Weather weather) {
        System.out.println("Sending mail with data = " + weather);
    }
}
