package com.papi.controller;

import java.security.SecureRandom;

public class sample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SecureRandom random = new SecureRandom();
		byte bytes[] = new byte[20];
		random.nextBytes(bytes);
		String token = bytes.toString();
		System.out.println("praveenshetg:"+token);
	}

}
