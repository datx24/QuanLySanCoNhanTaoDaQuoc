package Utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CommonUtils {
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
}