//package com.timelink;
//
//import java.io.Serializable;
//import java.util.List;
//
//import javax.ejb.Stateless;
//import javax.enterprise.context.Dependent;
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.TypedQuery;
//
//import com.timelink.Supplier;
//
//@Dependent
//@Stateless
//public class SupplierManager implements Serializable {
//    //@PersistenceContext(unitName="inventory-jpa") EntityManager em;
//    
//    
//    //Create
//    public void persist(Supplier s) {
//        //em.persist(s);
//    }
//    
//    //Read
//    public Supplier find(int id) {
//        //return em.find(Supplier.class, id);
//      return null;
//    }
//    
//    //Update
//    public void merge(Supplier s) {
//        //em.merge(s);
//    }
//    
//    //Delete
//    public void remove(Supplier s) {
//        s = find(s.getId());
//        //em.remove(s);
//    }
//    
//    public Supplier[] getAll() {
//        /*TypedQuery<Supplier> query = em.createQuery("SELECT s FROM Supplier s", Supplier.class);
//        List<Supplier> suppliers = query.getResultList();
//        Supplier[] array = new Supplier[suppliers.size()];
//        for (int i = 0; i < array.length; ++i) {
//            array[i] = suppliers.get(i);
//        }
//        return array;*/
//      return null;
//    }
//
//    
//    public Supplier[] getSearch(String searchTerm) {
//        /*TypedQuery<Supplier> query = em.createQuery("SELECT s FROM Supplier s WHERE s.supplierName like :search", Supplier.class);
//        query.setParameter("search", "%" + searchTerm + "%");
//        List<Supplier> suppliers = query.getResultList();
//        Supplier[] array = new Supplier[suppliers.size()];
//        for (int i = 0; i < array.length; ++i) {
//            array[i] = suppliers.get(i);
//        }
//        return array;*/
//      return null;
//    }
//}
