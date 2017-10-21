package com.sp.controller;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.sp.controllers.FriendshipsRestController;
import com.sp.services.FriendshipsService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = FriendshipsRestController.class, secure = false)
public class FriendshipsRestControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private FriendshipsService friendshipsService;

	private String createConnectionJson = "{\"friends\":[\"andy@example.com\",\"john@example.com\"]}";

	@Test
	public void testCreateConnectionSuccess() throws Exception {
		Mockito.when(friendshipsService.createConnection(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/friendships/create/")
				.content(createConnectionJson).contentType(MediaType.APPLICATION_JSON);
		;
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = "{success:true}";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void testCreateConnectionFailedForService() throws Exception {
		Mockito.when(friendshipsService.createConnection(Mockito.anyString(), Mockito.anyString())).thenReturn(false);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/friendships/create/")
				.content(createConnectionJson).contentType(MediaType.APPLICATION_JSON);
		;
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = "{success:false}";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void testCreateConnectionFailedForOneEmail() throws Exception {
		String request = "{\"friends\":[\"andy@example.com\"]}";
		Mockito.when(friendshipsService.createConnection(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/friendships/create/").content(request)
				.contentType(MediaType.APPLICATION_JSON);
		;
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = "{success:false, errorMessage:No friends list provided.}";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void testSearchSuccess() throws Exception {
		String request = "{\"email\":\"andy@example.com\"}";
		Set<String> result = new HashSet<String>();
		Collections.addAll(result, "june@example.com", "judy@example.com");
		Mockito.when(friendshipsService.searchFriends(Mockito.anyString())).thenReturn(result);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/friendships/searches/").content(request)
				.contentType(MediaType.APPLICATION_JSON);
		;
		MvcResult myResponse = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(myResponse.getResponse().getContentAsString());
		String expected = "{success:true, friends:[june@example.com,judy@example.com], count:2}";
		JSONAssert.assertEquals(expected, myResponse.getResponse().getContentAsString(), false);
	}

	@Test
	public void testSearchCommonSuccess() throws Exception {
		String request = "{\"friends\":[\"be2n@example.com\",\"ben1@example.com\"]}";
		Set<String> result = new HashSet<String>();
		Collections.addAll(result, "june@example.com", "judy@example.com");
		Mockito.when(friendshipsService.searchFriends(Mockito.anyList())).thenReturn(result);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/friendships/searches/common").content(request)
				.contentType(MediaType.APPLICATION_JSON);
		;
		MvcResult myResponse = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(myResponse.getResponse().getContentAsString());
		String expected = "{success:true, friends:[june@example.com,judy@example.com], count:2}";
		JSONAssert.assertEquals(expected, myResponse.getResponse().getContentAsString(), false);
	}
}
