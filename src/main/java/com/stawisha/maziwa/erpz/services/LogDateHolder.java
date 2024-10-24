package com.stawisha.maziwa.erpz.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LogDateHolder {

    LocalDateTime date;

    String path;

    String method;

    String uri;

    List<?> requestbody;

    String tenant;

    String time;

    String status;

    public LogDateHolder() {
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
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
            //System.out.println(temp1[3]);
        }

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

    public List<?> getRequestbody() {
        return requestbody;
    }

    public void setRequestbody(List<?> requestbody) {
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
