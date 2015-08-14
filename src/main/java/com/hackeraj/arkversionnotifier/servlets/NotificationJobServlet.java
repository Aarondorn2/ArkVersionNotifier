package com.hackeraj.arkversionnotifier.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hackeraj.arkversionnotifier.notificationjob.NotificationJobRunner;

public class NotificationJobServlet extends HttpServlet{
    
	private static final long serialVersionUID = -8368259585487096066L;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public NotificationJobServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = (request.getParameter("action") != null ? request.getParameter("action").trim().toLowerCase() : null);
		String job = (request.getParameter("job") != null ? request.getParameter("job").trim().toLowerCase() : null);
		PrintWriter writer = response.getWriter();
		
		if (job == null) {
			writer.println("No job specified");
		} else if (job.contains("ark") || job.contains("notification") || job.contains("version") ) {

			if (action == null) {
				writer.println("No action specified");
			} else if (action.equalsIgnoreCase("start")) {
				NotificationJobRunner.start();
				writer.println("Job started");
			} else if (action.equalsIgnoreCase("stop")) {
				NotificationJobRunner.stop();
				writer.println("Job stopped");
			} else if (action.equalsIgnoreCase("status")) {
				writer.println(NotificationJobRunner.checkStatus());
			} else {
				writer.println("Unknown action: " + action);
			}
		} else {
			writer.println("Unkown job: " + job);
		}
		
		writer.flush();
		writer.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
