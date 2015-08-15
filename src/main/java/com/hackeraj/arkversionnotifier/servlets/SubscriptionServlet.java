package com.hackeraj.arkversionnotifier.servlets;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.routines.EmailValidator;

import com.hackeraj.arkversionnotifier.datamodel.Subscription;
import com.hackeraj.arkversionnotifier.utils.Hash;

public class SubscriptionServlet extends HttpServlet {

	private static final long serialVersionUID = 712010313100708028L;
	
	@Override
	  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	    Subscription subscription;

	    
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
		    deleteSubscription(email);

	    	ofy().save().entity(subscription); //subscribe new entity

		    
		    resp.sendRedirect("/success.jsp");
	    } else {
	    	resp.sendRedirect("/subscribe.jsp?invalid=true");
	    }
	    

	  }
	
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		if("unsubscribe".equals(req.getParameter("type"))) {
			String email = req.getParameter("email");

			deleteSubscription(email);
		}
		resp.sendRedirect("/subscribe.jsp?unsubscribe=true");
	}
	
	private void deleteSubscription(String email) {
		
		if (email != null) {
			ofy().delete().keys(ofy().load().type(Subscription.class).filter("emailHash", Hash.getHash(email)).keys());
		}
	}
	
}
