package com.ecare.hospital.admin.controller;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecare.hospital.admin.common.Constants;
import com.ecare.hospital.admin.service.AdminService;


@RestController
@RequestMapping("admin/")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@PostMapping("registration/doctor")
	public ResponseEntity<String>  doctorRegistration(@RequestBody Document document){

		String result = adminService.doctorRegistration(document);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@PostMapping("update/doctor")
	public ResponseEntity<String>  updateDoctor(@RequestBody Document document){

		String result = adminService.updateDoctor(document);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@GetMapping("get/doctor")
	public List  getDoctorDetails(){

		List<Document> result = adminService.getDoctorDetails();
		return result;
	}

	@GetMapping("get/doctor/{id}")
	public ResponseEntity<Document>  getDoctorDetailsById(@PathVariable Integer id){

		Document result = adminService.getDoctorDetailsById(id);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	
	@GetMapping("delete/doctor/{id}")
	public ResponseEntity<String>  deleteDoctor(@PathVariable Integer id){

		boolean result = adminService.deleteDoctor(id);
		if(result) {
			return ResponseEntity.status(HttpStatus.OK).body(Constants.DELETE_SUCCESS);
		}else {
			return ResponseEntity.status(HttpStatus.OK).body(Constants.DELETE_FAILED);
		}
	}


}
