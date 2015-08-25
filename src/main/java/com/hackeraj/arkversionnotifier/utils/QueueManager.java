package com.hackeraj.arkversionnotifier.utils;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.hackeraj.arkversionnotifier.queuetasks.QueueTask;

public class QueueManager {
	public static final String QNAME_CONFIRMATION = "confirmationQueue";
	
	public void sendMessage(String queueName, String taskClassName, String[] params) throws Exception {
		String fullyQualifiedName = "com.hackeraj.arkversionnotifier.queuetasks." + taskClassName;
		
		Object task = Class.forName(fullyQualifiedName).newInstance();
		if (!(task instanceof QueueTask)) {
			throw new Exception("Can only use a taskClassName of a class that extends a QueueTask");
		}
		
		String token = Encryption.encrypt(String.valueOf(System.currentTimeMillis()));
		
		Queue queue = QueueFactory.getQueue(queueName);
		queue.add(TaskOptions.Builder
				.withUrl("/queue/worker")
				.param(Globals.SERVLET_PARAM_TASK, fullyQualifiedName)
				.param(Globals.SERVLET_PARAM_SECURITY_TOKEN, token)
				.param(Globals.SERVLET_PARAM_PARAMS, Utils.arrayToString(params)));
	}
	
}
