package com.cybage.gms.app.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cybage.gms.app.dao.DepartmentRepository;
import com.cybage.gms.app.dao.RoleRepository;
import com.cybage.gms.app.dao.UserRepository;
import com.cybage.gms.app.dto.ChangePasswordDTO;
import com.cybage.gms.app.dto.ForgotPasswordDTO;
import com.cybage.gms.app.dto.UserDTO;
import com.cybage.gms.app.exception.InvalidEmailException;
import com.cybage.gms.app.exception.InvalidPasswordException;
import com.cybage.gms.app.exception.InvalidUsernameException;
import com.cybage.gms.app.exception.RoleNotFoundException;
import com.cybage.gms.app.model.Role;
import com.cybage.gms.app.model.User;
import com.cybage.gms.app.model.UserType;
import com.cybage.gms.app.payload.request.SignupRequest;
import com.cybage.gms.app.payload.response.MessageResponse;
import com.cybage.gms.app.security.jwt.JwtUtils;

@Service
public class UserDetailsServiceImpl implements UserDetailsService, IUserService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private DepartmentRepository departmentRepository;

	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
		if(!user.getIsEnabled()) {
			throw new BadCredentialsException("User is not enabled");
		}
		return UserDetailsImpl.build(user);
	}

	public List<UserDTO> getAllUser(UserType type) {
		return userRepository.findByType(type).stream().map(user -> userToUserDto(user)).collect(Collectors.toList());
	}

	@Override
	public List<UserDTO> findByRole(UserType role) {
		return userRepository.findByType(role).stream().map(u -> {
			UserDTO dto = new UserDTO();
			BeanUtils.copyProperties(u, dto);
			return dto;
		}).collect(Collectors.toList());
	}

	public UserDTO findById(Integer UserId) {
		User user = userRepository.findById(UserId)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with userId: " + UserId));
		return userToUserDto(user);
	}
	
	@Transactional
	public User findByIds(Integer UserId) {
		User user = userRepository.findById(UserId)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with userId: " + UserId));
		return user;
	}
	
	@Transactional
	public UserDTO deleteUser(Integer UserId) {
		User user = userRepository.findById(UserId)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with userId: " + UserId));
		userRepository.deleteById(UserId);
		return userToUserDto(user);
	}

	@Transactional
	public UserDTO updateUserDetails(Integer UserId, UserDTO userDTO) {
		User user = userRepository.findById(UserId)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with userId: " + UserId));
		user.setEmail(userDTO.getEmail());
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setDob(userDTO.getDob());
		user.setPhoneNo(userDTO.getPhoneNo());
		user.setGender(userDTO.getGender());
		user.setAddress(userDTO.getAddress());
		userRepository.save(user);
		System.out.println("updated user dtls " + user);
		BeanUtils.copyProperties(user, userDTO);
		return userDTO;
	}

	public UserDTO getUserByTypeId(UserType type, Integer id) {
		User user = userRepository.findByTypeId(type, id)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with userId: " + id));
		return userToUserDto(user);
	}

	@Override
	@Transactional
	public User createUser(@Valid SignupRequest signUpRequest) {
		// Create new user's account
		User user = new User(signUpRequest.getFirstName(), signUpRequest.getLastName(), signUpRequest.getUsername(),
				signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()), signUpRequest.getDob(),
				signUpRequest.getPhoneNo(), signUpRequest.getGender(), signUpRequest.getAddress());
//		signUpRequest.setRole(new HashSet<>(List.of("citizen")));
		Set<String> strRoles = signUpRequest.getRole();
