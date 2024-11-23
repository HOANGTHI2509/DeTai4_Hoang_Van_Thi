import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class HangHoa implements Serializable {
    private static final long serialVersionUID = 1L;
    private String maHangHoa;
    private String tenHangHoa;
    private int soLuong;
    private double gia;

    public HangHoa(String maHangHoa, String tenHangHoa, int soLuong, double gia) {
        this.maHangHoa = maHangHoa;
        this.tenHangHoa = tenHangHoa;
        this.soLuong = soLuong;
        this.gia = gia;
    }

    public String getMaHangHoa() {
        return maHangHoa;
    }

    public void hienThiThongTin() {
        System.out.printf("| %-10s | %-20s | %-10d | %-10.2f |\n", maHangHoa, tenHangHoa, soLuong, gia);
    }

    public String getTenHangHoa() {
        return tenHangHoa;
    }

    public void setTenHangHoa(String tenHangHoa) {
        this.tenHangHoa = tenHangHoa;
    }

    public void setSoLuong(int soLuongMoi) {
    }

    public void setGia(double giaMoi) {
    }
}

class QuanLyHangHoa {
    private ArrayList<HangHoa> danhSachHangHoa = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    private final String TEN_FILE = "danhSachHangHoa.dat";

    public QuanLyHangHoa() {
        docTuFile(); // Tự động tải dữ liệu từ file khi khởi động
    }

