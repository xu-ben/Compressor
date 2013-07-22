/*
 * 文件名：		MyGZip.java
 * 创建日期：	2013-7-22
 * 最近修改：	2013-7-22
 * 作者：		徐犇
 */
package ben;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

/**
 * 
 * @author ben
 *
 */
public final class MyGZip extends MyArchiver {

	@Override
	public final void doArchiver(File[] files, String destpath) throws IOException {
		// TODO Auto-generated method stub
	}

	@Override
	public final void doUnArchiver(File srcfile, String destpath, String password)
			throws IOException {
		FileInputStream fis = new FileInputStream(srcfile);
		GZIPInputStream gzis = new GZIPInputStream(fis);

		File tf = new File(destpath);
		FileOutputStream fos = new FileOutputStream(tf);
		BufferedOutputStream bos = new BufferedOutputStream(fos);

		byte[] buf = new byte[1024];

		int len;
		while ((len = gzis.read(buf)) > 0) {
			bos.write(buf, 0, len);
		}

		bos.flush();
		bos.close();

		gzis.close();
	}

}
