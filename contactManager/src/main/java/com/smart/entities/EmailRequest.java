package com.smart.entities;

public class EmailRequest {
    private String subject;
    private String to;

    private String message;

    public EmailRequest(String subject, String to, String message) {
        this.subject = subject;
        this.to = to;
        this.message = message;
    }

    public EmailRequest() {
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "EmailRequest{" +
                "subject='" + subject + '\'' +
                ", to='" + to + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
