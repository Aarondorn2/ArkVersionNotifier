package com.hackeraj.arkversionnotifier.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hackeraj.arkversionnotifier.queuetasks.QueueTask;
import com.hackeraj.arkversionnotifier.utils.Encryption;
import com.hackeraj.arkversionnotifier.utils.Globals;
import com.hackeraj.arkversionnotifier.utils.Utils;

public class QueueWorkerServlet extends HttpServlet {
	
	private static final long serialVersionUID = 5163932874249089093L;
	private static final long fiveMinutes = 5L * 1000L * 60L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String taskClassName = request.getParameter(Globals.SERVLET_PARAM_TASK);
        String token = request.getParameter(Globals.SERVLET_PARAM_SECURITY_TOKEN);
        String params = request.getParameter(Globals.SERVLET_PARAM_PARAMS);
        
        long tokenTime = Long.valueOf(Encryption.decrypt(token));
        
        if (System.currentTimeMillis() - tokenTime < fiveMinutes) {
            try {
    			QueueTask task = (QueueTask) Class.forName(taskClassName).newInstance(); //build class
    			task.setParams(Utils.stringToArray(params)); //set params
    			task.run(); //run
    		} catch (InstantiationException | IllegalAccessException
    				| ClassNotFoundException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        }
        
    }
}
