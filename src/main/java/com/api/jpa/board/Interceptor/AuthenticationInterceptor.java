package com.api.jpa.board.Interceptor;

import com.api.jpa.board.repository.UserRepository;
import com.api.jpa.board.Enum.AccountType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component

public class AuthenticationInterceptor implements HandlerInterceptor {
    private Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    private UserRepository userRepository;

    /**
     * Header Authorization 사용자 구분
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("accountType", "OUT");
        httpSession.setAttribute("accountId", "");
        try {
             String headerAuthorization = request.getHeader("Authorization") == null ? "" : request.getHeader("Authorization") ;
             if(headerAuthorization != null){
                String[] accountTypeId = headerAuthorization.split(" ");
                String accountType = AccountType.get(accountTypeId[0]).getCode();
                if(!accountType.equals("OUT")){
                    httpSession.setAttribute("accountType", accountType);
                    httpSession.setAttribute("accountId", accountTypeId[1]);
                }
            }
        }catch(Exception e){
            logger.error("exception preHandle"+e.getMessage(), e);
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
