package io.jgirlapps.authserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@SpringBootApplication
@EnableDiscoveryClient
@EnableResourceServer
@RestController
public class AuthserverApplication {
	
	private static final Logger LOG = LoggerFactory.getLogger(AuthserverApplication.class);

	@RequestMapping("/user")
	public Principal user(Principal user) {
		LOG.info("user method called");
		return user;
	}

	public static void main(String[] args) {
		SpringApplication.run(AuthserverApplication.class, args);
	}
	
	@Configuration	
	@EnableAuthorizationServer
	protected static class OAuth2Config extends AuthorizationServerConfigurerAdapter {

		@Autowired
		private AuthenticationManager authenticationManager;
		
		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
			LOG.info("configure endpoints has been called");
			endpoints.authenticationManager(authenticationManager);
		}
		
		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
			
			LOG.info("configure clients has been called");
			
			clients.inMemory()
				.withClient("acme")
				.secret("acmesecret")
				.authorizedGrantTypes("authorization_code", "refresh_token", "implicit", "password", "client_credentials")
				.autoApprove(true)
				.scopes("webshop");
		}
	}
}
