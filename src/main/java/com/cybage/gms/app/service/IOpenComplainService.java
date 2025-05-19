package com.cybage.gms.app.service;

import java.util.List;

import com.cybage.gms.app.dto.OpenComplainDTO;
import com.cybage.gms.app.model.Complain;
import com.cybage.gms.app.model.OpenComplain;

public interface IOpenComplainService {
	OpenComplainDTO createOpenComplain(Complain complain,OpenComplain openComplain);
	
	OpenComplainDTO updateOpenComplain(Integer openComplainId,OpenComplain openComplain);
	OpenComplainDTO findById(Integer openComplainId);
	List<OpenComplainDTO> findAllOpenComplain();
	List<OpenComplainDTO> findByCitizenId(Integer citizenId);
}
