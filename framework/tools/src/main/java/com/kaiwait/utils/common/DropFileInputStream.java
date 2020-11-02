/**
 * 
 */
package com.kaiwait.utils.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * 在关闭时删除原文件的流
 *
 */
public class DropFileInputStream extends FileInputStream {

	private File file;
	public DropFileInputStream(File file) throws FileNotFoundException {
		super(file);
		this.file = file;
	}
	/* (non-Javadoc)
	 */
	@Override
	public int read() throws IOException {
		// TODO Auto-generated method stub
		return super.read();
	}

	/* (non-Javadoc)
	 */
	@Override
	public int read(byte[] b) throws IOException {
		// TODO Auto-generated method stub
		return super.read(b);
	}

	/* (non-Javadoc)
	 */
	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		// TODO Auto-generated method stub
		return super.read(b, off, len);
	}

	/* (non-Javadoc)
	 */
	@Override
	public long skip(long n) throws IOException {
		// TODO Auto-generated method stub
		return super.skip(n);
	}

	/* (non-Javadoc)
	 */
	@Override
	public int available() throws IOException {
		// TODO Auto-generated method stub
		return super.available();
	}

	/* (non-Javadoc)
	 */
	@Override
	public void close() throws IOException {
		super.close();
		if (this.file != null) {
			this.file.delete();
		}
	}

	/* (non-Javadoc)
	 */
	@Override
	public FileChannel getChannel() {
		// TODO Auto-generated method stub
		return super.getChannel();
	}

	

}
