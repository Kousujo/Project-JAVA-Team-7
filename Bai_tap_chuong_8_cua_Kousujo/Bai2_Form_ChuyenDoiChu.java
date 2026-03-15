import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Bai2_Form_ChuyenDoiChu extends JFrame implements ActionListener {
    private JTextField txtNhapchuoi;
    private JLabel lblKetqua;
    private JButton btnUpper, btnLower;
    private JPanel mainPanel, buttonPanel;

    public Bai2_Form_ChuyenDoiChu() {
        setTitle("Form đổi chữ hoa - chữ thường");    
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        Font font = new Font("Arial", Font.PLAIN, 18);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        txtNhapchuoi = new JTextField("Nhập chuỗi tại đây");
        txtNhapchuoi.setFont(font);

        lblKetqua = new JLabel("Kết quả sẽ hiển thị ở đây");
        lblKetqua.setHorizontalAlignment(SwingConstants.CENTER);
        lblKetqua.setBackground(Color.LIGHT_GRAY);
        lblKetqua.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        lblKetqua.setFont(font);

        btnUpper = new JButton("Chữ hoa");
        btnUpper.setFont(new Font("Arial", Font.BOLD, 18));
        btnLower = new JButton("Chữ thường");
        btnLower.setFont(new Font("Arial", Font.BOLD, 18));

        buttonPanel.add(btnUpper, BorderLayout.WEST);
        buttonPanel.add(btnLower, BorderLayout.EAST);

        mainPanel.add(lblKetqua, BorderLayout.CENTER);
        mainPanel.add(txtNhapchuoi, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        this.add(mainPanel);

        btnUpper.addActionListener(this);
        btnLower.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String input = txtNhapchuoi.getText();
        if (e.getSource() == btnUpper) {
            lblKetqua.setText("Kết quả: " + input.toUpperCase());
        } else if (e.getSource() == btnLower) {
            lblKetqua.setText("Kết quả: " + input.toLowerCase());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Bai2_Form_ChuyenDoiChu().setVisible(true);
        });
    }
}