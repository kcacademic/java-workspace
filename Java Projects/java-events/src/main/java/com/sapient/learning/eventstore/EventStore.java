package com.sapient.learning.eventstore;

import java.net.InetSocketAddress;
import java.util.UUID;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.Status;
import akka.actor.Status.Failure;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import eventstore.akka.Settings;
import eventstore.akka.tcp.ConnectionActor;
import eventstore.core.EsException;
import eventstore.core.Event;
import eventstore.core.EventData;
import eventstore.core.ReadEvent;
import eventstore.core.ReadEventCompleted;
import eventstore.core.ReadStreamEvents;
import eventstore.core.WriteEvents;
import eventstore.core.WriteEventsCompleted;
import eventstore.j.EventDataBuilder;
import eventstore.j.ReadEventBuilder;
import eventstore.j.ReadStreamEventsBuilder;
import eventstore.j.SettingsBuilder;
import eventstore.j.WriteEventsBuilder;

public class EventStore {

	public static void main(String[] args) {

		final ActorSystem system = ActorSystem.create();
		final Settings settings = new SettingsBuilder()
				.address(new InetSocketAddress("127.0.0.1", 1113))
				.defaultCredentials("admin", "changeit").build();
		final ActorRef connection = system
				.actorOf(ConnectionActor.getProps(settings));

		final ActorRef writeResult = system
				.actorOf(Props.create(WriteResult.class));

		final EventData event = new EventDataBuilder("event-type")
				.eventId(UUID.randomUUID()).jsonData("{\"a\": \"1\"}").build();

		final WriteEvents writeEvents = new WriteEventsBuilder("newstream")
				.addEvent(event).expectAnyVersion().build();

		connection.tell(writeEvents, writeResult);

		final ActorRef readResult = system
				.actorOf(Props.create(ReadResult.class));

		final ReadStreamEvents readEvents = new ReadStreamEventsBuilder(
				"newstream").maxCount(10).resolveLinkTos(false)
						.requireMaster(true).build();

		connection.tell(readEvents, readResult);

		final ReadEvent readEvent = new ReadEventBuilder("newstream").first()
				.resolveLinkTos(false).requireMaster(true).build();

		connection.tell(readEvent, readResult);

	}

	public static class WriteResult extends AbstractActor {

		final LoggingAdapter log = Logging.getLogger(getContext().system(),
				this);

		@Override
		public Receive createReceive() {
			return receiveBuilder().match(WriteEventsCompleted.class, m -> {
				log.info("range: {}, position: {}", m.numbersRange(),
						m.position());
				context().system().terminate();
			}).match(Status.Failure.class, f -> {
				final EsException exception = (EsException) f.cause();
				log.error(exception, exception.toString());
			}).build();
		}
	}

	public static class ReadResult extends AbstractActor {
		final LoggingAdapter log = Logging.getLogger(getContext().system(),
				this);

		@Override
		public Receive createReceive() {
			return receiveBuilder().match(ReadEventCompleted.class, m -> {
				final Event event = m.event();
				log.info("event: {}", event);
				context().system().terminate();
			}).match(Failure.class, f -> {
				final EsException exception = (EsException) f.cause();
				log.error(exception, exception.toString());
				context().system().terminate();
			}).build();
		}
	}

}
