package com.authservice.controller;


import com.authservice.dto.ApiResponse;
import com.authservice.dto.LoginDto;
import com.authservice.dto.UserDto;
import com.authservice.service.JwtService;
import com.authservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/")
public class UserController {


    private UserService userService;

    private JwtService jwtService;

    private AuthenticationManager authenticationManager;

    public UserController(UserService userService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody UserDto dto) {
        ApiResponse<String> response = userService.register(dto);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> loginChek(@RequestBody LoginDto loginDto){
        ApiResponse<String> response = new ApiResponse<>();
               try{
                   UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDto.getUsername(),loginDto.getPassword());

                   Authentication authenticate  =  authenticationManager.authenticate(token);
            if(authenticate.isAuthenticated()){
                String jwtToken = jwtService.generateToken(loginDto.getUsername(),
                        authenticate.getAuthorities().iterator().next().getAuthority());

                response.setData(jwtToken);
                response.setMessage("Login Success");
                response.setStatus(200);
                return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        response.setMessage("Invalid");
        response.setStatus(500);
        response.setData("Un-Authorized");
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
    }


}
