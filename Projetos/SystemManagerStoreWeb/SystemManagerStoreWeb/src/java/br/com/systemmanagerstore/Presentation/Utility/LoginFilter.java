/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.systemmanagerstore.Presentation.Utility;

import br.com.systemmanagerstore.DomainModel.Funcionario;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author arley
 */
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Funcionario funcionario = null;

        HttpSession sess = ((HttpServletRequest) request).getSession(false);

        String uri = ((HttpServletRequest) request).getRequestURI();

        if (sess != null) {
            funcionario = (Funcionario) sess.getAttribute("funcionarioLogado");
        }
        String contextPath = ((HttpServletRequest) request).getContextPath();
        if (funcionario == null) {
            ((HttpServletResponse) response).sendRedirect(contextPath + "/Login.xhtml");
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
    }
}
