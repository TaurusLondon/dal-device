package com.vf.uk.dal.utility.solr.entity;
public class MonthlyPrice
{
    private String gross;

    private String net;

    private String vat;

    public void setGross(String gross){
        this.gross = gross;
    }
    public String getGross(){
        return this.gross;
    }
    public void setNet(String net){
        this.net = net;
    }
    public String getNet(){
        return this.net;
    }
    public void setVat(String vat){
        this.vat = vat;
    }
    public String getVat(){
        return this.vat;
    }
}