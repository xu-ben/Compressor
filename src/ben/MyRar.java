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
import de.innosystec.unrar.rarfile.MainHeader;

/**
 * @author ben
 * 
 */
public final class MyRar extends Archiver {

	private FileNameExtensionFilter filter = new FileNameExtensionFilter(
			"RAR压缩文件(*.rar)", "rar");

	@Override
	public final void doArchiver(File[] files, String destpath)
			throws IOException {
	}
	
	private boolean crack(Archive ar, String pass, File tmpf) {
		BufferedOutputStream bos = null;
		try {
			ar.setPassword(pass);
			FileHeader fh = ar.nextFileHeader();
			FileOutputStream fos = new FileOutputStream(tmpf);
			bos = new BufferedOutputStream(fos);
			ar.extractFile(fh, bos);
			bos.flush();
			bos.close();
		} catch (RarException e) {
			try {
				bos.flush();
				bos.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return false;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public final String crackRar(File srcfile, String tmpdir, CodeIterator ci) {
		//临时文件
		File tmpf = new File(".~tmpf");
		String pass = null;
		String ret = null;
		Archive ar = new Archive(srcfile);
		while((pass = ci.nextCode()) != null) {
			if(crack(ar, pass, tmpf)) {
				ret = pass;
				break;
			}
		}
		try {
			ar.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		tmpf.delete();//使用完毕，删除
		return ret;
	}

	@Override
	public final void doUnArchiver(File srcfile, String destpath,
			String password) throws IOException, WrongPassException {
		try {
			Archive a = new Archive(srcfile, password, false);
			FileHeader fh;
			while ((fh = a.nextFileHeader()) != null) {
				File f = new File(destpath + "/"
						+ fh.getFileNameString().trim());

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
		} catch (RarException e) {
			throw new WrongPassException();
		}
	}

	@Override
	public final FileNameExtensionFilter getFileFilter() {
		return this.filter;
	}
}

/**
 * 继承Archive，以便对其进行修改以增加功能
 * @author ben
 *
 */
class MyArchive extends Archive {
	public MyArchive(File file) throws RarException, IOException {
		super(file, null, false);
	}
	
	public void updatePass(String password) {
		
	}
	
}