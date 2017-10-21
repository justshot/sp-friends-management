package com.sp.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sp.entities.ResponseWrapper;
import com.sp.entities.SubscriptionRequestWrapper;
import com.sp.services.FriendshipsService;
import com.sp.services.SubscriptionService;
import com.sp.utils.Texts;

@Controller
@RequestMapping("/subscription")
public class SubscriptionRestController {
	@Autowired
	SubscriptionService subscriptionService;

	@Autowired
	FriendshipsService friendshipsService;

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public @ResponseBody ResponseWrapper createSubscription(@RequestBody SubscriptionRequestWrapper requestWrapper) {
		ResponseWrapper responseWrapper = new ResponseWrapper();
		if (requestWrapper.getRequestor() != null && requestWrapper.getTarget() != null) {
			subscriptionService.createSubscription(requestWrapper.getRequestor(), requestWrapper.getTarget());
			responseWrapper.setSuccess(true);
		} else {
			responseWrapper.setSuccess(false);
			responseWrapper.setErrorMessage("No email list provided.");
		}
		return responseWrapper;
	}

	@RequestMapping(value = "block", method = RequestMethod.POST)
	public @ResponseBody ResponseWrapper block(@RequestBody SubscriptionRequestWrapper requestWrapper) {
		ResponseWrapper responseWrapper = new ResponseWrapper();
		if (requestWrapper.getRequestor() != null && requestWrapper.getTarget() != null) {
			subscriptionService.blockSubscription(requestWrapper.getRequestor(), requestWrapper.getTarget());
			responseWrapper.setSuccess(true);
		} else {
			responseWrapper.setSuccess(false);
			responseWrapper.setErrorMessage("No email list provided.");
		}
		return responseWrapper;
	}

	@RequestMapping(value = "searches", method = RequestMethod.POST)
	public @ResponseBody ResponseWrapper getRecipients(@RequestBody SubscriptionRequestWrapper requestWrapper) {
		ResponseWrapper responseWrapper = new ResponseWrapper();
		if (requestWrapper.getSender() != null) {
			Set<String> friends = friendshipsService.searchFriends(requestWrapper.getSender());
			Set<String> subscriptions = subscriptionService.searchSubscriptions(requestWrapper.getSender());
			Set<String> mentioned = Texts.extractEmails(requestWrapper.getText());
			Set<String> blocked = subscriptionService.searchBlocksMe(requestWrapper.getSender());

			friends.addAll(subscriptions);
			friends.addAll(mentioned);
			friends.removeAll(blocked);

			responseWrapper.setRecipients(friends);
			responseWrapper.setCount(friends.size());
			;
			responseWrapper.setSuccess(true);
		} else {
			responseWrapper.setSuccess(false);
			responseWrapper.setErrorMessage("No sender email provided.");
		}
		return responseWrapper;
	}
}
