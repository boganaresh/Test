package com.slokam.healthcare.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.slokam.healthcare.repo.IVisitingRepo;
import com.slokam.healthcare.service.IVisitingService;

@Repository
public class VisitingServiceImpl implements IVisitingService{

	@Autowired
	private IVisitingRepo visitingRepo;
	
	@Override
	public List<Object[]> getVisitingByPatientId(Integer id) {
		return visitingRepo.getVisitingByPatientId(id);
	}

}
