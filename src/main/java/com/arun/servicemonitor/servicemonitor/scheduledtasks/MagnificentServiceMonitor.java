package com.arun.servicemonitor.servicemonitor.scheduledtasks;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.arun.servicemonitor.servicemonitor.monitorstatus.MonitorStatusHolder;

@Component
public class MagnificentServiceMonitor {

	@Value("${magnificent_service}")
	private String magnificentService;

	@Value("${magnificent_text}")
	private String magnificentText;
	
	@Autowired
	private MonitorStatusHolder monitorStatusHolder;
	
	private static final Logger log = LoggerFactory.getLogger(MagnificentServiceMonitor.class);

	// Each execution of the task is independent and service monitor happens
	// every 10 seconds
	@Scheduled(fixedRate = 10000)
	public void reportCurrentTime() {
		log.debug("Magnificent Service Monitor Started");

		try {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response = restTemplate.getForEntity(magnificentService, String.class);
			log.debug(response.getBody());
			// Catch failure in terms of status code 
			if (response.getStatusCode() == HttpStatus.OK) {
				// Catch failure in terms of expected text
				if(response.getBody().equals(magnificentText)){
					log.debug("Success");
					monitorStatusHolder.setValuefifoFixedQueue(String.format("Service work well at : %s ", new Date().toString()));
					
				}else {
					log.debug("Filure");
					monitorStatusHolder.setValuefifoFixedQueue(String.format("Service Failed at : %s ", new Date().toString()));
				}
				
			} else {
				log.debug("Filure");
				monitorStatusHolder.setValuefifoFixedQueue(String.format("Service Failed at : %s ", new Date().toString()));
			}
		} // Catch failure in terms of unreachable server error
		catch(org.springframework.web.client.HttpServerErrorException e){
			log.debug("Filure");
			monitorStatusHolder.setValuefifoFixedQueue(String.format("Service Failed at : %s ", new Date().toString()));
		}
		
		log.debug("Magnificent Service Monitor End");
	}

}
