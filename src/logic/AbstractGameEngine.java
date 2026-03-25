package logic;

import java.util.Random;

public abstract class AbstractGameEngine {
    protected int targetNumber; // Số mục tiêu cần đoán
    protected int attemptsUsed; // Số lượt đã sử dụng
    protected int maxAttempts; 
    protected int currentScore;
    protected boolean isGameOver;
    protected boolean isWin;
    protected Random random;

    public AbstractGameEngine() {
        this.random = new Random();
    }

    // Các hàm ảo (abstract) bắt buộc các lớp con phải viết lại
    public abstract void startNewGame();
    public abstract String checkGuess(int guess);
    public abstract int calculateFinalScore();

    // Các hàm dùng chung (Getter/Setter)
    public boolean isGameOver() { return isGameOver; }
    public boolean isWin() { return isWin; }
    public int getAttemptsUsed() { return attemptsUsed; }
    public int getMaxAttempts() { return maxAttempts; }
    public int getCurrentScore() { return currentScore; }
}