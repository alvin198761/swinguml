package org.alvin.sys;

import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.alvin.bean.shape.BaseShape;
import org.alvin.bean.shape.ctrl.CtrlResizeShape;
import org.alvin.bean.shape.data.BaseDataShape;
import org.alvin.bean.shape.data.BaseLineShape;
import org.alvin.biz.BaseDragBiz;
import org.alvin.biz.BaseDrawPanelBiz;
import org.alvin.factory.AbstractShapeFactory;
import org.alvin.gui.action.file.ExitAction;
import org.alvin.gui.comp.JTitlePanel;
import org.alvin.gui.comp.ScaleCombbox;
import org.alvin.gui.drag.ShapeDragManager;
import org.alvin.gui.drawpane.DrawPane;
import org.alvin.gui.drawpane.DrawPaneClipboard;
import org.alvin.gui.drawpane.UndoableEditManager;
import org.alvin.gui.frame.CenterPanelManager;
import org.alvin.gui.frame.MainFrame;
import org.alvin.gui.frame.MenuBarManager;
import org.alvin.gui.frame.StartWindow;
import org.alvin.gui.frame.StatusbarManager;
import org.alvin.gui.frame.ToolbarManager;
import org.alvin.util.LogUtil;

/**
 * Ӧ�ü������
 * 
 * @author ��ֲ��
 * 
 */
public class Application {

	private Application() {

	}

	// ��������
	public static final String OPER_NONE = "none";
	public static final String OPER_DRAWLINE = "drawline";
	public static final String OPER_DRAWSELECTBOX = "drawselectbox";
	public static final String OPER_DRAGSHAPE = "drag";
	public static final String OPER_RESIZE = "resize";
	public static final String OPER_SELECTALL = "selectAll";
	// ͼ���϶�����
	public static ShapeDragManager dragManager;
	// ��ǰѡ�е�ͼ��
	public static BaseDataShape currentSelectShape;
	// ��ǰҪ�����Ŀ��Ƶ�
	public static CtrlResizeShape currentResizeShape;
	// ����������ͼ��
	public static LinkedList<BaseDataShape> shapes = new LinkedList<BaseDataShape>();
	// ��ǰ����������
	public static String operStatus = OPER_NONE;
	// �Ƿ񱣴�
	public static boolean saveed = true;
	// �����ļ���·��
	public static File saveFile;
	// ס����ؼ�
	public static JFrame mainFrame;
	// ��ͼ��
	public static DrawPane drawPanel;
	// ������
	public static JPanel toolbarBox;
	// ��ߵĹ�����
	public static JTitlePanel shapePanel;
	// �ײ���״̬��
	public static Box statusBox;
	// ����Ĳ˵���
	public static JMenuBar menuBar;
	// ���������
	public static JSplitPane mainSplitPanel;
	// ���ĵ����
	public static JPanel contentPane;
	// ��ʱ��Ĭ�����
	public static JPanel defaultPanel;
	// ��������
	public static String saveType;
	// ê��
	public static Point2D anchor = new Point2D.Double(-1, -1);
	// ѡ���
	public static Rectangle2D selectBox = new Rectangle2D.Double(-1, -1, -1, -1);
	// �ڴ�ͼƬ
	public static BufferedImage image = new BufferedImage(600, 600,
			BufferedImage.TYPE_3BYTE_BGR);
	// ��ͼ����
	public static Graphics2D g2d = image.createGraphics();
	// ���ʵ�����ѡ���
	public static ScaleCombbox scaleCombox;
	// ��ͼ����
	public static BaseDrawPanelBiz drawPanelBiz;
	// �϶�����
	public static BaseDragBiz dragBiz;
	// ͼ�������ĳ��󹤳�
	public static AbstractShapeFactory shapeFactory;

	// ����ΨһID
	public static synchronized String getTimeId() {
		return SMMSystem.getCreateTimeId();
	}

	public static void addShape(BaseDataShape shape) {
		shapes.add(shape);
		ActionManager.firePropertyChangeEditAction();
	}

	public static void removeAllShapes() {
		shapes.clear();
		currentSelectShape = null;
		ActionManager.firePropertyChangeEditAction();
	}

	public static void removeSelectShape() {
		for (int i = shapes.size() - 1; i >= 0; i--) {
			BaseDataShape shape = shapes.get(i);
			if (!shape.isSelect()) {
				continue;
			}
			if (shape instanceof BaseLineShape) {
				BaseLineShape line = ((BaseLineShape) shape);
				Application.shapes.remove(line);
				if (line.getBeginShape() != null) {
					line.getBeginShape().removeLine(line);
				}
				if (line.getEndShape() != null) {
					line.getEndShape().removeLine(line);
				}
				continue;
			}
			Application.shapes.remove(i);
			List<BaseLineShape> lines = shape.getAllLines();
			for (BaseLineShape line : lines) {
				Application.shapes.remove(line);
				if (line.getBeginShape() != null) {
					line.getBeginShape().removeLine(line);
				}
				if (line.getEndShape() != null) {
					line.getEndShape().removeLine(line);
				}
			}
		}
		Application.currentSelectShape = null;
		drawPanel.reDraw();
		drawPanel.repaint();
	}

