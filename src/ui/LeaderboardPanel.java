package ui;

import com.formdev.flatlaf.FlatClientProperties;
import component.MultiLineOutlineLabel;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

// TODO: HOÀN THÀNH DATABASE
// import database.GameDAO;

public class LeaderboardPanel extends JPanel implements ActionListener {
    private MainFrame mainframe;
    private Image backgroundImage;
    private JButton btnBack;
    private JTable table;
    private DefaultTableModel model;

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

        // KHỞI TẠO MODEL TRỐNG
        String[] columns = {"HẠNG", "NGƯỜI CHƠI", "ĐIỂM", "CHẾ ĐỘ"};
        model = new DefaultTableModel(null, columns) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        table = new JTable(model);
        table.setRowHeight(50);
        table.setFont(new Font("SansSerif", Font.PLAIN, 16));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        table.setShowGrid(false);
        table.setOpaque(false);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

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
        
        // LOAD DỮ LIỆU LẦN ĐẦU (TẠM THỜI DÙNG DEMO)
        loadDemoData();
    }

        private void loadDemoData() {
        Object[][] demo = {
            {"1", "Kousujo", "999", "HARD"},
            {"2", "Merlin", "850", "NORMAL"},
            {"3", "Team 7", "720", "HARD"},
            {"4", "Unknown", "500", "EASY"},
            {"5", "JavaMaster", "400", "NORMAL"}
        };
        for (Object[] row : demo) model.addRow(row);
    }
    
    /**
     * Hàm làm mới bảng từ Database thật
     */
    public void refreshTable() {
        // TODO: HOÀN THÀNH DATABASE
        /*
        GameDAO dao = new GameDAO();
        Vector<Vector<Object>> data = dao.getTopPlayers();
        model.setDataVector(data, new Vector<>(java.util.Arrays.asList("HẠNG", "NGƯỜI CHƠI", "ĐIỂM", "CHẾ ĐỘ")));
        */
    }

    private void showPlayerStats(String name) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), true);
        dialog.setUndecorated(true);
        dialog.setBackground(new Color(0, 0, 0, 0));

        JPanel content = new JPanel(new GridLayout(4, 1, 10, 10)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 245));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.setColor(new Color(70, 130, 180));
                g2.setStroke(new BasicStroke(3));
                g2.drawRoundRect(1, 1, getWidth()-3, getHeight()-3, 30, 30);
                g2.dispose();
            }
        };
        content.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // TODO: Gọi DAO để lấy chỉ số thật của 'name'
        JLabel lblName = new JLabel("Người chơi: " + name, SwingConstants.CENTER);
        lblName.setFont(new Font("SansSerif", Font.BOLD, 18));
        JLabel lblWinRate = new JLabel("📊 Tỷ lệ thắng: 65%", SwingConstants.LEFT);
        JLabel lblTotalWins = new JLabel("🏆 Số game thắng: 42 ván", SwingConstants.LEFT);
        JLabel lblStreak = new JLabel("🔥 Chuỗi thắng tốt nhất: 5 ván", SwingConstants.LEFT);
        
        Font statFont = new Font("SansSerif", Font.PLAIN, 16);
        lblWinRate.setFont(statFont); lblTotalWins.setFont(statFont); lblStreak.setFont(statFont);

        content.add(lblName); content.add(lblWinRate);
        content.add(lblTotalWins); content.add(lblStreak);

        dialog.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { dialog.dispose(); }
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
        if (e.getSource() == btnBack) mainframe.showScreen("Welcome");
    }
}