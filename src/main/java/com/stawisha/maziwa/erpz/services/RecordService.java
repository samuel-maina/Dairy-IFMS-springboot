package com.stawisha.maziwa.erpz.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stawisha.maziwa.erpz.exceptions.RecordExistsException;
import com.stawisha.maziwa.erpz.exceptions.RecordNotFoundException;
import com.stawisha.maziwa.erpz.model.Employee;
import com.stawisha.maziwa.erpz.model.Member;
import com.stawisha.maziwa.erpz.repositories.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.stawisha.maziwa.erpz.model.Record;
import com.stawisha.maziwa.erpz.model.Tenant;
import java.security.Principal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author samuel
 */
@Service
public class RecordService {

    @Autowired
    private RecordRepository recordRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private TenantService tenantService;

    @Autowired
    private MemberService memberService;

    DecimalFormat format = new DecimalFormat("0.00");

    @Transactional
    public Record save(Record record, String tenantId, String employeeId, String memberId) {
        //this.recordExists(memberId, tenantId);
        //update records
        Record tempRecord = new Record();
        Optional<Record> rec = recordRepository.findRecordByMemberIdAndTenantIdAndDate(tenantId, memberId, record.getDate());
        if (rec.isPresent()) {
            Record r = rec.get();
            r.setAmount(record.getAmount());
            r.setUpdated(new Date());
            tempRecord = recordRepository.save(r);
        } else {
            //insert first time for current date
            Tenant tenant = tenantService.findById(tenantId);
            Employee employee = employeeService.findEmployeeByPhone(employeeId);
            Member member = memberService.findmemberByIdAndTenantId(memberId, tenantId);
            member.setTenant(tenant);
            record.setMember(member);
            if (record.getDate() == null) {
                record.setDate(LocalDate.now());
            } else {

            }
            record.setInserted(new Date());
            Record r = recordRepository.save(record);

            List<Record> temp = employee.getRecords();
         
            temp.add(r);
            employee.setRecords(temp);
            employeeService.saveEmployeeAndRecordInformation(employee);
            tempRecord = r;

            //}
        }
        return tempRecord;
    }
    
    public void saveRestore(Record record, String tenantId, String employeeId, String memberId){}

    public void recordExists(String member, String tenant) {
        Optional<Record> record = recordRepository.findRecordByMemberIdAndTenantIdAndDate(tenant, member, LocalDate.now());
        if (record.isPresent()) {
            throw new RecordExistsException("");
        }
    }

    public List<Record> findRecordByMemberIdAndTenantIdAndMonth(String memberId, String tenantId, LocalDate date) {
        List<Record> temp = recordRepository.findRecordByMemberIdAndTenantIdAndMonth(memberId, tenantId, date.getYear(), date.getMonthValue());
        DailyRecordBuilder builder = new DailyRecordBuilder(temp, date);
        return builder.build();
    }

    public static class DailyRecordBuilder {

        List<Record> records;
        LocalDate dater;

        public DailyRecordBuilder(List<Record> records, LocalDate date) {
            this.records = records;
            this.dater = date;
        }

        public List<Record> build() {

            int days = dater.lengthOfMonth() + 1;
            int month = dater.getMonthValue();
            int year = dater.getYear();
            List<Record> all = new ArrayList<>();
            for (int day = 1; day < days; day++) {
                LocalDate date = LocalDate.of(year, month, day);
                Record record = new Record();
                record.setDate(date);

                record.setAmount(0);
                all.add(record);

            }
            for (Record r : records) {
                for (Record a : all) {
                    if (r.getDate().getDayOfMonth() == a.getDate().getDayOfMonth()) {

                        int index = all.indexOf(a);
                        all.add(index, r);
                        all.remove(a);
                        break;
                    }
                }
            }

            return all;
        }
    }

    public int findSumByTenantId(String tenantId) {
        return recordRepository.findSumByTenantId(tenantId);
    }

    public List<Map<Integer, String>> findAnnualSumationsByTenantId(String tenant) {
        return recordRepository.findAnnualSumationsByTenantId(tenant);
    }

    public List<PayLoad> findMonthlySumationsByTenantIdAndYear(String tenant, Integer year) {
        List<PayLoad> payLoad = new ArrayList();
        List<PayLoad> deliverly = new ArrayList();

        List<Map<Integer, String>> monthlySums = recordRepository.findMonthlySumationsByTenantIdANdYear(tenant, year);
        ObjectMapper objectMapper = new ObjectMapper();
        for (Map m : monthlySums) {
            PayLoad p = objectMapper.convertValue(m, PayLoad.class);
            payLoad.add(p);
        }
        for (int i = 0; i < 12; i++) {
            PayLoad load = new PayLoad();
            load.setAmount(0.0);
            load.setMonth(i + 1);
            deliverly.add(load);
        }
        for (PayLoad p : payLoad) {
            p.setAmount(Double.valueOf(format.format(p.getAmount())));

            deliverly.set(p.getMonth() - 1, p);

        }
        return deliverly;
    }

