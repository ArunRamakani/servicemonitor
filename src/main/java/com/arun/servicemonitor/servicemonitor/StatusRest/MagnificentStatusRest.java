package com.arun.servicemonitor.servicemonitor.StatusRest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arun.servicemonitor.servicemonitor.monitorstatus.MonitorStatusHolder;

@RestController
public class MagnificentStatusRest {

	@Autowired
	private MonitorStatusHolder monitorStatusHolder;
	
	// Prints the latest 50 monitoring status
	@RequestMapping("/")
    public String magnificentStatusRest() {
		
		String totalStatus= "";
		for (String status : monitorStatusHolder.getfifoFixedQueue()) {
			totalStatus = String.format("%s </br> %s", totalStatus, status);
        }
		return totalStatus;
    }

}
