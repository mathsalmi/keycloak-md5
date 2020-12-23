package com.msalmi;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MD5HashProviderTest {

	@Test
	public void encodeHelloWorld() {
		final var provider = new MD5HashProvider(MD5HashProviderFactory.ID);
		var expected = "5eb63bbbe01eeed093cb22bb8f5acdc3";
		var encoded = provider.encode("hello world", 0);
		assertTrue(encoded.equals(expected));
	}
	
	@Test
	public void encodeEmptyString() {
		final var provider = new MD5HashProvider(MD5HashProviderFactory.ID);
		var expected = "d41d8cd98f00b204e9800998ecf8427e";
		var encoded = provider.encode("", 0);
		assertTrue(encoded.equals(expected));
	}
	
	@Test
	public void ensureIterationParameterIsIgnored() {
		final var provider = new MD5HashProvider(MD5HashProviderFactory.ID);
		var expected = "d41d8cd98f00b204e9800998ecf8427e";
		var encoded = provider.encode("", 0);
		assertTrue(encoded.equals(expected));
		
		expected = "d41d8cd98f00b204e9800998ecf8427e";
		encoded = provider.encode("", 42); // any random number
		assertTrue(encoded.equals(expected));
	}
}
