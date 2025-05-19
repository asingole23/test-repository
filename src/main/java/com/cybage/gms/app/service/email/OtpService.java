package com.cybage.gms.app.service.email;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.cybage.gms.app.service.UserDetailsServiceImpl;

@Description(value = "Service responsible for handling OTP related functionality.")
@Service
public class OtpService {

    private final Logger LOGGER = LoggerFactory.getLogger(OtpService.class);
    
    @Autowired
    private OtpGenerator otpGenerator;  
    
    @Autowired
    private UserDetailsServiceImpl userService;    
    
    
    @Autowired
    private MailSender mailSender;
    
    /**
     * Method for generate OTP number
     *
     * @param key - provided key (username in this case)
     * @return boolean value (true|false)
     */
    public Boolean generateOtp(String key)
    {
        // generate otp
        Integer otpValue = otpGenerator.generateOTP(key);
        if (otpValue == -1)
        {
            LOGGER.error("OTP generator is not working...");
            return  false;
        }

        LOGGER.info("Generated OTP: {}", otpValue);

        // fetch user e-mail from database
        String userEmail = userService.findEmailByUsername(key);
        List<String> recipients = new ArrayList<>();
        recipients.add(userEmail);

        // generate emailDTO object
      			//send email to the user about login details
        
        			Properties prop = new Properties();     
        
        
					StringBuilder text = new StringBuilder();
					SimpleMailMessage mail = new SimpleMailMessage();
					mail.setTo(userEmail);
					mail.setFrom(prop.getProperty("spring.mail.username"));
					mail.setSubject("OTP for login on GMS");
					text.append("Dear User," + "\n\n");
					text.append("Your OTP is : [ "+otpValue +" ]\n");
					text.append("Use this passcode to authenticate. Please do not share this otp to anyone." + "\n\n");
					text.append("Regards,\n"+"Admin \nGrievance Management System \nPune Municipal Corporation");
					String message = text.toString();
					mail.setText(message);
					try {
						mailSender.send(mail);
						return true;
					} catch (MailException e) {
						LOGGER.error(e.getMessage());
						return false;
					}		
    }

    /**
     * Method for validating provided OTP
     *
     * @param key - provided key
     * @param otpNumber - provided OTP number
     * @return boolean value (true|false)
     */
    public Boolean validateOTP(String key, Integer otpNumber)
    {
        // get OTP from cache
        Integer cacheOTP = otpGenerator.getOPTByKey(key);
        if (cacheOTP!=null && cacheOTP.equals(otpNumber))
        {
            otpGenerator.clearOTPFromCache(key);
            return true;
        }
        return false;
    }
}