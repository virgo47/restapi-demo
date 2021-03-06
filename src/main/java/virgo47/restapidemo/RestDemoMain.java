package virgo47.restapidemo;

import javax.servlet.Filter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@SpringBootApplication
public class RestDemoMain {

	public static void main(String[] args) {
		SpringApplication.run(RestDemoMain.class, args);
	}

	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		MappingJackson2HttpMessageConverter jsonConverter =
			new MappingJackson2HttpMessageConverter();
		// We can either create new ObjectMapper and set it (setter/constructor)
		// or modify the default one.
//		ObjectMapper objectMapper = new ObjectMapper();
//		jsonConverter.setObjectMapper(objectMapper);
		ObjectMapper objectMapper = jsonConverter.getObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		objectMapper.registerModule(new DemoConverterModule());
		return jsonConverter;
	}

	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		Filter threadRenameFilter = new ThreadRenameFilter();
		registrationBean.setFilter(threadRenameFilter);
		registrationBean.setOrder(1);
		return registrationBean;
	}
}
