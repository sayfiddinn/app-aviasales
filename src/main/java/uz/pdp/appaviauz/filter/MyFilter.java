package uz.pdp.appaviauz.filter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.pdp.appaviauz.util.Utils;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class MyFilter extends OncePerRequestFilter {
    JwtProvider jwtProvider;
    Base64 base64;
    PasswordEncoder passwordEncoder;
    UserDetailsService userDetailsService;


    @Autowired
    public MyFilter(@Lazy JwtProvider jwtProvider,
                    @Lazy Base64 base64,
                    @Lazy PasswordEncoder passwordEncoder,
                    @Lazy UserDetailsService userDetailsService) {
        this.jwtProvider = jwtProvider;
        this.base64 = base64;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String authorization = request.getHeader("authorization");

        label:
        {
            if (Objects.isNull(authorization))
                break label;
            if (authorization.startsWith(Utils.BEARER)) {

                String token = authorization.substring(7);
                try {
                    String email = jwtProvider.getSubjectFromToken(token);
                    UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                    if (checkCredentials(userDetails))
                        break label;
                    setUserToContext(userDetails);

                } catch (Exception exception) {
                    response.getWriter().write(exception.getMessage());
                    response.setContentType("application/json");
                    response.setStatus(400);
                    return;
                }
            } else if (authorization.startsWith(Utils.BASIC)) {
                String basicAuth = authorization.substring(6);
                byte[] decode = base64.decode(basicAuth);
                basicAuth = new String(decode);
                String[] split = basicAuth.split(":");
                UserDetails userDetails = userDetailsService.loadUserByUsername(split[0]);
                if (checkCredentials(userDetails))
                    break label;
                if (passwordEncoder.matches(split[split.length - 1], userDetails.getPassword()))
                    setUserToContext(userDetails);
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean checkCredentials(UserDetails userDetails) {
        return !userDetails.isCredentialsNonExpired() ||
                !userDetails.isAccountNonExpired() ||
                !userDetails.isAccountNonLocked() ||
                !userDetails.isEnabled();
    }

    public void setUserToContext(UserDetails user) {
        UsernamePasswordAuthenticationToken authentication
                = new UsernamePasswordAuthenticationToken(
                user,
                null,
                user.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
