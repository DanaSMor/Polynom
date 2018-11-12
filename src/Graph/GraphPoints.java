package Graph;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.TexturePaint;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.graphics.Location;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.plots.lines.LineRenderer;
import de.erichseifert.gral.ui.InteractivePanel;
import myMath.Polynom;

public class GraphPoints extends JFrame {

	public GraphPoints(Polynom p, double x0, double x1)  {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1000, 800);

//		Image icon = new ImageIcon(this.getClass().getResource("/monkey.png")).getImage();
//		setIconImage(icon);

		Polynom pD = (Polynom) p.derivative();

		DataTable data = new DataTable(Double.class, Double.class);
		DataTable MinMax = new DataTable(Double.class, Double.class);
		DataTable points = new DataTable(Double.class, Double.class);
		double temp;
		for (double x = x0; x <=x1; x+=0.20) {
			double y = p.f(x);		
			if((pD.f(x) >= 0 && pD.f(x+0.20) < 0) || (pD.f(x) < 0 && pD.f(x+0.20) > 0)) {
				temp = pD.root(x, x+0.20, 0.0001);
				MinMax.add(temp,p.f(temp));
				points.add(temp,(p.f(temp)-0.5));
				data.add(temp,p.f(temp));
			}
			else
				data.add(x,y);
		}



		XYPlot plot = new XYPlot(data,MinMax,points);
		getContentPane().add(new InteractivePanel(plot));

		try {
			BufferedImage bgImage = ImageIO.read(this.getClass().getResource("/monkey.png"));
			TexturePaint background = new TexturePaint(bgImage, new Rectangle2D.Double(0.0, 0.0, 1.0, 1.0));
			plot.getPlotArea().setBackground(background);
		} catch (IOException e) {

		}


		LineRenderer lines = new DefaultLineRenderer2D();
		plot.setLineRenderers(data, lines);

		lines.setGap(1.0);

		Color colorM = new Color(0.7f, 0.0f, 0.9f);
		Color colorD = new Color(0.3f, 0.3f,0.7f);

		plot.getLineRenderers(data).get(0).setColor(colorD);
		plot.getPointRenderers(data).get(0).setColor(colorD);

		plot.getPointRenderers(data).get(0).setShape(new Ellipse2D.Double(0.0, 0.0, 0.0, 0.0));
		
		plot.getPointRenderers(points).get(0).setShape(new Ellipse2D.Double(0.0, 0.0, 0.0, 0.0));
		
		plot.getPointRenderers(MinMax).get(0).setColor(colorM);
		plot.getPointRenderers(MinMax).get(0).setShape(new Ellipse2D.Double(-4.0, -4.0, 10.0, 10.0));

		//setting the points
		plot.getPointRenderers(MinMax).get(0).setValueVisible(true);
		//plot.getPointRenderers(MinMax).get(0).
		
	//	plot.getPointRenderers(MinMax).get(0).setValueLocation(plot.getPointRenderers(MinMax).get(0).getValueLocation()-(1.5,2,3,4));;
	//	plot.getPointRenderers(MinMax).get(0).setValueAlignmentX();
		plot.getPointRenderers(MinMax).get(0).setValueAlignmentY(1);
	//	plot.setLegendVisible(true);

	}

}
