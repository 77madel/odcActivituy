package com.odk.Auth;

import com.odk.Repository.UtilisateurRepository;
import com.odk.Service.Interface.Service.JwtUtile;
import com.odk.dto.ReqRep;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class Login {

    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private UtilisateurRepository utilisateurRepository;
    private JwtUtile jwtUtile;

//    @PostMapping("/login")
//    public ReqRep login(@RequestBody ReqRep loginRequest) {
//        ReqRep response = new ReqRep();
//        try {
//             Authentication authenticate =   authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
//            );
//
//            Utilisateur user = utilisateurRepository.findByEmail(loginRequest.getEmail())
//                    .orElseThrow(() -> new RuntimeException("User not found"));
//
//            if (authenticate.isAuthenticated()){
//                return (ReqRep) this.jwtUtile.generate(loginRequest.getEmail());
//            }
//            // Ajouter les détails de l'utilisateur dans la réponse
//            response.setStatusCode(200);
//            response.setRole(user.getRole());
//            response.setNom(user.getNom());
//            response.setPrenom(user.getPrenom());
//            response.setEmail(user.getEmail());
//            response.setPhone(user.getPhone());
//            response.setMessage("Successfully Logged In");
//
//        } catch (Exception e) {
//            e.printStackTrace();  // Afficher l'erreur dans la console
//            response.setStatusCode(500);
//            response.setMessage("Authentication failed: " + e.getMessage());
//        }
//        return response;
//    }

    @PostMapping(path ="login")
    public Map<String, String> connexion(@RequestBody ReqRep loginrequest){
        final Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginrequest.getEmail(), loginrequest.getPassword())
        );
        if (authenticate.isAuthenticated()){
            return this.jwtUtile.generate(loginrequest.getEmail());
        }
        return null;
    }
}
