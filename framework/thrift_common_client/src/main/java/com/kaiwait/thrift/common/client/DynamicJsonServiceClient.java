package com.kaiwait.thrift.common.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import com.kaiwait.thrift.common.server.DynamicJsonService;

public class DynamicJsonServiceClient {

	public static final String SERVER_IP = "localhost";
	public static final int SERVER_PORT = 8083;
	public static final int TIMEOUT = 30000;
	
	public static String invoke(String serviceIp, int port, int timeMs, String jsonMsg) throws TException {
		try (TTransport transport = new TSocket(serviceIp, port, timeMs);){
			transport.open();
			// 协议要和服务端一致
			TProtocol protocol = new TBinaryProtocol(transport);
			// TProtocol protocol = new TCompactProtocol(transport);
//			 TProtocol protocol = new TJSONProtocol(transport);
			 DynamicJsonService.Client client = new DynamicJsonService.Client(
					protocol);
			String result = client.invoke(jsonMsg);
			return result;
		} 
	}
 
	
	public static void main(String[] args) throws TException {
		invoke(SERVER_IP, SERVER_PORT, TIMEOUT, "王会堂");
	}

}
