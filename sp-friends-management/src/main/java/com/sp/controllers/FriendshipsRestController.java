package com.sp.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sp.entities.FriendsRequestWrapper;
import com.sp.entities.ResponseWrapper;
import com.sp.services.FriendshipsService;

@Controller
@RequestMapping("/friendships")
public class FriendshipsRestController {
	@Autowired
	FriendshipsService friendshipsService;

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public @ResponseBody ResponseWrapper createConnection(@RequestBody FriendsRequestWrapper requestWrapper) {
		ResponseWrapper responseWrapper = new ResponseWrapper();
		if (requestWrapper.getFriends() != null && requestWrapper.getFriends().size() > 1) {
			responseWrapper.setSuccess(friendshipsService.createConnection(requestWrapper.getFriends().get(0),
					requestWrapper.getFriends().get(1)));
		} else {
			responseWrapper.setSuccess(false);
			responseWrapper.setErrorMessage("No friends list provided.");
		}
		return responseWrapper;
	}

	@RequestMapping(value = "searches", method = RequestMethod.POST)
	public @ResponseBody ResponseWrapper getFriends(@RequestBody FriendsRequestWrapper requestWrapper) {
		ResponseWrapper responseWrapper = new ResponseWrapper();
		if (requestWrapper.getEmail() != null) {
			Set<String> friends = friendshipsService.searchFriends(requestWrapper.getEmail());
			responseWrapper.setFriends(friends);
			responseWrapper.setCount(friends.size());
			;
			responseWrapper.setSuccess(true);
		} else {
			responseWrapper.setSuccess(false);
			responseWrapper.setErrorMessage("No email provided.");
		}
		return responseWrapper;
	}

	@RequestMapping(value = "/searches/common", method = RequestMethod.POST)
	public @ResponseBody ResponseWrapper getCommonFriends(@RequestBody FriendsRequestWrapper requestWrapper) {
		ResponseWrapper responseWrapper = new ResponseWrapper();
		if (requestWrapper.getFriends() != null && requestWrapper.getFriends().size() != 0) {
			Set<String> friends = friendshipsService.searchFriends(requestWrapper.getFriends());
			responseWrapper.setFriends(friends);
			responseWrapper.setCount(friends.size());
			;
			responseWrapper.setSuccess(true);
		} else {
			responseWrapper.setSuccess(false);
			responseWrapper.setErrorMessage("No email provided.");
		}
		return responseWrapper;
	}
}
