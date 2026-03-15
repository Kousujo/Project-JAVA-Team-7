    import java.awt.*;
    import java.awt.event.*;
    import javax.swing.*;
    import javax.swing.border.*;

    public class Bai5_Form_ChonGioiTinh extends JFrame implements ActionListener {
        private JPanel mainPanel, radioButtonPanel, buttonPanel;
        private JLabel lblChongioitinh;
        private JRadioButton[] radioBtn ;
        private String[] listGioitinh = {"Nam", "Nữ", "Gay", "khác"};
        private JButton btnXacnhan;

        public Bai5_Form_ChonGioiTinh() {
            setTitle("Form chọn giới tính");    
            setSize(400, 400);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            Font font = new Font("Arial", Font.PLAIN, 18);

            mainPanel = new JPanel();
            mainPanel.setLayout(new BorderLayout(10, 10));
            mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 20));

            radioButtonPanel = new JPanel();
            radioButtonPanel.setLayout(new GridLayout(0, 1, 10, 15));

            buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

            lblChongioitinh = new JLabel("Chọn giới tính");
            lblChongioitinh.setFont(new Font("Arial", Font.BOLD, 24));
            lblChongioitinh.setHorizontalAlignment(SwingConstants.CENTER);
            Border gap = BorderFactory.createEmptyBorder(20, 20, 20, 20);
            Border line = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.RED);
            lblChongioitinh.setBorder(BorderFactory.createCompoundBorder(line, gap));


            radioBtn = new JRadioButton[listGioitinh.length];
            ButtonGroup groupGioitinh = new ButtonGroup();
            for (int i = 0; i < radioBtn.length; i++) {
                radioBtn[i] = new JRadioButton(listGioitinh[i]);
                radioBtn[i].setFont(font);
                radioButtonPanel.add(radioBtn[i]);
                groupGioitinh.add(radioBtn[i]);
            }
            
            btnXacnhan = new JButton("Xác Nhận");
            btnXacnhan.setFont(new Font("Arial", Font.BOLD, 18));
            buttonPanel.add(btnXacnhan, BorderLayout.CENTER);

            mainPanel.add(lblChongioitinh, BorderLayout.NORTH);
            mainPanel.add(radioButtonPanel, BorderLayout.CENTER);
            mainPanel.add(buttonPanel, BorderLayout.SOUTH);
            this.add(mainPanel);

            btnXacnhan.addActionListener(this);
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btnXacnhan) {
                boolean isCheck = false;
                String result = "Giới tính của bạn là: ";
                
                for (JRadioButton jRadioButton : radioBtn) {
                    if (jRadioButton.isSelected()) {
                        isCheck = true;
                        result += jRadioButton.getText();
                        break;
                    }
                }

                if (!isCheck) {
                    JOptionPane.showMessageDialog(this, "Bro là lưỡng tính à?");        
                }
                else {
                    JOptionPane.showMessageDialog(this, result);
                }               
            }
        }

        public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> {
                new Bai5_Form_ChonGioiTinh().setVisible(true);
            });
        }
    }