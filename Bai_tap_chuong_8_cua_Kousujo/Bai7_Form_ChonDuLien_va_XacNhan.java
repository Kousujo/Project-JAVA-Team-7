import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Bai7_Form_ChonDuLien_va_XacNhan extends JFrame implements ActionListener {
    private JPanel mainPanel, buttonPanel;
    private JLabel lblChonmonhoc;
    private String[] list = {"Java", "C++", "Python", "C#", "PHP", "JavaScript", "Rust"};
    private JList<String> listMonhoc;
    private DefaultListModel<String> model;
    private JButton btnXacnhan;
    private JScrollPane scrollPane;
    

    public Bai7_Form_ChonDuLien_va_XacNhan() {
        setTitle("Form Nhập Nội Dung");
        setSize(400, 300);
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

        lblChonmonhoc = new JLabel("Chọn môn học :");
        lblChonmonhoc.setFont(titleFont);
        mainPanel.add(lblChonmonhoc, BorderLayout.NORTH);

        model = new DefaultListModel<>();
        for (int i = 0; i < list.length; i++) {
            model.addElement(list[i]);
        }
        listMonhoc = new JList<>(model);
        listMonhoc.setFont(font);
        listMonhoc.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        scrollPane = new JScrollPane(listMonhoc);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        btnXacnhan = new JButton("Xác Nhận");
        btnXacnhan.setFont(new Font("Arial", Font.BOLD, 18));
        btnXacnhan.addActionListener(this);
        buttonPanel.add(btnXacnhan);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        this.add(mainPanel);

    }
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btnXacnhan) {
                String selected = listMonhoc.getSelectedValue();
        
                if (selected != null) {
                    JOptionPane.showMessageDialog(this, "Môn học đã chọn: " + selected);
                } else {
                    JOptionPane.showMessageDialog(this, "Bro chưa chọn môn nào cả!");
                }
            }
        }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Bai7_Form_ChonDuLien_va_XacNhan().setVisible(true);
        });
    }
}   