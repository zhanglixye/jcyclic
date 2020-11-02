package com.kaiwait.thrift.common.client;

import java.lang.reflect.Constructor;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class ThriftClientProxy {

	public static final String SERVER_IP = "localhost";
	public static final int SERVER_PORT = 8082;
	public static final int TIMEOUT = 30000;
//	private static TTransport transport;
	@SuppressWarnings("unchecked")
	public static <T> T getClient(Class<T> service) {
		try {
			TTransport transport = new TSocket(SERVER_IP, SERVER_PORT, TIMEOUT);
			transport.open();
			// 协议要和服务端一致
			TProtocol protocol = new TBinaryProtocol(transport);
			@SuppressWarnings("rawtypes")
			Class client = Class.forName(service.getName());  
			TMultiplexedProtocol importServiceProtocol = new TMultiplexedProtocol(protocol, "importService");
            @SuppressWarnings("rawtypes")
			Constructor con = client.getConstructor(TProtocol.class);  
            return (T)con.newInstance(importServiceProtocol);  
		} catch (Throwable e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

}
