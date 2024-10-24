package com.stawisha.maziwa.erpz.APILoggers;

import ch.qos.logback.access.servlet.TeeFilter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfiguration {
    @Autowired
    private TeeFilter filters;

	@Autowired
	@Bean
	public FilterRegistrationBean requestResponseFilter() {

    	final FilterRegistrationBean<TeeFilter> filterRegBean = new FilterRegistrationBean();
    	TeeFilter filter = new TeeFilter();
        
    	filterRegBean.setFilter(filter);
        List<String>urlPatterns= new ArrayList<>();
        urlPatterns.add("/api/v1/");
    	filterRegBean.setUrlPatterns(urlPatterns);
    	filterRegBean.setName("Request Response Filter");
    	filterRegBean.setAsyncSupported(Boolean.TRUE);
    	return filterRegBean;
	}
        
       
}