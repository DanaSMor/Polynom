package Graph;
import java.awt.Color;
import java.awt.Image;
import java.awt.TexturePaint;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.plots.lines.LineRenderer;
import de.erichseifert.gral.ui.InteractivePanel;
import myMath.Polynom;

public class GraphPoints extends JFrame {

	public GraphPoints(Polynom p, double x0, double x1)  {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1000, 800);
		
		Polynom pD = (Polynom) p.derivative();  // Save derivative of the Polynom to find the Max - Min Points in the function.

		DataTable data = new DataTable(Double.class, Double.class);
		DataTable MinMax = new DataTable(Double.class, Double.class);

		double temp;
		for (double x = x0; x <=x1; x+=0.20) { // Add Points with a 0.20 gap between every two points
			double y = p.f(x);		
			if((pD.f(x) >= 0 && pD.f(x+0.20) < 0) || (pD.f(x) < 0 && pD.f(x+0.20) > 0)) {  // If we find different gradient between 2 point so there is Min-Max Point
				temp = pD.root(x, x+0.20, 0.0001);     // Find the Min-Max Points accord with help of root function
				MinMax.add(temp,p.f(temp)); // Add the points with a  different color of the point
				data.add(temp,p.f(temp)); // Add it to the original graph points as well
			}
			else
				data.add(x,y); // It's not Min-Max point so just add it as regular
		}

		// Create new plot kind of XY Graph
		XYPlot plot = new XYPlot(data,MinMax);
		getContentPane().add(new InteractivePanel(plot));

		try {
			Image icon = new ImageIcon(this.getClass().getResource("/monkey.png")).getImage(); // Add awesome icon
			setIconImage(icon);
			
			BufferedImage bgImage = ImageIO.read(this.getClass().getResource("/monkey.png")); // Add awesome Background!
			TexturePaint background = new TexturePaint(bgImage, new Rectangle2D.Double(0.0, 0.0, 1.0, 1.0));
			plot.getPlotArea().setBackground(background);
		} catch (IOException e) {

		}

		// Connect all the points with a line
		LineRenderer lines = new DefaultLineRenderer2D();
		plot.setLineRenderers(data, lines);
		// Set a gap between the line and the dot
		lines.setGap(1.0);
		
		// Paint line and points
		Color colorM = new Color(0.7f, 0.0f, 0.9f);
		Color colorD = new Color(0.3f, 0.3f,0.7f);

		plot.getLineRenderers(data).get(0).setColor(colorD);
		plot.getPointRenderers(data).get(0).setColor(colorD);
		plot.getPointRenderers(MinMax).get(0).setColor(colorM);

		// Set special shapeS for the points
		plot.getPointRenderers(data).get(0).setShape(new Ellipse2D.Double(0.0, 0.0, 0.0, 0.0));
		plot.getPointRenderers(MinMax).get(0).setShape(new Ellipse2D.Double(-4.0, -4.0, 10.0, 10.0));

		// Show y value on the graph
		plot.getPointRenderers(MinMax).get(0).setValueVisible(true);
		plot.getPointRenderers(MinMax).get(0).setValueRotation(55);
		plot.getPointRenderers(MinMax).get(0).setValueAlignmentX(1);
	}

}