    public static class PayLoad {

        private int month;
        private Double amount;

        public PayLoad() {
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public Double getAmount() {
            return amount;
        }

        public void setAmount(Double amount) {
            this.amount = amount;
        }

        @Override
        public String toString() {
            return "PayLoad{" + "month=" + month + ", amount=" + amount + '}';
        }

    }

    public static class PayLoadForMonth {

        private int date;
        private double amount;

        public PayLoadForMonth() {
        }

        public int getDate() {
            return date;
        }

        public void setDate(int date) {
            this.date = date;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

    }

    public List<PayLoadForMonth> findDailySumationsByTenantIdAndMonth(String tenant, int year, String month) {
        List<PayLoadForMonth> payLoad = new ArrayList();
        List<PayLoadForMonth> deliverly = new ArrayList();

        List<Map<Integer, String>> dailySums = recordRepository.findDailySumationsByTenantIdAndMonth(tenant, year, Integer.parseInt(month));
        ObjectMapper objectMapper = new ObjectMapper();
        for (Map m : dailySums) {
            PayLoadForMonth p = objectMapper.convertValue(m, PayLoadForMonth.class);
            payLoad.add(p);
        }
        String dateString = year + "-" + month + "-01";

        LocalDate date = LocalDate.parse(dateString);

        int numberOfDays = date.lengthOfMonth();
        for (int i = 0; i < numberOfDays; i++) {
            PayLoadForMonth load = new PayLoadForMonth();
            load.setAmount(0);
            load.setDate(i + 1);
            deliverly.add(load);
        }
        for (PayLoadForMonth p : payLoad) {

            p.setAmount(Double.parseDouble(format.format(p.getAmount())));
            deliverly.set(p.getDate() - 1, p);

        }
        return deliverly;
    }
private final AtomicBoolean lock = new AtomicBoolean();
    @Transactional
    public void deleteRecordByMemberIdAndTenantIdAndDate(String memberId, String tenantId, LocalDate date) {
        Record r = recordRepository.findRecordByMemberIdAndTenantIdAndDate(tenantId, memberId, date).orElseThrow(() -> new RecordNotFoundException("Record does not exist"));
        long id = r.getId();
        for(int i=0;i<2;i++){
        if(lock.compareAndSet(false, true)){
            System.out.println("Deleteing child");
        employeeService.deleteEmployeeRecordById(id);}else if(lock.compareAndSet(true, false)){
            System.out.println("Deleteing parent");
        try{
        recordRepository.deleteRecordByMemberIdAndTenantIdAndDate(memberId, tenantId, date);
        }catch(Exception ex){
        System.out.println("Error here");
        }}}
    }

    public Double findSumByMemberIdAndTenantIdAndMonth(String memberId, String tenantId, int year, int month) {
        return Double.valueOf(new DecimalFormat("0.00").format(recordRepository.findSumByMemberIdAndTenantIdAndMonth(memberId, tenantId, year, month).orElseThrow(() -> new RecordNotFoundException(""))));
    }

    public Double findSumByTenantIdAndYear(String tenant, int year) {
        return Double.valueOf(new DecimalFormat("0.00").format(recordRepository.findSumByTenantIdAndYear(tenant, year)));
    }

    public Double findSumTodayByEmployeeAndTenantId(String tenant, Principal principal) {
        LocalDate date = LocalDate.now();
        Employee employee = employeeService.findEmployeeByPhone(principal.getName());
        //return (recordRepository.findSumTodayByEmployeeAndTenantId(tenant, employee.getId(), date));
        return Double.valueOf(new DecimalFormat("0.00").format(recordRepository.findSumTodayByEmployeeAndTenantId(tenant, employee.getId(), date)));
    }

    public int findCountTodayByEmployeeAndTenantId(String tenant, Principal principal) {
        LocalDate date = LocalDate.now();
        Employee employee = employeeService.findEmployeeByPhone(principal.getName());
        return recordRepository.findCountTodayByEmployeeAndTenantId(tenant, employee.getId(), date);
    }

    public List<Record> findRecordsByDateAndEmployeeAndTenant(String tenant, Principal principal) {
        LocalDate date = LocalDate.now();
        System.out.println(date.getMonthValue());
        Employee employee = employeeService.findEmployeeByPhone(principal.getName());
        List<Record> all = recordRepository.findRecordsByDateAndEmployeeAndTenant(tenant, employee.getId(), date);
        System.out.println(all);
        for (Record r : all) {
            Double sum = this.findSumByMemberIdAndTenantIdAndMonth(r.getMember().getId(), tenant, date.getYear(), date.getMonthValue());
            r.setSum(sum);
        }
        return all;
    }
}
