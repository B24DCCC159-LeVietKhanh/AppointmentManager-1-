# Ứng dụng Quản lý lịch hẹn

Bài thực hành lập trình Android - Lưu trữ dữ liệu bằng SQLite.

## Tài khoản đăng nhập
- **Username:** admin
- **Password:** 123456

## Cách mở và chạy dự án

1. Mở **Android Studio**.
2. Chọn **File → Open** và chọn thư mục `AppointmentManager`.
3. Đợi Gradle sync xong (có thể cần Internet lần đầu để tải dependencies).
4. Kết nối thiết bị thật hoặc mở Emulator.
5. Nhấn nút **Run** (▶) để cài và chạy ứng dụng.

## Cấu trúc chính

- `LoginActivity`: Đăng nhập kiểm tra bảng `users` trong SQLite.
- `MainActivity`: Hiển thị danh sách lịch hẹn (ListView), thống kê, nút thêm.
- `AddAppointmentActivity`: Thêm lịch hẹn mới với DatePicker + TimePicker.
- `AppointmentDetailActivity`: Xem chi tiết, bật/tắt nhắc nhở, xóa.
- `DatabaseHelper`: SQLiteOpenHelper quản lý `appointments.db`.
- `Appointment`: Model class.

## Dữ liệu mẫu
Ba lịch hẹn mẫu được chèn sẵn khi tạo database lần đầu.

## Yêu cầu đã đáp ứng
- Java + XML
- SQLite qua SQLiteOpenHelper
- Intent truyền id
- Không dùng Room / Firebase / ArrayList làm lưu trữ chính
- Tất cả phương thức DatabaseHelper theo yêu cầu
- Sắp xếp theo thời gian, kiểm tra thời gian tương lai khi thêm
- Dữ liệu tồn tại sau khi tắt app
