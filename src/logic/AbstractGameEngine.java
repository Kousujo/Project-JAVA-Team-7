package logic;

import java.util.Random;

public abstract class AbstractGameEngine {
    protected int targetNumber; 
    protected int attemptsUsed; 
    protected int maxAttempts; 
    protected int currentScore;
    protected boolean isGameOver;
    protected boolean isWin;
    protected Random random;

    public AbstractGameEngine() {
        this.random = new Random();
    }

    public abstract void startNewGame();
    public abstract String checkGuess(int guess);
    public abstract int calculateFinalScore();

    public boolean isGameOver() { return isGameOver; }
    public boolean isWin() { return isWin; }
    public int getAttemptsUsed() { return attemptsUsed; }
    public int getMaxAttempts() { return maxAttempts; }
    public int getCurrentScore() { return currentScore; }
    public int getTargetNumber() { return targetNumber; }
    
}