    private void luuVaoFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(TEN_FILE))) {
            oos.writeObject(danhSachHangHoa);
        } catch (IOException e) {
            System.out.println("Lỗi khi lưu dữ liệu: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void docTuFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(TEN_FILE))) {
            danhSachHangHoa = (ArrayList<HangHoa>) ois.readObject();
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

    public void themHangHoa() {
        try {
            System.out.print("Nhập mã hàng hóa: ");
            String maHangHoa = scanner.nextLine();
            if (kiemTraTonTai(maHangHoa)) {
                System.out.println("Mã hàng hóa đã tồn tại. Vui lòng thử lại.");
                return;
            }

            System.out.print("Nhập tên hàng hóa: ");
            String tenHangHoa = scanner.nextLine();
            System.out.print("Nhập số lượng: ");
            int soLuong = Integer.parseInt(scanner.nextLine());
            if (soLuong < 0) throw new NumberFormatException("Số lượng phải lớn hơn hoặc bằng 0.");

            System.out.print("Nhập giá: ");
            double gia = Double.parseDouble(scanner.nextLine());
            if (gia < 0) throw new NumberFormatException("Giá phải lớn hơn hoặc bằng 0.");

            danhSachHangHoa.add(new HangHoa(maHangHoa, tenHangHoa, soLuong, gia));
            luuVaoFile();
            System.out.println("Thêm hàng hóa thành công và đã lưu vào file!");
        } catch (NumberFormatException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    private boolean kiemTraTonTai(String maHangHoa) {
        for (HangHoa hangHoa : danhSachHangHoa) {
            if (hangHoa.getMaHangHoa().equalsIgnoreCase(maHangHoa)) {
                return true;
            }
        }
        return false;
    }

    public void hienThiHangHoa() {
        if (danhSachHangHoa.isEmpty()) {
            System.out.println("Danh sách hàng hóa trống.");
        } else {
            System.out.printf("| %-10s | %-20s | %-10s | %-10s |\n", "Mã HH", "Tên hàng hóa", "Số lượng", "Giá");
            System.out.println("------------------------------------------------------------");
            for (HangHoa hangHoa : danhSachHangHoa) {
                hangHoa.hienThiThongTin();
            }
            System.out.println("------------------------------------------------------------");
        }
    }
    public void timKiemHangHoaTheoMa() {
        System.out.print("Nhập mã hàng hóa cần tìm: ");
        String maHangHoa = scanner.nextLine();
        boolean found = false;
        for (HangHoa hangHoa : danhSachHangHoa) {
            if (hangHoa.getMaHangHoa().equalsIgnoreCase(maHangHoa)) {
                hangHoa.hienThiThongTin();
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Không tìm thấy hàng hóa với mã: " + maHangHoa);
        }
    }
    public void timKiemHangHoaTheoTen() {
        System.out.print("Nhập tên hàng hóa cần tìm: ");
        String tenHangHoa = scanner.nextLine();
        boolean found = false;
        for (HangHoa hangHoa : danhSachHangHoa) {
            if (hangHoa.getTenHangHoa().toLowerCase().contains(tenHangHoa.toLowerCase())) {
                hangHoa.hienThiThongTin();
                found = true;
            }
        }
        if (!found) {
            System.out.println("Không tìm thấy hàng hóa với tên: " + tenHangHoa);
        }
    }
    public void capNhatHangHoa() {
        System.out.print("Nhập mã hàng hóa cần cập nhật: ");
        String maHangHoa = scanner.nextLine();
        for (HangHoa hangHoa : danhSachHangHoa) {
            if (hangHoa.getMaHangHoa().equalsIgnoreCase(maHangHoa)) {
                System.out.print("Nhập tên mới: ");
                String tenMoi = scanner.nextLine();
                System.out.print("Nhập số lượng mới: ");
                int soLuongMoi = Integer.parseInt(scanner.nextLine());
                System.out.print("Nhập giá mới: ");
                double giaMoi = Double.parseDouble(scanner.nextLine());

                hangHoa.setTenHangHoa(tenMoi);
                hangHoa.setSoLuong(soLuongMoi);
                hangHoa.setGia(giaMoi);

                luuVaoFile();
                System.out.println("Cập nhật thông tin hàng hóa thành công.");
                return;
            }
        }
        System.out.println("Không tìm thấy hàng hóa với mã: " + maHangHoa);
    }

    public void xoaHangHoaTheoMa() {
        System.out.print("Nhập mã hàng hóa cần xóa: ");
        String maHangHoa = scanner.nextLine();
        if (danhSachHangHoa.removeIf(h -> h.getMaHangHoa().equalsIgnoreCase(maHangHoa))) {
            luuVaoFile();
            System.out.println("Xóa hàng hóa thành công.");
        } else {
            System.out.println("Không tìm thấy hàng hóa với mã: " + maHangHoa);
        }
    }

    public void xoaHangHoaTheoTen() {
        System.out.print("Nhập tên hàng hóa cần xóa: ");
        String tenHangHoa = scanner.nextLine();
        if (danhSachHangHoa.removeIf(h -> h.getTenHangHoa().equalsIgnoreCase(tenHangHoa))) {
            luuVaoFile();
            System.out.println("Xóa hàng hóa thành công.");
        } else {
            System.out.println("Không tìm thấy hàng hóa với tên: " + tenHangHoa);
        }
    }
    public void kiemTraHangHoaHetHan() {
        System.out.println("Chức năng kiểm tra hết hạn chưa được triển khai.");
    }

    public void hienThiHangHoaTuFile() {
        docTuFile(); // Đảm bảo rằng dữ liệu mới nhất từ file được nạp vào danh sách
        hienThiHangHoa(); // Hiển thị danh sách hàng hóa
    }

    public void menu() {
        int luaChon = 0;
        do {
            System.out.println("\n===== Quản Lý Hàng Hóa =====");
            System.out.println("1. Thêm hàng hóa");
            System.out.println("2. Hiển thị danh sách hàng hóa");
            System.out.println("3. Tìm kiếm hàng hóa theo mã");
            System.out.println("4. Tìm kiếm hàng hóa theo tên");
            System.out.println("5. Cập nhật thông tin hàng hóa");
            System.out.println("6. Xóa hàng hóa theo mã");
            System.out.println("7. Xóa hàng hóa theo tên");
            System.out.println("8. Kiểm tra hàng hóa hết hạn");
            System.out.println("9. Thoát");
            System.out.print("Nhập lựa chọn: ");
            try {
                luaChon = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Vui lòng nhập số.");
                continue;
            }

            switch (luaChon) {
                case 1:
                    themHangHoa();
                    break;
                case 2:
                    hienThiHangHoaTuFile();
                    break;
                case 3:
                    timKiemHangHoaTheoMa();
                    break;
                case 4:
                    timKiemHangHoaTheoTen();
                    break;
                case 5:
                    capNhatHangHoa();
                    break;
                case 6:
                    xoaHangHoaTheoMa();
                    break;
                case 7:
                    xoaHangHoaTheoTen();
                    break;
                case 8:
                    kiemTraHangHoaHetHan();
                    break;
                case 9:
                    System.out.println("Thoát quản lý hàng hóa.");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại!");
            }
        } while (luaChon != 9);
    }
}
