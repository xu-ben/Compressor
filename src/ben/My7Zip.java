/*
 * 文件名：		My7Zip.java
 * 创建日期：	2013-7-24
 * 最近修改：	2013-7-24
 * 作者：		徐犇
 */
package ben;

import java.io.File;
import java.io.IOException;
import javax.swing.filechooser.FileNameExtensionFilter;


/**
 * @author Administrator
 *
 */
public final class My7Zip extends Archiver {

	private FileNameExtensionFilter filter = new FileNameExtensionFilter(
			"7-ZIP打包文件(*.7z)", "7z");


	@Override
	public final void doArchiver(File[] files, String destpath)
			throws IOException {
	}

	@Override
	public void doUnArchiver(File srcfile, String destpath, String password)
			throws IOException, WrongPassException {
	}

	@Override
	public final FileNameExtensionFilter getFileFilter() {
		return this.filter;
	}

}
