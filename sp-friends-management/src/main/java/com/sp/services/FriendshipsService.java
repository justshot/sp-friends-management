package com.sp.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class FriendshipsService {
	private final static Map<String, Set<String>> friends = new HashMap<>();

	public boolean createConnection(String email1, String email2) {
		if (email1 == null || email2 == null) {
			return false;
		}
		return friends.computeIfAbsent(email1, v -> new HashSet<>()).add(email2)
				& friends.computeIfAbsent(email2, v -> new HashSet<>()).add(email1);
	}

	public Set<String> searchFriends(String email) {
		return friends.getOrDefault(email, new HashSet<String>());
	}

	public Set<String> searchFriends(List<String> emails) {
		Set<String> set = null;
		for (String email : emails) {
			if (set == null) {
				set = friends.get(email);
			} else {
				set.retainAll(friends.get(email));
			}
		}
		return set;
	}
}
