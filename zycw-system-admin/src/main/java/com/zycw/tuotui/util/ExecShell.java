package com.zycw.tuotui.util;

/**
 * shell脚本工具
 * @author guangliangwen
 *
 */
public class ExecShell {

	/**
	 * 执行shell mv
	 * 异常 直接向上抛
	 * @param fromDir
	 * @param endDir
	 * @return
	 */
	public static void execMv(String fromDir, String endDir) throws Exception {
		// linux中要执行的代码 "sh generator.sh"
		String shellStr = "mv -f " + fromDir + " " + endDir;
		Runtime.getRuntime().exec(shellStr);
	}
}
