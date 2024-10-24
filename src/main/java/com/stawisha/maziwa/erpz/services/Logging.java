package com.stawisha.maziwa.erpz.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.stawisha.maziwa.erpz.model.Record;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.text.DateFormatter;
import javax.transaction.Transactional;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author samuel
 */
@Service
public class Logging {

    @Autowired
    private TenantService tenantService;

    @Autowired

    private RecordService recordService;

    private final AtomicBoolean lock = new AtomicBoolean();

    private ObjectMapper r = new ObjectMapper();

    public ResponseData postLogger(String[] path, String[] methods, String[] status, String[] tenant) {
        List<LogDateHolder> item = containsAny(path, methods, status, tenant);
        //Collections.reverse(item.subList(0, item.size()-1));

        List<LogDateHolder> dateHolder = item;

        List<LocalDateTime> lDT = new ArrayList<>();
        List<GraphData> graph = new ArrayList();
        List<String> paths = new ArrayList();
        List<String> httpStatus = new ArrayList();

        //LocalDateTime date= LocalDateTime.parse(dateHolder.get(0).getDate().replace(" ", "T"));
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd HH:mm:ss,SSS")
                //.appendFraction(ChronoField.MILLI_OF_SECOND, 2, 3, true) // min 2 max 3
                .toFormatter();

        for (int i = 00; i < 60; i++) {
            final int t = i;
            LocalDateTime date = LocalDateTime.now();

            long count = item.stream().filter(k -> k.getDate().getDayOfMonth() == date.getDayOfMonth() && k.getDate().getMinute() == t && date.getHour() == k.getDate().getHour()).count();
            //item.stream().filter(k -> k.getDate() == (t) && k.getHour() == (LocalDateTime.now().getHour()) && k.getDayOfMonth() == LocalDate.now().getDayOfMonth()).count());
            String time = date.getHour() + ":" + t;

            graph.add(new GraphData(count, time));
        }
        for (LogDateHolder a : item) {

            if (!paths.contains(a.getPath())) {
                paths.add(a.getPath());
            }
            if (!httpStatus.contains(a.getStatus())) {
                httpStatus.add(a.getStatus());
            }
        }

        return new ResponseData(item.stream().sorted(new DateComparator()).toList(), graph, paths, httpStatus);
    }

    class GraphData {

        long count = 0;
        String time = "";

        public GraphData(long count, String time) {
            this.count = count;
            this.time = time;
        }

        public long getCount() {
            return count;
        }

        public void setCount(long count) {
            this.count = count;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

    }

    class ResponseData {

        List<LogDateHolder> table;
        List<GraphData> graph;
        List<String> paths;
        List<String> httpStatus;

        public List<String> getPaths() {
            return paths;
        }

        public List<String> getHttpStatus() {
            return httpStatus;
        }

        public void setHttpStatus(List<String> httpStatus) {
            this.httpStatus = httpStatus;
        }

        public void setPaths(List<String> paths) {
            this.paths = paths;
        }

        public ResponseData(List<LogDateHolder> table, List<GraphData> graph, List<String> paths, List<String> httpStatus) {
            this.table = table;
            this.graph = graph;
            this.paths = paths;
            this.httpStatus = httpStatus;
        }

        public List<LogDateHolder> getTable() {
            return table;
        }

        public void setTable(List<LogDateHolder> table) {
            this.table = table;
        }

        public List<GraphData> getGraph() {
            return graph;
        }

        public void setGraph(List<GraphData> graph) {
            this.graph = graph;
        }
    }

