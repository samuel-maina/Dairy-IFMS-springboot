package com.stawisha.maziwa.erpz.Controller;

import com.stawisha.maziwa.erpz.model.Deduction;
import com.stawisha.maziwa.erpz.model.Employee;
import com.stawisha.maziwa.erpz.services.DeductionService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RequestMapping("/api/v1/deductions/")
public class DeductionController {

    @Autowired
    private DeductionService deductionService;

    @PostMapping("/{tenant}")
    public ResponseEntity<?> save(@RequestBody Deduction deduction, @PathVariable String tenant) {
        deductionService.save(deduction, tenant);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{tenant}/{member}/{date}")
    public ResponseEntity<?> findDeductionByMonthAndMemberAndTenant(@PathVariable String date, @PathVariable String member, @PathVariable String tenant) {
        LocalDate date_ = LocalDate.parse(date);
        List<Deduction> deduction = deductionService.findDeductionByMonthAndMemberAndTenant(date_, member, tenant);
        return new ResponseEntity<>(deduction, HttpStatus.CREATED);
    }

    @GetMapping("/{tenant}/{member}/{date}/sum")
    public ResponseEntity<?> findDeductionSumByMonthAndMemberAndTenant(@PathVariable String date, @PathVariable String member, @PathVariable String tenant) {
        LocalDate date_ = LocalDate.parse(date);
        return new ResponseEntity<>(deductionService.findDeductionSumByMonthAndMemberAndTenant(date_, member, tenant), HttpStatus.OK);
    }
}
