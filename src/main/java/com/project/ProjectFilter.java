package com.project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.GenericFilterBean;

import com.project.table.UserModule;


@Configuration
public class ProjectFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        if (allowUrl(req)) {
            chain.doFilter(request, response);
            return;
        }
		if(req.getSession().getAttribute("username") == null || req.getSession().getAttribute("userModuleMenus") == null) {
			resp.sendRedirect("/login");
			return;
		}

		if(req.getRequestURI().startsWith("/api")){
			chain.doFilter(request, response);
		}else{
			if(checkPermission(req)) {
				chain.doFilter(request, response);
			} else {
				resp.getWriter().print("You do not have permission.");
			}
		}

//		chain.doFilter(request, response);
	}
	public boolean checkPermission(HttpServletRequest req) {
        @SuppressWarnings("unchecked")
        List<UserModule> danhMucQuyens = (List<UserModule>) req.getSession().getAttribute("userModuleMenus");

        String uri = req.getRequestURI();
        if (uri.equals("/")) return true;
        boolean checkPermission = false;
        for (UserModule tbDanhMucQuyen : danhMucQuyens) {
            if (tbDanhMucQuyen.getUrl() != null && uri.startsWith(tbDanhMucQuyen.getUrl())) {
                checkPermission = true;
            }
        }
        return checkPermission;
	}
	public Boolean allowUrl(HttpServletRequest req) {
		ArrayList<String> listAllow = new ArrayList<>();
		listAllow.add("/font/*");
		listAllow.add("/fonts/*");
		listAllow.add("/webfonts/*");
		listAllow.add("/select2-develop/*");
		listAllow.add("/css/*");
		listAllow.add("/js/*");
		listAllow.add("/image/*");
		listAllow.add("/images/*");
		listAllow.add("/img/*");
		listAllow.add("/favicon.ico");
		listAllow.add("/login");
		listAllow.add("/logout");
		listAllow.add("/static/file/*");
		listAllow.add("/register");
		listAllow.add("/plugins/*");
		listAllow.add("/change-pass");

		return checkAllow(listAllow, req);
	}
	private Boolean checkAllow(ArrayList<String> listAllow, HttpServletRequest req) {
		for (String string : listAllow) {
			if (req.getRequestURI().matches(string.replace("*", "[\\w\\W]*"))) {
				return true;
			}
		}
		return false;
	}
}
