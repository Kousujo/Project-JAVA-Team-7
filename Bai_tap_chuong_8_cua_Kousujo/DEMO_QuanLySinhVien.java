import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

class SinhVien { 
    int ma_sinh_vien;
    String ho;
    String ten;
    int sdt;

    void nhapTT() {
        Scanner nhap = new Scanner(System.in);
        System.out.print("Nhap ma sinh vien: ");
        ma_sinh_vien = nhap.nextInt();
        System.out.print("Nhap ho sinh vien: ");
        ho = nhap.nextLine();
        System.out.print("Nhap ten sinh vien: ");
        ten = nhap.nextLine();
        System.out.print("Nhap so dien thoai sinh vien: ");
        sdt = nhap.nextInt();
        System.out.println();
    }

    void xuatTT() {
        System.out.println("Ma sinh vien: " + ma_sinh_vien);
        System.out.println("Ho sinh vien: " + ho);
        System.out.println("Ten sinh vien: " + ten);
        System.out.println("So dien thoai sinh vien: " + sdt);
    }
}

class Quanlysv {
    void nhapDS(SinhVien sv[], int n) {
        for (int i = 0 ; i < n ; i++) {
            System.out.println("Nhap thong tin sinh vien thu " + (i + 1) + ": ");
            sv[i] = new SinhVien();
            sv[i].nhapTT();
        }
    }

    void sapXepTen(SinhVien sv[], int n) {
        for (int i = 0 ; i < n - 1 ; i++) {
            for (int j = i + 1 ; j < n ; j++) {
                if (sv[i].ten.compareToIgnoreCase(sv[j].ten) > 0) {
                    SinhVien temp = sv[i];
                    sv[i] = sv[j];
                    sv[j] = temp;
                }
            }
        }
    }
}

public class DEMO_QuanLySinhVien extends JFrame {
    private JTextField txtMaSV, txtHo, txtTen, txtSDT;
    private DefaultTableModel tableModel;
    private ArrayList<SinhVien> listSV = new ArrayList<>();

    public DEMO_QuanLySinhVien() {
        setTitle("Team 7 - Quản Lý Sinh Viên");
        setSize(600, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());


        JPanel pnlInput = new JPanel(new GridLayout(4, 2, 10, 10));
        pnlInput.setBorder(BorderFactory.createTitledBorder("Thông tin sinh viên"));
        pnlInput.add(new JLabel("Mã Sinh Viên:")); txtMaSV = new JTextField(); pnlInput.add(txtMaSV);
        pnlInput.add(new JLabel("Họ:")); txtHo = new JTextField(); pnlInput.add(txtHo);
        pnlInput.add(new JLabel("Tên:")); txtTen = new JTextField(); pnlInput.add(txtTen);
        pnlInput.add(new JLabel("Số Điện Thoại:")); txtSDT = new JTextField(); pnlInput.add(txtSDT);


        JPanel pnlButtons = new JPanel();
        JButton btnAdd = new JButton("Thêm Sinh Viên");
        JButton btnSort = new JButton("Sắp Xếp Theo Tên");
        pnlButtons.add(btnAdd);
        pnlButtons.add(btnSort);


        String[] columns = {"Mã SV", "Họ", "Tên", "SĐT"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        add(pnlInput, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(pnlButtons, BorderLayout.SOUTH);


        btnAdd.addActionListener(e -> {
            try {
                SinhVien sv = new SinhVien();
                sv.ma_sinh_vien = Integer.parseInt(txtMaSV.getText());
                sv.ho = txtHo.getText();
                sv.ten = txtTen.getText();
                sv.sdt = Integer.parseInt(txtSDT.getText());

                listSV.add(sv);
                updateTable();
                clearFields();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số cho Mã SV và SĐT!");
            }
        });


        btnSort.addActionListener(e -> {
            Collections.sort(listSV, Comparator.comparing(s -> s.ten, String.CASE_INSENSITIVE_ORDER));
            updateTable();
            JOptionPane.showMessageDialog(this, "Đã sắp xếp danh sách theo tên!");
        });
    }

    private void updateTable() {
        tableModel.setRowCount(0);
        for (SinhVien sv : listSV) {
            tableModel.addRow(new Object[]{sv.ma_sinh_vien, sv.ho, sv.ten, sv.sdt});
        }
    }

    private void clearFields() {
        txtMaSV.setText(""); txtHo.setText(""); txtTen.setText(""); txtSDT.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DEMO_QuanLySinhVien().setVisible(true));
    }
}