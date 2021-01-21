package com.sapient.learning;

import static com.mongodb.client.model.Filters.eq;

import java.net.UnknownHostException;
import java.util.List;
import java.util.UUID;

import org.bson.Document;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.WriteResult;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.sapient.learning.adaptor.CustomerAdaptor;
import com.sapient.learning.model.Customer;

public class Main {

	public static void main(String[] args) throws UnknownHostException {

		// Using the Java Driver directly
		final MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
		final MongoDatabase database = mongoClient.getDatabase("test");
		final MongoCollection<Document> collection = database.getCollection("customer");
		final UUID uuid = UUID.randomUUID();
		collection.insertOne(CustomerAdaptor.toDocument(new Customer(uuid, "Kumar", "Chandrakant")));
		FindIterable<Document> result = collection.find(eq("_id", uuid));
		Document document = result.first();
		System.out.println(document);
		DeleteResult deleteResult = collection.deleteOne(eq("_id", uuid));
		System.out.println(deleteResult.getDeletedCount());
		mongoClient.close();

		// Using an ODM like Morphia
		final Morphia morphia = new Morphia();
		morphia.mapPackage("com.sapient.learning");
		final Datastore datastore = morphia
				.createDatastore(new MongoClient(new MongoClientURI("mongodb://localhost:27017")), "test");
		datastore.ensureIndexes();
		final Customer customer = new Customer(UUID.randomUUID(), "Kumar", "Chandrakant");
		datastore.save(customer);
		final Query<Customer> query = datastore.createQuery(Customer.class);
		final List<Customer> employees = query.asList();
		System.out.println(employees);
		WriteResult writeResult = datastore.delete(query);
		System.out.println(writeResult.getN());

	}

}