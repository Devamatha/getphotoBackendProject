package com.techpixe.getphoto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.techpixe.getphoto.config.TwilioConfig;
import com.twilio.Twilio;

import jakarta.annotation.PostConstruct;

//import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableConfigurationProperties
public class GetPhotoProjectApplication {
	
	@Autowired
	private TwilioConfig twilioConfig;

	public static void main(String[] args) {
		SpringApplication.run(GetPhotoProjectApplication.class, args);
	}
	@PostConstruct
	public void initTwilio(){
		Twilio.init(twilioConfig.getAccountSid(),twilioConfig.getAuthToken());
	}

}
