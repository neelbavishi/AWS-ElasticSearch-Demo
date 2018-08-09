/* Copyright (c) 2017, Oracle and/or its affiliates. All rights reserved.*/
package com.pc.aws.search.entity;

public class SearchQuery {
    private String planName;
    private String sponsorName;
    private String sponsorState;
    
    public String getPlanName() {
        return planName;
    }
    public void setPlanName(String planName) {
        this.planName = planName;
    }
    public String getSponsorName() {
        return sponsorName;
    }
    public void setSponsorName(String sponsorName) {
        this.sponsorName = sponsorName;
    }
    public String getSponsorState() {
        return sponsorState;
    }
    public void setSponsorState(String sponsorState) {
        this.sponsorState = sponsorState;
    }
    
    public SearchQuery() {};
    
    public SearchQuery(String planName, String sponsorName, String sponsorState) {
        this.planName = planName;
        this.sponsorName = sponsorName;
        this.sponsorState = sponsorState;
    }
}
