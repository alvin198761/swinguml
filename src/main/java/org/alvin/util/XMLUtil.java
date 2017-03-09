package org.alvin.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.InvalidPropertiesFormatException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

public class XMLUtil {
	private XMLUtil() {
	}

	private static DocumentBuilderFactory factory = DocumentBuilderFactory
			.newInstance();

	/**
	 * 读取xml
	 * 
	 * @param xmlFile
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public static Document getDocument(File xmlFile) throws SAXException,
			IOException, ParserConfigurationException {
		return factory.newDocumentBuilder().parse(xmlFile);
	}

	/**
	 * 读取xml
	 * 
	 * @param is
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public static Document getDocument(InputStream xmlIs) throws SAXException,
			IOException, ParserConfigurationException {
		return factory.newDocumentBuilder().parse(xmlIs);
	}

	/**
	 * 创建xml
	 * 
	 * @return
	 * @throws ParserConfigurationException
	 */
	public static Document createNewDocument()
			throws ParserConfigurationException {
		return factory.newDocumentBuilder().newDocument();
	}

	/**
	 * 获取根节点
	 * 
	 * @param doc
	 * @return
	 */
	public static Element getRootElement(Document doc) {
		return doc.getDocumentElement();
	}

	/**
	 * 获取所有子节点
	 * 
	 * @param node
	 * @return
	 */
	public static List<Element> childs(Node node) {
		NodeList children = node.getChildNodes();
		List<Element> result = new LinkedList<Element>();
		for (int i = 0; i < children.getLength(); i++) {
			Node c = children.item(i);
			if (c.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}
			result.add((Element) c);
		}
		return result;
	}

	/**
	 * 写入xml
	 * 
	 * @param doc
	 * @param file
	 * @throws TransformerException
	 * @throws IOException
	 */
	public static void writeXml(Document doc, File file)
			throws TransformerException, IOException {
		TransformerFactory tr = TransformerFactory.newInstance();
		tr.setAttribute("indent-number", new Integer(4));// 设置缩进长度为4
		Transformer trs = tr.newTransformer();
		trs.setOutputProperty(OutputKeys.INDENT, "yes");// 设置自动换行
		DOMSource dom = new DOMSource(doc);
		StreamResult st = new StreamResult(file);
		trs.setOutputProperty(OutputKeys.ENCODING, "utf-8");
		trs.transform(dom, st);
	}

	public static void setText(Document doc, Element node, String text) {
		Text textNode = doc.createTextNode(text);
		node.appendChild(textNode);
	}

	/**
	 * 加载国际化
	 * 
	 * @param filePath
	 * @return
	 */
	public static Properties loadLanguage(String filePath) {
		LogUtil.info("读取国际化文件");
		Properties p = new Properties();
		File f = new File(filePath);
		InputStream is = null;
		try {
			is = new BufferedInputStream(new FileInputStream(f));
			p.loadFromXML(is);
		} catch (FileNotFoundException e) {
			p = new Properties();
			saveLanguage(filePath, p);
			LogUtil.info("如果没有国际化文件，就初始化一个！");
			e.printStackTrace();
		} catch (InvalidPropertiesFormatException e) {
			p = new Properties();
			e.printStackTrace();
		} catch (IOException e) {
			p = new Properties();
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return p;
	}

	/**
	 * 保存国际化
	 * 
	 * @param filePath
	 * @param map
	 */
	public static void saveLanguage(String filePath, Properties map) {
		LogUtil.info("保存国际化文件");
		OutputStream os = null;
		try {
			os = new BufferedOutputStream(new FileOutputStream(new File(
					filePath)));
			map.storeToXML(os, "smm", "utf-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				os.flush();
				os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws ParserConfigurationException,
			TransformerException, IOException {
		Document doc = createNewDocument();
		Element root = doc.createElement("root");
		doc.appendChild(root);
		for (int i = 0; i < 10; i++) {
			Element shapeElement = doc.createElement("shape");
			root.appendChild(shapeElement);
			Element cElement = doc.createElement("text");
			shapeElement.appendChild(cElement);
			setText(doc, cElement, "text" + i);
			cElement = doc.createElement("type");
			setText(doc, cElement, i + "");
			shapeElement.appendChild(cElement);
		}
		writeXml(doc, new File("e:\\test2.txt"));
	}
}
