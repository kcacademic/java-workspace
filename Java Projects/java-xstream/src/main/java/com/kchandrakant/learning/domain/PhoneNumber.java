package com.kchandrakant.learning.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PhoneNumber {
	private int code;
	private String number;
}
