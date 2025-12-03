package com.hainv.booking.security;

import com.google.gson.Gson;
import com.hainv.booking.service.IUserService;
import com.hainv.booking.utils.ApiResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final IUserService userService;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, IUserService userService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            if (jwtTokenProvider.validateToken(token)) {
                String username = jwtTokenProvider.getUsernameFromToken(token);
                // find user -> set role -> check active
                com.hainv.booking.entity.user.User user = userService.findUserByUserName(username);
                if (user.getIsActive() > 0){
                    UserDetails userDetails = User.withUsername(username)
                            .password("") // không cần password
//                        .authorities(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")))
                            .roles("USER")
                            .build();

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request));

                    request.setAttribute("USER", user);

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }else {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.setContentType("application/json;charset=UTF-8");
                    ApiResponse<Void> body = ApiResponse.fail(HttpServletResponse.SC_FORBIDDEN, "User không Active");
                    response.getWriter().write(new Gson().toJson(body));
                    return;
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
