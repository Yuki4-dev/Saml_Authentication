package com.auth;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.servlet.http.Cookie;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.auth.component.AuthorizationComponentIntf;
import com.auth.controller.AuthorizationController;


@SpringBootTest
@AutoConfigureMockMvc
class AuthApplicationTests
{
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AuthorizationComponentIntf authorizationComponent;

	@InjectMocks
	private AuthorizationController authorizationController;

	private final String TARGET_URL = "targeturl";
	private final String AUTH_TOKEN = "authtoken";
	private final String AUTH_OK = "ok";
	private final String AUTH_NG = "ng";
	private final String USERNAME = "username";
	private final String PASSWORD = "password";

	@Test
	void test1() throws Exception
	{
		var token = "test";
		when(authorizationComponent.checkToken(any(),any())).thenReturn(true);
		mockMvc.perform(get("/check")
				.param(AUTH_TOKEN, token))
				.andExpect(status().isOk())
				.andExpect(content().string(AUTH_OK));
	}

	@Test
	void test2() throws Exception
	{
		var token = "test";
		when(authorizationComponent.checkToken(any(),any())).thenReturn(false);
		mockMvc.perform(get("/check")
				.param(AUTH_TOKEN, token))
				.andExpect(status().isOk())
				.andExpect(content().string(AUTH_NG));
	}

	@Test
	void test3() throws Exception
	{
		var url = "http://www.test.com";
		mockMvc.perform(get("/login")
				.param(TARGET_URL, url))
				.andExpect(status().isOk())
				.andExpect(view().name("login"));
	}

	@Test
	void test4() throws Exception
	{
		var domain = "test.com";
		var url = "http://www." + domain;
		var token = "test";

		when(authorizationComponent.login(any(), any())).thenReturn(true);
		when(authorizationComponent.createToken()).thenReturn(new Cookie(AUTH_TOKEN, token));

		mockMvc.perform(get("/login.do")
				.sessionAttr(TARGET_URL, url)
				.param(USERNAME, "test3")
				.param(PASSWORD, "3333"))
				.andExpect(status().is3xxRedirection())
				.andExpect(cookie().domain(AUTH_TOKEN, domain))
				.andExpect(cookie().value(AUTH_TOKEN, token))
				.andExpect(redirectedUrl(url));
	}

	@Test
	void test5() throws Exception
	{
		var domain = "test.com";
		var url = "http://www." + domain;
		var token = "test";

		when(authorizationComponent.login(any(), any())).thenReturn(false);
		when(authorizationComponent.createToken()).thenReturn(new Cookie(AUTH_TOKEN, token));

		mockMvc.perform(get("/login.do")
				.sessionAttr(TARGET_URL, url)
				.param(USERNAME, "test3")
				.param(PASSWORD, "3333"))
				.andExpect(status().isOk())
				.andExpect(model().attribute("error", true))
				.andExpect(view().name("login"));
	}
}
