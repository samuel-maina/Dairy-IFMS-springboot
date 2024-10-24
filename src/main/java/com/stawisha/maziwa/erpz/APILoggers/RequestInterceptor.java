
package com.stawisha.maziwa.erpz.APILoggers;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author samuel
 */
@Component
@Configuration
public class RequestInterceptor implements WebMvcConfigurer{
@Override
public void addInterceptors(InterceptorRegistry registry){
    //registry.addInterceptor(new RequestLogger());
}


}
