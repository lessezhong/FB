package com.example.entity;

public class City {
	public String name;
	public String city_num;
	public String now="";
	public String today="";
	public String future="";

	public void setName(String name) {
		this.name = name;
	}

	public void setCity_num(String city_num) {
		this.city_num = city_num;
	}

	public String getName() {
		return name;
	}

	public String getCity_num() {
		return city_num;
	}

	public String getNow() {
		return now;
	}

	public void setNow(String now) {
		this.now = now;
	}

	public String getToday() {
		return today;
	}

	public void setToday(String today) {
		this.today = today;
	}

	public String getFuture() {
		return future;
	}

	public void setFuture(String future) {
		this.future = future;
	}
	

}
