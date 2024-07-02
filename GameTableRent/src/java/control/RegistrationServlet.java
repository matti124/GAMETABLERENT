package control;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.*;
/*	SERVLET ADEBITA ALLA REGISTRAZIONE DI NUOVI UTENTI
 * UNA VOLTA COMPILATO IL FORM DI REGISTRAZIONE, I DATI VENGONO INOLTRATI A QUESTA SERVLET CHE PRIMA DI TUTTO CONTROLLA 
 * CHE NON VI SIA GIA' UN ACCOUNT CHE UTILIZZI QUELLA E-MAIL, PER POI SALVARE TUTTI I DATI OTTENUTI DAL FORM IN UN DTO
 * APPOSITO PER L'UTENTE DOVE AL MOMENTO DEL SALVATAGGIO DELLA PSW, LA SALVEREMO HASHATA. UNA VOLTA FATTO CIO' NON RESTA
 * CHE CHIAMARE LA FUNZIONE APPOSITA DEL DAO CHE PERMETTERA' DI SALVARE IL NUOVO UTENTE NEL DATABASE E CON LUI ANCHE IL 
 * CARRELLO ASSOCIATO. (SE UTENTE PRIMA DI REGISTRARSI AVEVA INSERITO PRODOTTI NEL CARRELLO ALLORA SALVIAMO QUEL CARRELLO NEL DB
 * 
 *
 * 
 *
 * 
 * 
 * 
 * 
 * 
 */
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
        boolean gotElements=false;
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
            UtenteDTO user = new UtenteDTO(0, nome, cognome, indirizzo, email, hashedPassword, 0);

            if (userDao.doRetrieveByEmail(email)) {
                request.setAttribute("ValueReg", 0);
                request.getRequestDispatcher("/Login.jsp").forward(request, response);
                return; 
            } else {
                userDao.doSave(user);
                user=userDao.doRetrieveByPSW(hashedPassword);
                request.getSession().setAttribute("utente", user);
                
                CarrelloDTO cart = (CarrelloDTO) request.getSession().getAttribute("cart");
               //se il carrello della sessione è vuoto ne creo uno nuovo
                if(cart.getCart().isEmpty()) {
                	System.out.println("L'utente non registrato non aveva prodotti nel carrello");

                	ArrayList<ProdottoCarrelloDTO> listaProds=new ArrayList<>();
                	cart=new CarrelloDTO(0, listaProds, user.getID());}
                //se vi sono prodotti nel carrello della sessione ne creo uno nuovo che presenta i prodotti di quello della sessione
                else {
                	System.out.println("L'utente non registrato aveva prodotti nel carrello");
                	cart=new CarrelloDTO(0, cart.getCart(), user.getID());
                	gotElements=true;
              
                	
                
                
                }
                
                CarrelloDAO cartDAO = new CarrelloDAO();
                int idCart=cartDAO.doSave(cart);
                
              
                //SE L'UTENTE PRIMA DI REGISTRARSI AVEVA PRODOTTI NEL CARRELLO ALLORA UNA VOLTA CHE CARICO IL CARRELLO E LO SALVO, DEVO SALVARE ANCHE I PRODOTTI AL SUO INTERNO
            	if(gotElements) {
                ProdottoCarrelloDAO dao=new ProdottoCarrelloDAO();
                for(ProdottoCarrelloDTO x: cart.getCart()) {
                	
            		dao.doSave(new ProdottoCarrelloDTO(idCart, x.getId_prodotto(), x.getPrezzo(), x.getPrezzoXdays(), x.getQuantita(), x.getGiorni(), x.getImage(), x.getName()));}
            	}
                
                
                
                request.getSession().setAttribute("cart", cart);
                response.sendRedirect("Login.jsp");
                return; 
            }
        } catch (SQLException e) {
            System.out.println("Error:" + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
            return;
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
