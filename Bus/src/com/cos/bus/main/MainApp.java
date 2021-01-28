package com.cos.bus.main;
import com.cos.bus.api.ApiExplorer;
import com.cos.bus.api.ApiExplorer2;
import com.cos.bus.api.ApiExplorer3;

public class MainApp {

	public static void main(String[] args) throws Exception {
    	ApiExplorer ae = new ApiExplorer();
    	ae.busInfoParse();
    	
    	ApiExplorer2 ae2 = new ApiExplorer2();
    	ae2.busStationInfo();
    	
    	ApiExplorer3 ae3 = new ApiExplorer3();
    	ae3.busArrInfo();
    	
    	String serviceKey = "lm5Rn%2BrBLMSh%2F6ttTvkPQMpci6a7OgyXiIDbg%2BhszsGlhwdWfebxdTLLZmOixK4oCgarJHor47NPjb8PGSJfPQ%3D%3D";
    	serviceKey.replaceAll("%25", "%");
    	System.out.println(serviceKey);
    }

}