    public List<LogDateHolder> loadData() {
        r.registerModule(new JSR310Module());
        List<LogDateHolder> logObjects = new ArrayList();
        try {
            File file = new File("/home/samuel/loggers/debug.log");

            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                LogDateHolder logDateHolder;
                try {

                    logDateHolder = r.readValue(sc.nextLine(), LogDateHolder.class);
                    logDateHolder.setPathAndMethod();

                    //logDateHolder.getRequestbody().get(0).toString().replace("[", "");
                    String tenant = logDateHolder.getTenant();
                    if (tenant != null) {
                        logDateHolder.setTenant(tenantService.findByIdAlt(tenant));
                    }
                    logObjects.add(logDateHolder);
                } catch (JsonProcessingException ex) {
                    Logger.getLogger(Logging.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Logging.class.getName()).log(Level.SEVERE, null, ex);
        }

        return logObjects;

    }

    public List<LogDateHolder> getUpdated() {
        return loadData();//.stream().filter(k -> ("POST".equals(k.getMethod()) || "PUT".equals(k.getMethod()) || "DELETE".equals(k.getMethod())) && k.getStatus().equals("202")).toList();

    }

    public List<LogDateHolder> containsAny(String[] paths, String[] methods, String[] status, String[] tenant) {

        List<LogDateHolder> temp = loadData();
        List<LogDateHolder> temp2 = new ArrayList<>();
        try {
            if (null != paths && paths.length > 0) {
                for (String path : paths) {
                    //System.out.println(path);
                    temp2.addAll(temp.stream().filter(k -> k.getPath().equals(path)).toList());
                }
                temp.clear();
                temp.addAll(temp2);
                if (temp2.isEmpty()) {
                    System.out.println("HOOLAY");
                    //return new ArrayList<>();
                }
                temp2.clear();
            }

            if (null != methods && methods.length > 0) {
                for (String method : methods) {
                    temp2.addAll(temp.stream().filter(k -> k.getMethod().equals(method)).toList());
                }
                temp.clear();
                temp.addAll(temp2);
                temp2.clear();
            }

            if (null != status && status.length > 0) {
                for (String st : status) {
                    temp2.addAll(temp.stream().filter(k -> k.getStatus().equals(st)).toList());
                }
                temp.clear();
                temp.addAll(temp2);
                temp2.clear();
            }
            if (null != tenant && tenant.length > 0) {
                for (String t : tenant) {
                    temp2.addAll(temp.stream().filter(k -> null != k.getTenant() && k.getTenant().equals(t)).toList());
                }
                temp.clear();
                temp.addAll(temp2);
                temp2.clear();
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return temp;

    }

    public void restoreData(String[] paths, String[] methods, String[] status, String[] tenant) {
        List<LogDateHolder> temp = this.containsAny(paths, methods, status, tenant);

        for (LogDateHolder dateHolder : temp) {
            if (dateHolder.getMethod().equals("POST")) {
                lock.set(false);
            } else {
                lock.set(true);
            }
            if (lock.compareAndSet(false, true)) {
                System.out.println("Lock Aquired");
                String uri = dateHolder.getUri();
                String[] path = uri.split("/");
                String URItokens = path[5].split(" ")[0];
                String tenantID = path[4];
                JSONObject t = new JSONObject(dateHolder.requestbody.get(0).toString().replace("=", ":"));
                try {
                    r.registerModule(new JSR310Module());
                    Record record = r.readValue(t.toString(), Record.class);
                    System.out.println("Inserting");
                    recordService.save(record, tenantID, "0707588686", URItokens).getId();

                } catch (JsonProcessingException ex) {
                    Logger.getLogger(Logging.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (lock.compareAndSet(true, false)) {
                System.out.println("I am already locked");
                String uri = dateHolder.getUri();

                String[] path = uri.split(" ");
                String[] pathVariable = path[1].replace(" ", "").split("/");
                String tenantID = pathVariable[4];
                String member = pathVariable[5];
                String date = pathVariable[6];
                LocalDate f = LocalDate.parse(date);
                Record r = new Record();
                r.setDate(f);
                r.setUpdated(new Date());
                r.setAmount(0);
                recordService.save(r, tenantID, member, member);
                //recordService.deleteRecordByMemberIdAndTenantIdAndDate(member, tenantID, f);
            }

        }
    }

}
