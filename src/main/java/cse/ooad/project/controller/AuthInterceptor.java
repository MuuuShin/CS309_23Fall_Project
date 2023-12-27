package cse.ooad.project.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNullApi;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import cse.ooad.project.utils.JwtUtils;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
@CrossOrigin
public class AuthInterceptor implements HandlerInterceptor {


    private final Set<String> blackList = new HashSet<>();

    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void clearBlackList() {
       for (String jwt : blackList) {
           try {
               JwtUtils.parseJWT(jwt);
           } catch (Exception e) {
               blackList.remove(jwt);
           }
       }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url=request.getRequestURI();
        String regexLogin="^/login$";
//        String regexRegister="^/register$";
        String regexLogout="^/logout$";
        String jwt = request.getHeader("Authorization");
        if ("OPTIONS".equals(request.getMethod().toUpperCase())){
            return true;
        }
        log.info("Authorization: {}", jwt);

        if (url.matches(regexLogin)){
            log.info("Login");
            return true;
        }

        if (url.matches(regexLogout)){
            log.info("Logout");
            blackList.add(jwt);
            return true;
        }

//        if(url.matches(regexLogin)||url.matches(regexRegister)){
//            log.info("Login or register");
//            return true;
//        }

        if (blackList.contains(jwt)){
            log.info("Blacklist");
            Result<Object> UnauthorizedResult = Result.unauthorized("You have been logged out");
            String notLogin = UnauthorizedResult.toString();
            response.getWriter().write(notLogin);
            return false;
        }


        if (!StringUtils.hasText(jwt)) {
            log.info("Authorization header is missing");
            Result<Object> UnauthorizedResult = Result.unauthorized("Authorization header is missing");
            String notLogin = UnauthorizedResult.toString();
            response.getWriter().write(notLogin);
            return false;
        }

        try {
            JwtUtils.parseJWT(jwt);
        } catch (Exception e) {
            log.info("Invalid JWT");
            Result<Object> UnauthorizedResult = Result.unauthorized("Invalid JWT");
            String notLogin = UnauthorizedResult.toString();
            response.getWriter().write(notLogin);
            return false;
        }

        log.info("Authorization success");
        return true;


    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
