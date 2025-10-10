package vn.huy.jobhunter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.huy.jobhunter.service.EmailService;
import vn.huy.jobhunter.service.SubscriberService;
import vn.huy.jobhunter.util.annotition.ApiMessage;

@RestController
@RequestMapping("/api/v1")
public class EmailController {

    private final EmailService emailService;
    private final SubscriberService subscriberService;

    public EmailController(EmailService emailService,
            SubscriberService subscriberService) {
        this.emailService = emailService;
        this.subscriberService = subscriberService;
    }

    @GetMapping("/email")
    @ApiMessage("Send simple email")
    public String sendSimpleEmail() {
        // this.emailService.sendSimpleEmail();
        // this.emailService.sendEmailSync("winsp203@gmail.com", "test send email",
        // "<h1><b> hello </b></h1>", false, true);
        // this.emailService.sendEmailFromTemplateSync("winsp203@gmail.com", "test send
        // email", "job");
        this.subscriberService.sendSubscribersEmailJobs();
        return "ok";
    }

}
