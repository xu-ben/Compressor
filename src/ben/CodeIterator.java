/*
 * 文件名：		CodeIterator.java
 * 创建日期：	2013-7-24
 * 最近修改：	2013-7-24
 * 作者：		徐犇
 */
package ben;

/**
 * 密码迭代器
 * 
 * @author ben
 * 
 */
public class CodeIterator {
	private Integer num = 980000;

	public String nextCode() {
		if (num < 1000000) {
			num++;
			return num.toString();
		}
		return null;
	}

}
