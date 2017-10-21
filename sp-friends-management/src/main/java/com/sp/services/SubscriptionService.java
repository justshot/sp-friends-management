package com.sp.services;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {
	private final static Map<String, Set<String>> subscriptions = new HashMap<>();
	private final static Map<String, Set<String>> iBlocks = new HashMap<>();
	private final static Map<String, Set<String>> blockMe = new HashMap<>();

	public boolean createSubscription(String requestor, String target) {
		if (requestor == null || target == null) {
			return false;
		}
		return subscriptions.computeIfAbsent(target, v -> new HashSet<>()).add(requestor);
	}

	public boolean blockSubscription(String requestor, String target) {
		if (requestor == null || target == null) {
			return false;
		}
		return iBlocks.computeIfAbsent(requestor, v -> new HashSet<>()).add(target)
				& blockMe.computeIfAbsent(target, v -> new HashSet<>()).add(requestor);
	}

	public Set<String> searchSubscriptions(String email) {
		return subscriptions.getOrDefault(email, Collections.EMPTY_SET);
	}

	public Set<String> searchBlocksMe(String email) {
		return blockMe.getOrDefault(email, Collections.EMPTY_SET);
	}
}
