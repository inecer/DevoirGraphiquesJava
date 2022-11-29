package Vues;

import Controlers.CtrlGraphique;
import Entities.DatasGraph;
import Tools.ConnexionBDD;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.RingPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultKeyedValues2DDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYZDataset;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Map;

public class FrmGraphique extends JFrame{
    private JPanel pnlGraph1;
    private JPanel pnlGraph2;
    private JPanel pnlGraph3;
    private JPanel pnlGraph4;
    private JPanel pnlRoot;
    CtrlGraphique ctrlGraphique;

    public FrmGraphique() throws SQLException, ClassNotFoundException {
        this.setTitle("Devoir graphique");
        this.setContentPane(pnlRoot);
        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        ConnexionBDD cnx = new ConnexionBDD();
        ctrlGraphique = new CtrlGraphique();

        //Graph1
        DefaultCategoryDataset donneesGraph1 = new DefaultCategoryDataset();
        int salaireEmp;
        int ageEMP;
        for (Integer valeur : ctrlGraphique.GetDatasGraphique1().keySet())
        {
            salaireEmp = ctrlGraphique.GetDatasGraphique1().get(valeur);
            ageEMP = valeur;
            donneesGraph1.setValue(salaireEmp,ageEMP);
        }
        JFreeChart chart1 = ChartFactory.createLineChart(
                "Salaire moyen par age",
                "Age",
                "Salaire (€)",
                donneesGraph1,
                PlotOrientation.VERTICAL,false, true, false);
        ChartPanel graph = new ChartPanel(chart1);
        graph = new ChartPanel(chart1);

        //Graph2
        DefaultKeyedValues2DDataset donneesGraph2 = new DefaultKeyedValues2DDataset ();
        int Homme;
        int Femme;
        for (Integer valeur : ctrlGraphique.GetDatasGraphique1().keySet())
        {
            Homme = ctrlGraphique.GetDatasGraphique1().get(valeur);
            Femme = valeur;
            donneesGraph2.setValue(Homme,Femme);
        }
        JFreeChart chart2 = ChartFactory.createStackedBarChart(
                "Pyramide des ages",
                "Homme / Femme",
                "Age",
                donneesGraph2,
                PlotOrientation.VERTICAL,true, true, false);
        graph = new ChartPanel(chart1);
        pnlGraph2.add(graph);
        pnlGraph2.validate();

        //Graph3
        DefaultPieDataset donneesGraph3 = new DefaultPieDataset( );
        int HommePourcentage;
        int FemmePourcentage;
        for (int valeur : ctrlGraphique.GetDatasGraphique3().keySet())
        {
            HommePourcentage = ctrlGraphique.GetDatasGraphique3().get(valeur);
            FemmePourcentage = valeur;

            donneesGraph3.setValue(HommePourcentage,FemmePourcentage);
        }
        chart1 = ChartFactory.createRingChart("Pourcentage de femmes et d'hommes", donneesGraph3, true, false, false);
        RingPlot plot = (RingPlot) chart1.getPlot();
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{1}"));
        graph = new ChartPanel(chart1);
        pnlGraph3.add(graph);
        pnlGraph3.validate();

        //Graph4
        DefaultCategoryDataset donneesGraph4 = new DefaultCategoryDataset();
        for (String valeur : ctrlGraphique.GetDatasGraphique4().keySet())
        {
            for(int i = 0;i< ctrlGraphique.GetDatasGraphique4().get(valeur).size();i+=2)
            {
                donneesGraph4.setValue
                        (
                                Double.parseDouble(ctrlGraphique.GetDatasGraphique4().get(valeur).get(i+1)),
                                valeur.toString(),
                                ctrlGraphique.GetDatasGraphique4().get(valeur).get(i).toString()
                        );
            }
        }
        chart1 = ChartFactory.createBarChart(
                "Montant des ventes par magasin",
                "Magasins",
                "Montant des ventes",
                donneesGraph4,
                PlotOrientation.VERTICAL,
                true, true, false);
        graph = new ChartPanel(chart1);
        pnlGraph4.add(graph);
        pnlGraph4.validate();
    }
}
