package logic;

public class HardModeEngine extends AbstractGameEngine {
    private String targetString; 

    @Override
    public void startNewGame() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            sb.append(random.nextInt(10));
        }
        this.targetString = sb.toString();
        this.targetNumber = Integer.parseInt(targetString);
        
        this.attemptsUsed = 0;
        this.maxAttempts = 7; 
        this.isGameOver = false;
        this.isWin = false;
        this.currentScore = 0;
    }

    public String checkGuess(int guess) {
        String guessStr = String.format("%05d", guess);
        
        if (isGameOver) return "GAME_OVER";
        attemptsUsed++;

        if (guessStr.equals(targetString)) {
            isWin = true;
            isGameOver = true;
            currentScore = calculateFinalScore();
            return "G,G,G,G,G"; 
        }

        if (attemptsUsed >= maxAttempts) {
            isGameOver = true;
            currentScore = 0;
        }

        return evaluateColors(guessStr);
    }


    private String evaluateColors(String guess) {
        char[] targetChars = targetString.toCharArray();
        char[] guessChars = guess.toCharArray();
        char[] result = new char[]{'X', 'X', 'X', 'X', 'X'}; 
        boolean[] targetUsed = new boolean[5]; // Đánh dấu các số trong đáp án đã bị "ăn"

        // BƯỚC 1: Tìm tất cả các ô màu XANH LÁ (G) trước
        for (int i = 0; i < 5; i++) {
            if (guessChars[i] == targetChars[i]) {
                result[i] = 'G';
                targetUsed[i] = true; 
            }
        }

        // BƯỚC 2: Tìm các ô màu VÀNG (Y)
        for (int i = 0; i < 5; i++) {
            if (result[i] == 'G') continue; // Bỏ qua những ô đã Xanh

            // Cầm số bị sai vị trí đi quét xem có trong đáp án không
            for (int j = 0; j < 5; j++) {
                if (!targetUsed[j] && guessChars[i] == targetChars[j]) {
                    result[i] = 'Y';
                    targetUsed[j] = true; // Đánh dấu là số này đã cấp màu Vàng, không cấp lại nữa
                    break;
                }
            }
        }

        // Nối mảng kết quả thành chuỗi cách nhau bởi dấu phẩy
        return result[0] + "," + result[1] + "," + result[2] + "," + result[3] + "," + result[4];
    }

    public String getSecretCode() {
        return targetString;
    }

    @Override
    public int calculateFinalScore() {
        if (!isWin) return 0;
        return (9 - attemptsUsed) * 500;
    }
}