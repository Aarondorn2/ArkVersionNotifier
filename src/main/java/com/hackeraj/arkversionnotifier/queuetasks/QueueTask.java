package com.hackeraj.arkversionnotifier.queuetasks;


public class QueueTask implements Runnable {
	
	protected String[] params = null;
	
	@Override
	public void run() {	}
	
	
	
	public void setParams(String[] params) {
		this.params = params;
	}

}
