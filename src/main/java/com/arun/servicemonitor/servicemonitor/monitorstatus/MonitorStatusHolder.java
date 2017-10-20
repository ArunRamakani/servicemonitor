package com.arun.servicemonitor.servicemonitor.monitorstatus;

import org.springframework.stereotype.Component;

import java.util.Queue;
import org.apache.commons.collections4.queue.CircularFifoQueue;


// Bean to hold last 50 monitor status
@Component
public class MonitorStatusHolder {

	private Queue<String> fifoFixedQueue = new CircularFifoQueue<String>(50);
	
	public Queue<String> getfifoFixedQueue(){
		return fifoFixedQueue;
	}
	public void setValuefifoFixedQueue(String Status){
		 fifoFixedQueue.add(Status);
	}

}
