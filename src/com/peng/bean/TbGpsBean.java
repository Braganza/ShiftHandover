package com.peng.bean;

public class TbGpsBean implements java.io.Serializable {
	
	// Fields
	private static final long serialVersionUID = 1L;	
	private String messageId;
	private String vehicleId;
	private String vehicleNum;
	private Double longi;
	private Double lati;
	private Double px;
	private Double py;
	private Double speed;
	private Double direction;
	private Integer state;
	private Integer carState;
	private String speedTime;
	private String dbTime;
	private String note;
	
	public TbGpsBean(){
		
	}	
	public TbGpsBean(String messageId, String vehicleId, String vehicleNum,
			Double longi, Double lati, Double px, Double py, Double speed,
			Double direction, Integer state, Integer carState,
			String speedTime, String dbTime, String note) {
		super();
		this.messageId = messageId;
		this.vehicleId = vehicleId;
		this.vehicleNum = vehicleNum;
		this.longi = longi;
		this.lati = lati;
		this.px = px;
		this.py = py;
		this.speed = speed;
		this.direction = direction;
		this.state = state;
		this.carState = carState;
		this.speedTime = speedTime;
		this.dbTime = dbTime;
		this.note = note;
	}
	public TbGpsBean(String vehicleId, String vehicleNum, Double longi,
			Double lati, Double px, Double py, Double speed, Double direction,
			Integer state, Integer carState, String speedTime, String dbTime,
			String note) {
		super();
		this.vehicleId = vehicleId;
		this.vehicleNum = vehicleNum;
		this.longi = longi;
		this.lati = lati;
		this.px = px;
		this.py = py;
		this.speed = speed;
		this.direction = direction;
		this.state = state;
		this.carState = carState;
		this.speedTime = speedTime;
		this.dbTime = dbTime;
		this.note = note;
	}
	//messageId
	public void setMessageId(String messageId){
		this.messageId = messageId;
	}
	public String getMessageId(){
		return messageId;
	}
	//vehicleId
	public void setVehicleId(String vehicleId){
		this.vehicleId = vehicleId;
	}
	public String getVehicleId(){
		return vehicleId;
	}
	//vehicleNum
	public void setVehicleNum(String vehicleNum){
		this.vehicleNum = vehicleNum;
	}
	public String getVehicleNum(){
		return vehicleNum;
	}
	//longi
	public void setLongi(Double longi){
		this.longi = longi;
	}
	public Double getLongi(){
		return longi;
	}
	//lati
	public void setLati(Double lati){
		this.lati = lati;
	}
	public Double getLati(){
		return lati;
	}
	//px
	public void setPx(Double px){
		this.px = px;
	}
	public Double getPx(){
		return px;
	}
	//py
	public void setPy(Double py){
		this.py = py;
	}
	public Double getPy(){
		return py;
	}
	//speed
	public void setSpeed(Double speed){
		this.speed = speed;
	}
	public Double getSpeed(){
		return speed;
	}
	//direction
	public void setDirection(Double direction){
		this.direction = direction;
	}
	public Double getDirection(){
		return direction;
	}
	//state
	public void setState(Integer state){
		this.state = state;
	}
	public Integer getState(){
		return state;
	}
	//carState
	public void setCarState(Integer carState){
		this.carState = carState;
	}
	public Integer getCarState(){
		return carState;
	}	
	//speedTime
	public void setSpeedTime(String speedTime){
		this.speedTime = speedTime;
	}
	public String getSpeedTime(){
		return speedTime;
	}
	//dbTime
	public void setDbTime(String dbTime){
		this.dbTime = dbTime;
	}
	public String getDbTime(){
		return dbTime;
	}	
	//note
	public void setNote(String note){
		this.note = note;
	}
	public String getNote(){
		return note;
	}
		
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lati == null) ? 0 : lati.hashCode());
		result = prime * result + ((longi == null) ? 0 : longi.hashCode());
		result = prime * result
				+ ((vehicleId == null) ? 0 : vehicleId.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TbGpsBean other = (TbGpsBean) obj;
		if (lati == null) {
			if (other.lati != null)
				return false;
		} else if (!lati.equals(other.lati))
			return false;
		if (longi == null) {
			if (other.longi != null)
				return false;
		} else if (!longi.equals(other.longi))
			return false;
		if (vehicleId == null) {
			if (other.vehicleId != null)
				return false;
		} else if (!vehicleId.equals(other.vehicleId))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		/*return "TbGps [messageId=" + messageId + ", vehicleNum=" + vehicleNum
				+ ", longi=" + longi + ", lati=" + lati + ", state=" + state
				+ ", speedTime=" + speedTime + "]";*/
		return messageId + "	" + vehicleNum + "	" + longi + "	" + lati + "	" + speedTime;
	}
	
}
