package io.github.benbarron.springauthorizationserver.filters;

import io.github.benbarron.springauthorizationserver.models.User;
import io.github.benbarron.springauthorizationserver.services.JsonWebTokenService;
import io.github.benbarron.springauthorizationserver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {
    private final UserService userService;
    private final JsonWebTokenService jsonWebTokenService;

    @Autowired
    public AuthenticationFilter(UserService userService, JsonWebTokenService jsonWebTokenService) {
        this.userService = userService;
        this.jsonWebTokenService = jsonWebTokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String jwt = request.getHeader("x-auth-token");
        String username = this.jsonWebTokenService.extractUsername(jwt);
        if(jwt != null && username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User user = this.userService.loadUserByUsername(username);
            if(this.jsonWebTokenService.isValid(jwt, user)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        user.getUsername(), user.getPassword(), user.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
}
