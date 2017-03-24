package com.timelink.themes;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
 
@ManagedBean(name="themeService", eager = true)
@ApplicationScoped
public class ThemeService {
     
    private List<Theme> themes;
     
    @PostConstruct
    public void init() {
        themes = new ArrayList<Theme>();
        themes.add(new Theme(0, "Afterwork", "afterwork"));
        themes.add(new Theme(1, "Black-Tie", "black-tie"));
    }
     
    public List<Theme> getThemes() {
        return themes;
    } 
}