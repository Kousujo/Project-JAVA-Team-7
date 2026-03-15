import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class Bai4_Form_ChonSoThich extends JFrame implements ActionListener {
    private JPanel mainPanel, checkboxPanel, buttonPanel;
    private JLabel lblChonsothich;
    private JCheckBox[] chxSothich ;
    private String[] listSothich = {"Đọc sách", "Nghe nhạc", "Chơi thể thao", "Du lịch", "Code", "Sóc lọ"};
    private JButton btnXacnhan;

    public Bai4_Form_ChonSoThich() {
        setTitle("Form chọn sở thích");    
        setSize(400, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        Font font = new Font("Arial", Font.PLAIN, 18);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 20));

        checkboxPanel = new JPanel();
        checkboxPanel.setLayout(new GridLayout(0, 1, 10, 15));

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        lblChonsothich = new JLabel("Chọn sở thích");
        lblChonsothich.setFont(new Font("Arial", Font.BOLD, 24));
        lblChonsothich.setHorizontalAlignment(SwingConstants.CENTER);
        Border gap = BorderFactory.createEmptyBorder(20, 20, 20, 20);
        Border line = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.RED);
        lblChonsothich.setBorder(BorderFactory.createCompoundBorder(line, gap));

        chxSothich = new JCheckBox[listSothich.length];
        for (int i = 0; i < chxSothich.length; i++) {
            chxSothich[i] = new JCheckBox(listSothich[i]);
            chxSothich[i].setFont(font);
            checkboxPanel.add(chxSothich[i]);
        }
        
        btnXacnhan = new JButton("Xác Nhận");
        btnXacnhan.setFont(new Font("Arial", Font.BOLD, 18));
        buttonPanel.add(btnXacnhan, BorderLayout.CENTER);

        mainPanel.add(lblChonsothich, BorderLayout.NORTH);
        mainPanel.add(checkboxPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        this.add(mainPanel);

        btnXacnhan.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnXacnhan) {
            boolean isCheck = false;
            StringBuilder temp = new StringBuilder("Sở thích : ");
            
            for (JCheckBox jCheckBox : chxSothich) {
                if (jCheckBox.isSelected()) {
                    isCheck = true;
                    temp.append(jCheckBox.getText()).append(", ");          
                }
            }

            if (!isCheck) {
                JOptionPane.showMessageDialog(this, "Bro ko có sở thích à?");        
            }
            else {
                String result = temp.substring(0, temp.length() - 2);
                JOptionPane.showMessageDialog(this, result);
            }               
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Bai4_Form_ChonSoThich().setVisible(true);
        });
    }
}