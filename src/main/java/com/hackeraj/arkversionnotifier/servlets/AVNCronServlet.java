package com.hackeraj.arkversionnotifier.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hackeraj.arkversionnotifier.job.AVNJob;

public class AVNCronServlet extends HttpServlet{
    
	private static final long serialVersionUID = -8368259585487096066L;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public AVNCronServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AVNJob.startAVNJob();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
