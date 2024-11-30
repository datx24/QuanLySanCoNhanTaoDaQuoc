# Quản Lý Sân Bóng

Dự án **Quản Lý Sân Bóng** được phát triển nhằm hỗ trợ quản lý sân bóng, lượt đặt sân, hóa đơn, và người dùng một cách chuyên nghiệp, dễ dàng, đồng thời cung cấp báo cáo và phân tích để cải thiện hiệu quả kinh doanh.

---

## Chức Năng Dự Án

### Giai đoạn 1: Cơ bản

#### 1. Đăng nhập/Đăng xuất và Phân quyền
- **Chức năng:**
  - Giao diện đăng nhập dành riêng cho admin.(Done)
  - Kiểm tra tài khoản và phân quyền (admin hoặc user thường).
  - Đảm bảo admin có thể truy cập được các trang quản trị (dashboard), trong khi user không được phép.
- **Mục tiêu:**
  - Xác định rõ vai trò và quyền hạn của admin trong hệ thống.

#### 2. Dashboard - Tổng quan hệ thống
- **Chức năng:**
  - Hiển thị các thông tin tổng quan:
    - Số sân bóng hiện có.
    - Số lượt đặt sân trong ngày/tháng.
    - Tổng doanh thu.
    - Danh sách yêu cầu đặt sân đang chờ xử lý.
- **Mục tiêu:**
  - Giúp admin có cái nhìn nhanh về tình trạng hoạt động của hệ thống.

---

### Giai đoạn 2: Chức năng quản lý chính

#### 3. Quản lý sân bóng (`fields_64130299`)
- **Chức năng:**
  - Thêm, sửa, xóa thông tin sân bóng.
  - Upload hình ảnh sân bóng.
  - Tìm kiếm và lọc sân theo trạng thái (còn trống, đang bảo trì).
- **Mục tiêu:**
  - Admin có thể kiểm soát hoàn toàn danh sách sân bóng.

#### 4. Quản lý đặt sân (`bookings_64130299`)
- **Chức năng:**
  - Duyệt hoặc hủy yêu cầu đặt sân từ khách hàng.
  - Tìm kiếm các yêu cầu đặt sân theo thời gian hoặc sân cụ thể.
  - Đánh dấu các yêu cầu đã hoàn thành hoặc bị hủy.
- **Mục tiêu:**
  - Đảm bảo tất cả các yêu cầu đặt sân đều được xử lý hiệu quả.

#### 5. Quản lý người dùng (`users_64130299`)
- **Chức năng:**
  - Hiển thị danh sách người dùng (email, tên).(Done)
  - Tìm kiếm người dùng theo tên, email.
  - Khóa/mở khóa tài khoản người dùng khi cần thiết.
- **Mục tiêu:**
  - Kiểm soát tốt tài khoản của khách hàng và ngăn chặn hành vi sai phạm.

---

### Giai đoạn 3: Chức năng nâng cao

#### 6. Quản lý hóa đơn (`invoices_64130299`)
- **Chức năng:**
  - Hiển thị danh sách hóa đơn đã tạo.
  - Tìm kiếm hóa đơn theo mã, thời gian, hoặc sân bóng.
  - Xác nhận trạng thái thanh toán (đã thanh toán/chưa thanh toán).
- **Mục tiêu:**
  - Quản lý tài chính rõ ràng và minh bạch.

#### 7. Quản lý bảo trì sân bóng (`maintenance_64130299`)
- **Chức năng:**
  - Lập lịch bảo trì sân bóng (ngày bắt đầu, ngày kết thúc).
  - Đánh dấu sân bóng đang trong trạng thái bảo trì.
  - Hiển thị danh sách lịch sử bảo trì sân bóng.
- **Mục tiêu:**
  - Đảm bảo hệ thống quản lý được tình trạng kỹ thuật của sân.

---

### Giai đoạn 4: Chức năng hỗ trợ và phân tích

#### 8. Báo cáo và phân tích
- **Chức năng:**
  - Thống kê doanh thu theo ngày/tháng/năm.
  - Thống kê lượt đặt sân theo thời gian hoặc loại sân.
  - Biểu đồ trực quan (doanh thu, lượt đặt sân).
- **Mục tiêu:**
  - Hỗ trợ admin đưa ra quyết định kinh doanh tốt hơn.

#### 9. Gửi thông báo
- **Chức năng:**
  - Gửi email hoặc thông báo đến người dùng khi đặt sân thành công, bị hủy.
  - Gửi thông báo bảo trì sân bóng trước thời hạn.
- **Mục tiêu:**
  - Tăng tương tác giữa hệ thống và người dùng.

---

## Công Nghệ Sử Dụng
- **Frontend:** JavaFX (FXML), CSS.
- **Backend:** Java, JDBC.
- **Database:** MySQL.
- **Frameworks:** Bootstrap (nếu cần giao diện web), Apache POI (nếu cần xuất dữ liệu).

---

## Mục Tiêu Dự Án
- Tối ưu hóa quy trình quản lý sân bóng.
- Tạo ra hệ thống quản lý chuyên nghiệp, dễ dàng mở rộng và nâng cấp.
- Cải thiện trải nghiệm người dùng và admin thông qua giao diện hiện đại và trực quan.

---

