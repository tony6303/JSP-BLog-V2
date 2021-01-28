package com.cos.blog.domain.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.cos.blog.config.DB;
import com.cos.blog.domain.user.dto.JoinReqDto;
import com.cos.blog.domain.user.dto.LoginReqDto;

public class UserDao {
	public int save(JoinReqDto dto) { // 회원가입
		String sql = "insert into user(username, password, email, address, userRole, createDate)";
		sql += "values(?, ?, ?, ?, 'USER', now())";
		
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUsername());
			pstmt.setString(2, dto.getPassword());
			pstmt.setString(3, dto.getEmail());
			pstmt.setString(4, dto.getAddress());
			int result = pstmt.executeUpdate();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DB.close(conn, pstmt);
		}
		return -1;
	}

	public void update() { // 회원수정

	}

	public int findByUsername(String username) { //아이디 중복 체크
		String sql = "SELECT * FROM user WHERE username = ?";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs  = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username); //dto가 아닌 그냥 username
			rs =  pstmt.executeQuery(); //조회 결과가 1건이라도 있으면 1 , 없으면 -1

			if(rs.next()) { //1건이라도 있다 = true 일때 실행
				return 1; // 있어
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally { // 무조건 실행
			DB.close(conn, pstmt, rs);
		}
		return -1; // 없어
	}

	public void findById() { // 회원정보보기

	}

	public User findByUsernameAndPassword(LoginReqDto dto) { // 로그인
		String sql = "SELECT id, username, email, address FROM user WHERE username = ? and password =?";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs  = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUsername()); //dto가 아닌 그냥 username
			pstmt.setString(2, dto.getPassword());
			rs =  pstmt.executeQuery(); 

			// Persistence API
			if(rs.next()) { //1건이라도 있다 = true 일때 실행
				User user = User.builder()
						.id(rs.getInt("id"))
						.username(rs.getString("username"))
						.email(rs.getString("email"))
						.address(rs.getString("address"))
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
	
}
