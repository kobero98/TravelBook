import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;

public class Main {
	@FXML
	private ImageView i;
	@FXML
	private Pane p;
	@FXML
	private Button s;
	int j=0;
	List<Image> list=new ArrayList();
	@FXML
	private void hendler() {
		i.setImage(list.get(j));
		j++;
	}
	public void metti() {
		String MyUrl="jdbc:mysql://172.29.54.230:3306/mydb1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";	
		Connection connessione = null;
		
			try {
				connessione= DriverManager.getConnection(MyUrl,"root","root");
			
			if (connessione != null) { 
				System.out.println("connesso");
				String query="Select LinkPhoto from PhotoStep where Step_Number=1 and codiceViaggio=21";
				Statement stmt=connessione.createStatement();
				ResultSet rs=stmt.executeQuery(query);
				while(rs.next())
					{
					Image imagine=new Image(rs.getBinaryStream(1));
					list.add(imagine);
					}
				
		} }catch (SQLException e) {
				e.printStackTrace();
			}
	
	}
}