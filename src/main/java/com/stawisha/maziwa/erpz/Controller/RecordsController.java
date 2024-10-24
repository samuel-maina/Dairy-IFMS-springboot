package com.stawisha.maziwa.erpz.Controller;

import com.stawisha.maziwa.erpz.model.Record;
import com.stawisha.maziwa.erpz.services.RecordService;
import java.security.Principal;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author samuel
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/records/")
public class RecordsController {

    @Autowired
    private RecordService recordService;

    @PostMapping("/{tenantId}/{memberId}")
    public ResponseEntity<?> save(@RequestBody Record record, @PathVariable String tenantId, @PathVariable String memberId, Principal principal) {

        return new ResponseEntity<>(recordService.save(record, tenantId, principal.getName(), memberId), HttpStatus.ACCEPTED);
    }

    @GetMapping("/{tenantId}/{memberId}/{date}")
    public ResponseEntity<?> findRecordByMemberIdAndTenantIdAndMonth(@PathVariable String date, @PathVariable String tenantId, @PathVariable String memberId) {
        LocalDate date_ = LocalDate.parse(date);
        return new ResponseEntity<>(recordService.findRecordByMemberIdAndTenantIdAndMonth(memberId, tenantId, date_), HttpStatus.OK);
    }

    @GetMapping("/{tenant}")
    public ResponseEntity<?> findSumByTenantId(@PathVariable String tenant) {
        return new ResponseEntity<>(recordService.findSumByTenantId(tenant), HttpStatus.OK);
    }

    @GetMapping("/tenant/employee/today/{tenant}")
    public ResponseEntity<?> findSumTodayByEmployeeAndTenantId(@PathVariable String tenant, Principal principal) {
        return new ResponseEntity<>(recordService.findSumTodayByEmployeeAndTenantId(tenant, principal), HttpStatus.OK);
    }

    @GetMapping("/tenant/employee/today/count/{tenant}")
    public ResponseEntity<?> findCountTodayByEmployeeAndTenantId(@PathVariable String tenant, Principal principal) {
        return new ResponseEntity<>(recordService.findCountTodayByEmployeeAndTenantId(tenant, principal), HttpStatus.OK);
    }

    @GetMapping("/{tenant}/annual-sums/")
    public ResponseEntity<?> findAnnualSumationsByTenantId(@PathVariable String tenant) {
        return new ResponseEntity<>(recordService.findAnnualSumationsByTenantId(tenant), HttpStatus.OK);
    }

    @GetMapping("/{tenant}/{yearDuration}/tenant")
    public ResponseEntity<?> findMonthlySumationsByTenantIdAndYear(@PathVariable String tenant, @PathVariable Integer yearDuration) {
        return new ResponseEntity<>(recordService.findMonthlySumationsByTenantIdAndYear(tenant, yearDuration), HttpStatus.OK);
    }

    @GetMapping("/{tenant}/{yearDuration}/{monthDuration}/daily-sums")
    public ResponseEntity<?> findDailySumationsByTenantIdAndMonth(@PathVariable String tenant, @PathVariable Integer yearDuration, @PathVariable String monthDuration) {
        return new ResponseEntity<>(recordService.findDailySumationsByTenantIdAndMonth(tenant, yearDuration, monthDuration), HttpStatus.OK);
    }

    @DeleteMapping("/{tenantId}/{memberId}/{date}")
    public ResponseEntity<?> deleteRecordByMemberIdAndTenantIdAndDate(@PathVariable String memberId, @PathVariable String tenantId, @PathVariable String date) {
        recordService.deleteRecordByMemberIdAndTenantIdAndDate(memberId, tenantId, LocalDate.parse(date));
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/{tenantId}/{memberId}/{date}/individual")
    public ResponseEntity<?> findSumByMemberIdAndTenantIdAndMonth(@PathVariable String memberId, @PathVariable String tenantId, @PathVariable String date) {
        LocalDate tempDate = LocalDate.parse(date);

        return new ResponseEntity<>(recordService.findSumByMemberIdAndTenantIdAndMonth(memberId, tenantId, tempDate.getYear(), tempDate.getMonthValue()), HttpStatus.OK);
    }

    @GetMapping("/{tenant}/{year}")
    public ResponseEntity<?> findSumByTenantIdAndYear(@PathVariable String tenant, @PathVariable int year) {
        return new ResponseEntity<>(recordService.findSumByTenantIdAndYear(tenant, year), HttpStatus.OK);
    }
    
     @GetMapping("/tenant/employee/today/records/{tenant}")
    public ResponseEntity<?> findRecordsbyEmployeeAndTenantAndDate(@PathVariable String tenant, Principal principal) {
        return new ResponseEntity<>(recordService.findRecordsByDateAndEmployeeAndTenant(tenant, principal), HttpStatus.OK);
    }

}
