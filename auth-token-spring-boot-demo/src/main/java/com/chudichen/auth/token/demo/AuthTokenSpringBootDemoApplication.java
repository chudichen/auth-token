package com.chudichen.auth.token.demo;

import com.chudichen.auth.token.spring.EnableAuthToken;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 使用@EnableAuthToken即可开启auth token
 *
 * @author chudichen
 * @since 2020-09-22
 */
@EnableAuthToken
@SpringBootApplication
public class AuthTokenSpringBootDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthTokenSpringBootDemoApplication.class, args);
	}

}
