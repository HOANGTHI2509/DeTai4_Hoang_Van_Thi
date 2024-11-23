import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

class QuanLyKho {
    private HashMap<String, Integer> kho = new HashMap<>();
    private Scanner scanner = new Scanner(System.in);
    private final String TEN_FILE = "kho.dat";

    public QuanLyKho() {
        docTuFile();
    }

    private void luuVaoFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(TEN_FILE))) {
            oos.writeObject(kho);
        } catch (IOException e) {
            System.out.println("Lỗi khi lưu dữ liệu: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void docTuFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(TEN_FILE))) {
            kho = (HashMap<String, Integer>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("File chưa tồn tại. Dữ liệu mới sẽ được tạo.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Lỗi khi đọc dữ liệu: " + e.getMessage());
        }
    }

    public void nhapKho() {
        System.out.print("Nhập mã hàng hóa: ");
        String maHangHoa = scanner.nextLine();
        System.out.print("Nhập số lượng nhập: ");
        int soLuong = Integer.parseInt(scanner.nextLine());

        kho.put(maHangHoa, kho.getOrDefault(maHangHoa, 0) + soLuong);
        luuVaoFile();
        System.out.println("Nhập kho thành công và đã lưu vào file!");
    }

    public void xuatKho() {
        System.out.print("Nhập mã hàng hóa: ");
        String maHangHoa = scanner.nextLine();
        if (!kho.containsKey(maHangHoa)) {
            System.out.println("Hàng hóa không tồn tại trong kho.");
            return;
        }

        System.out.print("Nhập số lượng xuất: ");
        int soLuong = Integer.parseInt(scanner.nextLine());
        int soLuongHienTai = kho.get(maHangHoa);

        if (soLuong > soLuongHienTai) {
            System.out.println("Số lượng không đủ để xuất kho.");
        } else {
            kho.put(maHangHoa, soLuongHienTai - soLuong);
            luuVaoFile();
            System.out.println("Xuất kho thành công và đã lưu vào file!");
        }
    }

    public void hienThiTonKho() {
        if (kho.isEmpty()) {
            System.out.println("Kho trống.");
        } else {
            System.out.printf("| %-10s | %-10s |\n", "Mã HH", "Số lượng");
            System.out.println("-------------------------");
            kho.forEach((maHangHoa, soLuong) -> System.out.printf("| %-10s | %-10d |\n", maHangHoa, soLuong));
        }
    }

    public void hienThiTonKhoTuFile() {
        docTuFile(); // Đọc dữ liệu từ file để đảm bảo cập nhật mới nhất
        System.out.println("Dữ liệu tồn kho từ file:");
        hienThiTonKho(); // Hiển thị dữ liệu tồn kho
    }

    public void kiemKe() {
        System.out.print("Nhập mã hàng hóa: ");
        String maHangHoa = scanner.nextLine();

        if (!kho.containsKey(maHangHoa)) {
            System.out.println("Hàng hóa không tồn tại trong kho. Bạn có muốn thêm mới? (y/n)");
            String confirm = scanner.nextLine();
            if (confirm.equalsIgnoreCase("y")) {
                System.out.print("Nhập số lượng thực tế: ");
                int soLuong = Integer.parseInt(scanner.nextLine());
                kho.put(maHangHoa, soLuong);
                System.out.println("Hàng hóa mới đã được thêm vào kho.");
            } else {
                System.out.println("Không có thay đổi nào được thực hiện.");
                return;
            }
        } else {
            System.out.print("Nhập số lượng thực tế: ");
            int soLuongThucTe = Integer.parseInt(scanner.nextLine());
            kho.put(maHangHoa, soLuongThucTe);
            System.out.println("Cập nhật kiểm kê thành công!");
        }

        luuVaoFile(); // Lưu lại trạng thái sau kiểm kê
    }
    public void menu() {
        int luaChon = 0;
        do {
            System.out.println("\n===== Quản Lý Nhập/Xuất Kho =====");
            System.out.println("1. Nhập kho");
            System.out.println("2. Xuất kho");
            System.out.println("3. Hiển thị tồn kho từ file");
            System.out.println("4. Kiểm kê");
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
                    nhapKho();
                    break;
                case 2:
                    xuatKho();
                    break;
                case 3:
                    hienThiTonKhoTuFile();
                    break;
                case 4:
                    kiemKe(); // Gọi phương thức kiểm kê
                    break;
                case 5:
                    System.out.println("Thoát quản lý nhập/xuất kho.");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại!");
            }
        } while (luaChon != 5);
    }
}
