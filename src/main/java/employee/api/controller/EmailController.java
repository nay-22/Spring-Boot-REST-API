package employee.api.controller;

import employee.api.domain.EmailDetails;
import employee.api.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
 

@RestController
public class EmailController {
 
    @Autowired private EmailService emailService;
 
    @PostMapping("/sendMail")
    public String sendMail(@RequestBody EmailDetails details)
    {
        return emailService.sendEmail(details);
    }
}