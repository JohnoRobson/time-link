//package com.timelink;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.enterprise.context.SessionScoped;
//import javax.inject.Inject;
//import javax.inject.Named;
//
//@Named
//@SessionScoped
//public class Form implements Serializable {
//    
//  @Inject SupplierManager sman;
//    
//  private List<Supplier> list;
//    
//  private String search;
//    
//  private Supplier temp = new Supplier();
//    
//  public List<Supplier> getList() {
//        
//    if (list == null) {
//            //refreshList();
//    }
//    return list;
//  }
//    
//  public void refreshList() {
//    Supplier[] sup = sman.getAll();
//    list = new ArrayList<Supplier>();
//    for (Supplier q : sup) {
//      list.add(q);
//    }
//  }
//    
//  public void setList(List<Supplier> sl) {
//    list = sl;
//  }
//    
//  public String deleteRow(Supplier s) {
//    sman.remove(s);
//    list.remove(s);
//    return null;
//  }
//    
//    public String addRow() {
//        list.add(temp);
//        return null;
//    }
//    
//    public String save() {
//        for (Supplier s : list) {
//            sman.merge(s);
//        }
//        return null;
//    }
//
//    public String getSearch() {
//        return search;
//    }
//
//    public void setSearch(String search) {
//        this.search = search;
//    }
//    
//    public String searchFor() { 
//        Supplier[] s = sman.getSearch(search);
//        list = new ArrayList<Supplier>();
//        for (Supplier q : s) {
//            list.add(q);
//        }
//        return null;
//    }
//
//    public Supplier getTemp() {
//        return temp;
//    }
//
//    public void setTemp(Supplier temp) {
//        this.temp = temp;
//    }
//    
//    public String saveTemp() {
//        sman.merge(temp);
//        return null;
//    }
//
//}
