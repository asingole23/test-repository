package com.cybage.gms.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cybage.gms.app.dto.ChangePasswordDTO;
import com.cybage.gms.app.dto.ForgotPasswordDTO;
import com.cybage.gms.app.payload.request.LoginRequest;
import com.cybage.gms.app.payload.request.SignupRequest;
import com.cybage.gms.app.payload.response.JwtResponse;
import com.cybage.gms.app.security.jwt.JwtUtils;
import com.cybage.gms.app.service.IUserService;
import com.cybage.gms.app.service.UserDetailsImpl;
import com.cybage.gms.app.service.email.OtpService;

@CrossOrigin("http://localhost:4200")  //CORS -> prevents web browser from producing and consuming request on any other port 
@RestController	//Work as a @Controller as well as @ResponseBody
@RequestMapping("/api/auth")	//used for mapping web request in different methods like POST, GET,PUT, DELETE
public class AuthController {
	@Autowired	//used for spring bean dependecy injection at runtime 
	private AuthenticationManager authenticationManager;

	
	@Autowired
	PasswordEncoder encoder;	
	

	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private OtpService otpService;	

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		
		return ResponseEntity.ok(new JwtResponse(jwt, 
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getFirstName(),
												 userDetails.getEmail(),
												 userDetails.isEnabled(),
												 roles));
	}

	@PostMapping("/signup")		//for HTTP Post request 
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {		//resp.entity used for setting status code and to send properties
		return userService.createUserResponse(signUpRequest);
	}
	
	@PostMapping(value = "generate/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> generateOTP(@PathVariable String username)
    {
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //String username = authentication.getName();

        Map<String, String> response = new HashMap<>(2);

        // check authentication
        if (username == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // generate OTP.
        Boolean isGenerated = otpService.generateOtp(username);
        if (!isGenerated)
        {
            response.put("status", "error");
            response.put("message", "OTP can not be generated.");

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        // success message
        response.put("status", "success");
        response.put("message", "OTP successfully generated. Please check your e-mail!");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "validate/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> validateOTP(@RequestBody Map<String, String> otp,@PathVariable String username)
    {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
    	int requestedOtp=0;
    	try {
    		requestedOtp=Integer.parseInt(otp.get("otp"));    		
    	}catch (NumberFormatException e) {
			
		}
    	
    	System.out.println(username);
    	System.out.println(otp);
    	
        Map<String, String> response = new HashMap<>(2);

        // check authentication
        if (username == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }        
        
        // validate provided OTP.
        Boolean isValid = otpService.validateOTP(username, requestedOtp);
        if (!isValid)
        {
            response.put("status", "error");
            response.put("message", "OTP is not valid!");

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        // success message
        response.put("status", "success");
        response.put("message", "Entered OTP is valid!");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    
  //change password
  	@PutMapping("/forgot/changePassword/{username}")
  	public ResponseEntity<?> changePassword(@RequestBody ForgotPasswordDTO forgotPasswordDTO,@PathVariable String username) {
  		int requestedOtp=forgotPasswordDTO.getOtp();    	
    	
    	System.out.println(username);
    	System.out.println(requestedOtp);
    	
    	Map<String, String> response = new HashMap<>(2);
    	
        // check authentication
        if (username == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }        
        
        // validate provided OTP.
        Boolean isValid = otpService.validateOTP(username, requestedOtp);
        if (!isValid)
        {
            response.put("status", "error");
            response.put("message", "OTP is not valid!");

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        // success message
        response.put("status", "success");
        response.put("message", "Entered OTP is valid!");
        response.put("passwordChange", ""+userService.changeForgotPassword(username,forgotPasswordDTO));
  		return ResponseEntity.ok(response);
  	}
}
