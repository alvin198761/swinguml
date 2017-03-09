package org.alvin.util;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.alvin.sys.Application;
import org.alvin.sys.SMMSystem;

public class ObjectUtil {

	private ObjectUtil() {
	}

	public static Object cloneObject(Serializable o) throws Exception {
		File f = new File(SMMSystem.tmp_dir.concat(Application.getTimeId()));
		writeObj(o, f);
		Object obj = readObj(f);
		f.delete();
		return obj;
	}

	public static void writeObjectToXML(Serializable obj, File xmlFile)
			throws FileNotFoundException {
		XMLEncoder e = new XMLEncoder(new BufferedOutputStream(
				new FileOutputStream(xmlFile)));
		e.writeObject(obj);
		e.close();
	}

	public static Object readObjectFromXML(File xmlFile)
			throws FileNotFoundException {
		XMLDecoder d = new XMLDecoder(new BufferedInputStream(
				new FileInputStream(xmlFile)));
		Object result = d.readObject();
		d.close();
		return result;
	}

	public static void writeObj(Object obj, File file) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(
				new BufferedOutputStream(new FileOutputStream(file)));
		oos.writeObject(obj);
		oos.flush();
		oos.close();
	}

	public static Object readObj(File file) throws Exception {
		ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(
				new FileInputStream(file)));
		Object obj = ois.readObject();
		ois.close();
		return obj;
	}
}
