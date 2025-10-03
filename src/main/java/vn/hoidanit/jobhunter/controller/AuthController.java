package vn.hoidanit.jobhunter.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.domain.dto.ReqLoginDTO;
import vn.hoidanit.jobhunter.domain.dto.ResLoginDTO;
import vn.hoidanit.jobhunter.domain.dto.ResUserAccountDTO;
import vn.hoidanit.jobhunter.domain.dto.ResUserLoginDTO;
import vn.hoidanit.jobhunter.service.UserService;
import vn.hoidanit.jobhunter.util.SecurityUtil;
import vn.hoidanit.jobhunter.util.annotition.ApiMessage;
import vn.hoidanit.jobhunter.util.error.ResourceNotFoundException;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    private AuthenticationManagerBuilder authenticationManagerBuilder;
    private SecurityUtil securityUtil;
    private UserService userService;

    @Value("${hoidanit.jwt.refresh-token-validity-in-seconds}")
    private long refreshTokenExpiration;

    public AuthController(
            AuthenticationManagerBuilder authenticationManagerBuilder,
            SecurityUtil securityUtil,
            UserService userService) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.securityUtil = securityUtil;
        this.userService = userService;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<ResLoginDTO> login(@Valid @RequestBody ReqLoginDTO loginDto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(), loginDto.getPassword());

        // xác thực người dùng => cần viết hàm loadUserByUsername
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // set info người dùng vào context (có thể sử dụng sau này)
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // create a token
        ResLoginDTO resLoginDTO = new ResLoginDTO();

        ResUserLoginDTO user = new ResUserLoginDTO();
        User currentUserDB = this.userService.handleGetUserByUsername(loginDto.getUsername());

        if (currentUserDB != null) {
            user.setId(currentUserDB.getId());
            user.setEmail(currentUserDB.getEmail());
            user.setName(currentUserDB.getName());
            resLoginDTO.setUser(user);
        }

        String access_Token = this.securityUtil.createAccessToken(authentication.getName(), resLoginDTO);

        resLoginDTO.setAccessToken(access_Token);

        String refresh_token = this.securityUtil.createRefreshToken(loginDto.getUsername(), resLoginDTO);

        // update user
        this.userService.updateUserToken(refresh_token, loginDto.getUsername());

        // set cookie
        ResponseCookie resCookie = ResponseCookie.from("refresh_token", refresh_token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(refreshTokenExpiration)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, resCookie.toString())
                .body(resLoginDTO);
    }

    @GetMapping("/auth/account")
    @ApiMessage("fetch account")
    public ResponseEntity<ResUserAccountDTO> getAccount() {
        String email = SecurityUtil.getCurrentUserLogin().isPresent() ? SecurityUtil.getCurrentUserLogin().get() : "";

        User currentUserDB = this.userService.handleGetUserByUsername(email);
        ResUserAccountDTO userAccountDTO = new ResUserAccountDTO();
        ResUserLoginDTO userLogin = new ResUserLoginDTO();

        if (currentUserDB != null) {
            userLogin.setId(currentUserDB.getId());
            userLogin.setEmail(currentUserDB.getEmail());
            userLogin.setName(currentUserDB.getName());
            userAccountDTO.setUser(userLogin);
        }
        return ResponseEntity.ok(userAccountDTO);
    }

    @GetMapping("/auth/refresh")
    @ApiMessage("Get user by refresh token")
    public ResponseEntity<ResLoginDTO> getRefreshToken(
            @CookieValue(name = "refresh_token", defaultValue = "default_Value") String refresh_token)
            throws ResourceNotFoundException {
        if (refresh_token.equals("default_Value")) {
            throw new ResourceNotFoundException("Token không hợp lệ");
        }
        Jwt decodedToken = this.securityUtil.checkValidRefreshToken(refresh_token);
        String email = decodedToken.getSubject();

        // check user by token + email
        User currentUser = this.userService.getUserByRefreshTokenAndEmail(refresh_token, email);
        if (currentUser == null) {
            throw new ResourceNotFoundException("Refresh token không hợp lệ");
        }

        ResLoginDTO resLoginDTO = new ResLoginDTO();

        ResUserLoginDTO user = new ResUserLoginDTO();
        User currentUserDB = this.userService.handleGetUserByUsername(email);

        if (currentUserDB != null) {
            user.setId(currentUserDB.getId());
            user.setEmail(currentUserDB.getEmail());
            user.setName(currentUserDB.getName());
            resLoginDTO.setUser(user);
        }

        String access_Token = this.securityUtil.createAccessToken(email, resLoginDTO);

        resLoginDTO.setAccessToken(access_Token);

        String new_refresh_token = this.securityUtil.createRefreshToken(email, resLoginDTO);

        // update user
        this.userService.updateUserToken(new_refresh_token, email);

        // set cookie
        ResponseCookie resCookie = ResponseCookie.from("refresh_token", new_refresh_token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(refreshTokenExpiration)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, resCookie.toString())
                .body(resLoginDTO);
    }

    @PostMapping("/auth/logout")
    @ApiMessage("Logout user")
    public ResponseEntity<Void> logout() throws ResourceNotFoundException {

        String email = SecurityUtil.getCurrentUserLogin().isPresent() ? SecurityUtil.getCurrentUserLogin().get() : null;
        if (email.equals("")) {
            throw new ResourceNotFoundException("Access token không hợp lệ");
        }

        this.userService.updateUserToken(null, email);

        ResponseCookie deleteCookie = ResponseCookie
                .from("refresh_token", null)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, deleteCookie.toString())
                .body(null);
    }
}
