package com.chudichen.auth.token.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 将此注解加到spring boot启动类上，即可完成auth-token与spring boot的集成
 *
 * @author chudichen
 * @since 2020-09-22
 */
@Documented
@Configuration
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({SpringAuthToken.class})
public @interface EnableAuthToken {
}
