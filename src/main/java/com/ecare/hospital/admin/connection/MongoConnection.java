package com.ecare.hospital.admin.connection;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;


@Configuration
public class MongoConnection {
	
	private MongoClient mongoClient;
	private MongoDatabase mongoDataBase;
	
	@Value("${spring.data.mongodb.database}")
	private String database;

	public MongoClient getMongoClient() {
		return mongoClient;
	}

	public void setMongoClient(MongoClient mongoClient) {
		this.mongoClient = mongoClient;
	}

	public MongoDatabase getMongoDataBase() {
		return mongoDataBase;
	}

	public void setMongoDataBase(MongoDatabase mongoDataBase) {
		this.mongoDataBase = mongoDataBase;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}
	
	@PostConstruct
	public void init() {
		try {
			String dataBase = this.database;
			this.mongoClient = new MongoClient();
			this.mongoDataBase = this.mongoClient.getDatabase(database);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
