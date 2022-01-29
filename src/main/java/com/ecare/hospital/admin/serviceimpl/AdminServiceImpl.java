package com.ecare.hospital.admin.serviceimpl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecare.hospital.admin.connection.MongoConnection;
import com.ecare.hospital.admin.service.AdminService;
import com.ecare.hospital.admin.util.DateUtil;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

@Service
public class AdminServiceImpl implements AdminService{

	@Autowired
	private MongoConnection mngoConnection;
	
	@Autowired
	private DateUtil dateUtil;

	@Override
	public String doctorRegistration(Document doc) {

		MongoDatabase db = mngoConnection.getMongoDataBase();
		MongoCollection<Document>  collection = db.getCollection("Doctor");

		Document doctorId = new Document();
		LinkedHashMap document = new LinkedHashMap<>();
		document =  (LinkedHashMap) doc.get("data");

		doctorId.put("doctorId",document.get("DoctorId"));
		
		Document chkExistingRecord = new Document();
		chkExistingRecord = isPresent(doctorId);
			if(chkExistingRecord != null){
			return "Record already exist with given ID";
		}
		doc.put("createdBy", "");
		doc.put("createdDate", dateUtil.getDate());
		collection.insertOne(doc);

		return "Doctor Registartion got Successfully";
	}

	@Override
	public Document getDoctorDetailsById(Integer id) {

		Document chkExistingRecord = new Document();
		Document doctorId = new Document();
		doctorId.put("doctorId",id);
		return chkExistingRecord = isPresent(doctorId);

	}

	private Document isPresent( Document doc) {

		MongoDatabase db = mngoConnection.getMongoDataBase();
		MongoCollection<Document>  collection = db.getCollection("Doctor");

		
		BasicDBList searchIfRecordExit = new BasicDBList();

		searchIfRecordExit.add(new BasicDBObject("data".concat(".")+"DoctorId",doc.get("doctorId")));
		BasicDBObject search = new BasicDBObject("$or", searchIfRecordExit);
		Document isRecordExist = collection.find(search).first();
		return isRecordExist;
	}

	@Override
	public boolean deleteDoctor(Integer docId) {
		MongoDatabase db = mngoConnection.getMongoDataBase();
		MongoCollection<Document>  collection = db.getCollection("Doctor");
		
		boolean isDeleted = false;
		long deleteCount = 0;
		Document deleteDoctor = new Document("data.DoctorId",docId);
		
		try {
			DeleteResult delete = collection.deleteOne(deleteDoctor);
			deleteCount = delete.getDeletedCount();
			if(deleteCount > 0) {
				isDeleted = true;
			}
			
		} catch (MongoException e) {
			System.out.println("Error while deleting the Doctor's detail");
		}
		return isDeleted;
	}

	@Override
	public String updateDoctor(Document requestBody) {
		MongoDatabase db = mngoConnection.getMongoDataBase();
		MongoCollection<Document>  collection = db.getCollection("Doctor");

		requestBody.put("updatedBy", "");
		requestBody.put("updatedDate", dateUtil.getDate());
		
		BasicDBList searchIfRecordExit = new BasicDBList();

		Document doctorId = new Document();
		LinkedHashMap document = new LinkedHashMap<>();
		document =  (LinkedHashMap) requestBody.get("data");

		doctorId.put("doctorId",document.get("DoctorId"));

		searchIfRecordExit.add(new BasicDBObject("data".concat(".")+"DoctorId",doctorId.get("doctorId")));

		BasicDBObject search = new BasicDBObject("$or", searchIfRecordExit);

		BasicDBObject modifiedObject = new BasicDBObject();
		modifiedObject.put("$set",document);
		modifiedObject.put("$set",requestBody);
		
		
		final UpdateResult updateResult = collection.updateOne(search,modifiedObject);

		if(updateResult.getModifiedCount() > 0) {
			return "Doctor's Data Updated uccessfully";
		}else {
			return "Updated got failed";
		}
		
	}

	@Override
	public List<Document> getDoctorDetails() {
		
		MongoDatabase db = mngoConnection.getMongoDataBase();
		MongoCollection<Document>  collection = db.getCollection("Doctor");
		
		List<Document> listData = new ArrayList<>();

		MongoCursor<Document> documents = collection.find().sort(new BasicDBObject()).cursor();
		
		while(documents.hasNext()) {
			Document doc = documents.next();
			listData.add(doc);
		}
		return listData;
	}

}
