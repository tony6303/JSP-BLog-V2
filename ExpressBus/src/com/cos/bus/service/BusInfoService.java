package com.cos.bus.service;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.cos.bus.config.ApiExplorer;
import com.cos.bus.domain.BusInfo;

public class BusInfoService {
	private ApiExplorer apiExplorer;
	
	public BusInfoService() {
		apiExplorer = new ApiExplorer();
	}
	public List<BusInfo> 버스조회(String dep, String arr, String depTime) throws IOException, ParserConfigurationException, SAXException{
		return apiExplorer.findAll(dep, arr,depTime); 
	}
}
