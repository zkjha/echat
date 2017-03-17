package com.ecard.util;

/**
 * 控制台输出类
 * @author zhengwei
 *
 */
public class PrintUtil {
	private static final boolean IS_PRINT = true;
	
	/**
	 * 换行打印
	 */
	public static void println(Object o) {
		if(IS_PRINT) {
			System.out.println(o);
		}
	}
	
	/**
	 * 不换行打印
	 */
	public static void print(Object o) {
		if(IS_PRINT) {
			System.out.print(o);
		}
	}
}
