package com.cos.bus.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ApiExplorer3 {
	
	private static String getTagValue(String tag, Element eElement) {
	    NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
	    Node nValue = (Node) nlList.item(0);
	    if(nValue == null) 
	        return null;
	    return nValue.getNodeValue();
	}
	
	public void busArrInfo() throws IOException, Exception {
		StringBuilder urlBuilder = new StringBuilder("http://openapi.tago.go.kr/openapi/service/ExpBusArrInfoService/getArrTmnFromDepTmn"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=lm5Rn%2BrBLMSh%2F6ttTvkPQMpci6a7OgyXiIDbg%2BhszsGlhwdWfebxdTLLZmOixK4oCgarJHor47NPjb8PGSJfPQ%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("depTmnCd","UTF-8") + "=" + URLEncoder.encode("010", "UTF-8")); /*출발터미널코드*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("5", "UTF-8")); /*행의 수*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
//        System.out.println(sb.toString());
    
        DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
        Document doc = dBuilder.parse(urlBuilder.toString());

        // root tag 
        doc.getDocumentElement().normalize();
        System.out.println("ApiExplorer3 busArrInfo() 실행됨");
//        System.out.println("Root element: " + doc.getDocumentElement().getNodeName()); // 최상위 태그 : response

        NodeList nList = doc.getElementsByTagName("item"); // xml 형식에 맞춰서 작성
//        System.out.println("파싱할 리스트 수 : "+ nList.getLength()); // item의 개수
        
        HashMap<String,String> map = new HashMap();
        
        for(int temp = 0; temp < nList.getLength(); temp++){ //nList로 찾았던 element들의 길이만큼 반복
        	Node nNode = nList.item(temp); // 노드 리스트에서 temp를 인덱스로 추출
        	if(nNode.getNodeType() == Node.ELEMENT_NODE){ // 노드의 타입이 element라면
        						
        		Element eElement = (Element) nNode; //node를 Element 타입으로 캐스팅
        		String arrTmnCdStr = getTagValue("arrTmnCd", eElement);
        		String arrTmnNmStr = getTagValue("arrTmnNm", eElement);
        		
        		System.out.println("######################");
//        		System.out.println(eElement.getTextContent());
//        		System.out.println("첫번째태그 : " + eElement.getFirstChild().getNodeName());
//        		System.out.println("두번째태그 : " + eElement.getFirstChild().getNextSibling().getNodeName());
//        		getNextSibling() : 다음 형제관계 노드에 접근하는 함수
        		System.out.println("터미널코드  : " + arrTmnCdStr);
        		System.out.println("이름  : " + arrTmnNmStr);
        		
        		map.put(arrTmnCdStr , arrTmnNmStr);
        	}	// for end
        }	// if end
//        System.out.println(map);
//        map.keySet().forEach((key)->System.out.println("terminalId : " + key));
	}
}
