package logic;

public class EasyModeEngine extends AbstractGameEngine {

    @Override
    public void startNewGame() {
        this.targetNumber = random.nextInt(101);
        this.attemptsUsed = 0; 
        this.maxAttempts = 10;
        this.isGameOver = false;
        this.isWin = false;
        this.currentScore = 0;
    }

    @Override
    public String getSecretCode() {
        return String.valueOf(targetNumber);
    }

    @Override
    public String checkGuess(int guess) {
        if (isGameOver) return "TRÒ CHƠI ĐÃ KẾT THÚC!";
        attemptsUsed++;

        if (guess == targetNumber) {
            isWin = true;
            isGameOver = true;
            currentScore = calculateFinalScore();
            return "MATCH!";
        } 
        
        if (attemptsUsed >= maxAttempts) {
            isGameOver = true;
            currentScore = 0;
            return "GAME OVER";
        }

        return (guess > targetNumber) ? "QUÁ CAO" : "QUÁ THẤP";
    }

    @Override
    public int calculateFinalScore() {
        if (!isWin) return 0;
        return (10 - attemptsUsed ) * 100;
    }
}