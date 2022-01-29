/**
 * 
 */
package com.ecare.hospital.admin.service;

import java.util.List;

import org.bson.Document;

/**
 * @author hp
 *
 */
public interface AdminService {

	public String doctorRegistration(Document document);

	public Document getDoctorDetailsById(Integer id);

	public boolean deleteDoctor(Integer id);

	public String updateDoctor(Document document);

	public List<Document> getDoctorDetails();
	}
