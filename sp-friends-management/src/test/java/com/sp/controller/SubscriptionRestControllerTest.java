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

import com.sp.controllers.SubscriptionRestController;
import com.sp.services.FriendshipsService;
import com.sp.services.SubscriptionService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = SubscriptionRestController.class, secure = false)
public class SubscriptionRestControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SubscriptionService subscriptionService;

	@MockBean
	private FriendshipsService friendshipsService;

	@Test
	public void testCreateSuccess() throws Exception {
		String request = "{\"requestor\":\"andy@example.com\", \"target\":\"bobo@example.com\"}";
		Mockito.when(subscriptionService.createSubscription(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/subscription/create").content(request)
				.contentType(MediaType.APPLICATION_JSON);
		;
		MvcResult myResponse = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(myResponse.getResponse().getContentAsString());
		String expected = "{success:true}";
		JSONAssert.assertEquals(expected, myResponse.getResponse().getContentAsString(), false);
	}

	@Test
	public void testBlockSuccess() throws Exception {
		String request = "{\"requestor\":\"andy@example.com\", \"target\":\"bobo@example.com\"}";
		Mockito.when(subscriptionService.blockSubscription(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/subscription/block").content(request)
				.contentType(MediaType.APPLICATION_JSON);
		;
		MvcResult myResponse = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(myResponse.getResponse().getContentAsString());
		String expected = "{success:true}";
		JSONAssert.assertEquals(expected, myResponse.getResponse().getContentAsString(), false);
	}

	/**
	 * Mention 1 Friends 2 Subscription 1 Block 1 Result 3
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSearchesSuccess() throws Exception {
		String request = "{\"sender\":\"andy@example.com\", \"text\":\"Hello, kate@example.com\"}";
		Set<String> result = new HashSet<String>();
		Collections.addAll(result, "judy@example.com");
		Mockito.when(subscriptionService.searchSubscriptions(Mockito.anyString())).thenReturn(result);
		Set<String> result1 = new HashSet<String>();
		Collections.addAll(result1, "may@example.com", "linda@example.com");
		Mockito.when(friendshipsService.searchFriends(Mockito.anyString())).thenReturn(result1);
		Set<String> result2 = new HashSet<String>();
		Collections.addAll(result2, "linda@example.com");
		Mockito.when(subscriptionService.searchBlocksMe(Mockito.anyString())).thenReturn(result2);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/subscription/searches").content(request)
				.contentType(MediaType.APPLICATION_JSON);
		;
		MvcResult myResponse = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(myResponse.getResponse().getContentAsString());
		String expected = "{success:true,  recipients:[may@example.com, kate@example.com, judy@example.com]}";
		JSONAssert.assertEquals(expected, myResponse.getResponse().getContentAsString(), false);
	}

	/**
	 * Mention 1 Friends 2 Subscription 1 Block 0 Result 4
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSearchesSuccess1() throws Exception {
		String request = "{\"sender\":\"andy@example.com\", \"text\":\"Hello, kate@example.com\"}";
		Set<String> result = new HashSet<String>();
		Collections.addAll(result, "judy@example.com");
		Mockito.when(subscriptionService.searchSubscriptions(Mockito.anyString())).thenReturn(result);
		Set<String> result1 = new HashSet<String>();
		Collections.addAll(result1, "may@example.com", "linda@example.com");
		Mockito.when(friendshipsService.searchFriends(Mockito.anyString())).thenReturn(result1);
		Set<String> result2 = new HashSet<String>();
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/subscription/searches").content(request)
				.contentType(MediaType.APPLICATION_JSON);
		;
		MvcResult myResponse = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(myResponse.getResponse().getContentAsString());
		String expected = "{success:true,  recipients:[may@example.com, kate@example.com, judy@example.com, linda@example.com]}";
		JSONAssert.assertEquals(expected, myResponse.getResponse().getContentAsString(), false);
	}

	/**
	 * Mention 1 Friends 2 Subscription 0 Block 0 Result 3
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSearchesSuccess2() throws Exception {
		String request = "{\"sender\":\"andy@example.com\", \"text\":\"Hello, kate@example.com\"}";
		Set<String> result1 = new HashSet<String>();
		Collections.addAll(result1, "may@example.com", "linda@example.com");
		Mockito.when(friendshipsService.searchFriends(Mockito.anyString())).thenReturn(result1);
		Set<String> result2 = new HashSet<String>();
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/subscription/searches").content(request)
				.contentType(MediaType.APPLICATION_JSON);
		;
		MvcResult myResponse = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(myResponse.getResponse().getContentAsString());
		String expected = "{success:true,  recipients:[may@example.com, kate@example.com, linda@example.com]}";
		JSONAssert.assertEquals(expected, myResponse.getResponse().getContentAsString(), false);
	}

	/**
	 * Mention 1 Friends 0 Subscription 0 Block 0 Result 1
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSearchesSuccess3() throws Exception {
		String request = "{\"sender\":\"andy@example.com\", \"text\":\"Hello, kate@example.com\"}";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/subscription/searches").content(request)
				.contentType(MediaType.APPLICATION_JSON);
		;
		MvcResult myResponse = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(myResponse.getResponse().getContentAsString());
		String expected = "{success:true,  recipients:[kate@example.com]}";
		JSONAssert.assertEquals(expected, myResponse.getResponse().getContentAsString(), false);
	}

	/**
	 * Mention 0 Friends 0 Subscription 0 Block 0 Result 0
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSearchesSuccess4() throws Exception {
		String request = "{\"sender\":\"andy@example.com\", \"text\":\"Hello, World\"}";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/subscription/searches").content(request)
				.contentType(MediaType.APPLICATION_JSON);
		;
		MvcResult myResponse = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(myResponse.getResponse().getContentAsString());
		String expected = "{success:true,  recipients:[]}";
		JSONAssert.assertEquals(expected, myResponse.getResponse().getContentAsString(), false);
	}
}
