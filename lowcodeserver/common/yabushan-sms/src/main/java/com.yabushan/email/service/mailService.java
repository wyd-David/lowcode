package com.yabushan.email.service;

public interface mailService {
    public void sendSimpleMail(String to, String subject, String content);

    public void sendHtml(String to, String subject, String content);

    public void sendAttachmentsMail(String to, String subject, String content,String filePath);
}
