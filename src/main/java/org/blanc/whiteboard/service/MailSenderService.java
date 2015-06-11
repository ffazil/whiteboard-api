package org.blanc.whiteboard.service;

import org.blanc.whiteboard.util.EmailServiceTokenModel;

public interface MailSenderService {

    public EmailServiceTokenModel sendVerificationEmail(EmailServiceTokenModel emailServiceTokenModel);

    public EmailServiceTokenModel sendRegistrationEmail(EmailServiceTokenModel emailServiceTokenModel);

    public EmailServiceTokenModel sendLostPasswordEmail(EmailServiceTokenModel emailServiceTokenModel);


}