//				System.out.println("iiiiiiiiiiiiiiiiii " +strRoles);
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			System.out.println("in null role");
			Role userRole = roleRepository.findByName(UserType.CITIZEN).orElseThrow(RoleNotFoundException::new);
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "department":
					Role departmentRole = roleRepository.findByName(UserType.DEPARTMENT)
							.orElseThrow(RoleNotFoundException::new);
					roles.add(departmentRole);

					break;
				case "citizen":
					Role citizenRole = roleRepository.findByName(UserType.CITIZEN)
							.orElseThrow(RoleNotFoundException::new);
					roles.add(citizenRole);

					break;
				case "user_dept":
					Role userDeptRole = roleRepository.findByName(UserType.USER_DEPT)
							.orElseThrow(RoleNotFoundException::new);
					roles.add(userDeptRole);
					break;
				case "admin":
					Role adminRole = roleRepository.findByName(UserType.ADMIN).orElseThrow(RoleNotFoundException::new);
					roles.add(adminRole);
					break;
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);
		return user;
	}

	@Override
	@Transactional
	public ResponseEntity<?> createUserResponse(@Valid SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		if (userRepository.existsByPhoneNo(signUpRequest.getPhoneNo())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Mobile is already in use!"));
		}
		createUser(signUpRequest);
		
		//send email to the user about login details
		Properties prop = new Properties(); 
		StringBuilder text = new StringBuilder();
		SimpleMailMessage mail = new SimpleMailMessage();
		
		mail.setTo(signUpRequest.getEmail());
		mail.setFrom(prop.getProperty("spring.mail.username"));
		mail.setSubject("Your Login Credentials");
		text.append("Hello " + signUpRequest.getFirstName()+" "+signUpRequest.getLastName() + ",\n");
		text.append("We recieved your signup request ,below are your credentials for login to GMS. \n "
				+ "Email ID : "+signUpRequest.getEmail()+"\n"
				+ "Username : "+signUpRequest.getUsername()+"\n"
				+ "Password : "+signUpRequest.getPassword()+"\n\n");
		text.append("Regards,\n"+"Admin \nGrievance Management System \nPune Municipal Corporation");
		String message = text.toString();
		mail.setText(message);
		mailSender.send(mail);
		
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

	@Override
	@Transactional
	public boolean setLock(String username) {
		User user = userRepository.findByUsername(username).orElseThrow(InvalidUsernameException::new);
		if (user.getIsEnabled()) {
			throw new InvalidUsernameException("The user is locked");
		}
		user.setIsEnabled(true);
		return user.getIsEnabled();
	}

	@Override
	@Transactional
	public boolean setUnlock(String username) {
		User user = userRepository.findByUsername(username).orElseThrow(InvalidUsernameException::new);
		
		user.setIsEnabled(true);
		return user.getIsEnabled();
	}

	private static UserDTO userToUserDto(User user) {
		UserDTO userDto = new UserDTO();
		BeanUtils.copyProperties(user, userDto);
		return userDto;
	}

	@Override
	public UserDTO findByUsername(String username) {
		User user = userRepository.findByUsername(username).orElseThrow(InvalidUsernameException::new);
		return userToUserDto(user);
	}

	@Override
	public UserDTO findByEmail(String email) {
		User user = userRepository.findByEmail(email).orElseThrow(InvalidEmailException::new);
		return userToUserDto(user);
	}

	@Override
	@Transactional
	public UserDTO removeRoles(String username, Set<String> roles) throws InvalidUsernameException {
		User user = userRepository.findByUsername(username).orElseThrow(InvalidUsernameException::new);
		Set<Role> persistedRoles =  user.getRoles();
		for (String r : roles) {
			switch (r) {
			case "department":
				Role departmentRole = roleRepository.findByName(UserType.DEPARTMENT)
						.orElseThrow(RoleNotFoundException::new);
				persistedRoles.remove(departmentRole);
				break;
			case "citizen":
				Role citizenRole = roleRepository.findByName(UserType.CITIZEN).orElseThrow(RoleNotFoundException::new);
				persistedRoles.remove(citizenRole);
				break;
			case "user_dept":
				Role userDeptRole = roleRepository.findByName(UserType.USER_DEPT)
						.orElseThrow(RoleNotFoundException::new);
				persistedRoles.remove(userDeptRole);
				break;
			case "admin":
				Role adminRole = roleRepository.findByName(UserType.ADMIN).orElseThrow(RoleNotFoundException::new);
				persistedRoles.remove(adminRole);
				break;
			}
		}
		return userToUserDto(user);
	}

	@Override
	@Transactional
	public UserDTO addRoles(String username, Set<String> roles) throws InvalidUsernameException {
		User user = userRepository.findByUsername(username).orElseThrow(InvalidUsernameException::new);
		Set<Role> persistedRoles =  user.getRoles();
		for (String r : roles) {
			switch (r) {
			case "department":
				Role departmentRole = roleRepository.findByName(UserType.DEPARTMENT)
						.orElseThrow(RoleNotFoundException::new);
				persistedRoles.add(departmentRole);
				break;
			case "citizen":
				Role citizenRole = roleRepository.findByName(UserType.CITIZEN).orElseThrow(RoleNotFoundException::new);
				persistedRoles.add(citizenRole);
				break;
			case "user_dept":
				Role userDeptRole = roleRepository.findByName(UserType.USER_DEPT)
						.orElseThrow(RoleNotFoundException::new);
				persistedRoles.add(userDeptRole);
				break;
			case "admin":
				Role adminRole = roleRepository.findByName(UserType.ADMIN).orElseThrow(RoleNotFoundException::new);
				persistedRoles.add(adminRole);
				break;
			}
		}
		return userToUserDto(user);
	}
	
	public String findEmailByUsername(String username)
    {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return user.get().getEmail();
        }
        return null;
    }
	
	@Transactional
	public List<UserDTO> findAllUser() {
		return userRepository.findAll().stream().map(user -> {
			UserDTO dto = new UserDTO();
			BeanUtils.copyProperties(user, dto);
			return dto;
		}).collect(Collectors.toList());
	}
	
	
	@Override
	@Transactional
	public boolean setLock(Integer userId) {
		User user=userRepository.findById(userId)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with userId: " + userId));
		user.setIsEnabled(false);
		return true;
	}

	@Transactional
	@Override
	public boolean changePassword(ChangePasswordDTO changePasswordDTO) {
		User user=userRepository.findById(changePasswordDTO.getUserDTO().getUserId())
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with userId: " + changePasswordDTO.getUserDTO().getUserId()));
		String oldPassword=changePasswordDTO.getPasswordDTO().getOldPassword();
		System.out.println(oldPassword);
		System.out.println(user.getPassword());
		boolean passwordConfirm=passwordEncoder.matches(oldPassword, user.getPassword());
		if(!passwordConfirm) {
			throw new InvalidPasswordException();
		}
		String newPassword=passwordEncoder.encode(changePasswordDTO.getPasswordDTO().getNewPassword());
		user.setPassword(newPassword);
		return true;
	}
	
	@Transactional
	@Override
	public boolean changeForgotPassword(String username, ForgotPasswordDTO forgotPasswordDTO) {
		User user=userRepository.findByUsername(username).orElseThrow(()->new InvalidUsernameException("Username not found"));
		user.setPassword(passwordEncoder.encode(forgotPasswordDTO.getNewPassword()));
		return true;
	}

}
