package com.kaiwait.core.utils;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class PdfToZipUtil {
	
	public static String pdfToZip(List<byte[]> listByte,String  reqFileName ,List<String> reqPdfNo,String ReInNO,String pdfName ) throws Exception {
		String outpdfName[]=null;
		if(pdfName!=null&&!pdfName.equals("")) {
			outpdfName =  pdfName.split(",");
		}
		// 内存缓冲
		String splitReqPdfNo="";
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// 创建zip输出流
		ZipOutputStream out = new ZipOutputStream(baos);
		for (int i=0;i<listByte.size();i++) {
			//压缩pdfa到内存缓冲
//			fileName = UUID.randomUUID() + ".pdf";
			if(reqPdfNo==null) {
				if(i==0) {
					splitReqPdfNo = outpdfName[i];
				}else {
					splitReqPdfNo = outpdfName[i];
				}
			}else {
				splitReqPdfNo = reqPdfNo.get(i);
			}
			
			if(reqPdfNo==null) {
				out.putNextEntry(new ZipEntry(splitReqPdfNo+"_"+ReInNO+ ".pdf"));
			}else {
				out.putNextEntry(new ZipEntry(reqFileName+"_"+splitReqPdfNo + ".pdf"));
			}
			
			out.write(listByte.get(i));
			out.closeEntry();
		}
		
		//刷新压缩输出流的缓存区内容到内存缓冲
		out.flush();
		// 关闭流
		out.close();
		baos.close();
		
		//压缩后的文件的字节表示，将这个字节数组按以前的单PDF,base64方式输出到前端就行了
		byte[] byteArray = baos.toByteArray();
		return Base64.getEncoder().encodeToString(byteArray);
	}

}
