package com.cybage.gms.app.service;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.cybage.gms.app.dao.LogRepository;
import com.cybage.gms.app.model.Log;
@Service
@Transactional
public class LogServiceImpl implements ILogService {
	@Autowired
	private LogRepository logRepository;

	@Override
	public Page<Log> findAll(Integer page, Integer size) {
		return logRepository.findAll(PageRequest.of(page, size));
	}
	@Override 
	public Boolean clear() {
		logRepository.deleteAll();
		return true;
	}
	@Override 
	public Boolean deleteById(Integer id) {
		logRepository.deleteById(id);
		return true;
	}
	@Override
	public List<Log> getAllLogs() {
		return logRepository.findAll();
	}

}
