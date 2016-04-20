package com.peng.util;

import java.util.ArrayList;
import java.util.LinkedList;

import com.peng.bean.TbGpsBean;
import com.peng.dao.TbGpsDao;

public class ExtractOD {
	
	public static void main(String args[]){
		LinkedList<String> Partitionlist = new LinkedList<String>();
		Partitionlist.add("PARTITION(TB_GPS_1201_4)");Partitionlist.add("PARTITION(TB_GPS_1201_5)");Partitionlist.add("PARTITION(TB_GPS_1201_6)");
		Partitionlist.add("PARTITION(TB_GPS_1201_7)");Partitionlist.add("PARTITION(TB_GPS_1201_8)");Partitionlist.add("PARTITION(TB_GPS_1201_9)");Partitionlist.add("PARTITION(TB_GPS_1201_10)");
		Partitionlist.add("PARTITION(TB_GPS_1201_11)");Partitionlist.add("PARTITION(TB_GPS_1201_12)");Partitionlist.add("PARTITION(TB_GPS_1201_13)");Partitionlist.add("PARTITION(TB_GPS_1201_14)");
		Partitionlist.add("PARTITION(TB_GPS_1201_15)");Partitionlist.add("PARTITION(TB_GPS_1201_16)");Partitionlist.add("PARTITION(TB_GPS_1201_17)");Partitionlist.add("PARTITION(TB_GPS_1201_18)");
		Partitionlist.add("PARTITION(TB_GPS_1201_19)");Partitionlist.add("PARTITION(TB_GPS_1201_20)");Partitionlist.add("PARTITION(TB_GPS_1201_21)");Partitionlist.add("PARTITION(TB_GPS_1201_22)");
		Partitionlist.add("PARTITION(TB_GPS_1201_23)");Partitionlist.add("PARTITION(TB_GPS_1201_24)");Partitionlist.add("PARTITION(TB_GPS_1201_25)");Partitionlist.add("PARTITION(TB_GPS_1201_26)");
		Partitionlist.add("PARTITION(TB_GPS_1201_27)");Partitionlist.add("PARTITION(TB_GPS_1201_28)");Partitionlist.add("PARTITION(TB_GPS_1201_29)");Partitionlist.add("PARTITION(TB_GPS_1201_30)");
		Partitionlist.add("PARTITION(TB_GPS_1201_31)");
		
		TbGpsDao tbGpsDao = new TbGpsDao();
		for(int i=0;i<Partitionlist.size();i++){
			System.out.println("*"+ Partitionlist.get(i) + "*");
			ArrayList<String> totalVehicleNum = tbGpsDao.selectTotalVehicleNum();
			
			for(int k=0;k<700;k++){
				ArrayList<TbGpsBean> list = tbGpsDao.selectByVehicleNumAndSpeedTime(Partitionlist.get(i), totalVehicleNum.get(k), "201201040000", "201201040559");
				System.out.println(list.size());
				ArrayList<TbGpsBean> total = ExtractOD.extractPickUpPoint(list);
				System.out.println(total.size());
				for(int j=0;j<total.size();j++)
					System.out.println(total.get(j).toString());
			}
		}		
	}
	
	
	//提取乘客上车热点
	public static ArrayList<TbGpsBean> extractPickUpPoint(ArrayList<TbGpsBean> list){
		ArrayList<TbGpsBean> POIList = new ArrayList<TbGpsBean>();
		//空车状态起始
		if(list.get(0).getState()==0){
			for(int i=0;i<list.size();){
				while(i<list.size() && list.get(i).getState()==0)
					i++;
				if(i<list.size() && list.get(i).getState()==1)
					POIList.add(clone(list.get(i)));
				while(i<list.size() && list.get(i).getState()==1)
					i++;
			}
		}
		//载客状态起始
		else {
			int i = 0;
			while(i<list.size() && list.get(i).getState()==1)
				i++;
			for( ;i<list.size(); ){
				while(i<list.size() && list.get(i).getState()==0)
					i++;
				if(i<list.size() && list.get(i).getState()==1)
					POIList.add(clone(list.get(i)));
				while(i<list.size() && list.get(i).getState()==1)
					i++;
			}
		}
		return POIList;		
	}

	//提取乘客下车热点
	public static ArrayList<TbGpsBean> extractDropOffPoint(ArrayList<TbGpsBean> list){
		ArrayList<TbGpsBean> POIList = new ArrayList<TbGpsBean>();
		//空车状态起始
		if(list.get(0).getState()==0){
			int i = 0;
			while(i<list.size() && list.get(i).getState()==0)
				i++;
			for( ;i<list.size();){
				while(i<list.size() && list.get(i).getState()==1)
					i++;
				if(i<list.size() && list.get(i).getState()==0)
					POIList.add(clone(list.get(i)));
				while(i<list.size() && list.get(i).getState()==0)
					i++;
			}
		}
		//载客状态起始
		else {
			for(int i=0;i<list.size(); ){
				while(i<list.size() && list.get(i).getState()==1)
					i++;
				if(i<list.size() && list.get(i).getState()==0)
					POIList.add(clone(list.get(i)));
				while(i<list.size() && list.get(i).getState()==0)
					i++;
			}
		}
		return POIList;		
	}
	
	//克隆函数
	public static TbGpsBean clone(TbGpsBean tbGps){
		TbGpsBean tbGpsCopy = new TbGpsBean();
		
		tbGpsCopy.setMessageId(tbGps.getMessageId());
		tbGpsCopy.setVehicleId(tbGps.getVehicleId());
		tbGpsCopy.setVehicleNum(tbGps.getVehicleNum());
		tbGpsCopy.setLongi(tbGps.getLongi());
		tbGpsCopy.setLati(tbGps.getLati());
		tbGpsCopy.setPx(tbGps.getPx());
		tbGpsCopy.setPy(tbGps.getPy());
		tbGpsCopy.setSpeed(tbGps.getSpeed());
		tbGpsCopy.setDirection(tbGps.getDirection());
		tbGpsCopy.setState(tbGps.getState());
		tbGpsCopy.setCarState(tbGps.getCarState());
		tbGpsCopy.setSpeedTime(tbGps.getSpeedTime());
		tbGpsCopy.setDbTime(tbGps.getDbTime());
		tbGpsCopy.setNote(tbGps.getNote());
		
		return tbGpsCopy;
	}
	
}
