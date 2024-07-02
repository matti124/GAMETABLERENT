package control;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.UtenteDTO;

/**
 * Servlet Filter implementation class FilterForToken
 */
@WebFilter(filterName = "AdminFilter", urlPatterns = {"/AdminControl", "/ProductControl?action=update", "/ProductoControl?action=elimina", "/ProductControl?action=aggiungi"})
public class AdminFilter implements Filter {

    /**
     * Default constructor.
     */
    public AdminFilter() {
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
        // Cleanup code if needed
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession(false);
        
        boolean isAdmin = session != null && session.getAttribute("user") != null && ((UtenteDTO) session.getAttribute("user")).getIsAdmin() == 1;

        // Verifica se l'utente è admin per i servlet specificati
        if (isAdmin) {
            filterChain.doFilter(request, response); // Prosegui con la richiesta
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Accesso non consentito");
        }
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
    }
}
