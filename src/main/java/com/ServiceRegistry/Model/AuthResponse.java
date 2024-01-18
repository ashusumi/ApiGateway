package com.ServiceRegistry.Model;

import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthResponse {

	private String userId;
	
	private String accessToken;
	
	private String refreshToken;
	
	private long expiringAt;
	
	private Collection<String> authorities;
}
