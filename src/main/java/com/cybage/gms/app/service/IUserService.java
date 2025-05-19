package com.cybage.gms.app.service;

import java.util.List;
import java.util.Set;

import javax.management.relation.RoleNotFoundException;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.cybage.gms.app.dto.ChangePasswordDTO;
import com.cybage.gms.app.dto.ForgotPasswordDTO;
import com.cybage.gms.app.dto.UserDTO;
import com.cybage.gms.app.exception.InvalidEmailException;
import com.cybage.gms.app.exception.InvalidUsernameException;
import com.cybage.gms.app.model.User;
import com.cybage.gms.app.model.UserType;
import com.cybage.gms.app.payload.request.SignupRequest;

/**
 *	User service functionalities 
 *	@date: 18/11/21
 *
 */
public interface IUserService {
	/**
	 * Creates user and returns the created instance
	 * @param signUpRequest
	 * @return {@link User}
	 * @throws {@link RoleNotFoundException} 
	 */
	User createUser(@Valid SignupRequest signUpRequest) throws RoleNotFoundException;
	/**
	 * Locks the user and returns the lock status
	 * @param username
	 * @return {@link Boolean}
	 * @throws {@link InvalidUsernameException}
	 */
	boolean setLock(String username) throws InvalidUsernameException;
	/**
	 * Unlocks the user and returns the unlock status
	 * @param username
	 * @return {@link Boolean}
	 * @throws {@link InvalidUsernameException}
	 */
	boolean setUnlock(String username) throws InvalidUsernameException;
	/**
	 * deletes the specified user by id
	 * @param userId
	 * @return {@link UserDTO}
	 * @throws UsernameNotFoundException
	 */
	UserDTO deleteUser(Integer userId) throws UsernameNotFoundException;
	/**
	 * creates a user and returns the response to be given 
	 * @param signUpRequest
	 * @return {@link ResponseEntity}
	 */
	ResponseEntity<?> createUserResponse(@Valid SignupRequest signUpRequest);
	/**
	 * find the list of users by role
	 * @param role
	 * @return {@link List}
	 */
	List<UserDTO> findByRole(UserType role);
	/**
	 * find user by username
	 * to be used by admin
	 * @param username
	 * @return {@link UserDTO}
	 * @throws InvalidUsernameException
	 */
	UserDTO findByUsername(String username) throws InvalidUsernameException;
	/**
	 * Find user by email
	 * 
	 * @param email
	 * @return {@link UserDTO}
	 * @throws InvalidEmailException
	 */
	UserDTO findByEmail(String email) throws InvalidEmailException;
	/**
	 * Remove role from user
	 * @param username
	 * @return {@link UserDTO}
	 * @throws InvalidUsernameException
	 */
	UserDTO removeRoles(String username, Set<String> roles) throws InvalidUsernameException;
	/**
	 * Assign roles to user
	 * @param username
	 * @return {@link UserDTO}
	 * @throws InvalidUsernameException
	 */
	UserDTO addRoles(String username, Set<String> roles) throws InvalidUsernameException;
	/**
	 * Lock User
	 * @param userId
	 * @return
	 */
	boolean setLock(Integer userId);
	
	/**
	 * change password
	 * @param changePasswordDTO
	 * @return
	 */
	public boolean changePassword(ChangePasswordDTO changePasswordDTO);
	
	/**
	 * change forgot password
	 * @param username
	 * @param forgotPasswordDTO
	 * @return
	 */
	boolean changeForgotPassword(String username, ForgotPasswordDTO forgotPasswordDTO);
	
}
