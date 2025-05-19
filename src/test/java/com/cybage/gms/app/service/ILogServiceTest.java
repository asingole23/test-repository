package com.cybage.gms.app.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
@SpringBootTest
class ILogServiceTest {
	@Autowired
	public ILogService logService;
	@Test
	void testFindAll() {
		assertTrue(logService.findAll(0, 10) instanceof Page<?>);
	}

}
