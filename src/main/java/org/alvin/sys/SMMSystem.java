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

	// ϵͳ����
	public static String sysName = "SMM";
	// �汾��
	public static String version = "V-0.3";
	// ϵͳ����
	public static String author = "Alvin";
	// ��������
	public static String mail = "alvin198761@163.com";
	// ������ַ
	public static String url = "http://www.mgxd.com";
	// �����ĵ���·��
	public static String helpPath = "help.html";
	// ���ڵ��ļ�·��
	public static String about = "about.html";
	// �����ķ�������ip
	public static String serverIp = "127.0.0.1";
	// �������ʵĶ˿�
	public static String serverPort = "8761";
	// ϵͳ�Ŀ�ݼ�����
	public static String hotKeyConfig = "hotkey.xml";
	// ����˵�
	public static JMenu pluginMenu;
	// �����ϵͳ�ı���
	public static final String line = System.getProperty("line.separator");
	public static final String realPath = System.getProperty("user.dir");
	public static final String fileSeparator = System
			.getProperty("file.separator");
	// ϵͳ�����ļ�
	public static final String lang_dir = realPath.concat(fileSeparator)
			.concat("lang").concat(fileSeparator);
	public static final String conf_dir = realPath.concat(fileSeparator)
			.concat("conf").concat(fileSeparator);
	// ��ʱ�ļ�
	public static final String tmp_dir = System.getProperty("java.io.tmpdir");
	// ����ʱ��
	private static long startTime = System.currentTimeMillis();
	// ����������������
	public static final InstanceControl instance = new InstanceControl();
	// �ļ�ѡ���
	public static SMMFileChooser fileChooser;
	// �ļ�������
	private static final FileNameExtensionFilter filter = new FileNameExtensionFilter(
			"*.smm Files", "smm");
	// ϵͳ������
	public static DrawPaneClipboard drawPaneClipBoard;
	// ϵͳ�����ָ�
	public static UndoableEditManager undoManager;
	// �û��ϴδ��ļ���Ŀ¼����
	private static File openDir;
	// �û��ϴα����ļ���Ŀ¼����
	private static File saveDir;

	public static void initPath() {
		File f = new File(lang_dir);
		if (!f.exists()) {
			f.mkdirs();
			// ���������ļ�
			Language.createLanguageFile();
		}
		f = new File(conf_dir);
		if (!f.exists()) {
			f.mkdir();
			// ����Ĭ�������ļ�
			ConfigManager.createConfigFile();
		}
	}

	// ��������
	public static void saveConfig() {
		LogUtil.info("save config");
		Language.saveLanguage();
	}

	// ��������
	public static void loadConfig() {
		initPath();
		LogUtil.info("load config");
		ConfigManager.loadSystemConfig();
	}

	// ��ʼ��ϵͳ
	public static void start() {
		// ��������
		Language.loadLanguage();
		// �ж��Ƿ��Ѿ�����
		if (!isRunning()) {
			DialogUtil.promptError(Language.getValue("SMM is running"));
			System.gc();
			return;
		}
		// ��������
		final StartWindow s = new StartWindow();
		s.startSplash();

		// ����Ƥ��
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		s.putProcessString(Language.getValue("Setting LookAndFeel"));
		s.setValue(3);
		// ���ع��ʻ�
		s.putProcessString(Language.getValue("Load Language"));
		s.setValue(8);
		// ������Դ
		s.putProcessString(Language.getValue("Load Resource"));
		ResourceUtil.loadResource();
		s.setValue(15);
		// ��������
		s.putProcessString(Language.getValue("Load Resource"));
		loadConfig();
		s.setValue(22);
		// ���ز��
		loadPlugin();
		s.setValue(28);
		// ������Ϣ����
		Application.start(s);
		// �ļ�ѡ���
		fileChooser = new SMMFileChooser();
		closeFileChooser();
		// �Ƿ���Ҫ�����û��Ĳ���ϰ��
		userActionListener();
		new Thread(new Runnable() {
			public void run() {
				// �ж��Ƿ���Ҫ����
				update();
				// �Ƿ���Ҫ�������
				advertising();
			}
		}).start();
	}

	private static void loadPlugin() {
		LogUtil.info("���ز��");
	}

	// ���²�Ʒ�汾
	private static void update() {
		LogUtil.info("�Ƿ���Ҫ���²�Ʒ");
	}

	// ����
	private static void advertising() {
		LogUtil.info("�Ƿ���Ҫ����");
	}

	// �û�����ϰ�� ����ƺ�����Ҫ��������ֻ������·�ӣ�Ŀǰû������
	private static void userActionListener() {
		LogUtil.info("�Ƿ���Ҫ�����û�����ϰ�� ");
	}

	// ����ϵͳID
	public static String getCreateTimeId() {
		return Long.toHexString(startTime++) + "";
	}

	/**
	 * ���ļ�
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
	 * �����ļ�
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
	 * �ر��ļ�ѡ����
	 */
	public static void closeFileChooser() {
		fileChooser.setSelectedFile(null);
		fileChooser.updateUI();
		fileChooser.setFileFilter(filter);
	}

	/**
	 * �ı��������title
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
