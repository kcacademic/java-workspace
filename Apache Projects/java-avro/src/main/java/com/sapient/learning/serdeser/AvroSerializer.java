package com.sapient.learning.serdeser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;

import com.sapient.learning.model.Customer;

public class AvroSerializer {

	public byte[] serealizeAvroHttpRequestJSON(Customer customer) {
		DatumWriter<Customer> writer = new SpecificDatumWriter<>(Customer.class);
		byte[] data = new byte[0];
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		Encoder jsonEncoder = null;
		try {
			jsonEncoder = EncoderFactory.get().jsonEncoder(Customer.getClassSchema(), stream);
			writer.write(customer, jsonEncoder);
			jsonEncoder.flush();
			data = stream.toByteArray();
		} catch (IOException e) {
			System.out.println("Serialization error " + e.getMessage());
		}
		return data;
	}

}
