package com.hackeraj.arkversionnotifier.servlets;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.routines.EmailValidator;

import com.hackeraj.arkversionnotifier.datamodel.Subscription;
import com.hackeraj.arkversionnotifier.mocking.MockDataManager;
import com.hackeraj.arkversionnotifier.mocking.MockQueueManager;
import com.hackeraj.arkversionnotifier.mocking.MockingUtils;
import com.hackeraj.arkversionnotifier.utils.DataManager;
import com.hackeraj.arkversionnotifier.utils.QueueManager;

public class SubscriptionServlet extends HttpServlet {
	
	private static final DataManager dataManager = 
			!MockingUtils.isMocking()
			? new DataManager()
			: new MockDataManager();
	private static final QueueManager queueManager = 
			!MockingUtils.isMocking()
			? new QueueManager()
			: new MockQueueManager();

	private static final long serialVersionUID = 712010313100708028L;
	private static final Logger logger = Logger.getLogger(SubscriptionServlet.class.getName());

	
	@Override
	  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
				
	    Subscription subscription = null;
	    String email = req.getParameter("email");
	    String[] subscriptionChoices = req.getParameterValues("subscriptionChoices");
	    boolean notifyUpcoming = false;
	    boolean notifyETAChange = false;
	    boolean notifyAvailable = false;
	    
	    for (String choice : subscriptionChoices) {
	    	if (choice.equals("upcoming")) {
	    		notifyUpcoming = true;
	    	} else
    		if (choice.equals("eta")) {
    			notifyETAChange = true;
	    	} else
    		if (choice.equals("available")) {
    			notifyAvailable = true;
	    	} 
	    }
	    
	    EmailValidator validator = EmailValidator.getInstance();
	    
	    if(validator.isValid(email)) {
		    subscription = new Subscription(email,
					notifyUpcoming,
					notifyETAChange,
					notifyAvailable);
		    
		    //get previous entities for this email address if any
		    dataManager.deleteSubscription(email);

	    	//subscribe new entity
	    	dataManager.saveSubscription(subscription);

	    	//send confirmation email
	    	try {
		    	String[] array = {email};
				queueManager.sendMessage(QueueManager.QNAME_CONFIRMATION, 
						"ConfirmationTask", 
						array);
			} catch (Exception e) {
				logger.log(Level.SEVERE, "doPost -> unable to place task on queue", e);
			}
		    
		    resp.sendRedirect("/success.jsp");
	    } else {
	    	resp.sendRedirect("/subscribe.jsp?invalid=true");
	    }
	    

	  }
	
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		if("unsubscribe".equalsIgnoreCase(req.getParameter("type"))) {
			String email = req.getParameter("email");

			dataManager.deleteSubscription(email);
		}
				
		resp.sendRedirect("/subscribe.jsp?unsubscribe=true");
	}
	
}
