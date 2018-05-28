package com.flyzebra.flydown.file;

import com.flyzebra.flydown.utils.TLog;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * 功能说明：
 * 
 * @author 作者：FlyZebra
 * @version 创建时间：2017年3月21日 上午11:22:34
 */
public class FileIO implements IFileIO {
	private RandomAccessFile mRandomAccessFile;

	public FileIO(String fileName) throws IOException {
		mRandomAccessFile = new RandomAccessFile(fileName, "rw");
	}

	@Override
	public synchronized void save(byte[] b, long start, int len) throws IOException {
		mRandomAccessFile.seek(start);
		mRandomAccessFile.write(b, 0, len);
	}

	@Override
	public void close()  {
		if (mRandomAccessFile != null) {
			try {
				mRandomAccessFile.close();
			} catch (IOException e) {
				TLog.e(e.toString());
				e.printStackTrace();
			}
			mRandomAccessFile = null;
		}
	}

	@Override
	public String readAll() {
		String readStr = null;
		try {
			byte b[] = new byte[1024];
			int byteread = 0;
			StringBuilder stringBuilder = new StringBuilder();
			while ((byteread = mRandomAccessFile.read(b)) != -1) {
				String s = new String(b,0,byteread);
				stringBuilder.append(s);
			}
			readStr = stringBuilder.toString();
		} catch (Exception e) {
			TLog.d("Reading File onFailed! %s", e.toString());
			e.printStackTrace();
		}
		return readStr;
	}

	@Override
	public void save(FileBlock fileBlock) throws IOException {
		byte t[] = fileBlock.toString().getBytes();
		save(t, fileBlock.getOrder()*t.length, t.length);
	}

}
