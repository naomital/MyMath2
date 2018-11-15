package myMath;
import java.awt.Color;
import java.util.Scanner;

import javax.swing.JFrame;
import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.plots.lines.LineRenderer;
import de.erichseifert.gral.plots.points.PointRenderer;
import de.erichseifert.gral.ui.InteractivePanel;
/**
 * the functionGraph Creates a new graph to the function that you insert according to the range you insert.
 * and color the critical points to pink. 
 * @author naomi&adi
 *
 */
public class FunctionGraph extends JFrame {
	public FunctionGraph(Polynom_able p1,double first,double last) {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600, 400);
		DataTable data = new DataTable(Double.class, Double.class);
		DataTable critical = new DataTable(Double.class, Double.class);
		Polynom_able p2 = p1.copy();
		p2 = p2.derivative();
		double x3 = 0;
		double x2 = 0;
		for (double x = first; x <= last; x+=0.25) {
			double y = p1.f(x);
			data.add(x, y);
			x2 = x+0.25;
			if(IsCritical(p2,x)==0) {
				critical.add(x, y);
			}
			else if(IsCritical(p2,x)!=IsCritical(p2,x2) && IsCritical(p2,x2) !=0) {
				x3 = p2.root(x,x2,0.0001);
				critical.add(x3,p1.f(x3));
			}
		}
		XYPlot plot = new XYPlot(data,critical);
		getContentPane().add(new InteractivePanel(plot));
		LineRenderer lines = new DefaultLineRenderer2D();
		plot.setLineRenderers(data, lines);
		Color color = new Color(0.0f, 0.0f, 0.0f);
		plot.getPointRenderers(data).get(0).setColor(color);
		plot.getLineRenderers(data).get(0).setColor(color);
		if(!plot.getPointRenderers(critical).isEmpty()) plot.getPointRenderers(critical).get(0).setColor(color.pink);	
	}
	/**
	 * the IsCritical is An auxiliary function that checks the  critical point according to derivative and X.
	 * @param deriv The polynomial we're checking.
	 * @param x The value of X that we are testing.
	 * @return 0 if it is critical point, 1 if the y is Positive, -1 if the y is negative.
	 */
	public int IsCritical(Polynom_able deriv,double x) {
		int flg=1;
		if(Math.abs(deriv.f(x))<0.25) flg = 0;
		else if(deriv.f(x)<0)flg=-1;
		return flg;
	}
	
}

