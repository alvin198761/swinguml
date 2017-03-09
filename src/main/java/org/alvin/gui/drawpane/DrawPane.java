package org.alvin.gui.drawpane;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import org.alvin.bean.shape.data.BaseDataShape;
import org.alvin.gui.frame.BaseDrawComp;
import org.alvin.sys.Application;

/**
 * »­Í¼°å
 * 
 * @author ÌÆÖ²³¬
 * 
 */
public class DrawPane extends JPanel implements BaseDrawComp {

	private static final long serialVersionUID = 1L;

	private double scale = 1;
	private double imageWidth = Application.image.getWidth() * scale;
	private double imageHeight = Application.image.getHeight() * scale;
	private double imagex = 0;
	private double imagey = 0;
	private Rectangle2D imageBox = new Rectangle2D.Double();

	public double getImagex() {
		return imagex;
	}

	public void setImagex(double imagex) {
		this.imagex = imagex;
	}

	public double getImagey() {
		return imagey;
	}

	public void setImagey(double imagey) {
		this.imagey = imagey;
	}

	public DrawPane() {
		new DrawPaneHelper(this);
		setBackground(Color.gray);
	}

	protected void paintComponent(Graphics g) {
		// Log.info("»æÖÆ");
		super.paintComponent(g);
		imagex = (this.getWidth() - (int) imageWidth) >> 1;
		imagey = (this.getHeight() - (int) imageHeight) >> 1;
		// Ë«»º³å»­Í¼
		g.drawImage(Application.image, (int) imagex, (int) imagey,
				(int) imageWidth, (int) imageHeight, this);
	}

	public void reDraw() {
		// Log.info("¼ÆËã");
		Application.g2d = Application.image.createGraphics();
		// ¿¹¾â³Ý ¶¶¶¯
		Application.g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		Application.g2d.setColor(this.getBackground());
		Application.g2d.fill(new Rectangle2D.Double(0, 0, Application.image
				.getWidth(), Application.image.getHeight()));
		// ½«½çÃæÆÌ°×
		Application.g2d.setColor(Color.white);
		Application.g2d.scale(scale, scale);
		Application.g2d.fill(new Rectangle2D.Double(0, 0, Application.image
				.getWidth(), Application.image.getHeight()));
		int baseSize = 10;
		// »­Ò»¸öÍø¸ñµã
		int hCount = Application.image.getHeight() / baseSize;
		Stroke defaultStroke = Application.g2d.getStroke();
		// »­ÐéÏßµÄ´úÂë
		BasicStroke dotted = new BasicStroke(1.5f, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND, 0, new float[] { 0, 10, 0, 10 }, 0);
		Application.g2d.setColor(Color.gray.brighter());
		Application.g2d.setStroke(dotted);
		for (int i = 1; i < hCount; i++) {
			Application.g2d.draw(new Line2D.Double(0, baseSize * i,
					Application.image.getWidth(), baseSize * i));
		}
		Application.shapes = Application.drawShapes(defaultStroke,
				Application.shapes, Application.currentSelectShape);
		// »­Ãªµã
		if (Application.anchor.getX() > -1) {
			Application.g2d.setColor(Color.red);
			Application.g2d.fill(new Ellipse2D.Double(
					Application.anchor.getX() - 3,
					Application.anchor.getY() - 3, 6, 6));
		}
		// »­Ñ¡Ôñ¿ò
		if (Application.selectBox != null) {
			Application.g2d.setColor(Color.green);
			Application.g2d.draw(Application.selectBox);
		}
	}

	public final void update(Graphics g) {
		this.paintComponent(g);
	}

	public BaseDataShape getShape() {
		return Application.currentSelectShape;
	}

	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
		imageWidth = Application.image.getWidth() * scale;
		imageHeight = Application.image.getHeight() * scale;
		this.setPreferredSize(new Dimension((int) imageWidth, (int) imageHeight));
	}

	public Rectangle2D getImageRect() {
		if (imagex < 0) {
			return this.getBounds();
		}
		imageBox.setRect(imagex, imagey, imageWidth, imageHeight);
		return imageBox;
	}
}
