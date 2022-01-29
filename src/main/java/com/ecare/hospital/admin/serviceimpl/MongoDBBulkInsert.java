
package com.ecare.hospital.admin.serviceimpl;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecare.hospital.admin.connection.MongoConnection;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


@Service
public class MongoDBBulkInsert {

	@Autowired
	private MongoConnection mngoConnection;
	

	public void insertmultipledocs() throws UnknownHostException {

		// Get a new connection to the db assuming that it is running

		MongoDatabase db = mngoConnection.getMongoDataBase();
		MongoCollection<Document>  collection = db.getCollection("test");

		//// fetch the collection object ,car is used here,use your own
		MongoCollection<Document> coll = db.getCollection("car");

		// create a new object
		Document d1 = new Document();

		// data for object d1
		d1.put("_id", 11);
		d1.put("name", "WagonR");
		d1.put("color", "MetallicSilver");
		d1.put("cno", "H141");
		d1.put("mfdcountry", "Australia");
		d1.put("speed", 66);

		Document d2 = new Document();

		// data for object d2
		d2.put("_id", 12);
		d2.put("name", "Xylo");
		d2.put("color", "JetBlue");
		d2.put("cno", "H142");
		d2.put("mfdcountry", "Europe");
		d2.put("speed", 69);

		Document d3 = new Document();

		// data for object d3
		d3.put("_id", 13);
		d3.put("name", "Alto800");
		d3.put("color", "JetGrey");
		d3.put("cno", "H143");
		d3.put("mfdcountry", "Austria");
		d3.put("speed", 74);

		// create a new list
		List<Document> docs = new ArrayList<>();

		// add d1,d2 and d3 to list docs
		docs.add(d1);
		docs.add(d2);
		docs.add(d3);

		// insert list docs to collection
		coll.insertMany(docs);

		// stores the result in cursor
		FindIterable<Document> carmuldocs = coll.find();

		for (Document d : carmuldocs)
			System.out.println(d);

	}

	// method that inserts documents with some fields
	public static void insertsomefieldsformultipledocs() throws UnknownHostException {

		// Get a new connection to the db assuming that it is running

		MongoClient mongoClient = new MongoClient("localhost");

		//// use test as a datbase,use your database here
		MongoDatabase db = mongoClient.getDatabase("test");

		//// fetch the collection object ,car is used here,use your own
		MongoCollection<Document> coll = db.getCollection("car");

		// create object d1
		Document d1 = new Document();

		// insert data for name,color and speed
		d1.put("name", "Indica");
		d1.put("color", "Silver");
		d1.put("cno", "H154");

		Document d2 = new Document();

		// insert data for id,name and speed
		d2.put("_id", 43);
		d2.put("name", "Astar");

		d2.put("speed", 79);

		List<Document> docs = new ArrayList<>();
		docs.add(d1);
		docs.add(d2);

		coll.insertMany(docs);

		FindIterable<Document> carmuldocs = coll.find();

		System.out.println("-----------------------------------------------");

		for (Document d : carmuldocs)
			System.out.println(d);

		mongoClient.close();

	}

	// method that checks for duplicate documents
	public static void insertduplicatedocs() throws UnknownHostException {

		// Get a new connection to the db assuming that it is running

		MongoClient mongoClient = new MongoClient("localhost");

		//// use test as a database, use your database here
		MongoDatabase db = mongoClient.getDatabase("test");

		//// fetch the collection object ,car is used here,use your own
		MongoCollection<Document> coll = db.getCollection("car");

		Document d1 = new Document();

		// insert duplicate data of id11
		d1.put("_id", 11);
		d1.put("name", "WagonR-Lxi");

		coll.insertOne(d1);

		FindIterable<Document> carmuldocs = coll.find();

		System.out.println("-----------------------------------------------");

		for (Document d : carmuldocs)
			System.out.println(d);

		mongoClient.close();

	}

}
