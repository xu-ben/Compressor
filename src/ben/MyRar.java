/*
 * 文件名：		MyRar.java
 * 创建日期：	2013-7-22
 * 最近修改：	2013-7-22
 * 作者：		徐犇
 */
package ben;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.filechooser.FileNameExtensionFilter;

import de.innosystec.unrar.Archive;
import de.innosystec.unrar.exception.RarException;
import de.innosystec.unrar.rarfile.FileHeader;

/**
 * @author ben
 *
 */
public final class MyRar extends Archiver {

	private FileNameExtensionFilter filter = new FileNameExtensionFilter(
			"RAR压缩文件(*.rar)", "rar");
	
	@Override
	public final void doArchiver(File[] files, String destpath) throws IOException {
	}

	@Override
	public final void doUnArchiver(File srcfile, String destpath, String password)
			throws IOException, RarException {
		Archive a = new Archive(srcfile, password, false);
		FileHeader fh; ;
		while ((fh = a.nextFileHeader()) != null) {
			File f = new File(destpath + "/" + fh.getFileNameString().trim());
			
			if (fh.isDirectory()) {
				f.mkdirs();
				continue;
			}
			
			/*
			 * 父目录不存在则创建
			 */
			File parent = f.getParentFile();
			if (!parent.exists()) {
				parent.mkdirs();
			}

			FileOutputStream fos = new FileOutputStream(f);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			
			a.extractFile(fh, bos);

			bos.flush();
			bos.close();
		}
		a.close();
	}

	@Override
	public final FileNameExtensionFilter getFileFilter() {
		return this.filter;
	}
}
