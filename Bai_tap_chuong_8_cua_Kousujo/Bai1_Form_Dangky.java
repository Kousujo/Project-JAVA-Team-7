import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Bai1_Form_Dangky extends JFrame implements ActionListener {
    private JTextField txtHoten, txtNamsinh;
    private JButton btnDangky;
    private JLabel lblHoten, lblNamsinh;
    private JPanel mainPanel, buttonPanel;

    public Bai1_Form_Dangky() {
        setTitle("Form Đăng Ký");
        setSize(450, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        Font font = new Font("Arial", Font.PLAIN, 18);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        lblHoten = new JLabel("Họ tên:");
        lblHoten.setFont(font);
        mainPanel.add(lblHoten);

        txtHoten = new JTextField();
        txtHoten.setFont(font);
        mainPanel.add(txtHoten);

        lblNamsinh = new JLabel("Năm sinh:");
        lblNamsinh.setFont(font);
        mainPanel.add(lblNamsinh);

        txtNamsinh = new JTextField();
        txtNamsinh.setFont(font);
        mainPanel.add(txtNamsinh);

        btnDangky = new JButton("Đăng ký");
        btnDangky.setFont(new Font("Arial", Font.BOLD, 18));
        btnDangky.addActionListener(this);
        buttonPanel.add(btnDangky);

        add(buttonPanel, BorderLayout.SOUTH);
        this.add(mainPanel);

    }
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String hoten = txtHoten.getText();
                int namsinh = Integer.parseInt(txtNamsinh.getText());
                JOptionPane.showMessageDialog(this, "Họ tên: " + hoten + "\nNăm sinh: " + namsinh);
            } catch (NumberFormatException er) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập năm sinh hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Bai1_Form_Dangky().setVisible(true);
        });
    }
}