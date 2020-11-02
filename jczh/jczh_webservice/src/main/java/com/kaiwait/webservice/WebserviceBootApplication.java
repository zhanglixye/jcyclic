package com.kaiwait.webservice;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.kaiwait.thrift.common.server.DynamicJsonService;
import com.kaiwait.thrift.common.server.impl.DynamicJsonServiceImpl;

@SpringBootApplication
public class WebserviceBootApplication {
	public static final int SERVER_PORT = 8082;
	//public static final int SERVER_PORT = 9092;
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		System.out.println("common-port-------"+SERVER_PORT);
//		SpringApplication.run(DemoWebserviceBootApplication.class, args);
		@SuppressWarnings("unused")
		ApplicationContext appCt2 = new FileSystemXmlApplicationContext("classpath:spring/applicationContext-*.xml");  
		startServer();
	}
	public static void startServer() {
		try {
			System.out.println("启动服务");
			TProcessor processor = new DynamicJsonService.Processor<DynamicJsonService.Iface>(new DynamicJsonServiceImpl());
//			TServerSocket serverTransport = new TServerSocket(SERVER_PORT);
//			TServer.Args args = new TServer.Args(serverTransport);
//			args.processor(processor);
//			args.protocolFactory(new TBinaryProtocol.Factory());
//			TServer server = new TSimpleServer(args);
//			server.serve();
            TServerTransport t = new TServerSocket(SERVER_PORT);
            // 线程池服务模型
            TThreadPoolServer.Args args = new TThreadPoolServer.Args(t);
            args.processor(processor);
            args.protocolFactory(new TBinaryProtocol.Factory());
			TServer server = new TThreadPoolServer(args);
            server.serve();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
