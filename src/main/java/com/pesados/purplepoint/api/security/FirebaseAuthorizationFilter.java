package com.pesados.purplepoint.api.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pesados.purplepoint.api.security.firebase.FirebaseAuthenticationToken;
import com.pesados.purplepoint.api.security.firebase.FirebaseParser;
import com.pesados.purplepoint.api.security.firebase.FirebaseTokenHolder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class FirebaseAuthorizationFilter extends OncePerRequestFilter {
	
	private static String HEADER_NAME = "X-Authorization-Firebase";

	@Autowired
	private FirebaseParser firebaseService;

	public FirebaseAuthorizationFilter(FirebaseParser firebaseService) {
		this.firebaseService = firebaseService;
	}

	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String xAuth = request.getHeader(HEADER_NAME);
		/*
		
		*/
		if ( xAuth == null || xAuth.equals("")) {
			String path = request.getRequestURI();
			String[] values = {"/v3/api-", "/swagger", "/api-doc"};
			boolean allowed_urls = Arrays.stream(values).anyMatch(path.substring(0,8)::equals);
			if (allowed_urls) {
				SecurityContextHolder.getContext().setAuthentication(
					new FirebaseAuthenticationToken("swagger", 
						null, 
						AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_DEVICE")
					)
				);
			}
			filterChain.doFilter(request, response);
			return;
		} else {
			try {
				FirebaseTokenHolder holder = firebaseService.parseToken(xAuth);
				if (holder != null) {
					String userName = holder.getUid();
					
					List<GrantedAuthority> res = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_DEVICE");
					Authentication auth = new FirebaseAuthenticationToken(userName, holder, res);
					SecurityContextHolder.getContext().setAuthentication(auth);
				}
				
				filterChain.doFilter(request, response);
			} catch (SecurityException e) {
				throw new SecurityException(e);
			}
		}
	}

}
