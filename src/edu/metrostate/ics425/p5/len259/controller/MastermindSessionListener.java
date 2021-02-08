package edu.metrostate.ics425.p5.len259.controller;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import edu.metrostate.ics425.p5.len259.model.*;

@WebListener
public class MastermindSessionListener implements HttpSessionListener {
	
	public MastermindSessionListener() {
	}

	
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		se.getSession().setAttribute("game", new Game());
	}

	
	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
	}
}
