package edu.metrostate.ics425.p5.len259.model;

import java.util.Random;

/**
 * Game represents the MasterMind game.
 * 
 * @author Luke Newman ICS 425 Fall 2019
 *
 */
public class Game {
	
	private Integer[][] guesses;
	private Integer[][] feedback;
	private int guessNumber;
	private final int NUM_OF_GUESSES_ALLOWED = 10;
	private final int PATTERN_LENGTH = 4;
	private CodePeg[] solution;
	private CodePeg selectedPeg;
	private boolean winner;
	
	/**
	 * Constructor for Game.  Initializes instance variables for a new game.
	 */
	public Game() {
		guessNumber = 1;
		winner = false;
		selectedPeg = null;
		guesses = new Integer[NUM_OF_GUESSES_ALLOWED][PATTERN_LENGTH];
		feedback = new Integer[NUM_OF_GUESSES_ALLOWED][PATTERN_LENGTH];
		solution = new CodePeg[PATTERN_LENGTH];
		for(int i = 0; i < PATTERN_LENGTH; i++) {
			solution[i] = randomCodePeg();
		}
	}

	/**
	 * Sets the selected CodePeg according the int passed into the method
	 * 
	 * @param i - an int (0 - 5) corresponding to the CodePeg
	 */
	public void setSelectedPeg(int i) {
		if (i >= 0 && i < 6) {
			selectedPeg = CodePeg.values()[i];
		}
	}
	
	/**
	 * Gets the int representation of the currently selected CodePeg 
	 * @return
	 */
	public int getSelectedPeg() {
		if(selectedPeg != null) {
			return selectedPeg.ordinal();
		}
		return -1;
	}
	
	/**
	 * Places selected CodePeg in the specified position of the current guess row
	 * 
	 * @param position - position (1 - 4) of the current guess
	 */
	public void placeCodePeg(int position) {
		if(selectedPeg != null && position >= 0 && position < 4) {
			guesses[guessNumber - 1][position] = selectedPeg.ordinal(); 
		}
	}
	
	/**
	 * Helper method to obtain a random CodePeg
	 * 
	 * @return a random CodePeg
	 */
	private CodePeg randomCodePeg() {
		return CodePeg.values()[new Random().nextInt(CodePeg.values().length)];
	}
	
	/**
	 * 
	 * @return CodePeg array representing the solution for the current game
	 */
	public CodePeg[] getSolution() {
		return solution;
	}
	
	/**
	 * 
	 * @return int array representing the solution for the current game
	 */
	public int[] getIntegerSolution() {
		int[] intSolution = new int[PATTERN_LENGTH];
		for(int i = 0; i < PATTERN_LENGTH; i++) {
			intSolution[i] = solution[i].ordinal();
		}
		return intSolution;
	}
	
	/**
	 * 
	 * @return The current guess number (1 - 10)
	 */
	public int getGuessNumber() {
		return guessNumber;
	}
	
	/**
	 * 
	 * @return Two dimensional Integer array representing the feedback for all 10 guess
	 */
	public Integer[][] getFeedback(){
		return feedback;
	}
	
	/**
	 * 
	 * @return Two dimensional Integer array representing the 10 guesses
	 */
	public Integer[][] getGuesses(){
		return guesses;
	}
	
	/**
	 * 
	 * @return true if the game is over
	 */
	public boolean isGameOver() {
		return guessNumber > 10? true: winner; 
	}
	
	/**
	 * 
	 * @return true if there is a winner in the current game
	 */
	public boolean isWinner() {
		return winner;
	}
	
	/**
	 * Checks the current guess. Advances to next guess.
	 * 
	 * @return feedback for the current guess.  
	 */
	public Integer[] checkGuess() {
		if (isGameOver()) {
			return null;
		}
		boolean status = true;
		for(Integer peg: guesses[guessNumber -1]) {
			if(peg == null) {
				status = false;
			}
		}
		if(status == false) {
			return null;
		}else {
			int numberOfPegsInFeedback = 0;
			boolean[] guessedCorrectlyFromSolution = {false, false, false, false};
			boolean[] guessUsed = {false, false, false, false};
			for(int i = 0; i < PATTERN_LENGTH; i++) {
				
				if (solution[i].equals(CodePeg.values()[guesses[guessNumber - 1][i]])) {
					feedback[guessNumber - 1][numberOfPegsInFeedback] = KeyPeg.RED.ordinal();
					numberOfPegsInFeedback++;
					guessedCorrectlyFromSolution[i] = true;
					guessUsed[i] = true;
				}
				
			}
			
			if (numberOfPegsInFeedback == 4) {
				winner = true;
			}
			
			for (int i = 0; i < PATTERN_LENGTH; i++) {
				
				int currentPeg = 0;
				for(CodePeg correctPeg: solution) {
					if (guessedCorrectlyFromSolution[currentPeg] == false) {
						for(int j = 0; j < PATTERN_LENGTH; j++) {
							if(guessUsed[j] == false && guessedCorrectlyFromSolution[currentPeg] == false) {
								if(correctPeg.equals(CodePeg.values()[guesses[guessNumber - 1][j]])) {
									feedback[guessNumber - 1][numberOfPegsInFeedback] = KeyPeg.WHITE.ordinal();
									numberOfPegsInFeedback++;
									guessedCorrectlyFromSolution[currentPeg] = true;
									guessUsed[j] = true;
								}
							}
						}
					}
					currentPeg++;
				}
			}
			guessNumber++;
			return feedback[guessNumber - 2];
		}
	}
}
