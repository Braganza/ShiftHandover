package com.peng.util;

import com.peng.bean.TbGpsBean;

public class Computer {
	
	/**
	 * 计算两点间的距离
	 * 
	 * @param Longitude1
	 * @param Latitude1
	 * @param Longitude2
	 * @param Latitude2
	 * @return 距离 double
	 */
	public static double computerDistance(TbGpsBean tbGps1, TbGpsBean tbGps2) {
		double Longitude1 = tbGps1.getLongi();
		double Latitude1 = tbGps1.getLati();
		double Longitude2 = tbGps2.getLongi();
		double Latitude2 = tbGps2.getLati();
		double radLat1 = (Latitude1 * Math.PI / 180.0);
		double radLat2 = (Latitude2 * Math.PI / 180.0);
		double a = radLat1 - radLat2;
		double b = (Longitude1 - Longitude2) * Math.PI / 180.0;
		double dis = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		dis = dis * 6378137.0;
		return dis;
	}
}
