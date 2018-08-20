package com.pc.aws.search.utils;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

public class CSVToElasticSearch {

    public static void main(String args[]) {
        ClassLoader classLoader = CSVToElasticSearch.class.getClassLoader();
        String inputFileName = "plans_input.csv";
        File inputFile = new File(classLoader.getResource(inputFileName).getFile());
        parseAndUploadCSVData(inputFile);
    }

    public static void parseAndUploadCSVData(File inputFile) {
        CsvSchema csvSchema = CsvSchema.builder().setUseHeader(true).build();
        CsvMapper csvMapper = new CsvMapper();
        try {
            List<Object> readAll = csvMapper.readerFor(Map.class).with(csvSchema).readValues(inputFile).readAll();
            ObjectMapper mapper = new ObjectMapper();
            for(int i=0; i<readAll.size(); i++) {        
                String jsonStr = mapper.configure(SerializationFeature.INDENT_OUTPUT, true).writeValueAsString(readAll.get(i));
                System.out.println(jsonStr);
                uploadData(i,jsonStr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void uploadData(int id, String planStr) throws IOException {

        String host = "search-plan-search-k6o3clisxgbhcp6sbbo73wh7ce.us-east-1.es.amazonaws.com"; // For example, my-test-domain.us-east-1.es.amazonaws.com
        String index = "plans";
        String type = "listings";

        RestClient client = RestClient.builder(new HttpHost(host, 443, "https")).build();
        HttpEntity entity = new NStringEntity(planStr, ContentType.APPLICATION_JSON);
        Response response = client.performRequest("PUT", "/" + index + "/" + type + "/" + id,
            Collections.<String, String>emptyMap(), entity);

        if(response.getStatusLine().getStatusCode() != 204) {
            System.out.println("Id " + id + " failed with following error: " + response.getStatusLine().getReasonPhrase());
        }
    }
}
