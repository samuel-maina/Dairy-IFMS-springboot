package com.stawisha.maziwa.erpz.Controller;

/**
 * AuthController class handles system-wide user registration and mobile only
 * user authentication for API
 *
 * @author Samuel Maina
 *
 * 1/22/2022
 *
 * @version 1.0
 *
 * AuthController.java
 */

import com.stawisha.maziwa.erpz.model.Employee;
import com.stawisha.maziwa.erpz.model.LoginPayLoad;
import com.stawisha.maziwa.erpz.security.JwtToken;
import com.stawisha.maziwa.erpz.security.JwtUtils;
import com.stawisha.maziwa.erpz.security.UserDetailsImpl;
import com.stawisha.maziwa.erpz.services.EmployeeService;
import io.jsonwebtoken.Jwts;
import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")

public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;
    
    @Autowired
    private EmployeeService employeeService;
    
    private final Logger log = Logger.getLogger(AuthController.class);

    /**
     * Login process handler for mobile devices
     *
     * @param emp
     * @param model object containing name of logged in user. Sent back using
     * the HttpResponse
     * @return ResponseEntity<> response object carried back to client device it
     * contains the JWT authentication token.
     */
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginPayLoad payLoad, Model model) {
        Employee emp= new Employee();
        emp.setPhone(payLoad.getPhone());
        emp.setLoginAccess(payLoad.getLoginAccess());
        log.debug("New login attempt by "+ emp.getId());
        
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(payLoad.getPhone(), emp.getLoginAccess()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        JwtToken jwtToken = new JwtToken();
        jwtToken.setType("JWT");
        jwtToken.setValue(jwt);
        Employee employee = employeeService.findEmployeeByPhone(payLoad.getPhone());
        jwtToken.setTenant(employee.getTenant().getId());
        jwtToken.setEmployee(employee.getFirstName());
        jwtToken.setTenantName(employee.getTenant().getName());
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
        model.addAttribute("name", authentication.getName());
        return new ResponseEntity<>(jwtToken, HttpStatus.OK);
    }
    


    /**
     * error 401 handler
     *
     * @return exception details and response code 401
     */
    /**
     * checks whether a JWT token is less than seven hours to expiry only used
     * by mobile client for auto login if true login auto-login fails on the
     * mobile client device if false auto-login proceeds
     *
     * @param request a HTTP payload
     * @return HttpStatus code 401 if fail else HttpStatus code 200
     */
    @GetMapping("/is_expired")
    public ResponseEntity<?> isExpired(HttpServletRequest request,Principal principal) {
        System.out.println(principal.getName());
        String token = request.getHeader("Authorization");
        if ("".equals(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        token = token.substring(7, token.length());
        Date date = null;
        try {
            date = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getExpiration();
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        long currentDate = new Date().getTime();
        long jwtTime = date.getTime();
        if ((jwtTime - currentDate) > 7 * 60 * 60 * 1000) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Value("${app.jwtSecret}")
    private String jwtSecret;

}
