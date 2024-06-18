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
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String email = request.getParameter("email");
        String psw = request.getParameter("psw");
        String indirizzo=request.getParameter("indirizzo");
		try {
			 psw= RegistrationServlet.hashWithSHA256(request.getParameter("Password"));
		} catch (NoSuchAlgorithmException e) {
			System.err.println("errore nell'hashing");
		}

		if(userDao.doRetrieveByPSW(psw)) {
			UtenteDTO utente=new UtenteDTO(0,nome, cognome, email, psw, indirizzo);
			request.getSession().setAttribute("utente", utente);
			response.sendRedirect(request.getContextPath()+"/home.jsp");
		}
		else 
			request.setAttribute("ValueLogin", 0);
		request.getRequestDispatcher("/Registrazione.jsp").forward(request, response);
		
		
	}

}
