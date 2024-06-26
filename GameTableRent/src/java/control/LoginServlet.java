package control;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UtenteDAO userDao = new UtenteDAO();
        UtenteDTO utente = null;
        String hashedPsw = null;
        String email=request.getParameter("email");
        String psw = request.getParameter("psw");
        try {
            hashedPsw = RegistrationServlet.hashWithSHA256(psw);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Errore nell'hashing");
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
            return;
        }
        if(userDao.doRetrieveByEmail(email))
        utente = userDao.doRetrieveByPSW(hashedPsw);
        if (utente != null) {
            CarrelloDAO cartDao = new CarrelloDAO();
            CarrelloDTO cart = cartDao.doRetrieveById(utente.getID());
            request.getSession().setAttribute("cart", cart);
            request.getSession().setAttribute("user", utente);
            response.sendRedirect(request.getContextPath() + "/UserHome.jsp");
        } else {
            request.setAttribute("ValueLogin", 0);
            request.getRequestDispatcher("/Login.jsp").forward(request, response);
        }
    }
}
