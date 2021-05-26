package com.kchandrakant.learning.serdeser;

import java.io.IOException;

import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;

import com.kchandrakant.learning.model.Customer;

public class AvroDeserializer {

	public Customer deSerealizeAvroHttpRequestJSON(byte[] data) {
		DatumReader<Customer> reader = new SpecificDatumReader<>(Customer.class);
		Decoder decoder = null;
		try {
			decoder = DecoderFactory.get().jsonDecoder(Customer.getClassSchema(), new String(data));
			return reader.read(null, decoder);
		} catch (IOException e) {
			System.out.println("Deserialization error" + e.getMessage());
		}
		return null;
	}

}
