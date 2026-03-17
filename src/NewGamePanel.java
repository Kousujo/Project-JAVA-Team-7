import com.formdev.flatlaf.FlatClientProperties;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class NewGamePanel extends JPanel implements ActionListener {
    private MainFrame mainframe;
    private JRadioButton rbEasy, rbNormal, rbHard;
    private ButtonGroup group;
    private JButton btnStart, btnBack;

    public NewGamePanel(MainFrame frame) {
        this.mainframe = frame;
        initUI();
    }

    private void initUI() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); 

        JLabel lblHeader = new JLabel("CHỌN CHẾ ĐỘ CHƠI");
        lblHeader.setFont(new Font("SansSerif", Font.BOLD, 24));
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2; 
        gbc.insets = new Insets(0, 0, 30, 0);
        add(lblHeader, gbc);

        rbEasy = new JRadioButton("DỄ (4 ô số - Phạm vi 1-50)");
        rbNormal = new JRadioButton("VỪA (7 ô số - Phạm vi 1-100)");
        rbHard = new JRadioButton("KHÓ (10 ô số - Phạm vi 1-500)");

        rbNormal.setSelected(true); 

        group = new ButtonGroup();
        group.add(rbEasy);
        group.add(rbNormal);
        group.add(rbHard);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST; 
        gbc.insets = new Insets(5, 0, 5, 0);
        
        gbc.gridy = 1; add(rbEasy, gbc);
        gbc.gridy = 2; add(rbNormal, gbc);
        gbc.gridy = 3; add(rbHard, gbc);

        btnStart = new JButton("BẮT ĐẦU");

        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(30, 0, 10, 0);
        add(btnStart, gbc);
        
        gbc.gridy = 5;
        add(btnBack, gbc);

        btnStart.addActionListener(this);   
        btnBack.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnStart) {
            String difficulty = "Normal";
            if (rbEasy.isSelected()) difficulty = "Easy";
            else if (rbHard.isSelected()) difficulty = "Hard";

            JOptionPane.showMessageDialog(this, "Bạn đã chọn chế độ: " + difficulty);
        } else if (e.getSource() == btnBack) {
            mainframe.showScreen("Welcome");
        }
    }
}