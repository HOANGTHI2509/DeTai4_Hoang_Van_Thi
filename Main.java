import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        QuanLyHangHoa quanLyHangHoa= new QuanLyHangHoa();
        QuanLyKho quanLyKho = new QuanLyKho();
        QuanLyNhanVien khoNhanVien = new QuanLyNhanVien();

        int luaChon;
        do {
            System.out.println("\n===== Hệ Thống Quản Lý Kho =====");
            System.out.println("1. Quản lý hàng hóa");
            System.out.println("2. Quản lý nhập/xuất kho");
            System.out.println("3. Quản lý nhân viên kho");
            System.out.println("4. Thoát");
            System.out.print("Nhập lựa chọn: ");
            luaChon = Integer.parseInt(scanner.nextLine());

            switch (luaChon) {
                case 1:
                    quanLyHangHoa.menu();
                    break;
                case 2:
                    quanLyKho.menu();
                    break;
                case 3:
                    khoNhanVien.menu();
                    break;
                case 4:
                    System.out.println("Thoát chương trình.");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại!");
            }
        } while (luaChon != 4);

        scanner.close();
    }
}
