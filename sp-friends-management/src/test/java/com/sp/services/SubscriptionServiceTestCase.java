package com.sp.services;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

public class SubscriptionServiceTestCase {
	private static SubscriptionService service = null;

	@BeforeClass
	public static void setup() {
		service = new SubscriptionService();
	}

	@Test
	public void testCreateSubscriptionSuccess() {
		assertTrue(service.createSubscription("email1", "email2"));
	}

	@Test
	public void testCreateSubscriptionFailure() {
		assertFalse(service.createSubscription("email1", null));
	}
}
