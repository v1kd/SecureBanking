package edu.asu.securebanking.util;

import edu.asu.securebanking.beans.AppUser;
import edu.asu.securebanking.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Vikranth on 10/17/2015.
 */
public class AppAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    @Qualifier("userDAO")
    private UserDAO userDAO;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HttpSession session = request.getSession(true);
        AppUser appUser = userDAO.getUser(user.getUsername());
        session.setAttribute("user", session);

        // super.onAuthenticationSuccess(request, response, authentication);
        response.sendRedirect(request.getContextPath() + "/user/home");
    }
}