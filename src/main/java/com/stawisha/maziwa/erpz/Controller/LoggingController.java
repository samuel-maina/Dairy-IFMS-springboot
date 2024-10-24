
package com.stawisha.maziwa.erpz.Controller;

import com.stawisha.maziwa.erpz.services.Logging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author samuel
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/logging")
public class LoggingController {
    @Autowired
    private Logging logging;
    @GetMapping("/post")
   public ResponseEntity<?>getPosts(@RequestParam(value="path",required=false) String[]path,@RequestParam(value="method",required=false) String[]method,@RequestParam(value="status",required=false) String[]status,@RequestParam(value="tenant",required=false) String[]tenant){
       //System.out.println(path.length);
       
   return new ResponseEntity<>(logging.postLogger(path,method,status,tenant),HttpStatus.OK);
   } 
}
