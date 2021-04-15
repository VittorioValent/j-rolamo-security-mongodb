package it.jrolamo.security.mail;

import java.io.File;
import java.util.Map;

import org.springframework.mail.SimpleMailMessage;

import freemarker.template.Template;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MailMessage extends SimpleMailMessage {
    
    private static final long serialVersionUID = 1L;

    private Map<String, File> attachments;

    private Map<String, Object> model;
    
    private String templateFilename;
    
    private Template template;

    public MailMessage(Template template){
        this.template = template;
    }

    public MailMessage(String templateFilename){
        this.templateFilename = templateFilename;
    }

}
