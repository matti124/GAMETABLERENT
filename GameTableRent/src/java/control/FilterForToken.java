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
@WebFilter(filterName = "FilterForToken", urlPatterns = {"/OwnHub.jsp"})
public class FilterForToken implements Filter {

    /**
     * Default constructor.
     */
    public FilterForToken() {
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
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession(false); 
        if (session != null) {
            UtenteDTO user = (UtenteDTO) session.getAttribute("utente");
            if (user != null && user.getID() != 0) {
                // Utente autenticato, procedi con la richiesta
                chain.doFilter(request, response);
            } else {
                // Utente non autenticato, reindirizza alla pagina di login
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/Login.jsp");
            }
        } else {
            // Nessuna sessione trovata, reindirizza alla pagina di login
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/Login.jsp");
        }
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
    }
}
