package com.msalmi;

import java.math.BigInteger;
import java.security.MessageDigest;

import org.keycloak.credential.hash.PasswordHashProvider;
import org.keycloak.models.PasswordPolicy;
import org.keycloak.models.credential.PasswordCredentialModel;

public class MD5HashProvider implements PasswordHashProvider {

	private final String providerId;

	public MD5HashProvider(String providerId) {
		this.providerId = providerId;
	}

	@Override
	public void close() {
	}

	@Override
	public boolean policyCheck(PasswordPolicy policy, PasswordCredentialModel credential) {
		return this.providerId.equals(credential.getPasswordCredentialData().getAlgorithm());
	}

	@Override
	public PasswordCredentialModel encodedCredential(String rawPassword, int iterations) {
		String encodedPassword = this.encode(rawPassword, iterations);
		return PasswordCredentialModel.createFromValues(this.providerId, new byte[0], iterations, encodedPassword);
	}

	@Override
	public boolean verify(String rawPassword, PasswordCredentialModel credential) {
		String encodedPassword = this.encode(rawPassword, credential.getPasswordCredentialData().getHashIterations());
		String hash = credential.getPasswordSecretData().getValue();
		return encodedPassword.equals(hash);
	}

	@Override
	public String encode(String rawPassword, int iterations) {
		try {
			MessageDigest md = MessageDigest.getInstance(this.providerId);
			md.update(rawPassword.getBytes());

			// convert the digest byte[] to BigInteger
			var aux = new BigInteger(1, md.digest());

			// convert BigInteger to 32-char lowercase string using leading 0s
			return String.format("%032x", aux);
		} catch (Exception e) {
			// fail silently
		}

		return null;
	}

}
