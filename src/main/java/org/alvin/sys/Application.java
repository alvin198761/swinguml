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
 * 应用级别的类
 * 
 * @author 唐植超
 * 
 */
public class Application {

	private Application() {

	}

	// 操作类型
	public static final String OPER_NONE = "none";
	public static final String OPER_DRAWLINE = "drawline";
	public static final String OPER_DRAWSELECTBOX = "drawselectbox";
	public static final String OPER_DRAGSHAPE = "drag";
	public static final String OPER_RESIZE = "resize";
	public static final String OPER_SELECTALL = "selectAll";
	// 图形拖动管理
	public static ShapeDragManager dragManager;
	// 当前选中的图形
	public static BaseDataShape currentSelectShape;
	// 当前要操作的控制点
	public static CtrlResizeShape currentResizeShape;
	// 操作的所有图形
	public static LinkedList<BaseDataShape> shapes = new LinkedList<BaseDataShape>();
	// 当前操作的类型
	public static String operStatus = OPER_NONE;
	// 是否保存
	public static boolean saveed = true;
	// 保存文件的路径
	public static File saveFile;
	// 住窗体控件
	public static JFrame mainFrame;
	// 画图板
	public static DrawPane drawPanel;
	// 工具栏
	public static JPanel toolbarBox;
	// 左边的工具箱
	public static JTitlePanel shapePanel;
	// 底部的状态条
	public static Box statusBox;
	// 上面的菜单条
	public static JMenuBar menuBar;
	// 主操作面板
	public static JSplitPane mainSplitPanel;
	// 中心的面板
	public static JPanel contentPane;
	// 打开时，默认面板
	public static JPanel defaultPanel;
	// 保存类型
	public static String saveType;
	// 锚点
	public static Point2D anchor = new Point2D.Double(-1, -1);
	// 选择框
	public static Rectangle2D selectBox = new Rectangle2D.Double(-1, -1, -1, -1);
	// 内存图片
	public static BufferedImage image = new BufferedImage(600, 600,
			BufferedImage.TYPE_3BYTE_BGR);
	// 画图对象
	public static Graphics2D g2d = image.createGraphics();
	// 比率调整的选择框
	public static ScaleCombbox scaleCombox;
	// 画图处理
	public static BaseDrawPanelBiz drawPanelBiz;
	// 拖动处理
	public static BaseDragBiz dragBiz;
	// 图形生产的抽象工厂
	public static AbstractShapeFactory shapeFactory;

	// 生成唯一ID
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
		// 画图的排序
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
		// 开始画图
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
		// 实例化画板
		s.putProcessString(Language.getValue("Init Drawpanel"));
		Application.drawPanel = new DrawPane();
		// 加载剪贴板和撤销恢复
		s.putProcessString(Language.getValue("Init ClipBoard"));
		SMMSystem.drawPaneClipBoard = new DrawPaneClipboard();
		s.putProcessString(Language.getValue("Init Undo Redo manager"));
		SMMSystem.undoManager = new UndoableEditManager();
		s.setValue(35);
		// 实例化窗体
		s.putProcessString(Language.getValue("Init Frame"));
		Application.mainFrame = new MainFrame();
		s.setValue(40);
		// 实例化工具条
		s.putProcessString(Language.getValue("Init Toolbar"));
		ToolbarManager.createToolbar();
		// 实例化状态条
		s.putProcessString(Language.getValue("Init StatusBar"));
		StatusbarManager.createStatusBar();
		s.setValue(55);
		// 实例化左边的菜单
		s.putProcessString(Language.getValue("Init Shape panel"));
		Application.dragManager = new ShapeDragManager(Application.mainFrame,
				Application.drawPanel);
		Application.shapePanel = new JTitlePanel();
		s.setValue(70);
		// 实例化菜单
		s.putProcessString(Language.getValue("Init Action"));
		MenuBarManager.createMenuBar();
		ActionManager.createPopup();
		s.setValue(85);
		// 界面信息实例化
		s.putProcessString(Language.getValue("Init GUI"));
		CenterPanelManager.createContentPanel();
		((MainFrame) Application.mainFrame).initGui();
		s.setValue(100);
		s.stopSplash();
		Application.mainFrame.setVisible(true);
		s = null;
		LogUtil.info("界面启动成功");

		Application.mainFrame.addWindowListener(new WindowAdapter() {
			
			public void windowClosing(WindowEvent e) {
				ActionManager.getAction(ExitAction.class).actionPerformed(null);
			}
		});
	}
}
