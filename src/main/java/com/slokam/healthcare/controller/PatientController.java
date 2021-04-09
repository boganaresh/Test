package com.slokam.healthcare.controller;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.slokam.healthcare.entity.Patient;
import com.slokam.healthcare.pojo.PatientSearchPojo;
import com.slokam.healthcare.service.IPatientService;
import com.slokam.healthcare.util.NullCheckForPatient;

@RestController
@RequestMapping("patient")
public class PatientController {

	@Autowired
	private IPatientService patientService;
	
	@PostMapping("/register")
	public ResponseEntity<Patient> registerPatient(@RequestBody Patient patient){
		patientService.patientRegistration(patient);
		return new ResponseEntity<Patient>(patient,HttpStatus.CREATED);
	}
	
	@PostMapping("/search")
	public ResponseEntity<List<Patient>> patientFullSearch(@RequestBody PatientSearchPojo patientSearch){
		List<Patient> patientList = patientService.patientFullSearch(patientSearch);
		return ResponseEntity.ok(patientList);
	}
	
	@GetMapping("/get/{name}/{email}")
	public ResponseEntity<List<Patient>> patientSearchByNameAndEmail(@PathVariable String name, @PathVariable String email){
		List<Patient> patientList = patientService.patientSearchByNameAndEmail(name, email);
		System.out.println("=======================Patient List==========="+patientList);
		patientList.forEach(e -> System.out.println(e.getName()));
		return new ResponseEntity<>(patientList,HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Patient>> getAllPatients(){
		List<Patient> patientList = patientService.getAllPatients();
		return ResponseEntity.ok(patientList);
	}
	
	@GetMapping("byId/{id}")
	public ResponseEntity<Patient> getPatientById(@PathVariable Integer id) {
		Patient patient = patientService.getPatientById(id);
		return ResponseEntity.ok(patient);
	}
	
	@GetMapping("/getAllEvenPatients")
	public ResponseEntity<List<Patient>> getAllEvenPatients(){
		//List<Patient> patientList = patientService.getAllPatients();
		//List<Patient> evenPatientList = patientList.stream().filter(patient -> patient.getId() % 2 == 0).collect(Collectors.toList());
		//return ResponseEntity.ok(evenPatientList);
		return ResponseEntity.ok(patientService.getAllPatients().stream().filter(patient -> NullCheckForPatient.nullCheckForPatient(patient))
				.collect(Collectors.toList()));
	}
	
	@GetMapping("/getPatientsByAge")
	public ResponseEntity<List<Patient>> getAllPatientsByAge(){
		List<Patient> patientListByAge = patientService.getAllPatients().stream().filter(patient -> NullCheckForPatient.nullCheckForPatient(patient))
				.collect(Collectors.toList());
		return ResponseEntity.ok(patientListByAge);
	}
	
	@GetMapping("/getPatientsByName")
	public ResponseEntity<List<String>> getPatientNames(){
		List<String> patientNames = patientService.getAllPatients().stream().map(Patient :: getName).collect(Collectors.toList());
		return ResponseEntity.ok(patientNames);
	};
	
}
