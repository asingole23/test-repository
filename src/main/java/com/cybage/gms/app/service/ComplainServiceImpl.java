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
import com.cybage.gms.app.dao.DepartmentRepository;
import com.cybage.gms.app.dao.UserRepository;
import com.cybage.gms.app.dto.ComplainDTO;
import com.cybage.gms.app.exception.ComplainNotFound;
import com.cybage.gms.app.exception.InvalidDepartmentException;
import com.cybage.gms.app.model.Complain;
import com.cybage.gms.app.model.Department;
import com.cybage.gms.app.model.Status;
import com.cybage.gms.app.model.User;

@Service
public class ComplainServiceImpl implements IComplainService {

	@Autowired
	ComplainRepository complainRepository;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	DepartmentRepository departmentRepository;

	private static ComplainDTO complainToComplainDto(Complain complain) {
		ComplainDTO complainDTO = new ComplainDTO();
		BeanUtils.copyProperties(complain, complainDTO);
		return complainDTO;
	}

	@Override
	public ComplainDTO findById(Integer id) {
		Complain complain = complainRepository.findById(id)
				.orElseThrow(() -> new ComplainNotFound("Complain not found with ID: " + id));
		return complainToComplainDto(complain);
	}

	@Override
	public List<ComplainDTO> findByCitizen(User citizen) {
		List<ComplainDTO> complainDTOs = new ArrayList<>();
		complainRepository.findByCitizen(citizen.getUserId()).forEach(citizenData -> {
			ComplainDTO complainDTO = new ComplainDTO();
			BeanUtils.copyProperties(citizenData, complainDTO);
			complainDTOs.add(complainDTO);
		});
		return complainDTOs;
	}

	
	@Override
	public List<ComplainDTO> findByDepartment(Department department) {
		List<ComplainDTO> complainDTOs = new ArrayList<>();
		complainRepository.findByDepartmentId(department.getId()).forEach(departmentData -> {
			ComplainDTO complainDTO = new ComplainDTO();
			BeanUtils.copyProperties(departmentData, complainDTO);
			complainDTOs.add(complainDTO);
		});
		return complainDTOs;
	}

	@Override
	public List<ComplainDTO> findAllComplains() {
		List<ComplainDTO> complainDTOs = new ArrayList<>();
		complainRepository.findAll().forEach(allComplain -> {
			ComplainDTO complainDTO = new ComplainDTO();
			BeanUtils.copyProperties(allComplain, complainDTO);
			complainDTOs.add(complainDTO);
		});
		return complainDTOs;
	}

	@Override
	public ComplainDTO createComplain(User citizen, Complain complain, Department department) {
		Optional<User> existingCitizen = userRepository.findById(citizen.getUserId());
		if (existingCitizen.isEmpty()) {
			throw new UsernameNotFoundException("User not found with ID: " + citizen.getUserId());
		} else {
			complain.setCreatedOn(now());
			complain.setComplainStatus(Status.SUBMITTED);
			complain.setCitizen(citizen);
			complain.setDepartment(department);
			complain.setOpenComplain(null);
			complainRepository.save(complain);
			return complainToComplainDto(complain);
		}

	}	

	@Override
	public boolean deleteComplain(Complain complain) {
		Optional<Complain> complain2 = complainRepository.findById(complain.getId());
		if (complain2.isEmpty()) {
			return false;
		} else {
			complainRepository.delete(complain);
			return true;
		}
	}
	
	@Transactional
	@Override
	public Complain findByIds(Integer id) {
		Complain complain = complainRepository.findById(id)
				.orElseThrow(() -> new ComplainNotFound("Complain not found with ID: " + id));
		return complain;
	}
	
	@Transactional
	@Override
	public boolean sendReminderToDepartment(Integer complainId) {
		Complain complain=complainRepository.findById(complainId).orElseThrow(()->new ComplainNotFound("Complain not found"));
		complain.setRemainder(true);
		return true;
	}

