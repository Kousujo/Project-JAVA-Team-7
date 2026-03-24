package ui;

import com.formdev.flatlaf.FlatClientProperties;

import component.MultiLineOutlineLabel;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

// TODO: HOÀN THÀNH LOGIC
// import logic.AuthService;

public class RegisterPanel extends JPanel implements ActionListener {
    private MainFrame mainframe;
    private JTextField txtUsername;
    private JPasswordField txtPassword, txtConfirmPassword;
    private JButton btnRegister, btnBack, btnGoLogin;
    private Image backgroundImage;

    public RegisterPanel(MainFrame frame) {
        this.mainframe = frame;
        backgroundImage = new ImageIcon("res/Toy.png").getImage();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        MultiLineOutlineLabel lblTitle = new MultiLineOutlineLabel("REGISTER", SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 72)); 
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setOutlineColor(new Color(0, 0, 0, 180));
        
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weighty = 0.2;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(90, 0, 0, 0); 
        add(lblTitle, gbc);     

        JPanel glassCard = new JPanel(new GridLayout(3, 1, 0, 15)) { 
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
        glassCard.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        txtUsername = new JTextField();
        txtUsername.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tên đăng nhập");
        txtPassword = new JPasswordField();
        txtPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Mật khẩu");
        txtConfirmPassword = new JPasswordField();
        txtConfirmPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Xác nhận mật khẩu");
        
        Dimension txtSize = new Dimension(300, 45);
        txtUsername.setPreferredSize(txtSize);
        txtPassword.setPreferredSize(txtSize);
        txtConfirmPassword.setPreferredSize(txtSize);
        
        txtUsername.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtPassword.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtConfirmPassword.setFont(new Font("SansSerif", Font.PLAIN, 16));

        txtUsername.putClientProperty(FlatClientProperties.STYLE, "arc: 20");
        txtPassword.putClientProperty(FlatClientProperties.STYLE, "arc: 20");
        txtConfirmPassword.putClientProperty(FlatClientProperties.STYLE, "arc: 20");

        glassCard.add(txtUsername);
        glassCard.add(txtPassword);
        glassCard.add(txtConfirmPassword);

        gbc.gridy = 1;
        gbc.weighty = 0.3;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 20, 0);
        add(glassCard, gbc);

        JPanel mainBtnRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        mainBtnRow.setOpaque(false);
        
        btnRegister = new JButton("REGISTER");
        btnBack = new JButton("BACK");
        
        Dimension btnSize = new Dimension(160, 55); 
        btnRegister.setPreferredSize(btnSize);
        btnBack.setPreferredSize(btnSize);
        
        btnRegister.setFont(new Font("SansSerif", Font.BOLD, 18));
        btnBack.setFont(new Font("SansSerif", Font.BOLD, 18));

        btnRegister.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_ROUND_RECT);
        btnRegister.setBackground(new Color(70, 130, 180));
        btnRegister.setForeground(Color.WHITE);
        
        btnBack.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_ROUND_RECT);

        mainBtnRow.add(btnRegister);
        mainBtnRow.add(btnBack);

        gbc.gridy = 2;
        gbc.weighty = 0.2;
        gbc.insets = new Insets(0, 0, 15, 0);
        add(mainBtnRow, gbc);

        btnGoLogin = new JButton("ĐÃ CÓ TÀI KHOẢN? ĐĂNG NHẬP NGAY");
        btnGoLogin.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnGoLogin.setPreferredSize(new Dimension(350, 45));
        btnGoLogin.setOpaque(false);
        btnGoLogin.setContentAreaFilled(false);
        btnGoLogin.setBorderPainted(false);
        btnGoLogin.setForeground(new Color(245, 245, 245)); 
        
        gbc.gridy = 3;
        gbc.weighty = 0.3;
        gbc.insets = new Insets(0, 0, 80, 0);
        add(btnGoLogin, gbc);

        btnBack.addActionListener(this);
        btnGoLogin.addActionListener(this);
        btnRegister.addActionListener(this);
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
        if (e.getSource() == btnGoLogin) {
            mainframe.showScreen("Login");
        }
        if (e.getSource() == btnRegister) {
            String username = txtUsername.getText();
            String pass = new String(txtPassword.getPassword());
            String confirm = new String(txtConfirmPassword.getPassword());

            if (username.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!");
                return;
            }

            if (!pass.equals(confirm)) {
                JOptionPane.showMessageDialog(this, "Mật khẩu xác nhận không khớp!");
                return;
            }

            // TODO: HOÀN THÀNH AuthService
            /*
            AuthService auth = new AuthService();
            int result = auth.register(username, pass); // 1 = Thành công, 0 = Trùng tên, -1 = Lỗi SQL
            
            if (result == 1) {
                JOptionPane.showMessageDialog(this, "Đăng ký thành công! Hãy đăng nhập nhé.");
                mainframe.showScreen("Login");
                clearFields(); 
            } else if (result == 0) {
                JOptionPane.showMessageDialog(this, "Tên đăng nhập đã tồn tại!");
            } else {
                JOptionPane.showMessageDialog(this, "Có lỗi xảy ra, vui lòng thử lại!");
            }
            */
        
            JOptionPane.showMessageDialog(this, "Đăng ký thành công! (Bản Demo)");
            mainframe.showScreen("Login");
        }
    }
}