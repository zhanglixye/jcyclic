package com.kaiwait.thrift.common.server;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;

import com.kaiwait.thrift.common.server.impl.DynamicJsonServiceImpl;

public class Main {
	public static final int SERVER_PORT = 8081;
	
	public static void startServer() {
		try {
			System.out.println("启动服务");
			TProcessor processor = new DynamicJsonService.Processor<DynamicJsonService.Iface>(new DynamicJsonServiceImpl());
			TServerSocket serverTransport = new TServerSocket(SERVER_PORT);
			TServer.Args args = new TServer.Args(serverTransport);
			args.processor(processor);
			args.protocolFactory(new TBinaryProtocol.Factory());
//			args.protocolFactory(new TJSONProtocol.Factory());
			TServer server = new TSimpleServer(args);
			server.serve();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		startServer();
	}
}
