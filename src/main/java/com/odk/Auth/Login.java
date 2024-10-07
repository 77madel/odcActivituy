package com.odk.Auth;

import com.odk.Repository.UtilisateurRepository;
import com.odk.Service.Interface.Service.JwtUtile;
import com.odk.dto.AuthentificationDTO;
import com.odk.dto.ReqRep;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@Slf4j
@Transactional
public class Login {

    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private UtilisateurRepository utilisateurRepository;
    private JwtUtile jwtUtile;

    @PostMapping(path = "login")
    public Map<String, String> connexion(@RequestBody AuthentificationDTO authentificationDTO) {
        final Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authentificationDTO.username(), authentificationDTO.password())
        );

        if(authenticate.isAuthenticated()) {
            return this.jwtUtile.generate(authentificationDTO.username());
        }
        return null;
    }

   /* @PostMapping(path = "refresh-token")
    public @ResponseBody Map<String, String> refreshToken(@RequestBody Map<String, String> refreshTokenRequest) {
        return this.jwtUtile.refreshToken(refreshTokenRequest);
    }*/

    /*@PostMapping(path = "deconnexion")
    public void deconnexion() {
        this.jwtUtile.deconnexion();
    }*/
}
