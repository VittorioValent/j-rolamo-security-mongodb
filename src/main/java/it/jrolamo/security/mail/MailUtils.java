package it.jrolamo.security.mail;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map.Entry;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

@Component
public class MailUtils {
    
    private final String templatePath = "/templates";

    @Autowired
    public JavaMailSender emailSender;

    @Autowired
    private Configuration freemarkerConfig;

    public void sendSimpleMessage(SimpleMailMessage mail) throws MessagingException, IOException, TemplateException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
            MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
            StandardCharsets.UTF_8.name());
            String html = mail.getText();

        if(mail instanceof MailMessage){
            MailMessage mailMessage = (MailMessage) mail;

            if(mailMessage.getTemplate() != null){
                html = FreeMarkerTemplateUtils.processTemplateIntoString(mailMessage.getTemplate(), mailMessage.getModel());
            }else{
                freemarkerConfig.setClassForTemplateLoading(this.getClass(), templatePath);
                html = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfig.getTemplate(mailMessage.getTemplateFilename()), mailMessage.getModel());
            }
            helper.setText(html, true);

            for(Entry<String, File> entry : mailMessage.getAttachments().entrySet()){
                helper.addAttachment(entry.getKey(), entry.getValue());
            }

        }else{
            helper.setText(html);
        }
        
        helper.setTo(mail.getTo());
        helper.setSubject(mail.getSubject());
        helper.setFrom(mail.getFrom());

        emailSender.send(message);
    }
}
