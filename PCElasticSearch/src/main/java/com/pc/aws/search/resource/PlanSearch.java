package com.pc.aws.search.resource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;                                    
import java.net.URLEncoder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.pc.aws.search.entity.SearchQuery; 

public class PlanSearch implements RequestHandler<SearchQuery, JSONObject> {    
    @Override
    public JSONObject handleRequest(SearchQuery searchQuery, Context context) {
        String url="https://search-plan-search-k6o3clisxgbhcp6sbbo73wh7ce.us-east-1.es.amazonaws.com/plans/_search?q=";
        if(searchQuery.getPlanName()!=null){
            try {
                url+="PLAN_NAME:\""+URLEncoder.encode(searchQuery.getPlanName(), "UTF-8")+"\"";
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }                       
        }
        
        if(searchQuery.getSponsorName()!=null){
            try {
                url+="SPONSOR_DFE_NAME:\""+URLEncoder.encode(searchQuery.getSponsorName(), "UTF-8")+"\"";
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    
                }
        if(searchQuery.getSponsorState()!=null){
            try {
                url+="SPONS_DFE_MAIL_US_STATE:\""+URLEncoder.encode(searchQuery.getSponsorState(), "UTF-8")+"\"";
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        StringBuffer response = new StringBuffer(); 
        JSONObject json = null;
        try {
            URL obj= new URL(url);
            System.out.println(url);
            HttpURLConnection con= (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            while((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } 
        
        JSONParser parser = new JSONParser();
        
        try {
            json = (JSONObject) parser.parse(response.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return json;
        }
}
