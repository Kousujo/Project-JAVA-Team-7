import com.formdev.flatlaf.FlatClientProperties;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class LoginPanel extends JPanel implements ActionListener {
    private MainFrame mainframe;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnBack, btnGoRegister;
    private Image backgroundImage;

    public LoginPanel(MainFrame frame) {
        this.mainframe = frame;
        backgroundImage = new ImageIcon("res/Toy.png").getImage();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        MultiLineOutlineLabel lblTitle = new MultiLineOutlineLabel("LOGIN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 72)); 
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setOutlineColor(new Color(0, 0, 0, 180));
        
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weighty = 0.2;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(90, 0, 0, 0); 
        add(lblTitle, gbc);

        JPanel glassCard = new JPanel(new GridLayout(2, 1, 0, 20)) { 
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 120));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40); 
                g2.dispose();
            }
        };
        glassCard.setOpaque(false);
        glassCard.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));

        txtUsername = new JTextField();
        txtUsername.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tên đăng nhập");
        txtPassword = new JPasswordField();
        txtPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Mật khẩu");
        
        Dimension txtSize = new Dimension(300, 50);
        txtUsername.setPreferredSize(txtSize);
        txtPassword.setPreferredSize(txtSize);
        
        txtUsername.setFont(new Font("SansSerif", Font.PLAIN, 18));
        txtPassword.setFont(new Font("SansSerif", Font.PLAIN, 18));

        txtUsername.putClientProperty(FlatClientProperties.STYLE, "arc: 20");
        txtPassword.putClientProperty(FlatClientProperties.STYLE, "arc: 20");

        glassCard.add(txtUsername);
        glassCard.add(txtPassword);

        gbc.gridy = 1;
        gbc.weighty = 0.3;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 30, 0);
        add(glassCard, gbc);

        JPanel mainBtnRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        mainBtnRow.setOpaque(false);
        
        btnLogin = new JButton("LOGIN");
        btnBack = new JButton("BACK");
        
        Dimension btnSize = new Dimension(160, 55); 
        btnLogin.setPreferredSize(btnSize);
        btnBack.setPreferredSize(btnSize);
        
        btnLogin.setFont(new Font("SansSerif", Font.BOLD, 18));
        btnBack.setFont(new Font("SansSerif", Font.BOLD, 18));

        btnLogin.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_ROUND_RECT);
        btnLogin.setBackground(new Color(70, 130, 180));
        btnLogin.setForeground(Color.WHITE);
        
        btnBack.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_ROUND_RECT);

        mainBtnRow.add(btnLogin);
        mainBtnRow.add(btnBack);

        gbc.gridy = 2;
        gbc.weighty = 0.3;
        gbc.insets = new Insets(0, 0, 20, 0);
        add(mainBtnRow, gbc);

        btnGoRegister = new JButton("CHƯA CÓ TÀI KHOẢN? ĐĂNG KÝ NGAY");
        btnGoRegister.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnGoRegister.setPreferredSize(new Dimension(350, 45));
        btnGoRegister.setOpaque(false);
        btnGoRegister.setContentAreaFilled(false);
        btnGoRegister.setBorderPainted(false);
        btnGoRegister.setForeground(new Color(245, 245, 245)); 
        
        gbc.gridy = 3;
        gbc.weighty = 0.2;
        gbc.insets = new Insets(0, 0, 80, 0);
        add(btnGoRegister, gbc);

        btnBack.addActionListener(this);
        btnGoRegister.addActionListener(this);
        btnLogin.addActionListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        g2d.setColor(new Color(0, 0, 0, 15));
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBack) {
            mainframe.showScreen("Welcome");
        }
        if (e.getSource() == btnGoRegister) {
            mainframe.showScreen("Register");
        }
        if (e.getSource() == btnLogin) {
            mainframe.showScreen("Welcome");
            // TODO: Kết nối GameDatabase
            JOptionPane.showMessageDialog(this, "Chào mừng bạn trở lại!");
        }
    }
}