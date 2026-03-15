import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Bai6_Form_NhapNoiDung extends JFrame implements ActionListener {
    private JPanel mainPanel, buttonPanel;
    private JLabel lblNhapnoidung;
    private JTextArea txtNoidung;
    private JButton btnHienthi;
    private JScrollPane scrollPane;
    

    public Bai6_Form_NhapNoiDung() {
        setTitle("Form Nhập Nội Dung");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        Font font = new Font("Arial", Font.PLAIN, 18);
        Font titleFont = new Font("Arial", Font.BOLD, 24);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        lblNhapnoidung = new JLabel("Nhập nội dung:");
        lblNhapnoidung.setFont(titleFont);
        mainPanel.add(lblNhapnoidung, BorderLayout.NORTH);

        txtNoidung = new JTextArea(10, 30);
        txtNoidung.setFont(font);
        txtNoidung.setLineWrap(true);
        txtNoidung.setWrapStyleWord(true);
        scrollPane = new JScrollPane(txtNoidung);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        btnHienthi = new JButton("Hiển Thị");
        btnHienthi.setFont(new Font("Arial", Font.BOLD, 18));
        btnHienthi.addActionListener(this);
        buttonPanel.add(btnHienthi);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        this.add(mainPanel);

    }
        @Override
        public void actionPerformed(ActionEvent e) {   
            if (e.getSource() == btnHienthi) {
                String noiDung = txtNoidung.getText();
                JOptionPane.showMessageDialog(this, "Nội dung hiện thị : " + noiDung);
            }
        }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Bai6_Form_NhapNoiDung().setVisible(true);
        });
    }
}