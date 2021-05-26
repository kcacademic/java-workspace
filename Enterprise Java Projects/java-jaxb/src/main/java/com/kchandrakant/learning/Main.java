package com.kchandrakant.learning;

import static javax.xml.stream.XMLStreamConstants.START_ELEMENT;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.kchandrakant.learning.domain.Book;

public class Main {

	public static void main(String[] args) throws JAXBException, FileNotFoundException, XMLStreamException {

		marshal();
		unmarshallFile();
		unmarshallStax();

	}

	private static void marshal() throws JAXBException {
		Book book = new Book();
		book.setId(1L);
		book.setName("Book1");
		book.setAuthor("Author1");
		book.setDate(new Date());

		JAXBContext context = JAXBContext.newInstance(Book.class);
		Marshaller mar = context.createMarshaller();
		mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		mar.marshal(book, new File("./target/book.xml"));
	}

	private static void unmarshallFile() throws JAXBException, FileNotFoundException {
		JAXBContext context = JAXBContext.newInstance(Book.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		Book book = (Book) unmarshaller.unmarshal(new FileReader("./target/book.xml"));
		System.out.println(book);
	}

	private static void unmarshallStax() throws JAXBException, FileNotFoundException, XMLStreamException {
		// Set up a StAX reader
		XMLInputFactory xmlFactory = XMLInputFactory.newInstance();
		XMLStreamReader reader = xmlFactory.createXMLStreamReader(new FileReader("./target/book.xml"));
		// Set up JAXB context
		JAXBContext context = JAXBContext.newInstance(Book.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		// Read data as stream
		reader.nextTag();
		while (reader.getEventType() == START_ELEMENT) {
			System.out.println("Hi");
			JAXBElement<Book> boolElement = unmarshaller.unmarshal(reader, Book.class);
			Book book = boolElement.getValue();
			System.out.println(book);
			if (reader.getEventType() == XMLStreamConstants.CHARACTERS) {
				reader.next();
			}
		}
	}

}