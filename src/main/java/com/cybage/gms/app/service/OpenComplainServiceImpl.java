package com.cybage.gms.app.service;

import static java.time.LocalDateTime.now;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cybage.gms.app.dao.ComplainRepository;
import com.cybage.gms.app.dao.OpenComplainRepository;
import com.cybage.gms.app.dao.UserRepository;
import com.cybage.gms.app.dto.OpenComplainDTO;
import com.cybage.gms.app.exception.ComplainNotFound;
import com.cybage.gms.app.model.Complain;
import com.cybage.gms.app.model.OpenComplain;
import com.cybage.gms.app.model.Status;
import com.cybage.gms.app.model.User;

@Service
public class OpenComplainServiceImpl implements IOpenComplainService {

	@Autowired
	OpenComplainRepository openComplainRepository;
	
	@Autowired
	ComplainRepository complainRepository;
	
	@Autowired
	UserRepository userRepository;

	private static OpenComplainDTO openComplainToOpenComplainDto(OpenComplain openComplain) {
		OpenComplainDTO openComplainDTO = new OpenComplainDTO();
		BeanUtils.copyProperties(openComplain, openComplainDTO);
		return openComplainDTO;
	}

	@Override
	public OpenComplainDTO createOpenComplain(Complain complain, OpenComplain openComplain) {
		if (complain == null || openComplain == null) {
			throw new ComplainNotFound("Invalid Data inserted");
		}
		openComplain.setComplain(complain);
		complain.setOpenComplain(openComplain);
		openComplain.setCreatedOn(now());
		complain.setComplainStatus(Status.OPENED);
		complain.setResolved(false);
		openComplainRepository.save(openComplain);
		return openComplainToOpenComplainDto(openComplain);
	}

	@Override
	public OpenComplainDTO updateOpenComplain(Integer openComplainId,OpenComplain openComplain) {
		Optional<OpenComplain> existingOpenComplain = openComplainRepository.findById(openComplainId);
		if (existingOpenComplain.isEmpty()) {
			throw new ComplainNotFound("Open Complain not found");
		}
		existingOpenComplain.get().setComplain(openComplain.getComplain());
		existingOpenComplain.get().setActionTakenDate(now());
		existingOpenComplain.get().setActionTaken(openComplain.getActionTaken());
		existingOpenComplain.get().setActionDescription(openComplain.getActionDescription());
		openComplainRepository.save(existingOpenComplain.get());
		return openComplainToOpenComplainDto(openComplain);

	}

	@Override
	public OpenComplainDTO findById(Integer openComplainId) {
		Optional<OpenComplain> existingOpenComplain = openComplainRepository.findById(openComplainId);
		if (existingOpenComplain.isEmpty()) {
			throw new ComplainNotFound("Open Complain not found with ID: " + openComplainId);
		}
		return openComplainToOpenComplainDto(existingOpenComplain.get());
	}

	@Override
	public List<OpenComplainDTO> findAllOpenComplain() {
		List<OpenComplainDTO> openComplainDTOs = new ArrayList<>();
		openComplainRepository.findAll().forEach(allOpenComplain -> {
			OpenComplainDTO openComplainDTO = new OpenComplainDTO();
			BeanUtils.copyProperties(allOpenComplain, openComplainDTO);
			openComplainDTOs.add(openComplainDTO);
		});
		return openComplainDTOs;
	}

	@Override
	public List<OpenComplainDTO> findByCitizenId(Integer citizenId) {
		User user=userRepository.findById(citizenId).orElseThrow(()-> new UsernameNotFoundException("user not found with id :"+citizenId) );
		List<OpenComplainDTO> openComplainDTOs = new ArrayList<>();
		System.out.println(user.getUserId());
		openComplainRepository.findByCitizenId(user).forEach(allOpenComplain -> {
			OpenComplainDTO openComplainDTO = new OpenComplainDTO();
			BeanUtils.copyProperties(allOpenComplain, openComplainDTO);
			openComplainDTOs.add(openComplainDTO);
		});
		return openComplainDTOs;
	}

}