	@Transactional
	@Override
	public ComplainDTO updateComplain(Integer departmentHeadId, ComplainDTO complainDTO) {
		Complain complain=complainRepository.findById(complainDTO.getId()).orElseThrow(()->new ComplainNotFound("Not found"));
		User deptHead=userRepository.findById(departmentHeadId).orElseThrow(()->new UsernameNotFoundException("Not Found"));
		if(deptHead.getDepartment()!=null && deptHead.getDepartment()!=complain.getDepartment()) {
			throw new InvalidDepartmentException("The requesting user does not belong to the appropriate department");
		}
		Status complainStatus=complainDTO.getComplainStatus();
		complain.setComplainStatus(complainStatus);
		if (complainStatus.equals(Status.ACCEPTED)) {
			complain.setAccepetdOn(now());
			complain.setResolved(false);
		} else if (complainStatus.equals(Status.OPENED)) {
			complain.setOpenedOn(now());
			complain.setResolved(false);
			complain.setClosedOn(null);
		} else if (complainStatus.equals(Status.CLOSED)) {
			complain.setClosedOn(now());
			complain.setResolved(true);
		}
		complain.setExpectedToResolve(complainDTO.getExpectedToResolve());		
		return Utility.complainToComplainDto(complain);		 
	}

	@Transactional
	@Override
	public ComplainDTO updateComplainDepartment(Integer departmentId, ComplainDTO complainDTO) {
		Department department=departmentRepository.findById(departmentId).orElseThrow(()->new InvalidDepartmentException("Not found"));
		Complain complain=complainRepository.findById(complainDTO.getId()).orElseThrow(()->new ComplainNotFound("Not found"));
		complain.setDepartment(department);
		return Utility.complainToComplainDto(complain);
	}

	@Override
	public List<ComplainDTO> findByResolved() {
		List<ComplainDTO> complainDTOs = new ArrayList<>();
		complainRepository.findByResolved().forEach(citizenData -> {
			ComplainDTO complainDTO = new ComplainDTO();
			BeanUtils.copyProperties(citizenData, complainDTO);
			complainDTOs.add(complainDTO);
		});
		return complainDTOs;
	}

	@Override
	public List<ComplainDTO> findByUnResolved() {
		List<ComplainDTO> complainDTOs = new ArrayList<>();
		complainRepository.findByUnResolved().forEach(citizenData -> {
			ComplainDTO complainDTO = new ComplainDTO();
			BeanUtils.copyProperties(citizenData, complainDTO);
			complainDTOs.add(complainDTO);
		});
		return complainDTOs;
	}

	@Override
	public List<ComplainDTO> findByDepartmentResolved(Department department) {
		List<ComplainDTO> complainDTOs = new ArrayList<>();
		complainRepository.findByDepartmentResolved(department.getId()).forEach(citizenData -> {
			ComplainDTO complainDTO = new ComplainDTO();
			BeanUtils.copyProperties(citizenData, complainDTO);
			complainDTOs.add(complainDTO);
		});
		return complainDTOs;
	}

	@Override
	public List<ComplainDTO> findByDepartmentUnresolved(Department department) {
		List<ComplainDTO> complainDTOs = new ArrayList<>();
		complainRepository.findByDepartmentUnresolved(department.getId()).forEach(citizenData -> {
			ComplainDTO complainDTO = new ComplainDTO();
			BeanUtils.copyProperties(citizenData, complainDTO);
			complainDTOs.add(complainDTO);
		});
		return complainDTOs;
	}

	@Override
	public List<ComplainDTO> getAllReminders(Department department) {
		List<ComplainDTO> complainDTOs = new ArrayList<>();
		complainRepository.findByDepartmentRemainder(department.getId()).forEach(citizenData -> {
			ComplainDTO complainDTO = new ComplainDTO();
			BeanUtils.copyProperties(citizenData, complainDTO);
			complainDTOs.add(complainDTO);
		});
		return complainDTOs;
	}

}
