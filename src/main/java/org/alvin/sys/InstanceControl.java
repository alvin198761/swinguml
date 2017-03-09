package org.alvin.sys;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.channels.FileLock;

/**
 * 应用实例控制类
 * 
 * @author 唐植超
 * 
 */
public class InstanceControl {

	// 判断该应用是否已启动
	public boolean isRunning() {
		try {
			// 获得文件锁
			FileLock lock = new FileOutputStream(new File(
					SMMSystem.tmp_dir.concat("smm"))).getChannel().tryLock();
			// 返回空表示文件已被运行的实例锁定
			if (lock == null)
				return false;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return true;
	}
}