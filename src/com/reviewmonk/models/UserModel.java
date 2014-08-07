package com.reviewmonk.models;

import org.json.JSONException;
import org.json.JSONObject;

public class UserModel {
	private String name;
	private String email;
	private String desc;
	private String work_place;
	private String native_location;
	private String language;
	private Double gps_latitude;
	private Double gps_longitude;

	public void set(String name, String email, String desc, String work_place,
			String native_location, String language, Double gps_latitude,
			Double gps_longitude) {
		
		 this.name=name;
		 this.email=email;
		 this.desc=desc;
		 this.work_place=work_place;
		 this.native_location=native_location;
		 this.language=language;
		 this.gps_latitude=gps_latitude;
		 this.gps_longitude=gps_longitude;
		

	}
	
	
	public String getEmail(){
		return email;
	}
	
	public JSONObject to_json(){
		
		JSONObject userJson=new JSONObject();
		try {
			userJson.put("name",name);
			userJson.put("email",email);
			userJson.put("description",desc);
			userJson.put("work_place",work_place);
			userJson.put("native_location",native_location);
			userJson.put("language", language);
			userJson.put("gps_latitude",gps_latitude);
			userJson.put("gps_longitude",gps_longitude);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return userJson;
	}
	
	
	public void from_json(JSONObject userJson){
		
		try {
			set(userJson.getString("name"),userJson.getString("email"),userJson.getString("description"),
					userJson.getString("work_place"),userJson.getString("native_location"),userJson.getString("language"),
					userJson.getDouble("gps_latitude"),userJson.getDouble("longitude"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
