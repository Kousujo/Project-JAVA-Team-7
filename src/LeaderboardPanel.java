import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class LeaderboardPanel extends JPanel implements ActionListener {
    private MainFrame mainframe;
    private Image backgroundImage;
    private JButton btnBack;
    private JTable table;

    public LeaderboardPanel(MainFrame frame) {
        this.mainframe = frame;
        backgroundImage = new ImageIcon("res/Toy.png").getImage();
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        MultiLineOutlineLabel lblTitle = new MultiLineOutlineLabel("TOP PLAYERS", SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 54));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setOutlineColor(new Color(0, 0, 0, 180));
        
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weighty = 0.2;
        gbc.insets = new Insets(60, 0, 20, 0);
        add(lblTitle, gbc);

        JPanel glassCard = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 120));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.dispose();
            }
        };
        glassCard.setOpaque(false);
        glassCard.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //DEMO
        String[] columns = {"HẠNG", "NGƯỜI CHƠI", "ĐIỂM", "CHẾ ĐỘ"};
        Object[][] data = {
            {"1", "Kousujo", "999", "HARD"},
            {"2", "Merlin", "850", "NORMAL"},
            {"3", "Team 7", "720", "HARD"},
            {"4", "Unknown", "500", "EASY"},
            {"5", "JavaMaster", "400", "NORMAL"}
        };

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        table = new JTable(model);
        table.setRowHeight(50);
        table.setFont(new Font("SansSerif", Font.PLAIN, 16));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        table.setShowGrid(false);
        table.setOpaque(false);
        
        // Bắt sự kiện click chuột vào bảng
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    String playerName = table.getValueAt(row, 1).toString();
                    showPlayerStats(playerName);
                }
            }
        });

        ((DefaultTableCellRenderer)table.getDefaultRenderer(Object.class)).setOpaque(false);
        table.getTableHeader().setOpaque(false);
        table.getTableHeader().setBackground(new Color(255, 255, 255, 50));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        glassCard.add(scrollPane, BorderLayout.CENTER);

        gbc.gridy = 1;
        gbc.weighty = 0.5; gbc.weightx = 0.1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 90, 30, 90);
        add(glassCard, gbc);

        // 3. NÚT BACK
        btnBack = new JButton("BACK TO MENU");
        btnBack.setFont(new Font("SansSerif", Font.BOLD, 22));
        btnBack.setPreferredSize(new Dimension(220, 60));
        btnBack.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_ROUND_RECT);
        
        gbc.gridy = 2;
        gbc.weighty = 0.2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 0, 55, 0);
        add(btnBack, gbc);

        btnBack.addActionListener(this);
    }

    // --- HÀM HIỂN THỊ THÔNG SỐ NGƯỜI CHƠI (POPUP) ---
    private void showPlayerStats(String name) {
        // Tạo một Dialog con
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Thông số người chơi", true);
        dialog.setUndecorated(true); // Bỏ khung Windows mặc định để tự vẽ
        dialog.setBackground(new Color(0, 0, 0, 0)); // Làm nền dialog trong suốt

        // Panel chứa nội dung theo style Glass
        JPanel content = new JPanel(new GridLayout(4, 1, 10, 10)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 240)); // Nền trắng đục hơn để dễ đọc
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.setColor(new Color(70, 130, 180)); // Viền màu xanh thép
                g2.setStroke(new BasicStroke(3));
                g2.drawRoundRect(1, 1, getWidth()-3, getHeight()-3, 30, 30);
                g2.dispose();
            }
        };
        content.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Dữ liệu ảo (Sau này lấy từ GameDataBase.java)
        JLabel lblName = new JLabel("Người chơi: " + name, SwingConstants.CENTER);
        lblName.setFont(new Font("SansSerif", Font.BOLD, 18));
        
        JLabel lblWinRate = new JLabel("📊 Tỷ lệ thắng: 65%", SwingConstants.LEFT);
        JLabel lblTotalWins = new JLabel("🏆 Số game thắng: 42 ván", SwingConstants.LEFT);
        JLabel lblStreak = new JLabel("🔥 Chuỗi thắng tốt nhất: 5 ván", SwingConstants.LEFT);
        
        Font statFont = new Font("SansSerif", Font.PLAIN, 16);
        lblWinRate.setFont(statFont); lblTotalWins.setFont(statFont); lblStreak.setFont(statFont);

        content.add(lblName);
        content.add(lblWinRate);
        content.add(lblTotalWins);
        content.add(lblStreak);

        // Click vào popup để đóng
        dialog.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dialog.dispose();
            }
        });

        dialog.add(content);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
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
    }
}