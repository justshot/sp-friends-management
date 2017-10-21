package com.sp.services;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class FriendshipsServiceTestCase {
	private FriendshipsService service = null;

	@Before
	public void setup() {
		service = new FriendshipsService();
	}

	@Test
	public void testCreateConnectionSuccess() {
		assertTrue(service.createConnection("email1", "email2"));
	}

	@Test
	public void testCreateConnectionFailure() {
		assertFalse(service.createConnection("email1", null));
	}

	@Test
	public void testSearchFriendsNoneIntersection() {
		service.createConnection("john1", "kate@gmail.com");
		service.createConnection("ben1", "lee@gmail.com");
		assertEquals(0, service.searchFriends(Arrays.asList("john1", "ben1")).size());
	}

	@Test
	public void testSearchFriendsPartialIntersection() {
		service.createConnection("john2", "kate@gmail.com");
		service.createConnection("john2", "moss@gmail.com");
		service.createConnection("ben2", "kate@gmail.com");
		service.createConnection("ben2", "john@gmail.com");
		assertArrayEquals(new String[] { "kate@gmail.com" },
				service.searchFriends(Arrays.asList("john2", "ben2")).toArray());
	}

	@Test
	public void testSearchFriendsFullIntersection() {
		service.createConnection("john3", "kate@gmail.com");
		service.createConnection("john3", "moss@gmail.com");
		service.createConnection("ben3", "kate@gmail.com");
		service.createConnection("ben3", "moss@gmail.com");
		assertArrayEquals(new String[] { "kate@gmail.com", "moss@gmail.com" },
				service.searchFriends(Arrays.asList("john3", "ben3")).toArray());
	}

}
