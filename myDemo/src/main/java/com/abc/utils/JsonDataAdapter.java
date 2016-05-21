package com.abc.utils;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JsonDataAdapter {
	private final ObjectMapper o ;
	
	public JsonDataAdapter(){
		o = new ObjectMapper();
	}	
	
	public <T> T read(String message, Class<T> clazz){
		try {
			return o.readValue(message, clazz);
		} catch (Exception e) {
			throw new RuntimeException("unable to read json object",e);
		}
	}	

}
