package com.slokam.healthcare.repo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.slokam.healthcare.entity.Patient;
import com.slokam.healthcare.pojo.PatientSearchPojo;
import com.slokam.healthcare.util.StringUtils;

@Repository
public class PatientCriteriaRepo {

	@Autowired
	private EntityManager em;
	
	public List<Patient> patientFullSearch(PatientSearchPojo patientSearch){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Patient> cq = cb.createQuery(Patient.class);
		Root<Patient> root = cq.from(Patient.class);
		List<Predicate> predicateList = new ArrayList<Predicate>();
		if (patientSearch != null) {
			if (StringUtils.blankCheck(patientSearch.getName())) {
				predicateList.add(cb.like(root.get("name"), patientSearch.getName()));
			}
			if (patientSearch.getPhone() != null) {
				predicateList.add(cb.equal(root.get("phone"), patientSearch.getPhone()));
			}
			if (patientSearch.getFromDate() != null) {
				predicateList.add(cb.greaterThan(root.get("registeredDate"), patientSearch.getFromDate()));
			}
			if (patientSearch.getToDate() != null) {
				predicateList.add(cb.lessThan(root.get("registeredDate"), patientSearch.getToDate()));
			}
			if (patientSearch.getStartingAge() != null) {
				predicateList.add(cb.ge(root.get("age"), patientSearch.getStartingAge()));
			}
			if (patientSearch.getEndingAge() != null) {
				predicateList.add(cb.le(root.get("age"), patientSearch.getEndingAge()));
			}
		}
		cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
		TypedQuery<Patient> patientQuery = em.createQuery(cq);
		
		return patientQuery.getResultList();
	}
	
	//===============================================================================================================================
	
	public List<Patient> patientSearchByNameAndEmail(String name, String email){
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		System.out.println(cb);
		CriteriaQuery<Patient> cq = cb.createQuery(Patient.class);
		System.out.println(cq);
		Root<Patient> root = cq.from(Patient.class);
		System.out.println(root);
		List<Predicate> predicateList = new ArrayList<Predicate>();
		if (name != null && name.trim().length() > 0 ) {
			predicateList.add(cb.equal(root.get("name"), name));
		}
		if (email != null && email.trim().length() > 0) {
			predicateList.add(cb.like(root.get("email"), "%" + email + "%"));
		}
		cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
		TypedQuery<Patient> patientQuery = em.createQuery(cq);
		
		System.out.println(patientQuery);
		return patientQuery.getResultList();
	}
}
