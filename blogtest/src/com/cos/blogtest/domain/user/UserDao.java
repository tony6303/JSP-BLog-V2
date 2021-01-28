package com.cos.blogtest.domain.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.cos.blogtest.config.DB;
import com.cos.blogtest.domain.user.dto.JoinReqDto;
import com.cos.blogtest.domain.user.dto.LoginReqDto;

public class UserDao {
	public int save(JoinReqDto dto) { // 회원가입
		String sql = "insert into user(username, password, email, userRole)";
		sql += "values(?, ?, ?, 'USER')";
		
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUsername());
			pstmt.setString(2, dto.getPassword());
			pstmt.setString(3, dto.getEmail());
			int result = pstmt.executeUpdate();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DB.close(conn, pstmt);
		}
		return -1;
	}
	
	
	public User findByUsernameAndPassword(LoginReqDto dto) { // 로그인
		String sql = "SELECT id, username, email, userRole FROM user WHERE username = ? and password =?";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs  = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUsername()); 
			pstmt.setString(2, dto.getPassword());
			rs =  pstmt.executeQuery(); 

			// Persistence API
			if(rs.next()) { //1건이라도 있다 = true 일때 실행
				User user = User.builder()
						.id(rs.getInt("id"))
						.username(rs.getString("username"))
						.email(rs.getString("email"))
						.userRole(rs.getString("userRole"))
						.build();
				
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally { // 무조건 실행
			DB.close(conn, pstmt, rs);
		}
		return null; // 없어
	}
	
	// SELECT 해서 User 객체를 컬렉션에 담아서 리턴
	public List<User> findAll() {
		List<User> users = new ArrayList<>();
		String sql = "select * from user;";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs  = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				User userEntity = User.builder()
						.id(rs.getInt("id"))
						.username(rs.getString("username"))
						.password(rs.getString("password"))
						.email(rs.getString("email"))
						.userRole(rs.getString("userRole"))
						.build();
				users.add(userEntity);
			}
			return users;
		} catch (Exception e) {
			e.printStackTrace();
		}finally { // 무조건 실행
			DB.close(conn, pstmt, rs);
		}
		
		return null;
	}
	
	public int deleteById(int id) {
		String sql = "DELETE FROM user WHERE id = ?";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			int result = pstmt.executeUpdate();
			return result;

		} catch (Exception e) {
			e.printStackTrace();
		} finally { // 무조건 실행
			DB.close(conn, pstmt);
		}
		return -1;
	}
}
