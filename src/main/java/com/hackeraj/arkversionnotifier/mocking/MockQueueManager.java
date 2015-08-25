package com.hackeraj.arkversionnotifier.mocking;

import com.hackeraj.arkversionnotifier.queuetasks.QueueTask;
import com.hackeraj.arkversionnotifier.utils.QueueManager;

public class MockQueueManager extends QueueManager {
	
	
	public void sendMessage(String queueName, String taskClassName, String[] params) throws Exception {
		String fullyQualifiedName = "com.hackeraj.arkversionnotifier.queuetasks." + taskClassName;
        
        try {
			QueueTask task = (QueueTask) Class.forName(fullyQualifiedName).newInstance(); //build class
			task.setParams(params); //set params
			task.run(); //run
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			e.printStackTrace();
		}
        
	}
}
