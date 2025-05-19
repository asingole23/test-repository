package com.cybage.gms.app.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.cybage.gms.app.model.Log;

public interface ILogService {
	/**
	 * Returns all logs of that page
	 * @param page
	 * @param size
	 * @return {@link Page}
	 */
	Page<Log> findAll(Integer page, Integer size);
	/**
	 * clears all db logs
	 * @return
	 */
	Boolean clear();
	/**
	 * deletes log by id
	 * @param id
	 * @return
	 */
	Boolean deleteById(Integer id);
	
	List<Log> getAllLogs();
}
