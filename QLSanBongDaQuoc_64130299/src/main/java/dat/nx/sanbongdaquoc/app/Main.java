package dat.nx.sanbongdaquoc.app;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import dat.nx.sanbongdaquoc.BLL.*;
import dat.nx.sanbongdaquoc.repositories.*;
import dat.nx.sanbongdaquoc.utils.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
    public void start(Stage primaryStage) throws Exception {
		//Gán primaryStage cho SceneManager
		SceneManager.setPrimaryStage(primaryStage);
		
        Parent root = FXMLLoader.load(getClass().getResource("/dat/nx/sanbongdaquoc/fxml/LoginRegisterView.fxml")); 
        primaryStage.setTitle("Quản Lý Sân Bóng");
//        primaryStage.setWidth(1100);
//        primaryStage.setHeight(600);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
	
	  public static void main(String[] args) {
	        try (Connection connection = DatabaseConnection.getConnection()) {
	            if (connection != null) {
	                System.out.println("Kết nối cơ sở dữ liệu thành công!");
	            } else {
	                System.out.println("Kết nối cơ sở dữ liệu thất bại!");
	            }
	        } catch (SQLException e) {
	            System.err.println("Đã xảy ra lỗi khi kết nối cơ sở dữ liệu:");
	            e.printStackTrace();
	        }
		  launch(args);
//		  String email = "xuandat475@gmail.com";
//		  String password = "XuanDat223@";
//		  UserDAL userDAL = new UserDAL();
//		  UserBLL userBLL = new UserBLL();
//		  System.out.println(userDAL.login(email, password));
//		  System.out.println(userBLL.checkLogin(email, password));
	    }
}
