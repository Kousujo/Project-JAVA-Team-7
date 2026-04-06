package logic;

import javax.sound.sampled.*;
import java.io.File;

public class SoundManager {
    
    public static boolean isSoundEnabled = true;
    public static boolean isBgmEnabled = true;
    public static String currentBgmPath = "assets/bgm2.wav";
    
    private static Clip bgmClip;

    // Hàm phát âm thanh cơ bản nhất (Sound Effects)
    public static void playSound(String filePath) {
        // Nếu người chơi tắt âm thì thoát luôn, không phát âm nữa
        if (!isSoundEnabled) {
            return; 
        }

        try {
            File soundFile = new File(filePath);
            if (soundFile.exists()) {
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile); // Đọc file âm thanh
                Clip clip = AudioSystem.getClip();

                clip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip.close(); // Giải phóng bộ nhớ ngay khi phát xong
                    }
                });

                clip.open(audioIn); // Mở file âm thanh vào Clip
                clip.start(); // Cứ cho chạy, không cần quan tâm bộ nhớ
            } else {
                System.out.println("Khong tim thay file SFX: " + filePath);
            }
        } catch (Exception e) {
            System.out.println("Loi phat SFX: " + e.getMessage());
        }
    }
    
    // Hàm phát nhạc nền (Loop)
    public static void playBgm(String filePath) {
        // Dừng nhạc nền cũ nếu đang phát
        stopBgm();
        
        if (!isBgmEnabled) {
            return;
        }
        
        try {
            File soundFile = new File(filePath);
            if (soundFile.exists()) {
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
                bgmClip = AudioSystem.getClip();
                bgmClip.open(audioIn);
                bgmClip.loop(Clip.LOOP_CONTINUOUSLY); // Lặp nhạc nền liên tục
                bgmClip.start();
            } else {
                System.out.println("Khong tim thay file BGM: " + filePath);
            }
        } catch (Exception e) {
            System.out.println("Loi phat BGM: " + e.getMessage());
        }
    }
    
    // Hàm dừng nhạc nền
    public static void stopBgm() {
        if (bgmClip != null && bgmClip.isRunning()) {
            bgmClip.stop();
            bgmClip.close();
            bgmClip = null;
        }
    }
}
