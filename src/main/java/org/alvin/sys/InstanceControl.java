package org.alvin.sys;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.channels.FileLock;

/**
 * Ӧ��ʵ��������
 * 
 * @author ��ֲ��
 * 
 */
public class InstanceControl {

	// �жϸ�Ӧ���Ƿ�������
	public boolean isRunning() {
		try {
			// ����ļ���
			FileLock lock = new FileOutputStream(new File(
					SMMSystem.tmp_dir.concat("smm"))).getChannel().tryLock();
			// ���ؿձ�ʾ�ļ��ѱ����е�ʵ������
			if (lock == null)
				return false;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return true;
	}
}