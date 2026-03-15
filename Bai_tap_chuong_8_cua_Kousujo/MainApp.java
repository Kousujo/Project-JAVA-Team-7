import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainApp extends JFrame implements ActionListener {
    private JMenuBar menuBar;
    private JMenu menuHeThong, menuBaiTap, menuTroGiup;
    private JMenuItem itemThoat, itemIntro;
    private JMenuItem itemBai1, itemBai2, itemBai3, itemBai4, itemBai5;

    public MainApp() {
        setTitle("Quản lý bài tập Java");
        setSize(1060, 640);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        BackgroundPanel mainPanel = new BackgroundPanel("Kaguya2.jpg");
        mainPanel.setLayout(new BorderLayout());

        MultiLineOutlineLabel lblWelcome = new MultiLineOutlineLabel("App Quản Lý Bài Tập\n- by Kousujo -", SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 45));
        lblWelcome.setBorder(BorderFactory.createEmptyBorder(0, 0 ,50, 0));
        lblWelcome.setForeground(Color.WHITE);
        lblWelcome.setOutlineColor(Color.BLUE);

        mainPanel.add(lblWelcome, BorderLayout.SOUTH);
        this.setContentPane(mainPanel);

        initMenu();
    }

    private void initMenu() {
        menuBar = new JMenuBar();

        menuHeThong = new JMenu("Hệ thống");
        itemThoat = new JMenuItem("Thoát");
        menuHeThong.add(itemThoat);

        menuBaiTap = new JMenu("Bài tập Chương 8");
        itemBai1 = new JMenuItem("Bài 1: Form Đăng ký");
        itemBai2 = new JMenuItem("Bài 2: Chuyển đổi chữ");
        itemBai3 = new JMenuItem("Bài 3: Hình thức học");
        itemBai4 = new JMenuItem("Bài 4: Chọn sở thích");
        itemBai5 = new JMenuItem("Bài 5: Chọn giới tính");

        menuBaiTap.add(itemBai1);
        menuBaiTap.add(itemBai2);
        menuBaiTap.add(itemBai3);
        menuBaiTap.add(itemBai4);
        menuBaiTap.add(itemBai5);

        menuTroGiup = new JMenu("Trợ giúp");
        itemIntro = new JMenuItem("Giới thiệu");
        menuTroGiup.add(itemIntro);

        menuBar.add(menuHeThong);
        menuBar.add(menuBaiTap);
        menuBar.add(menuTroGiup);

        this.setJMenuBar(menuBar);

        itemThoat.addActionListener(this);
        itemIntro.addActionListener(this);
        itemBai1.addActionListener(this);
        itemBai2.addActionListener(this);
        itemBai3.addActionListener(this);
        itemBai4.addActionListener(this);
        itemBai5.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == itemThoat) {
            System.exit(0);
        } 
        else if (e.getSource() == itemIntro) {
            JOptionPane.showMessageDialog(this, "Phần mềm quản lý bài tập\nAuthor: Kousujo");
        }
        else if (e.getSource() == itemBai1) {
            new Bai1_Form_Dangky().setVisible(true); 
        }
        else if (e.getSource() == itemBai2) {
            new Bai2_Form_ChuyenDoiChu().setVisible(true); 
        }
        else if (e.getSource() == itemBai3) {
            new Bai3_Form_HinhThucHoc().setVisible(true);
        }
        else if (e.getSource() == itemBai4) {
            new Bai4_Form_ChonSoThich().setVisible(true); 
        }
        else if (e.getSource() == itemBai5) {
            new Bai5_Form_ChonGioiTinh().setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainApp().setVisible(true);
        });
    }
}   