package org.alvin.sys;

import static org.alvin.sys.Application.saveFile;

import java.awt.Component;
import java.io.File;

import javax.swing.JMenu;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.alvin.gui.comp.SMMFileChooser;
import org.alvin.gui.drawpane.DrawPaneClipboard;
import org.alvin.gui.drawpane.UndoableEditManager;
import org.alvin.gui.frame.StartWindow;
import org.alvin.resource.ResourceUtil;
import org.alvin.util.DialogUtil;
import org.alvin.util.LogUtil;

public class SMMSystem {

	private SMMSystem() {
	}

	// 系统名称
	public static String sysName = "SMM";
	// 版本号
	public static String version = "V-0.3";
	// 系统作者
	public static String author = "Alvin";
	// 作者邮箱
	public static String mail = "alvin198761@163.com";
	// 官网地址
	public static String url = "http://www.mgxd.com";
	// 帮助文档的路径
	public static String helpPath = "help.html";
	// 关于的文件路径
	public static String about = "about.html";
	// 升级的服务器的ip
	public static String serverIp = "127.0.0.1";
	// 升级访问的端口
	public static String serverPort = "8761";
	// 系统的快捷键配置
	public static String hotKeyConfig = "hotkey.xml";
	// 插件菜单
	public static JMenu pluginMenu;
	// 跨操作系统的变量
	public static final String line = System.getProperty("line.separator");
	public static final String realPath = System.getProperty("user.dir");
	public static final String fileSeparator = System
			.getProperty("file.separator");
	// 系统配置文件
	public static final String lang_dir = realPath.concat(fileSeparator)
			.concat("lang").concat(fileSeparator);
	public static final String conf_dir = realPath.concat(fileSeparator)
			.concat("conf").concat(fileSeparator);
	// 临时文件
	public static final String tmp_dir = System.getProperty("java.io.tmpdir");
	// 启动时间
	private static long startTime = System.currentTimeMillis();
	// 虚拟机启动对象控制
	public static final InstanceControl instance = new InstanceControl();
	// 文件选择框
	public static SMMFileChooser fileChooser;
	// 文件过滤器
	private static final FileNameExtensionFilter filter = new FileNameExtensionFilter(
			"*.smm Files", "smm");
	// 系统剪贴板
	public static DrawPaneClipboard drawPaneClipBoard;
	// 系统撤销恢复
	public static UndoableEditManager undoManager;
	// 用户上次打开文件的目录所在
	private static File openDir;
	// 用户上次保存文件的目录所在
	private static File saveDir;

	public static void initPath() {
		File f = new File(lang_dir);
		if (!f.exists()) {
			f.mkdirs();
			// 创建语言文件
			Language.createLanguageFile();
		}
		f = new File(conf_dir);
		if (!f.exists()) {
			f.mkdir();
			// 创建默认配置文件
			ConfigManager.createConfigFile();
		}
	}

	// 保存配置
	public static void saveConfig() {
		LogUtil.info("save config");
		Language.saveLanguage();
	}

	// 加载配置
	public static void loadConfig() {
		initPath();
		LogUtil.info("load config");
		ConfigManager.loadSystemConfig();
	}

	// 初始化系统
	public static void start() {
		// 加载语言
		Language.loadLanguage();
		// 判断是否已经启动
		if (!isRunning()) {
			DialogUtil.promptError(Language.getValue("SMM is running"));
			System.gc();
			return;
		}
		// 启动窗体
		final StartWindow s = new StartWindow();
		s.startSplash();

		// 设置皮肤
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		s.putProcessString(Language.getValue("Setting LookAndFeel"));
		s.setValue(3);
		// 加载国际化
		s.putProcessString(Language.getValue("Load Language"));
		s.setValue(8);
		// 加载资源
		s.putProcessString(Language.getValue("Load Resource"));
		ResourceUtil.loadResource();
		s.setValue(15);
		// 加载配置
		s.putProcessString(Language.getValue("Load Resource"));
		loadConfig();
		s.setValue(22);
		// 加载插件
		loadPlugin();
		s.setValue(28);
		// 程序信息加载
		Application.start(s);
		// 文件选择框
		fileChooser = new SMMFileChooser();
		closeFileChooser();
		// 是否需要监听用户的操作习惯
		userActionListener();
		new Thread(new Runnable() {
			public void run() {
				// 判断是否需要升级
				update();
				// 是否需要弹出广告
				advertising();
			}
		}).start();
	}

	private static void loadPlugin() {
		LogUtil.info("加载插件");
	}

	// 更新产品版本
	private static void update() {
		LogUtil.info("是否需要更新产品");
	}

	// 打广告
	private static void advertising() {
		LogUtil.info("是否需要打广告");
	}

	// 用户操作习惯 这个似乎很重要，我这里只是留好路子，目前没能力搞
	private static void userActionListener() {
		LogUtil.info("是否需要监听用户操作习惯 ");
	}

	// 产生系统ID
	public static String getCreateTimeId() {
		return Long.toHexString(startTime++) + "";
	}

	/**
	 * 打开文件
	 * 
	 * @return
	 */
	public static File openFileChooser(Component c) {
		if (openDir != null) {
			fileChooser.setCurrentDirectory(openDir);
		}
		fileChooser.showOpenDialog(c);
		File f = fileChooser.getSelectedFile();
		if (f != null) {
			openDir = f.getParentFile();
		}
		return f;
	}

	/**
	 * 保存文件
	 * 
	 * @return
	 */
	public static File saveFileChooser(Component c) {
		if (saveDir != null) {
			fileChooser.setCurrentDirectory(saveDir);
		}
		fileChooser.showSaveDialog(c);
		File f = fileChooser.getSelectedFile();
		if (f != null) {
			saveDir = f.getParentFile();
			String path = f.getAbsolutePath();
			if (!path.toLowerCase().endsWith(".smm")) {
				path = path.concat(".smm");
			}
			f = new File(path);
		}
		return f;
	}

	/**
	 * 关闭文件选择器
	 */
	public static void closeFileChooser() {
		fileChooser.setSelectedFile(null);
		fileChooser.updateUI();
		fileChooser.setFileFilter(filter);
	}

	/**
	 * 改变主窗体的title
	 */
	public static void chanageFrameTitle() {
		if (saveFile == null) {
			Application.mainFrame.setTitle(SMMSystem.sysName + "--"
					+ SMMSystem.version);
			return;
		}
		Application.mainFrame.setTitle(saveFile.getAbsolutePath() + " "
				+ SMMSystem.sysName + "--" + SMMSystem.version);
	}

	public static boolean isRunning() {
		return instance.isRunning();
	}

}
