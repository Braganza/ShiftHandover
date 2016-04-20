package com.peng.util;

import java.util.LinkedList;

import com.peng.bean.TbGpsBean;

public class ParkingLocation {

	public static LinkedList<TbGpsBean> extractParkingLocations(LinkedList<TbGpsBean> emptyDrivingList){
		int size = emptyDrivingList.size();
		int i = 0;
		while(i<size){
			if(emptyDrivingList.get(i).getSpeed()==0){
				int j = i + 1;
				while(j<size){
					if(emptyDrivingList.get(j).getSpeed()==0 && Computer.computerDistance(emptyDrivingList.get(j), emptyDrivingList.get(j-1))<5)
						j = j + 1;
				}
			}
		}
		
		return null;
	}
}
