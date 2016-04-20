package com.peng.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

import com.peng.dao.TbGpsDao;

public class CleanData {

	private static final String driver = "oracle.jdbc.driver.OracleDriver";
	private static final String url = "jdbc:oracle:thin:@localhost:1521:ora"; 
	private static final String user = "HZGPS_TAXI";
	private static final String password = "wxf369898";
	
	public static void main(String args[]){
		LinkedList<String> list = new LinkedList<String>();
		list.add("PARTITION(TB_GPS_1201_4)");
		list.add("PARTITION(TB_GPS_1201_5)");
		list.add("PARTITION(TB_GPS_1201_6)");
		list.add("PARTITION(TB_GPS_1201_7)");
		list.add("PARTITION(TB_GPS_1201_8)");
		list.add("PARTITION(TB_GPS_1201_9)");list.add("PARTITION(TB_GPS_1201_10)");
		list.add("PARTITION(TB_GPS_1201_11)");list.add("PARTITION(TB_GPS_1201_12)");list.add("PARTITION(TB_GPS_1201_13)");list.add("PARTITION(TB_GPS_1201_14)");
		list.add("PARTITION(TB_GPS_1201_15)");list.add("PARTITION(TB_GPS_1201_16)");list.add("PARTITION(TB_GPS_1201_17)");list.add("PARTITION(TB_GPS_1201_18)");
		list.add("PARTITION(TB_GPS_1201_19)");list.add("PARTITION(TB_GPS_1201_20)");list.add("PARTITION(TB_GPS_1201_21)");list.add("PARTITION(TB_GPS_1201_22)");
		list.add("PARTITION(TB_GPS_1201_23)");list.add("PARTITION(TB_GPS_1201_24)");list.add("PARTITION(TB_GPS_1201_25)");list.add("PARTITION(TB_GPS_1201_26)");
		list.add("PARTITION(TB_GPS_1201_27)");list.add("PARTITION(TB_GPS_1201_28)");list.add("PARTITION(TB_GPS_1201_29)");list.add("PARTITION(TB_GPS_1201_30)");
		list.add("PARTITION(TB_GPS_1201_31)");
		
		TbGpsDao tbGpsDao = new TbGpsDao();
		CleanData cleanData = new CleanData();
		for(int i=0;i<list.size();i++){
			System.out.println("*"+ list.get(i) + "*");
			ArrayList<String> totalVehicleNum = tbGpsDao.selectTotalVehicleNum();
				for(int k=0;k<totalVehicleNum.size();k++){
					System.out.print(k + ":" + totalVehicleNum.get(k) + "	");
					cleanData.qingXiData2(list.get(i),totalVehicleNum.get(k));					
				}
		}
	}
	//删除GPS轨迹点数少于3500个或者空载GPS轨迹点数异常或者过多的出租车数据
	@SuppressWarnings("resource")
	public void qingXiData2(String partition,String vehicleNum){
		//一辆车GPS轨迹记录的条数
		String sql1 = "select count(*) from tb_gps_1201 ";
		String sql2 = " where tb_gps_1201.vehicle_num=? ";
		String sql3 = " and tb_gps_1201.state=0 ";
		
		Connection connection = null;
		try {
			Class.forName(driver);
			connection=DriverManager.getConnection(url,user,password);
		} catch (ClassNotFoundException e1) {
			System.out.println(e1);
		} catch(SQLException e2){
			System.out.println(e2);
		}
		PreparedStatement pstmt = null;
		ResultSet rst = null;		
		try{
			//车辆轨迹条数
			int num1 = 0;
			pstmt = connection.prepareStatement(sql1 + partition + sql2);				
			pstmt.setString(1, vehicleNum);
			rst = pstmt.executeQuery();
			if(rst.next())
				num1 = rst.getInt(1);
			System.out.print(num1 + " ");
			//做出判断
			boolean flag1 = num1<3500;
			if(flag1) {
				pstmt = connection.prepareStatement("delete from tb_gps_1201 t where t.vehicle_num=?");				
				pstmt.setString(1, vehicleNum);
				pstmt.execute();
				System.out.println("删除车牌：" + vehicleNum);
			} else {
				//车辆空载轨迹条数
				int num2 = 0;
				pstmt = connection.prepareStatement(sql1 + partition + sql2 + sql3);				
				pstmt.setString(1, vehicleNum);
				rst = pstmt.executeQuery();
				if(rst.next())
					num2 = rst.getInt(1);
				System.out.print(num2 + " ");
				//做出判断
				boolean flag2 = true;
				if(num1>0)
					flag2 = num2/num1 > 3/4;
				if(num2==0)
					flag2 = true;
				System.out.println(flag1 + " " + flag2);
				if(flag2){
					pstmt = connection.prepareStatement("delete from tb_gps_1201 t where t.vehicle_num=?");				
					pstmt.setString(1, vehicleNum);
					pstmt.execute();
					System.out.println("删除车牌：" + vehicleNum);
				}
			}
			rst.close();
			pstmt.close();					
		}catch(SQLException se1){
		System.out.println(se1);
		}finally{
			try{
				connection.close();
			}catch(SQLException se2){
				System.out.println(se2); 
			}
		}		
	}
	
