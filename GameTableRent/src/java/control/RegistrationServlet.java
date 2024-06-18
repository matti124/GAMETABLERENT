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
        String indirizzo=request.getParameter("indirizzo");

        // Hashing della password utilizzando SHA-256
        String hashedPassword=null;
		try {
			hashedPassword = hashWithSHA256(psw);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

       

        try {

            UtenteDTO user = new UtenteDTO(0, cognome, nome, indirizzo, email, hashedPassword, 0);
            if(userDao.doRetrieveByEmail(email)) {
            request.setAttribute("JustRegistered", "utente già registrato");
            
           request.getRequestDispatcher("/Registrazione.jsp").forward(request, response);}
            else
            userDao.doSave(user);

        } catch (SQLException e) {
            System.out.println("Error:" + e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/Home.jsp");

    }

    
    
    
    
    
    // Metodo per l'hashing della password utilizzando SHA-256
    private String hashWithSHA256(String password) throws NoSuchAlgorithmException {
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
