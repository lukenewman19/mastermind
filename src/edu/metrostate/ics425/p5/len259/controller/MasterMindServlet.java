package edu.metrostate.ics425.p5.len259.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.metrostate.ics425.p5.len259.model.*;
/**
 * Single WebServlet for the MasterMind game.
 * 
 * @author Luke Newman ICS 425 Fall 2019
 *
 */
@WebServlet(name="MasterMindServlet", urlPatterns = {"/checkgame"})
public class MasterMindServlet extends HttpServlet implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Handles all requests.
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletContext sc = getServletContext();
		Game game = (Game) request.getSession().getAttribute("game");
		if (game == null) {
			request.getSession().invalidate();
		}else {
			String action = request.getParameter("action");
			if(action.equals("Check")){
				
				Integer[] feedback = game.checkGuess();
				request.setAttribute("winner", game.isWinner());
				request.setAttribute("gameOver", game.isGameOver());
				if (feedback == null) {
					// No action. Not all 4 pegs have been set
				} else {
					request.setAttribute("feedback", feedback);
				}
				
			} else if (action.equals("getCodePeg")) {
				String color = request.getParameter("color");
				switch(color){
				case "red":
					game.setSelectedPeg(CodePeg.valueOf("RED").ordinal());
					break;
				case "green":
					game.setSelectedPeg(CodePeg.valueOf("GREEN").ordinal());
					break;
				case "blue":
					game.setSelectedPeg(CodePeg.valueOf("BLUE").ordinal());
					break;
				case "black":
					game.setSelectedPeg(CodePeg.valueOf("BLACK").ordinal());
					break;
				case "white":
					game.setSelectedPeg(CodePeg.valueOf("WHITE").ordinal());
					break;
				case "yellow":
					game.setSelectedPeg(CodePeg.valueOf("YELLOW").ordinal());
					break;
				}
				
			} else if (action.equals("setCodePeg")) {
				if(game.getSelectedPeg() != -1) {
					String position = request.getParameter("position");
					switch(position) {
					case "1":
						game.placeCodePeg(0);
						break;
					case "2":
						game.placeCodePeg(1);
						break;
					case "3":
						game.placeCodePeg(2);
						break;
					case "4":
						game.placeCodePeg(3);
						break;
					}
				}
			} else {
				// reset
				game = new Game();
				request.getSession().setAttribute("game", game);
			}
			
		}
		sc.getRequestDispatcher("/index.jsp").forward(request, response);
	}
	/**
	 * Redirects to processRequest()
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(req, resp);
	}
	
	/**
	 * Redirects to processRequest()
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(req, resp);
	}
}
