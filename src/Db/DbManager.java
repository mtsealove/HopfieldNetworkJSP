package Db;

import java.sql.*;
import java.util.ArrayList;

public class DbManager {
    private Connection conn;
    private PreparedStatement pstmt;

    public DbManager() {
        String dbURL = "jdbc:mysql://localhost:3306/AI?serverTimezone=UTC";
        String dbID = "root";
        String dbPassword = "xxxxxxxxxx";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // db에 학습 패턴 저장, 배열 형식이 아닌 ','로 구분한 -1, 1의 형태의 varchar형식으로 저장
    public int insert(String line) {
        String SQL = "INSERT INTO Pattern set Line=?";
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, line);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

//  db에 저장된 학습 패턴을 객체로 변환하여 반환
    public ArrayList<Pattern> getPatterns() {
        ArrayList<Pattern> patterns = new ArrayList<>();
        String query = "select Line from Pattern";
        try {
            pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
//            결과를 String 형식으로 받아 배열로 변환한 객체로 저장
            while (rs.next()) {
                patterns.add(new Pattern(rs.getString("Line")));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patterns;
    }
}
