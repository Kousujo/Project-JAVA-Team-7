import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

public class Bai3_Form_HinhThucHoc extends JFrame implements ActionListener {
    private JPanel mainPanel, checkboxPanel, buttonPanel;
    private JLabel lblHinhthuc;
    private JCheckBox chxOn, chxOff;
    private JButton btnXacnhan;

    public Bai3_Form_HinhThucHoc() {
        setTitle("Form chọn hình thức học");    
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        Font font = new Font("Arial", Font.PLAIN, 18);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 20));

        checkboxPanel = new JPanel();
        checkboxPanel.setLayout(new GridLayout(2, 1, 10, 15));
        Border padding = BorderFactory.createEmptyBorder(20, 20, 10, 20);
        Border line = BorderFactory.createLineBorder(Color.GRAY);
        checkboxPanel.setBorder(BorderFactory.createCompoundBorder(line, padding));

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        lblHinhthuc = new JLabel("Chọn hình thức học");
        lblHinhthuc.setFont(new Font("Arial", Font.BOLD, 24));
        lblHinhthuc.setHorizontalAlignment(SwingConstants.CENTER);

        chxOn = new JCheckBox("ONLINE");
        chxOff = new JCheckBox("OFFLINE");
        chxOn.setFont(font);
        chxOff.setFont(font);
        checkboxPanel.add(chxOn);
        checkboxPanel.add(chxOff);
        
        btnXacnhan = new JButton("Xác Nhận");
        btnXacnhan.setFont(new Font("Arial", Font.BOLD, 18));
        buttonPanel.add(btnXacnhan, BorderLayout.CENTER);

        mainPanel.add(lblHinhthuc, BorderLayout.NORTH);
        mainPanel.add(checkboxPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        this.add(mainPanel);

        btnXacnhan.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnXacnhan) {
            if (!chxOn.isSelected() && !chxOff.isSelected()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn ít nhất 1 hình thức học!");
                return; 
            }

            String result = "Hình Thức Học: ";
            if (chxOn.isSelected() && chxOff.isSelected()) {
                result += "ONLINE và OFFLINE";
            } else if (chxOn.isSelected()) {
                result += "ONLINE";
            } else {
                result += "OFFLINE";
            }

            JOptionPane.showMessageDialog(this, result);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Bai3_Form_HinhThucHoc().setVisible(true);
        });
    }
}