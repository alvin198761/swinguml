package org.alvin.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.alvin.bean.shape.data.BaseDataShape;
import org.alvin.bean.shape.data.BaseLineShape;
import org.alvin.sys.Application;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 解析本程序生成的文件，和将本程序中的图保存成文件
 * 
 * @author 唐植超
 * 
 */
public class SystemFileUtil {

	private SystemFileUtil() {
	}

	public static void analyzerFile(File file) throws Exception {
		InputStream is = null;
		try {
			Map<String, BaseDataShape> map = new HashMap<String, BaseDataShape>();
			is = new GZIPInputStream(new BufferedInputStream(
					new FileInputStream(file)));
			Document doc = XMLUtil.getDocument(is);
			Element root = XMLUtil.getRootElement(doc);
			Node node = root.getFirstChild();
			Application.saveType = node.getTextContent();
			try {
				Application.shapePanel.changeType(Application.saveType);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			NodeList list = root.getElementsByTagName("shape");
			BaseDataShape shape;
			for (int i = 0; i < list.getLength(); i++) {
				node = list.item(i);
				shape = Application.shapeFactory.toShape(node, map);
				Application.addShape(shape);
				shape.draw(Application.g2d);
				Application.repaint();
			}
		} finally {
			is.close();
		}
	}

	/**
	 * 压缩后保存
	 * 
	 * @param shapes
	 * @param file
	 * @return
	 */
	public static boolean saveFile(List<BaseDataShape> shapes, File file) {
		StringBuilder data = new StringBuilder();
		StringBuilder line = new StringBuilder();
		data.append("<type>" + Application.saveType + "</type>");
		// 将线的内容写在后面
		for (BaseDataShape shape : shapes) {
			if (shape instanceof BaseLineShape) {
				line.append(shape.toData());
				continue;
			}
			data.append(shape.toData());
		}
		data.append(line);
		try {
			zip(file, "<shapes>" + data.toString().trim() + "</shapes>");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 压缩并保存文件
	 * 
	 * @param file
	 * @param data
	 * @throws Exception
	 */
	public static void zip(File file, String data) throws Exception {
		Writer out = null;
		try {
			out = new OutputStreamWriter(new BufferedOutputStream(
					new GZIPOutputStream(new FileOutputStream(file))), "utf-8");
			out.write(data);
		} finally {
			out.flush();
			out.close();
		}
	}

	/**
	 * 解压
	 * 
	 * @param file
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static byte[] unZip(File file) throws FileNotFoundException,
			IOException {
		InputStream is = null;
		try {
			is = new GZIPInputStream(new BufferedInputStream(
					new FileInputStream(file)));

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int i;
			while ((i = is.read()) != -1) {
				baos.write(i);
			}
			byte[] buff = baos.toByteArray();
			baos.flush();
			baos.close();
			return buff;
		} finally {
			is.close();
		}
	}

	public static void main(String[] args) {
		// System.out.println(Long.toHexString(1348120672478l));
		System.out.println(Long.parseLong("139e240f8de", 16));
	}
}
