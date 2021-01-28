package com.cos.bus.api;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.TypeInfo;
import org.xml.sax.SAXException;

public class ApiExplorer { 
	private static String getTagValue(String tag, Element eElement) {
		//nlList = (NodeList)node
		//nlList.item(0) =  (NodeList)element
		//nlList.item(0).getNodeValue() = (Node)element
	    NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
	    Node nValue = (Node) nlList.item(0);
	    if(nValue == null) 
	        return null;
	    return nValue.getNodeValue();
	}
	
	private static String getTimeValue(String tag, Element eElement) {
		//nlList = (NodeList)node
		//nlList.item(0) =  (NodeList)element
		//nlList.item(0).getNodeValue() = (Node)element
	    NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
	    Node nValue = (Node) nlList.item(0);
	    if(nValue == null) 
	        return null;
	    return nValue.getNodeValue();
	}
	
	private static String timeMapping(StringBuffer time) {
		time.insert(4, "년");
		time.insert(7, "월");
		time.insert(10, "일");
		time.insert(13, "시");
		time.insert(16, "분");
		return time.toString();
	}
	
	public void busInfoParse() throws IOException, ParserConfigurationException, SAXException{
		StringBuilder urlBuilder = new StringBuilder("http://openapi.tago.go.kr/openapi/service/ExpBusInfoService/getStrtpntAlocFndExpbusInfo"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=lm5Rn%2BrBLMSh%2F6ttTvkPQMpci6a7OgyXiIDbg%2BhszsGlhwdWfebxdTLLZmOixK4oCgarJHor47NPjb8PGSJfPQ%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("5", "UTF-8")); /*한 페이지 결과 수*/
        //urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
        urlBuilder.append("&" + URLEncoder.encode("depTerminalId","UTF-8") + "=" + URLEncoder.encode("NAEK010", "UTF-8")); /*출발터미널ID*/
        urlBuilder.append("&" + URLEncoder.encode("arrTerminalId","UTF-8") + "=" + URLEncoder.encode("NAEK300", "UTF-8")); /*도착터미널ID*/
        urlBuilder.append("&" + URLEncoder.encode("depPlandTime","UTF-8") + "=" + URLEncoder.encode("20200101", "UTF-8")); /*출발일*/
        //urlBuilder.append("&" + URLEncoder.encode("busGradeId","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*버스등급ID*/
        URL url = new URL(urlBuilder.toString());
//        System.out.println(url);
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
        System.out.println("ApiExplorer busInfoParse() 실행됨");
//        System.out.println("Root element: " + doc.getDocumentElement().getNodeName()); // 최상위 태그 : response

        NodeList nList = doc.getElementsByTagName("item");
//        System.out.println("파싱할 리스트 수 : "+ nList.getLength()); // item의 개수
        
        for(int temp = 0; temp < nList.getLength(); temp++){ //nList로 찾았던 element들의 길이만큼 반복
        	Node nNode = nList.item(temp); // 노드 리스트에서 temp를 인덱스로 추출
        	if(nNode.getNodeType() == Node.ELEMENT_NODE){ // 노드의 타입이 element라면
        						
        		Element eElement = (Element) nNode; //node를 Element 타입으로 캐스팅
        		
//        		String arrPlandNmStr = getTagValue(eElement.getFirstChild().getNodeName(), eElement);
//        		String arrPlandNmStr2 = getTagValue(eElement.getNodeName(), eElement);
//        		String arrPlandTimeStr = getTagValue(eElement.getFirstChild().getNextSibling().getNodeName(), eElement);
//        		String chargeStr = getTagValue(eElement.getFirstChild().getNextSibling().getNextSibling().getNodeName(), eElement);
//        		String depPlandNmStr = getTagValue(eElement.getFirstChild().getNextSibling().getNextSibling().getNextSibling().getNodeName(), eElement);
//        		String depPlandTimeStr = getTagValue(eElement.getFirstChild().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNodeName(), eElement);
//        		String grandNmStr = getTagValue(eElement.getFirstChild().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNodeName(), eElement);
        		
        		String arrPlaceNmStr = getTagValue("arrPlaceNm", eElement);
        		String chargeStr = getTagValue("charge", eElement);
        		String depPlaceNmStr = getTagValue("depPlaceNm", eElement);
        		String gradeNmStr = getTagValue("gradeNm", eElement);
        		
        		
//        		String routeIdStr = getTagValue(eElement.getFirstChild().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNodeName(), eElement);
//        		System.out.println(eElement.getFirstChild().getNextSibling().getNodeName());
//        		System.out.println(eElement.getFirstChild().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNodeName());
//        		System.out.println(getTagValue(depPlandTimeStr.toString(), eElement));
        		
        		
        		
        		StringBuffer depPlandTime = new StringBuffer(getTagValue(eElement.getFirstChild().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNodeName(), eElement));
        		StringBuffer arrPlandTime = new StringBuffer(getTagValue(eElement.getFirstChild().getNextSibling().getNodeName(), eElement));
        		
        		
        		
        		
        		System.out.println("######################");
//        		System.out.println(eElement.getTextContent());
        		System.out.println("출발터미널  : " + depPlaceNmStr); //서울경부
        		System.out.println("도착터미널  : " + arrPlaceNmStr); // 대전복함
        		System.out.println("요금 : " + chargeStr +"원"); // 요금
        		System.out.println("출발시간  : " + timeMapping(depPlandTime));
        		System.out.println("도착시간  : " + timeMapping(arrPlandTime));
        		System.out.println("버스등급  : " + gradeNmStr);
        	}	// for end
        }	// if end
	}
	
    
}













