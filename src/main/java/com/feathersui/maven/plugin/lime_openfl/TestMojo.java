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

package com.feathersui.maven.plugin.lime_openfl;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import com.feathersui.maven.plugin.utils.LogOutputStream;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.JSHandle;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.cli.CommandLineUtils;
import org.codehaus.plexus.util.cli.Commandline;
import org.codehaus.plexus.util.cli.WriterStreamConsumer;

/**
 * Executes a <a href="https://lib.haxe.org/p/utest/">utest</a> runner for a
 * <a href="https://lime.software/">Lime</a>,
 * <a href="https://openfl.org/">OpenFL</a>, or
 * <a href="https://feathersui.com/">Feathers UI</a> project.
 */
@Mojo(name = "test", defaultPhase = LifecyclePhase.TEST)
public class TestMojo extends BaseMojo {
	@Parameter(defaultValue = "${project.build.directory}/utest", required = true, readonly = true)
	private File testBuildDirectory;

	@Parameter(defaultValue = "${basedir}/src/test/haxe", required = true, readonly = true)
	private File testSrcDirectory;

	@Parameter(defaultValue = "${maven.test.skip}", readonly = true)
	private boolean skip;

	public void execute() throws MojoExecutionException, MojoFailureException {
		if (skip) {
			getLog().info("Not running tests");
			return;
		}

		if ("html5".equals(target)) {
			try {
				HttpServer httpServer = HttpServer.create(new InetSocketAddress("localhost", 3000), 0);
				httpServer.createContext("/", new HttpHandler() {
					private final Map<String, String> MIME_MAP = new HashMap<>();
					{
						MIME_MAP.put("css", "text/css");
						MIME_MAP.put("gif", "image/gif");
						MIME_MAP.put("htm", "text/html");
						MIME_MAP.put("html", "text/html");
						MIME_MAP.put("js", "application/javascript");
						MIME_MAP.put("json", "application/json");
						MIME_MAP.put("jpg", "image/jpeg");
						MIME_MAP.put("jpeg", "image/jpeg");
						MIME_MAP.put("mp3", "audio/mpeg");
						MIME_MAP.put("mp4", "video/mp4");
						MIME_MAP.put("flv", "video/x-flv");
						MIME_MAP.put("mov", "video/quicktime");
						MIME_MAP.put("swf", "application/x-shockwave-flash");
						MIME_MAP.put("pdf", "application/pdf");
						MIME_MAP.put("doc", "application/msword");
						MIME_MAP.put("ogg", "application/x-ogg");
						MIME_MAP.put("png", "image/png");
						MIME_MAP.put("svg", "image/svg+xml");
						MIME_MAP.put("xml", "application/xml");
						MIME_MAP.put("zip", "application/zip");
						MIME_MAP.put("md", "text/plain");
						MIME_MAP.put("txt", "text/plain");
					};

					@Override
					public void handle(HttpExchange httpExchange) throws IOException {
						String requestPath = httpExchange.getRequestURI().getPath();

						requestPath = requestPath.substring(1);
						requestPath = requestPath.replaceAll("//", "/");
						if (requestPath.length() == 0) {
							requestPath = "index.html";
						}

						File testBuildHtml5Directory = new File(testBuildDirectory, "html5/bin");
						File file = new File(testBuildHtml5Directory, requestPath);
						if (file.exists()) {
							byte[] content = readFile(new FileInputStream(file));
							String extension = null;
							String fileName = file.getName();
							int extensionIndex = fileName.lastIndexOf(".");
							if (extensionIndex != -1) {
								extension = fileName.substring(extensionIndex + 1);
							}
							String mimeType = MIME_MAP.getOrDefault(extension, "application/octet-stream");
							writeContent(httpExchange, content.length, content, mimeType);
						} else {
							writeError(httpExchange, 404, "The requested resource was not found on server");
						}
					}

					private byte[] readFile(final InputStream in) throws IOException {
						ByteArrayOutputStream bout = new ByteArrayOutputStream();
						OutputStream gout = new DataOutputStream(bout);
						byte[] bs = new byte[4096];
						int r;
						while ((r = in.read(bs)) >= 0) {
							gout.write(bs, 0, r);
						}
						gout.flush();
						gout.close();
						in.close();
						return bout.toByteArray();
					}

					private void writeContent(HttpExchange httpExchange, int contentLength, byte[] content,
							String contentType) throws IOException {
						httpExchange.getResponseHeaders().set("Content-Type", contentType);
						httpExchange.sendResponseHeaders(200, contentLength);
						httpExchange.getResponseBody().write(content);
						httpExchange.getResponseBody().close();
					}

					private void writeError(HttpExchange httpExchange, int respCode, String errDesc)
							throws IOException {
						String message = "HTTP error " + respCode + ": " + errDesc;
						byte[] messageBytes = message.getBytes("UTF-8");

						httpExchange.getResponseHeaders().set("Content-Type", "text/plain; charset=utf-8");
						httpExchange.sendResponseHeaders(respCode, messageBytes.length);

						OutputStream os = httpExchange.getResponseBody();
						os.write(messageBytes);
						os.close();
					}
				});
				httpServer.start();

				Playwright playwright = Playwright.create();
				Browser browser = playwright.chromium().launch();
				Page page = browser.newPage();
				page.onConsoleMessage((t) -> {
					switch (t.type()) {
						case "error":
							getLog().error(t.text());
							break;
						case "info":
							getLog().info(t.text());
							break;
						case "warning":
							getLog().warn(t.text());
							break;
						case "debug":
							getLog().debug(t.text());
							break;
						default:
							getLog().info(t.text());
					}
				});
				page.navigate("http://localhost:3000/");
				page.evaluate("console.log(navigator.userAgent)");
				JSHandle resultHandle = page.waitForFunction("() => window[\"utestResult\"]");
				JSHandle statsHandle = resultHandle.getProperty("stats");
				JSHandle isOkHandle = statsHandle.getProperty("isOk");
				Object isOk = isOkHandle.jsonValue();
				page.close();
				browser.close();
				playwright.close();
				if (!isOk.equals(true)) {
					throw new MojoFailureException("Lime test run failure. Process exited with code: 1");
				}
			} catch (Exception e) {
				throw new MojoExecutionException("Fatal error running Lime tests", e);
			}
		} else {
			Commandline commandLine = new Commandline();
			commandLine.setWorkingDirectory(basedir);
			commandLine.setExecutable(getHaxelibPath());
			commandLine.createArg().setValue("run");
			commandLine.createArg().setValue("lime");
			commandLine.createArg().setValue("run");
			commandLine.createArg().setValue(projectFile.getAbsolutePath());
			commandLine.createArg().setValue(target);
			commandLine.createArg().setValue("--app-path=" + testBuildDirectory.getAbsolutePath());
			commandLine.createArg().setValue("--app-file=TestsMain");

			WriterStreamConsumer systemOut = new WriterStreamConsumer(
					new OutputStreamWriter(new LogOutputStream(getLog(), false)));
			WriterStreamConsumer systemErr = new WriterStreamConsumer(
					new OutputStreamWriter(new LogOutputStream(getLog(), true)));

			int exitCode = 1;
			try {
				exitCode = CommandLineUtils.executeCommandLine(commandLine, systemOut, systemErr);
			} catch (Exception e) {
				throw new MojoExecutionException("Fatal error running Lime tests", e);
			}
			if (exitCode != 0) {
				throw new MojoFailureException("Lime test run failure. Process exited with code: " + exitCode);
			}
		}
	}
}
