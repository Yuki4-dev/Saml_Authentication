package com.saml;

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

import com.saml.component.SamlAplComponentIntf;
import com.saml.controller.SamlAplController;

@SpringBootTest
@AutoConfigureMockMvc
class SamlApplicationTests
{
	private final String AUTH_TOKEN = "authtoken";

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SamlAplComponentIntf  SamlAplComponent;

	@InjectMocks
	private SamlAplController samlAplController;

	@Test
	void test1() throws Exception
	{
		var token = "test";
		when(SamlAplComponent.check(token)).thenReturn(true);
		mockMvc.perform(get("/hoge")
				.cookie(new Cookie(AUTH_TOKEN,token)))
				.andExpect(status().isOk());
	}

	@Test
	void test2() throws Exception
	{
		var token = "test";
		var url = "hoge";
		when(SamlAplComponent.check(token)).thenReturn(false);
		mockMvc.perform(get("/" + url)
				.cookie(new Cookie(AUTH_TOKEN,token)))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrlPattern("http://*/auth/login?targeturl=*" + url));
	}
}