	//删除一些运营不正常的车辆轨迹数据（删掉运营有间断的出租车GPS轨迹数据）
	public void qingXiDataFirst(){
		
		String sql1 = "delete from tb_gps_1201 t where t.vehicle_num in (select t1.vehicle_num from " + 
				"(select distinct tb_gps_1201.vehicle_num from tb_gps_1201 PARTITION(TB_GPS_1201_4))t1 " + 
				"LEFT JOIN (select distinct tb_gps_1201.vehicle_num from tb_gps_1201 ";
		String sql11 = ")t2 on t1.vehicle_num=t2.vehicle_num where t2.vehicle_num is null)";
		
		String sql2 = "delete from tb_gps_1201 t where t.vehicle_num in (select t1.vehicle_num from " + 
				"(select distinct tb_gps_1201.vehicle_num from tb_gps_1201 ";
		String sql22 = ")t1 LEFT JOIN (select distinct tb_gps_1201.vehicle_num from tb_gps_1201 PARTITION(TB_GPS_1201_4))t2 " + 
				"on t1.vehicle_num=t2.vehicle_num where t2.vehicle_num is null)";
		
		LinkedList<String> list = new LinkedList<String>();
		list.add("PARTITION(TB_GPS_1201_5)");list.add("PARTITION(TB_GPS_1201_6)");
		list.add("PARTITION(TB_GPS_1201_7)");list.add("PARTITION(TB_GPS_1201_8)");list.add("PARTITION(TB_GPS_1201_9)");list.add("PARTITION(TB_GPS_1201_10)");
		list.add("PARTITION(TB_GPS_1201_11)");list.add("PARTITION(TB_GPS_1201_12)");list.add("PARTITION(TB_GPS_1201_13)");list.add("PARTITION(TB_GPS_1201_14)");
		list.add("PARTITION(TB_GPS_1201_15)");list.add("PARTITION(TB_GPS_1201_16)");list.add("PARTITION(TB_GPS_1201_17)");list.add("PARTITION(TB_GPS_1201_18)");
		list.add("PARTITION(TB_GPS_1201_19)");list.add("PARTITION(TB_GPS_1201_20)");list.add("PARTITION(TB_GPS_1201_21)");list.add("PARTITION(TB_GPS_1201_22)");
		list.add("PARTITION(TB_GPS_1201_23)");list.add("PARTITION(TB_GPS_1201_24)");list.add("PARTITION(TB_GPS_1201_25)");list.add("PARTITION(TB_GPS_1201_26)");
		list.add("PARTITION(TB_GPS_1201_27)");list.add("PARTITION(TB_GPS_1201_28)");list.add("PARTITION(TB_GPS_1201_29)");list.add("PARTITION(TB_GPS_1201_30)");
		list.add("PARTITION(TB_GPS_1201_31)");
		
		Connection connection = null;
		try {
			Class.forName(driver);
			connection=DriverManager.getConnection(url,user,password);
		} catch (ClassNotFoundException e1) {
			System.out.println(e1);
		} catch(SQLException e2){
			System.out.println(e2);
		}
		
		PreparedStatement pstmt = null;
		try{		
			for(int i=0;i<list.size();i++){
				System.out.println(list.get(i)+"**");
				pstmt = connection.prepareStatement(sql1+list.get(i)+sql11);				
				System.out.println(pstmt);
				pstmt.execute();
			}
			
			System.out.println("倒过来"); 
						
			for(int i=0;i<list.size();i++){
				System.out.println("**"+list.get(i));
				pstmt = connection.prepareStatement(sql2+list.get(i)+sql22);				
				pstmt.execute();
			}
			pstmt.close();
		}catch(SQLException se1){
			System.out.println(se1);
		}finally{
			try{
				connection.close();
			}catch(SQLException se2){
				System.out.println(se2); 
			}
		}
	}

}
