package com.peng.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.peng.bean.TbGpsBean;

public class TbGpsDao {
	
	private static final String driver = "oracle.jdbc.driver.OracleDriver";
	private static final String url = "jdbc:oracle:thin:@localhost:1521:ora"; 
	private static final String user = "HZGPS_TAXI";
	private static final String password = "wxf369898";
		
	//在TB_GPS_1201_4分区中查询所有的出租车牌号
	public ArrayList<String> selectTotalVehicleNum()
	{
		Connection connection = null;
		try {
			Class.forName(driver);
			connection=DriverManager.getConnection(url,user,password);
		} catch (ClassNotFoundException e1) {
			System.out.println(e1);
		}
		catch(SQLException e2){
			System.out.println(e2);
		}
				
		PreparedStatement pstmt = null;
		ResultSet rst = null;
		ArrayList<String> totalList = new ArrayList<String>();
		try{		
			pstmt = connection.prepareStatement("select distinct tb_gps_1201.vehicle_num from tb_gps_1201 PARTITION(TB_GPS_1201_4)");			
			rst = pstmt.executeQuery();
			while(rst.next()){
				if(rst.getString(1)!=null){
					totalList.add(rst.getString(1));
				}			
			}
			pstmt.close();
			return totalList;
		}catch(SQLException se1){
			System.out.println(se1);
			return null;
		}finally{
			try{
				connection.close();
			}catch(SQLException se2){
				System.out.println(se2); 
				return null;
			}
		}
	}
	
	//在哪一个分区中，根据出租车牌号，查询本车的出租车轨迹
	public ArrayList<TbGpsBean> selectByVehicleNum(String partition, String vehicleNum){		
		Connection connection = null;
		try {
			Class.forName(driver);
			connection=DriverManager.getConnection(url,user,password);
		} catch (ClassNotFoundException e1) {
			System.out.println(e1);
		}
		catch(SQLException e2){
			System.out.println(e2);
		}
			
		PreparedStatement pstmt = null;
		ResultSet rst = null;
		ArrayList<TbGpsBean> totalList = new ArrayList<TbGpsBean>();		
		try{		
			pstmt = connection.prepareStatement("select * from tb_gps_1201 " + partition + " where tb_gps_1201.vehicle_num=? order by tb_gps_1201.message_id");
			pstmt.setString(1, vehicleNum);
			rst = pstmt.executeQuery();
			while(rst.next()){								
				TbGpsBean  tbGps = new TbGpsBean();
				tbGps.setMessageId(rst.getString(1));
				tbGps.setVehicleId(rst.getString(2));
				tbGps.setVehicleNum(rst.getString(3));
				tbGps.setLongi(rst.getDouble(4));
				tbGps.setLati(rst.getDouble(5));				
				tbGps.setPx(rst.getDouble(6));
				tbGps.setPy(rst.getDouble(7));
				tbGps.setSpeed(rst.getDouble(8));
				tbGps.setDirection(rst.getDouble(9));
				tbGps.setState(rst.getInt(10));	
				tbGps.setCarState(rst.getInt(11));
				tbGps.setSpeedTime(rst.getString(12));
				tbGps.setDbTime(rst.getString(13));	
				tbGps.setNote(rst.getString(14));	
				totalList.add(tbGps);
			}
			pstmt.close();
			return totalList;
		}catch(SQLException se1){
			System.out.println(se1);
			return null;
		}finally{
			try{
				connection.close();
			}catch(SQLException se2){
				System.out.println(se2); 
				return null;
			}
		}
	}
	
	//在哪一个分区中，根据车牌号，查询一辆车在哪一个时间段内的轨迹	
	public ArrayList<TbGpsBean> selectByVehicleNumAndSpeedTime(String partition, String vehicleNum, String time1, String time2){
		Connection connection = null;
		try {
			Class.forName(driver);
			connection=DriverManager.getConnection(url,user,password);
		} catch (ClassNotFoundException e1) {
			System.out.println(e1);
		}
		catch(SQLException e2){
			System.out.println(e2);
		}
		
		String sql1 = "select * from tb_gps_1201 ";
		String sql2 = " where tb_gps_1201.vehicle_num=? and to_char(t.speed_time, 'yyyymmddhh24mi') between '201201040000' annd '201201040559' order by t.message_id";		
		
		PreparedStatement pstmt = null;
		ResultSet rst = null;
		ArrayList<TbGpsBean> totalList = new ArrayList<TbGpsBean>();		
		try{		
			pstmt = connection.prepareStatement(sql1 + partition + sql2);
			pstmt.setString(1, vehicleNum);
			//pstmt.setString(2, time1);
			//pstmt.setString(3, time2);
			rst = pstmt.executeQuery();
			while(rst.next()){
				TbGpsBean  tbGps = new TbGpsBean();
				tbGps.setMessageId(rst.getString(1));
				tbGps.setVehicleId(rst.getString(2));
				tbGps.setVehicleNum(rst.getString(3));
				tbGps.setLongi(rst.getDouble(4));
				tbGps.setLati(rst.getDouble(5));				
				tbGps.setPx(rst.getDouble(6));
				tbGps.setPy(rst.getDouble(7));
				tbGps.setSpeed(rst.getDouble(8));
				tbGps.setDirection(rst.getDouble(9));
				tbGps.setState(rst.getInt(10));	
				tbGps.setCarState(rst.getInt(11));
				tbGps.setSpeedTime(rst.getString(12));
				tbGps.setDbTime(rst.getString(13));	
				tbGps.setNote(rst.getString(14));			
				totalList.add(tbGps);
			}
			pstmt.close();
			return totalList;
		}catch(SQLException se1){
			System.out.println(se1);
			return null;
		}finally{
			try{
				connection.close();
			}catch(SQLException se2){
				System.out.println(se2); 
				return null;
			}
		}	
	}
	
}
