package model;

import java.util.ArrayList;

//interfaccia generica per DAO sia di Prodotto_Carrello che Prodotto_Ordine , 
//non implemento DAO parametrico per entrambi poichè si creerebbe confusione 
//per gestire le query che si riferiscono ad entità differenti

public interface ProdGenericDAOInterfaccia<T> {
    boolean doSave(T entity);
    boolean doDelete(T entity);
    boolean doUpdate(T entity);
    T doRetrieveByKey(int id1, int id2);
    ArrayList<T> doRetrieveAll(int id1);
}
