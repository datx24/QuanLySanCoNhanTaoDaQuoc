package dat.nx.sanbongdaquoc.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;
import java.awt.Desktop; // Để mở file Excel tự động
import java.io.File; // Để làm việc với tệp
import java.io.FileOutputStream; // Để ghi dữ liệu vào tệp
import java.io.IOException;

import org.apache.poi.ss.usermodel.*; // Các lớp liên quan đến Excel
import org.apache.poi.xssf.usermodel.XSSFWorkbook; // Để làm việc với định dạng XLSX

import dat.nx.sanbongdaquoc.models.entities.BookingDTO;

import java.util.List; // Để làm việc với danh sách dữ liệu

public class CommonUtils {
	// Phương thức xuất báo cáo Excel
    public static void exportToExcel(List<BookingDTO> data, String filePath) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Booking Report");

            // Tạo header
            Row headerRow = sheet.createRow(0);
            String[] headers = {"ID đặt sân", "Thời điểm đặt", "Trạng thái", "Người đặt", "Tên sân", "Trạng thái trả tiền"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            // Ghi dữ liệu
            int rowIndex = 1;
            for (BookingDTO booking : data) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(booking.getBookingID());
                row.createCell(1).setCellValue(booking.getTimeDetails());
                row.createCell(2).setCellValue(booking.getStatus().getStatus());
                row.createCell(3).setCellValue(booking.getUserName());
                row.createCell(4).setCellValue(booking.getFieldName());
                row.createCell(5).setCellValue(booking.getPaymentStatus().getStatus());
            }

            // Lưu file Excel
            File outputFile = new File(filePath);
            try (FileOutputStream fileOut = new FileOutputStream(outputFile)) {
                workbook.write(fileOut);
                System.out.println("Báo cáo đã được xuất ra file: " + filePath);
            }

            // Mở file tự động sau khi ghi xong
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(outputFile);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
	// Phương thức mã hóa mật khẩu bằng SHA-256
	public static String encodePassword(String plainPassword) {
		if (plainPassword == null || plainPassword.isEmpty()) {
			throw new IllegalArgumentException("Mật khẩu không được để trống");
		}
		try {
			// Tạo một instance của MessageDigest với SHA-256
			MessageDigest digest = MessageDigest.getInstance("SHA-256");

			// Chuyển mật khẩu sang dạng byte và thực hiện hashing
			byte[] encodedHash = digest.digest(plainPassword.getBytes(StandardCharsets.UTF_8));

			// Chuyển kết quả hashing từ byte array sang chuỗi hex
			return bytesToHex(encodedHash);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Lỗi khi mã hóa mật khẩu bằng SHA-256", e);
		}
	}

	// Hàm chuyển byte array thành chuỗi hex
	private static String bytesToHex(byte[] hash) {
		StringBuilder hexString = new StringBuilder();
		for (byte b : hash) {
			// Chuyển byte thành chuỗi hex và thêm số 0 nếu cần
			String hex = Integer.toHexString(0xff & b);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}

	// Hàm kiểm tra email chuẩn RFC 5322
	public static boolean isValidEmail(String email) {
		String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
		return Pattern.matches(emailRegex, email);
	}

	// Hàm kiểm tra số điện thoại
	public static boolean isValidPhoneNumber(String phoneNumber) {
		String phoneRegex = "^(\\+\\d{1,3}[- ]?)?\\d{10}$";
		return Pattern.matches(phoneRegex, phoneNumber);
	}
	
	//Hàm kiểm tra mật khẩu
	public static boolean isValidPassword(String password) {
        return password.length() >= 6;
    }

}