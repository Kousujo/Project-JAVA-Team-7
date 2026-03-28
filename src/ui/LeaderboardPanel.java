package ui;

import com.formdev.flatlaf.FlatClientProperties;
import graphic.MultiLineOutlineLabel;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

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

        MultiLineOutlineLabel lblTitle = new MultiLineOutlineLabel("BEST RECORDS", SwingConstants.CENTER);
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

        String[] columns = {"HẠNG", "ĐIỂM", "CHẾ ĐỘ", "TIME_HIDDEN", "TURNS_HIDDEN", "SECRET_HIDDEN"};
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
        
        table.removeColumn(table.getColumnModel().getColumn(5)); // Secret
        table.removeColumn(table.getColumnModel().getColumn(4)); // Turns
        table.removeColumn(table.getColumnModel().getColumn(3)); // Time
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    int score = (int) model.getValueAt(row, 1);
                    String mode = model.getValueAt(row, 2).toString();
                    int time = (int) model.getValueAt(row, 3);
                    int turns = (int) model.getValueAt(row, 4);
                    String secret = (String) model.getValueAt(row, 5);
                    
                    showPlayerStats(score, mode, time, turns, secret);
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
        
        gbc.gridy = 2; gbc.weighty = 0.2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 0, 55, 0);
        add(btnBack, gbc);

        btnBack.addActionListener(this);
        
        refreshData();
    }

    public void refreshData() {
        model.setRowCount(0); 

        database.GameDAO dao = new database.GameDAO();
        List<Object[]> list = dao.getTopScores(); 
        
        for (Object[] row : list) {
            model.addRow(row);
        }
    }

    private void showPlayerStats(int score, String mode, int time, int turns, String secret) {
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

        JLabel lblTitle = new JLabel("CHI TIẾT TRẬN ĐẤU", SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblTitle.setForeground(new Color(70, 130, 180));

        JLabel lblTurns = new JLabel("🎯 Số lượt đoán: " + turns, SwingConstants.LEFT);
        JLabel lblTime = new JLabel("⏱ Thời gian: " + time + "s", SwingConstants.LEFT);
        JLabel lblSecret = new JLabel("🔑 Số bí mật: " + secret, SwingConstants.LEFT);
        
        Font statFont = new Font("SansSerif", Font.PLAIN, 16);
        lblTurns.setFont(statFont); lblTime.setFont(statFont); lblSecret.setFont(statFont);

        content.add(lblTitle);
        content.add(lblTurns);
        content.add(lblTime);
        content.add(lblSecret);

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
        g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        g2d.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBack) mainframe.showScreen("Welcome");
    }
}