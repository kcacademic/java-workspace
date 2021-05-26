package com.kchandrakant.learning;

import static com.mongodb.client.model.Filters.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.UUID;

import org.bson.Document;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.kchandrakant.learning.adaptor.CustomerAdaptor;
import com.kchandrakant.learning.model.Customer;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;

import de.flapdoodle.embed.mongo.Command;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.DownloadConfigBuilder;
import de.flapdoodle.embed.mongo.config.ExtractedArtifactStoreBuilder;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.config.RuntimeConfigBuilder;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.config.IRuntimeConfig;
import de.flapdoodle.embed.process.extract.UserTempNaming;
import de.flapdoodle.embed.process.io.directories.FixedPath;
import de.flapdoodle.embed.process.io.directories.IDirectory;
import de.flapdoodle.embed.process.runtime.Network;

@TestMethodOrder(OrderAnnotation.class)
public class MongoDBTests {

	private static MongoClient mongoClient;
	private static MongoDatabase database;
	private static MongoCollection<Document> collection;
	private static UUID uuid = UUID.randomUUID();

	private static MongodStarter _starter;
	private static MongodExecutable _mongodExe;
	private static MongodProcess _mongod;

	@BeforeAll
	public static void setUp() throws IOException {

		IDirectory artifactStorePath = new FixedPath(System.getProperty("user.home") + "/.embeddedMongodbCustomPath");

		IRuntimeConfig runtimeConfig = new RuntimeConfigBuilder().defaults(Command.MongoD)
				.artifactStore(new ExtractedArtifactStoreBuilder().defaults(Command.MongoD)
						.download(new DownloadConfigBuilder().defaultsForCommand(Command.MongoD)
								.artifactStorePath(artifactStorePath).build())
						.executableNaming(new UserTempNaming()))
				.build();

		_starter = MongodStarter.getInstance(runtimeConfig);

		_mongodExe = _starter.prepare(new MongodConfigBuilder().version(Version.Main.PRODUCTION)
				.net(new Net("localhost", 12345, Network.localhostIsIPv6())).build());
		_mongod = _mongodExe.start();

		mongoClient = new MongoClient("localhost", 12345);
		database = mongoClient.getDatabase("test");
		collection = database.getCollection("customer");
	}

	@AfterAll
	public static void tearDown() {
		_mongod.stop();
		_mongodExe.stop();
	}

	@Test
	@Order(1)
	public void insertDocument() {
		collection.insertOne(CustomerAdaptor.toDocument(new Customer(uuid, "Kumar", "Chandrakant")));
	}

	@Test
	@Order(2)
	public void fetchDocument() {
		FindIterable<Document> result = collection.find(eq("_id", uuid));
		Document document = result.first();
		assertEquals(document.get("_id"), uuid);
	}

	@Test
	@Order(3)
	public void deleteDocument() {
		DeleteResult result = collection.deleteOne(eq("_id", uuid));
		assertEquals(result.getDeletedCount(), 1);
	}

}
