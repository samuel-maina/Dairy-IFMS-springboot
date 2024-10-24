package com.stawisha.maziwa.erpz.APILoggers;

import ch.qos.logback.access.servlet.TeeFilter;
import java.security.Principal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

/**
 *
 * @author samuel
 */
@Configuration
public class RequestLoggingFilterConfig {
    
    @Bean
    public TeeFilter filter(){
    return new TeeFilter();
    }

}
