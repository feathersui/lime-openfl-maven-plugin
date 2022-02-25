/*
Copyright 2022 Bowler Hat LLC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package com.feathersui.maven.plugin.utils;

import java.io.OutputStream;

import org.apache.maven.plugin.logging.Log;

public class LogOutputStream extends OutputStream {
	public LogOutputStream(Log log, boolean error) {
		super();
		this.log = log;
		this.error = error;
	}

	private Log log;
	private boolean error;
	private StringBuilder builder;

	public void write(int b) {
		if (builder == null) {
			builder = new StringBuilder();
		}
		char next = (char) (b & 0xff);

		if (next == '\n') {
			flush();
		} else {
			builder.append(next);
		}
	}

	public void flush() {
		if (builder == null) {
			return;
		}
		if (error) {
			log.error(builder.toString());
		} else {
			log.info(builder.toString());
		}
		builder = null;
	}
}