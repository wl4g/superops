/*
 * Copyright 2017 ~ 2025 the original author or authors. <wanglsir@gmail.com, 983708408@qq.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wl4g.devops.ci.utils;

import com.wl4g.devops.common.bean.ci.dto.TaskResult;
import com.wl4g.devops.common.utils.io.FileIOUtils;
import com.wl4g.devops.shell.utils.ShellContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.function.Function;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Shell utility tools.
 *
 * @author Wangl.sir <983708408@qq.com>
 * @version v1.0 2019年5月24日
 * @since
 */
public abstract class CommandUtils {
	final private static Logger log = LoggerFactory.getLogger(CommandUtils.class);

	/**
	 * Execute commands in local
	 */
	public static String exec(String cmd) throws Exception {
		return exec(cmd, null);
	}

	public static String exec(String cmd, TaskResult taskResult) throws Exception {
		return exec(cmd, null, taskResult);
	}

	public static String exec(String cmd, Function<String, Boolean> callback, TaskResult taskResult) throws Exception {
		return exec(cmd, callback, taskResult, null);
	}

	public static String exec(String cmd, Function<String, Boolean> callback, TaskResult taskResult, String dirPath)
			throws Exception {
		if (log.isInfoEnabled()) {
			log.info("Execution native command for '{}'", cmd);
		}
		// TODO filter command

		StringBuilder slog = new StringBuilder();
		StringBuilder serr = new StringBuilder();
		Process ps;
		if (StringUtils.isBlank(dirPath)) {
			ps = Runtime.getRuntime().exec(new String[] { "/bin/bash", "-c", cmd });
		} else {
			ps = Runtime.getRuntime().exec(new String[] { "/bin/bash", "-c", cmd }, null, new File(dirPath));
		}

		try (BufferedReader blog = new BufferedReader(new InputStreamReader(ps.getInputStream()));
				BufferedReader berr = new BufferedReader(new InputStreamReader(ps.getErrorStream()))) {
			String inlog;
			while ((inlog = blog.readLine()) != null) {
				if (callback != null) {
					if (!callback.apply(inlog)) {
						throw new InterruptedException("Commands force interrupted!");
					}
				}
				slog.append(inlog).append("\n");
				writeResult(taskResult, inlog + "\n");
				log.info(inlog);
				ShellContextHolder.printfQuietly(inlog);
			}
			while ((inlog = berr.readLine()) != null) {
				serr.append(inlog).append("\n");
				writeResult(taskResult, inlog + "\n");
				log.info(inlog);
				ShellContextHolder.printfQuietly(inlog);
			}

			ps.waitFor();// wait for process exit , or maybe throw
			// java.lang.IllegalThreadStateException: process
			// hasn't exited
			int exitValue = ps.exitValue();
			if (exitValue != 0 && taskResult != null) {
				taskResult.setSuccess(false);
			}
			String log = slog.toString();
			String err = serr.toString();
			if (isNotBlank(err)) {
				log += err;
				throw new RuntimeException("Exec command fail,command=" + cmd + "\n cause:" + log.toString());
			}
			return log;
		}
	}

	private static void writeResult(TaskResult taskResult, String result) {
		if (null == taskResult || taskResult.getLogFile() == null) {
			return;
		}
		File logFile = taskResult.getLogFile();
		FileIOUtils.writeFile(logFile, result);
	}

	public static String execFile(String cmd, String filePath, TaskResult taskResult) throws Exception {
		File file = new File(filePath);
		FileIOUtils.writeFile(file, cmd, false);
		return exec("sh " + filePath, null, taskResult);
	}

	public static String execFile(String cmd, Function<String, Boolean> callback, String filePath, TaskResult taskResult)
			throws Exception {
		File file = new File(filePath);
		FileIOUtils.writeFile(file, cmd, false);
		return exec("sh " + filePath, callback, taskResult);
	}

	public static void main(String[] args) throws Exception {
		execFile("pwd\nls", "/Users/vjay/Downloads/myTest.sh", null);
	}

}