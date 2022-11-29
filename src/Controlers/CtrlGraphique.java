package Controlers;

import Entities.DatasGraph;
import Tools.ConnexionBDD;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CtrlGraphique
{
    private Connection cnx;
    private PreparedStatement ps;
    private PreparedStatement ps1;
    private ResultSet rs;
    private ResultSet rs1;
    CtrlGraphique ctrlGraphique;

    public CtrlGraphique() {
        cnx = ConnexionBDD.getCnx();
    }

    public HashMap<Integer,Integer> GetDatasGraphique1()
    {
        HashMap<Integer, Integer> datas = new HashMap();
        try {
            ps = cnx.prepareStatement("SELECT ageEMP,salaireEmp FROM employe");
            rs = ps.executeQuery();
            while(rs.next())
            {
                datas.put(rs.getInt("ageEMP"), rs.getInt("salaireEmp"));
            }
            ps.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlGraphique.class.getName()).log(Level.SEVERE, null, ex);
        }
        return datas;
    }

    public HashMap<Integer,Integer> GetDatasGraphique2()
    {
        HashMap<Integer, Integer> datas = new HashMap();
        try {
            //Tranche 40-49
            ps = cnx.prepareStatement("SELECT COUNT(ageEmp) as Homme FROM employe WHERE 40<ageEmp AND ageEmp<49 AND sexe = 'Homme' UNION SELECT COUNT(ageEmp) as Femme FROM employe WHERE 40<ageEmp AND ageEmp<49 AND sexe = 'Femme'");
            rs = ps.executeQuery();
            while(rs.next())
            {
                datas.put(rs.getInt("Homme"), rs.getInt("Femme"));
            }
            ps.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlGraphique.class.getName()).log(Level.SEVERE, null, ex);
        }
        return datas;
    }

    public HashMap<Integer,Integer> GetDatasGraphique3()
    {
        HashMap<Integer, Integer> datas = new HashMap();
        try {
            ps = cnx.prepareStatement("SELECT COUNT(sexe) / 200 as Homme FROM employe WHERE sexe = 'Homme' UNION SELECT COUNT(sexe) / 200 as Femme FROM employe WHERE sexe = 'Femme'");
            rs = ps.executeQuery();
            while(rs.next())
            {
                datas.put(rs.getInt("Homme"), rs.getInt("Femme"));
            }
            ps.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlGraphique.class.getName()).log(Level.SEVERE, null, ex);
        }
        return datas;
    }

    public HashMap<String, ArrayList<String>> GetDatasGraphique4()
    {
        HashMap<String, ArrayList<String>> datas = new HashMap();
        try {
            ps = cnx.prepareStatement("SELECT montant, nomSemestre, nomMagasin FROM vente");
            rs = ps.executeQuery();
            while(rs.next())
            {
                if(!datas.containsKey(rs.getString("nomMagasin")))
                {
                    ArrayList<String> lesGraphiques = new ArrayList<>();
                    lesGraphiques.add(rs.getString("nomSemestre"));
                    lesGraphiques.add(rs.getString("montant"));
                    datas.put(rs.getString("nomMagasin"),lesGraphiques);
                }
                else
                {
                    datas.get(rs.getString("nomMagasin")).add(rs.getString("nomSemestre"));
                    datas.get(rs.getString("nomMagasin")).add(rs.getString("montant"));
                }
            }
            ps.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlGraphique.class.getName()).log(Level.SEVERE, null, ex);
        }
        return datas;
    }
}
