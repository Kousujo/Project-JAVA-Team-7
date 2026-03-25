package logic;

public class MediumModeEngine extends AbstractGameEngine {
    private boolean hasBonus; // Cờ đánh dấu đã nhận thưởng chưa

    @Override
    public void startNewGame() {
        this.targetNumber = 1000 + random.nextInt(9000); // 1000 đến 9999
        this.attemptsUsed = 0;
        this.maxAttempts = 10;
        this.isGameOver = false;
        this.isWin = false;
        this.currentScore = 0;
        this.hasBonus = false;
    }

    @Override
    public String checkGuess(int guess) {
        if (isGameOver) return "TRÒ CHƠI ĐÃ KẾT THÚC!";
        attemptsUsed++;

        // Tách số thành 2 nửa
        int g1 = guess / 100, g2 = guess % 100;
        int t1 = targetNumber / 100, t2 = targetNumber % 100;

        String res1 = (g1 == t1) ? "MATCH" : (g1 > t1 ? "DOWN" : "UP");
        String res2 = (g2 == t2) ? "MATCH" : (g2 > t2 ? "DOWN" : "UP");

        // Xét Bonus: Khớp ít nhất 1 nửa trong 5 lượt đầu
        if (!hasBonus && attemptsUsed <= 5) {
            if (res1.equals("MATCH") || res2.equals("MATCH")) { 
                hasBonus = true;
            }
        }

        // Kiểm tra thắng
        if (res1.equals("MATCH") && res2.equals("MATCH")) {
            isWin = true;
            isGameOver = true;
            currentScore = calculateFinalScore();
            return "MATCH!|MATCH!";
        } 
        
        if (attemptsUsed >= maxAttempts) {
            isGameOver = true;
            currentScore = 0;
            return "GAME OVER";
        }

        // Trả về định dạng ngăn cách bởi dấu | để UI dễ cắt chuỗi (split)
        return res1 + "|" + res2; 
    }

    @Override
    public int calculateFinalScore() {
        if (!isWin) return 0;
        int score = (11 - attemptsUsed) * 150;
        if (hasBonus) score += 200;
        return score;
    }
}