	public static boolean isSelectAll() {
		for (int i = shapes.size() - 1; i >= 0; i--) {
			BaseDataShape shape = shapes.get(i);
			if (!shape.isSelect()) {
				return false;
			}
		}
		return true;
	}

	public static List<BaseDataShape> getAllCopyShape() {
		List<BaseDataShape> list = new LinkedList<BaseDataShape>();
		for (BaseDataShape shape : shapes) {
			if (!shape.canCopy()) {
				continue;
			}
			list.add(shape);
		}
		return list;
	}

	public static void clearSelect() {
		Iterator<BaseDataShape> list = shapes.iterator();
		BaseShape shape = null;
		while (list.hasNext()) {
			shape = list.next();
			shape.setSelect(false);
		}
		currentSelectShape = null;
		currentResizeShape = null;
	}

	public static LinkedList<BaseDataShape> drawShapes(Stroke defaultStroke,
			List<BaseDataShape> shapes, BaseDataShape currShape) {
		// ��ͼ������
		LinkedList<BaseDataShape> tempList = new LinkedList<BaseDataShape>();
		for (BaseDataShape shape : shapes) {
			if (shape.isSubSystem()) {
				tempList.add(shape);
			}
		}
		for (BaseDataShape shape : shapes) {
			if (!shape.isSubSystem() && !(shape instanceof BaseLineShape)) {
				tempList.add(shape);
			}
		}
		if (currShape != null && !currShape.isSubSystem()
				&& !(currShape instanceof BaseLineShape)) {
			tempList.remove(currShape);
			tempList.add(currShape);
		}
		for (BaseDataShape shape : shapes) {
			if (shape instanceof BaseLineShape) {
				tempList.add(shape);
			}
		}
		// ��ʼ��ͼ
		Application.g2d.setStroke(defaultStroke);
		for (BaseDataShape shape : shapes) {
			shape.draw(Application.g2d);
		}
		return tempList;
	}

	public static void addDraw(List<BaseDataShape> list) {
		for (BaseDataShape shape : list) {
			shape.setSelect(true);
		}
		shapes.addAll(drawShapes(g2d.getStroke(), list, null));
		drawPanel.repaint();
	}

	public static void repaint() {
		drawPanel.reDraw();
		drawPanel.repaint();
	}

	public static void selectAllShape() {
		for (int i = Application.shapes.size() - 1; i >= 0; i--) {
			BaseDataShape shape = Application.shapes.get(i);
			shape.setSelect(true);
		}
		Application.currentSelectShape = null;
		repaint();
	}

	public static void start(StartWindow s) {
		// ʵ��������
		s.putProcessString(Language.getValue("Init Drawpanel"));
		Application.drawPanel = new DrawPane();
		// ���ؼ�����ͳ����ָ�
		s.putProcessString(Language.getValue("Init ClipBoard"));
		SMMSystem.drawPaneClipBoard = new DrawPaneClipboard();
		s.putProcessString(Language.getValue("Init Undo Redo manager"));
		SMMSystem.undoManager = new UndoableEditManager();
		s.setValue(35);
		// ʵ��������
		s.putProcessString(Language.getValue("Init Frame"));
		Application.mainFrame = new MainFrame();
		s.setValue(40);
		// ʵ����������
		s.putProcessString(Language.getValue("Init Toolbar"));
		ToolbarManager.createToolbar();
		// ʵ����״̬��
		s.putProcessString(Language.getValue("Init StatusBar"));
		StatusbarManager.createStatusBar();
		s.setValue(55);
		// ʵ������ߵĲ˵�
		s.putProcessString(Language.getValue("Init Shape panel"));
		Application.dragManager = new ShapeDragManager(Application.mainFrame,
				Application.drawPanel);
		Application.shapePanel = new JTitlePanel();
		s.setValue(70);
		// ʵ�����˵�
		s.putProcessString(Language.getValue("Init Action"));
		MenuBarManager.createMenuBar();
		ActionManager.createPopup();
		s.setValue(85);
		// ������Ϣʵ����
		s.putProcessString(Language.getValue("Init GUI"));
		CenterPanelManager.createContentPanel();
		((MainFrame) Application.mainFrame).initGui();
		s.setValue(100);
		s.stopSplash();
		Application.mainFrame.setVisible(true);
		s = null;
		LogUtil.info("���������ɹ�");

		Application.mainFrame.addWindowListener(new WindowAdapter() {
			
			public void windowClosing(WindowEvent e) {
				ActionManager.getAction(ExitAction.class).actionPerformed(null);
			}
		});
	}
}
