package com.stawisha.maziwa.erpz.services;
import com.stawisha.maziwa.erpz.model.Record;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PostLogParser {

    String date;

    String path;

    String method;

    String uri;

    Record requestbody;

    String tenant;

    String time;

    String status;

    public PostLogParser() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPathAndMethod() {
        String temp = uri.replace(" ", "").replace("HTTP/1.1", "");
        String[] temp1 = temp.split("/");
        try {
            method = temp1[0];
            path = temp1[3];
            tenant = temp1[4];
        } catch (ArrayIndexOutOfBoundsException ex) {
        }
        //System.out.println(temp1[4]);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Record getRequestbody() {
        return requestbody;
    }

    public void setRequestbody(Record requestbody) {
        this.requestbody = requestbody;
    }

  

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "LogDateHolder{" + "date=" + date + ", path=" + path + ", method=" + method + ", uri=" + uri + ", requestBody=" + requestbody + ", tenant=" + tenant + '}';
    }

}

