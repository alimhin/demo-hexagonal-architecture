package com.alimhin.sample.hexagonalarchitecture.infrastructure.adapters.output.notification;

import com.alimhin.sample.hexagonalarchitecture.application.port.output.ArticleNotification;
import com.alimhin.sample.hexagonalarchitecture.domain.event.AbstractArticleEvent;
import com.alimhin.sample.hexagonalarchitecture.domain.event.CreatedArticleEvent;
import com.alimhin.sample.hexagonalarchitecture.domain.event.DraftArticleEvent;
import com.alimhin.sample.hexagonalarchitecture.domain.event.PublishedArticleEvent;
import com.alimhin.sample.hexagonalarchitecture.domain.event.UpdatedArticleEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;


@Component
@RequiredArgsConstructor
@Slf4j
public class ArticleMailNotificationAdapter implements ArticleNotification {

    private final JavaMailSender javaMailSender;

    @Override
    public void notifyArticleCreated(CreatedArticleEvent event) {
        this.sendMail("created", event);
    }

    @Override
    public void notifyArticleUpdated(UpdatedArticleEvent event) {
        this.sendMail("updated", event);
    }

    @Override
    public void notifyArticlePublished(PublishedArticleEvent event) {
        this.sendMail("published", event);
    }

    @Override
    public void notifyArticleDraft(DraftArticleEvent event) {
        this.sendMail("draft", event);
    }

    private void sendMail(String action, AbstractArticleEvent event) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, false, StandardCharsets.UTF_8.name());
            message.setTo("test@localhost");
            message.setFrom("admin@locahost");
            message.setSubject(String.format("Article %s", action));
            message.setText(String.format("Article %s with id `%d` and title `%s` at %s",action, event.getId(), event.getTitle(), event.getDate()));
            javaMailSender.send(mimeMessage);
            log.info("Sent email to 'test@localhost'");
        } catch (MailException | MessagingException e) {
            log.warn("Email could not be sent to 'test@localhost'", e);
        }
    }
}
