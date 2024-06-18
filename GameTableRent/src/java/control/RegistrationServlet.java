package control;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.*;

@WebServlet("/Registrazione")
public class RegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UtenteDAO userDao = new UtenteDAO();
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String email = request.getParameter("email");
        String psw = request.getParameter("psw");
        String indirizzo = request.getParameter("indirizzo");

        // Hashing della password utilizzando SHA-256
        String hashedPassword = null;
        try {
            hashedPassword = hashWithSHA256(psw);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            // Gestisci l'eccezione, ad esempio reindirizzando a una pagina di errore
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
            return;
        }

        try {
            UtenteDTO user = new UtenteDTO(0, nome, cognome, indirizzo, email, hashedPassword);

            if (userDao.doRetrieveByEmail(email)) {
                request.setAttribute("ValueReg", 0);
                request.getRequestDispatcher("/Login.jsp").forward(request, response);
                return; 
            } else {
                userDao.doSave(user);
                request.getSession().setAttribute("utente", user);
                CarrelloDTO cart = new CarrelloDTO(0, null, user.getID());
                CarrelloDAO cartDAO = new CarrelloDAO();
                cartDAO.doSave(cart);
                request.getSession().setAttribute("cart", cart);
                request.setAttribute("ValueReg", 1);
                request.getRequestDispatcher("Login.jsp").forward(request, response);
                return; 
            }
        } catch (SQLException e) {
            System.out.println("Error:" + e.getMessage());
            // Gestisci l'eccezione, ad esempio reindirizzando a una pagina di errore
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
            return; // Assicurati di uscire dopo il redirect
        }
    }

    // Metodo per l'hashing della password utilizzando SHA-256
    public static String hashWithSHA256(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(password.getBytes());

        StringBuilder hexString = new StringBuilder();
        for (byte b : encodedhash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
