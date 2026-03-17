import com.formdev.flatlaf.FlatDarkLaf;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class WelcomePanel extends JPanel implements ActionListener {
    private MainFrame mainframe; 
    private JLabel lbltitle;
    private JButton btnNewGame, btnContinue;

    public WelcomePanel(MainFrame frame) {
    this.mainframe = frame; 
    setLayout(new GridBagLayout()); 
    GridBagConstraints gbc = new GridBagConstraints(); 

    lbltitle = new JLabel("THE CHOSEN NUMBER");
    lbltitle.setFont(new Font("SansSerif", Font.BOLD, 30));
    
    gbc.gridx = 0; gbc.gridy = 0; 
    gbc.insets = new Insets(0, 0, 50, 0); 
    add(lbltitle, gbc);

    btnNewGame = new JButton("NEW GAME");

    gbc.gridy = 1; 
    gbc.insets = new Insets(0, 0, 10, 0);
    add(btnNewGame, gbc);

    btnContinue = new JButton("CONTINUE");
    btnContinue.setEnabled(false);
    
    gbc.gridy = 2;
    add(btnContinue, gbc);
    
    btnNewGame.addActionListener(this);
    btnContinue.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnNewGame) {
            mainframe.showScreen("Game");
        } else if (e.getSource() == btnContinue) {
        }
    }
}