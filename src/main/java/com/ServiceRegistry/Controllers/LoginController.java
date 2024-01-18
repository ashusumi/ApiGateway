package com.ServiceRegistry.Controllers;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ui.Model;
import com.ServiceRegistry.Model.AuthResponse;

@SuppressWarnings("deprecation")
@RestController
@RequestMapping("/auth")
public class LoginController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@GetMapping("/login")
	public ResponseEntity<AuthResponse> login(@RegisteredOAuth2AuthorizedClient("okta") OAuth2AuthorizedClient client,
			@org.springframework.security.core.annotation.AuthenticationPrincipal OidcUser user, Model model) {

		logger.info("user email id :{}", user.getEmail());

		AuthResponse authResponse = new AuthResponse();
		authResponse.setUserId(user.getEmail());
		authResponse.setAccessToken(client.getAccessToken().getTokenValue());
//		authResponse.setRefreshToken(client.getRefreshToken().getTokenValue());
		authResponse.setExpiringAt(client.getAccessToken().getExpiresAt().getEpochSecond());
		Collection<String> authority = user.getAuthorities().stream().map(GrantedAuthority -> {
			return GrantedAuthority.getAuthority();
		}).collect(Collectors.toList());
		authResponse.setAuthorities(authority);
		return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.OK);
		
	}

}
