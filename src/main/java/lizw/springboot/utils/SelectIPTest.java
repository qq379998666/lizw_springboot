package lizw.springboot.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import lizw.springboot.traceSourceMain.IP;

public class SelectIPTest {
	
	public static InputStream loadStream(String path) throws IOException {
		path.startsWith("xls");
		return null;
		
	}
	
	public static void main(String[] args){
		String ipValue = "221.183.12.114";
		IP.load("D:\\workspace\\lizw_springboot\\src\\main\\resources\\IpData\\ip.dat");//引入的文件			
		String[] ipArray = IP.find(ipValue);
		for (int i = 0; i < ipArray.length; i++) {
			System.out.println(ipArray[i]);
		}
		
		List<String> list = new ArrayList<String>();
		list.add("a");
		list.add("d");
		list.add("b");
		
		System.out.println(list.size());
		
		
		list =null;
		list.add("b");
		
		System.out.println(list.size());
	}

}
