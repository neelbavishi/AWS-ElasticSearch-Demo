# AWS-ElasticSearch-Demo

A micro service that invokes AWS elastic search and makes it available using API gateway and Lambda.

To run this demo project, you need to go through following steps:

1.) Create Elastic Search Domain in your AWS account and make it available.

2.) Create Index with property date_detection set to false so that we get text datatype for date fields. The reason to do this is that with date datatype, there cannot be null values while we have a use case for the same. The way to do this is to run following command in Kibana Developer console.

PUT plans
{
"mappings": {
      "listings": {
         "date_detection": false
      }
   }
}

3.) Convert CSV data set present in /resources to JSON which can then be uploaded to AWS Elastic Search cluster using CURL or native REST API for elastic search. In this case I have a JAVA class com.pc.aws.search.CSVToElasticSearch which reads csv record, converts it to json and then uploads it to elastic search cluster. The reason for me to use native api instead of CURL is that AWS ES has different domain tiers with specific data upload limitations. Micro and Small are the free instances. I have used a small instance for this project.

4.) The upload data can be confirmed with a feature prvided by AWS ES, Kibana. Since ES is for data analytics, Kibana is wonderful tool to visualize and explore data. For more information, refer here: https://aws.amazon.com/elasticsearch-service/kibana/ 

5.) Next, you have to use a service - API Gateway. I went ahead and created a resurce and GET function. The endpoint in this case would be your ES domain and can be accessed on the AWS ES dashboard. For this project, there were three query parameters - Plan Name, Sponsor DFE Name and Sponsor State. These can be created while creating the GET method.

6.) Once, you've created the API, you can decide how you would like to execute the API - say through a Lambda function, HTTP, Mapping or another AWS service. 

7.) For this particular instance, I am using a lambda handler function written in Java. I have a maven project here which can be compiled and uploaded through AWS Lambda console for your function. This function gets triggered whenever the API is called. You can find this function in package com.pc.aws.search.resource and its called PlanSearch.java 

TODO: API Gateway service should have some kind of Authentication Baisc Auth/OAUTH for accessing our APIs. 

Endpoints available for search via JAVA are:

Plan Name: If you search for TIFFANY AND COMPANY EMPLOYEE ASSISTANCE PROGRAM, the encoded link will be : https://ffvlub9y6d.execute-api.us-east-1.amazonaws.com/testprod/plans?planName=WINGSPAN%20INVESTMENT%20MANAGEMENT%20401(K)%20PLAN
e.g. https://ffvlub9y6d.execute-api.us-east-1.amazonaws.com/testprod/plans?planName= Your Plan Name

Sponsor Name: If you search for NORTHERN NEW JERSEY TEAMSTERS BENEFIT PLAN DEFINED CONTRIBUTION FUND, the link generated: https://ffvlub9y6d.execute-api.us-east-1.amazonaws.com/testprod/plans?
sponsorDefName=WINGSPAN%20INVESTMENT%20MANAGEMENT,%20LP
e.g. https://ffvlub9y6d.execute-api.us-east-1.amazonaws.com/testprod/plans?
sponsorDefName= Your Sponsor DFE Name

Sponsor State: Similary all the plans in Wisconsin would be as follows - https://ffvlub9y6d.execute-api.us-east-1.amazonaws.com/testprod/plans?sponsorState=NY
e.g. https://ffvlub9y6d.execute-api.us-east-1.amazonaws.com/testprod/plans?sponsorState= Your Sponsor State
