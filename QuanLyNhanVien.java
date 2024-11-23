import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class NhanVien implements Serializable {
    private static final long serialVersionUID = 1L;
    private String maNhanVien;
    private String tenNhanVien;
    private String chucVu;

    public NhanVien(String maNhanVien, String tenNhanVien, String chucVu) {
        this.maNhanVien = maNhanVien;
        this.tenNhanVien = tenNhanVien;
        this.chucVu = chucVu;
    }

    public void hienThiThongTin() {
        System.out.printf("| %-10s | %-20s | %-15s |\n", maNhanVien, tenNhanVien, chucVu);
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }
}

class QuanLyNhanVien {
    private ArrayList<NhanVien> danhSachNhanVien = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    private final String TEN_FILE = "nhanvien.dat";

    public QuanLyNhanVien() {
        docTuFile();
    }

    private void luuVaoFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(TEN_FILE))) {
            oos.writeObject(danhSachNhanVien);
        } catch (IOException e) {
            System.out.println("Lỗi khi lưu dữ liệu: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void docTuFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(TEN_FILE))) {
            danhSachNhanVien = (ArrayList<NhanVien>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("File chưa tồn tại. Dữ liệu mới sẽ được tạo.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Lỗi khi đọc dữ liệu: " + e.getMessage());
        }
    }

    public void docFileThuCong() {
        docTuFile();
        System.out.println("Dữ liệu từ file đã được tải lên bộ nhớ.");
    }

    public void themNhanVien() {
        System.out.print("Nhập mã nhân viên: ");
        String maNhanVien = scanner.nextLine();
        System.out.print("Nhập tên nhân viên: ");
        String tenNhanVien = scanner.nextLine();
        System.out.print("Nhập chức vụ: ");
        String chucVu = scanner.nextLine();

        danhSachNhanVien.add(new NhanVien(maNhanVien, tenNhanVien, chucVu));
        luuVaoFile();
        System.out.println("Thêm nhân viên thành công và đã lưu vào file!");
    }

    public void hienThiDanhSachNhanVien() {
        if (danhSachNhanVien.isEmpty()) {
            System.out.println("Danh sách nhân viên trống.");
        } else {
            System.out.printf("| %-10s | %-20s | %-15s |\n", "Mã NV", "Tên nhân viên", "Chức vụ");
            System.out.println("-----------------------------------------------------------");
            for (NhanVien nhanVien : danhSachNhanVien) {
                nhanVien.hienThiThongTin();
            }
        }
    }

    public void xoaNhanVien() {
        System.out.print("Nhập mã nhân viên cần xóa: ");
        String maNhanVien = scanner.nextLine();

        NhanVien nhanVienCanXoa = null;
        for (NhanVien nhanVien : danhSachNhanVien) {
            if (nhanVien.getMaNhanVien().equals(maNhanVien)) {
                nhanVienCanXoa = nhanVien;
                break;
            }
        }

        if (nhanVienCanXoa != null) {
            danhSachNhanVien.remove(nhanVienCanXoa);
            luuVaoFile();
            System.out.println("Xóa nhân viên thành công và đã lưu thay đổi vào file!");
        } else {
            System.out.println("Không tìm thấy nhân viên với mã: " + maNhanVien);
        }
    }

    public void hienThiNhanVienTuFile() {
        docTuFile();
        hienThiDanhSachNhanVien();
    }

    public void menu() {
        int luaChon = 0;
        do {
            System.out.println("\n===== Quản Lý Nhân Viên Kho =====");
            System.out.println("1. Thêm nhân viên");
            System.out.println("3. Xóa nhân viên");
            System.out.println("4. Hiển thị danh sách nhân viên");
            System.out.println("5. Thoát");
            System.out.print("Nhập lựa chọn: ");
            try {
                luaChon = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Vui lòng nhập số.");
                continue;
            }
            switch (luaChon) {
                case 1:
                    themNhanVien();
                    break;
                case 2:
                    xoaNhanVien();
                    break;
                case 3:
                    hienThiNhanVienTuFile();
                    break;
                case 4:
                    System.out.println("Thoát quản lý nhân viên kho.");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại!");
            }
        } while (luaChon != 4);
    }
}
