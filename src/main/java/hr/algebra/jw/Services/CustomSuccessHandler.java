package hr.algebra.jw.Services;

import hr.algebra.jw.Dto.LogDto;
import hr.algebra.jw.Model.User;
import hr.algebra.jw.Repositories.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;

@Service
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    LogService logService;
    @Autowired
    UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        var authorities = authentication.getAuthorities();
        var roles = authorities.stream().map(r -> r.getAuthority()).findFirst();



        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        User user = userRepository.findByEmail(username);
        Date now = new Date();
        String ipAddress = request.getRemoteAddr();

        LogDto logDto = new LogDto(user.getId(), now, ipAddress );

        logService.save(logDto);

        if (roles.orElse("").equals("ADMIN")) {
            response.sendRedirect("/admin/orders");
        } else if (roles.orElse("").equals("USER")) {
            response.sendRedirect("/");
        } else {
            response.sendRedirect("/error");
        }
    }



}

