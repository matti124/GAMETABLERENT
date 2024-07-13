package control;

import model.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;

@MultipartConfig
@WebServlet("/ProductControl")
public class ProductControl extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProdottoDAO prodottoDAO;

    public void init() {
        prodottoDAO = new ProdottoDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        UtenteDTO utente = (UtenteDTO) request.getSession().getAttribute("user");

        if (action == null) {
            response.sendRedirect(request.getContextPath());
            return;
        }

        switch (action) {
            case "dettaglio":
                dettaglioProdotto(request, response);
                break;
            case "mostraProdotti":
                mostraProdotti(request, response);
                break;
            case "image":
                mostraImmagine(request, response);
                break;
            case "elimina":
                eliminaProdotto(request, response, utente);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Azione non valida");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        UtenteDTO utente = (UtenteDTO) request.getSession().getAttribute("user");

        if (action == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Azione non specificata");
            return;
        }

        switch (action) {
            case "update":
                updateProdotto(request, response, utente);
                break;
            
            case "aggiungi":
                addProduct(request, response, utente);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Azione non valida");
        }
    }

    private void addProduct(HttpServletRequest request, HttpServletResponse response, UtenteDTO utente) throws IOException {
        if (utente.getIsAdmin() != 1) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Non sei autorizzato a eseguire questa azione");
            return;
        }

        try {
            String nome = request.getParameter("nome");
            String descrizione = request.getParameter("descrizione");
            double prezzo = Double.parseDouble(request.getParameter("prezzo"));
            double prezzoXDays = Double.parseDouble(request.getParameter("prezzoXDays"));
            int inCat = Integer.parseInt(request.getParameter("inCatalogo"));
            int quantity = Integer.parseInt(request.getParameter("quantita"));
            Part filePart = request.getPart("immagine");
            byte[] immagine = null;
            if (filePart != null && filePart.getSize() > 0) {
                InputStream inputStream = filePart.getInputStream();
                immagine = inputStream.readAllBytes();
            }

            ProdottoDTO nuovoProdotto = new ProdottoDTO(0, nome, descrizione, prezzo, prezzoXDays, quantity, inCat, immagine);

            boolean added = prodottoDAO.doSave(nuovoProdotto);
            if (added) {
                response.sendRedirect(request.getContextPath() + "/ProductControl?action=mostraProdotti");
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore durante l'aggiunta del prodotto");
            }
        } catch (NumberFormatException | IOException | ServletException | SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore durante l'elaborazione della richiesta");
        }
    }

    private void dettaglioProdotto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int codice = Integer.parseInt(request.getParameter("codice"));
        ProdottoDTO prodotto = prodottoDAO.doRetrieveByKey(codice);
        if (prodotto != null) {
            request.setAttribute("prodotto", prodotto);
            request.getRequestDispatcher("/ProductDetails.jsp").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Prodotto non trovato");
        }
    }

    private void mostraProdotti(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<ProdottoDTO> listaProdotti = prodottoDAO.doRetrieveAll();

        ArrayList<ProdottoDTO> listaProdottiInCatalogo = new ArrayList<>();
        for (ProdottoDTO prod : listaProdotti) {
            if (prod.getIN_CAT() != 0) {
                listaProdottiInCatalogo.add(prod);
            }
        }

        request.setAttribute("ListaProdotti", listaProdottiInCatalogo);
        request.getRequestDispatcher("/Catalogo.jsp").forward(request, response);
    }

    private void updateProdotto(HttpServletRequest request, HttpServletResponse response, UtenteDTO utente) throws ServletException, IOException {
        if (utente.getIsAdmin() != 1) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Non sei autorizzato a eseguire questa azione");
            return;
        }

        try {
            int id_prod = Integer.parseInt(request.getParameter("id"));
            String nome = request.getParameter("nome");
            String descrizione = request.getParameter("descrizione");
            double prezzo = Double.parseDouble(request.getParameter("prezzo"));
            double prezzoXDays = Double.parseDouble(request.getParameter("prezzoXDays"));
            int inCat = Integer.parseInt(request.getParameter("inCatalogo"));
            int quantity = Integer.parseInt(request.getParameter("quantita"));
            Part filePart = request.getPart("immagine");
            byte[] immagine = null; // Implementazione della gestione dell'immagine
            if (filePart != null && filePart.getSize() > 0) {
                InputStream inputStream = filePart.getInputStream();
                immagine = inputStream.readAllBytes();
            }

            ProdottoDTO updateProdotto = prodottoDAO.doRetrieveByKey(id_prod);
            if (updateProdotto != null) {
                updateProdotto.setNome(nome);
                updateProdotto.setDescrizione(descrizione);
                updateProdotto.setPrezzo(prezzo);
                updateProdotto.setPrezzoXDay(prezzoXDays);
                updateProdotto.setIN_CAT(inCat);
                updateProdotto.setQuantity(quantity);
                if (immagine != null)
                    updateProdotto.setImmagine(immagine);

                boolean updated = prodottoDAO.doUpdate(updateProdotto);
                if (updated) {
                    response.sendRedirect(request.getContextPath() + "/ProductControl?action=dettaglio&codice=" + updateProdotto.getID_Prod());
                } else {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore durante l'aggiornamento del prodotto");
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Prodotto non trovato");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore durante l'elaborazione della richiesta");
        }
    }

    private void eliminaProdotto(HttpServletRequest request, HttpServletResponse response, UtenteDTO utente) throws ServletException, IOException {
        if (utente.getIsAdmin() != 1) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Non sei autorizzato a eseguire questa azione");
            return;
        }

        try {
            int id_prod = Integer.parseInt(request.getParameter("id"));
            ProdottoDTO prodotto = prodottoDAO.doRetrieveByKey(id_prod);
            if (prodotto != null) {
                prodotto.setIN_CAT(0);
                boolean deleted = prodottoDAO.doUpdate(prodotto);
                
                if (deleted) {
                	System.out.println("Eliminato");
                    response.sendRedirect(request.getContextPath() + "/ProductControl?action=mostraProdotti");
                    return;
                } else {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore durante l'eliminazione del prodotto");
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Prodotto non trovato");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore durante l'elaborazione della richiesta");
        }
    }

    private void mostraImmagine(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id_prod = Integer.parseInt(request.getParameter("id"));
        ProdottoDTO prodotto = prodottoDAO.doRetrieveByKey(id_prod);
        if (prodotto != null && prodotto.getImmagine() != null) {
            response.setContentType("image/jpeg"); // o "image/png" a seconda del tipo di immagine
            response.setContentLength(prodotto.getImmagine().length);
            ServletOutputStream out = response.getOutputStream();
            out.write(prodotto.getImmagine());
            out.close();
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Immagine non trovata");
        }
    }
}
