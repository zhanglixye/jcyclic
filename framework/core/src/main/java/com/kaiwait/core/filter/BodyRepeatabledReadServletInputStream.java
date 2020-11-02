package com.kaiwait.core.filter;

import java.io.EOFException;
import java.io.IOException;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;

public class BodyRepeatabledReadServletInputStream extends ServletInputStream {

	private byte[] body;
	private int lastIndexRetrieved = -1;
	private int contentLength;
	private ReadListener listener;
	private int markIndex = -1;

	public BodyRepeatabledReadServletInputStream(byte[] body) {
		this.body = body;
		this.contentLength = body.length;
	}

	@Override
	public int read() throws IOException {
		if (isFinished()) {
			throw new EOFException("已经超出输入流的末尾,如果需要重复读取请求的输入流,请在每一次读取前执行mark操作,读取后执行reset操作");
		}
		int i = body[lastIndexRetrieved + 1];
		lastIndexRetrieved++;
		if (isFinished() && listener != null) {

			try {
				listener.onAllDataRead();
			} catch (IOException e) {
				listener.onError(e);
				throw e;
			}

		}
		return i;
	}

	@Override
	public boolean isFinished() {
		return lastIndexRetrieved >= contentLength - 1;
	}

	@Override
	public boolean isReady() {
		return !isFinished();
	}

	@Override
	public void setReadListener(ReadListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException("listener cann not be null");
		}
		if (this.listener != null) {
			throw new IllegalArgumentException("listener has been set");
		}
		this.listener = listener;
		if (!isFinished()) {
			try {
				listener.onDataAvailable();
				;
			} catch (IOException e) {
				listener.onError(e);
			}
		} else {
			try {
				listener.onAllDataRead();
			} catch (IOException e) {
				listener.onError(e);
			}
		}
	}

	@Override
	public int available() throws IOException {
		return contentLength - lastIndexRetrieved - 1;
	}

	@Override
	public void close() throws IOException {
		lastIndexRetrieved = contentLength - 1;
		body = null;
	}

	@Override
	public synchronized void mark(int readlimit) {
		this.markIndex = lastIndexRetrieved;
	}

	@Override
	public boolean markSupported() {
		return true;
	}

	@Override
	public synchronized void reset() throws IOException {
		this.lastIndexRetrieved = this.markIndex;
	}

}
