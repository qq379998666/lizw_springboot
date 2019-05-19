package lizw.springboot.utils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import lizw.springboot.entity.TraceAnalysisEntity;
import lizw.springboot.traceSourceMain.IP;
import lizw.springboot.traceSourceMain.ToDB;

public class PagingHandler extends DefaultHandler {

	int firstEndIndex = 0;// 判断标题头
	int columnIndex = 0;// 列的标志
	int resultIndex = 0;//插入数据计数器

	List<List<String>> allList = new ArrayList<List<String>>();

	private SharedStringsTable sst;

	public PagingHandler(SharedStringsTable sst) {
		this.sst = sst;
	}
	
	@Override
	public void startDocument() throws SAXException{		
		System.out.println("开始解析 !!!!");
	}
	

	/**
	 * 每个单元格开始时的处理
	 */
	@Override
	public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
//		System.out.println("-----------startElement-------------");

		// row 是第一个节点
		if (name.equals("row")) {
			columnIndex++;
//			System.out.println(attributes.getValue("r") + " - " + columnIndex);
		}

		// c 是第二个节点
		if (name.equals("c") && columnIndex == 1) {
			columnIndex++;
			System.out.println(attributes.getValue("r") + " - " + columnIndex);
		}

		// c 是第三个节点
		if (name.equals("v") && columnIndex >= 2) {
			columnIndex++;
//			System.out.println("v===" + columnIndex);
		}

	}

	/**
	 * 每个单元格结束时的处理
	 */
	@Override
	public void endElement(String uri, String localName, String name) throws SAXException {
//		System.out.println("-----------endElement-------------");

		if (columnIndex == 5) {
			columnIndex = 0;
		}

		firstEndIndex++;

	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
//		System.out.println("-----------characters-------------");

		String currectValue = new String(ch, start, length);
//		System.out.println("currectValue=" + currectValue + "  columnIndex =" + columnIndex);

		if (firstEndIndex < 16) {
			int ipInt = Integer.parseInt(currectValue);
			String ipValue = new XSSFRichTextString(sst.getEntryAt(ipInt)).toString();
			if (ipValue.equals("HOP")) {
//				System.out.println("HOP=" + ipValue);
				columnIndex--;
			} else if (ipValue.equals("IP")) {
//				System.out.println("IP=" + ipValue);
				columnIndex--;
			} else if (ipValue.equals("P1")) {
//				System.out.println("P1=" + ipValue);
				columnIndex--;
				columnIndex--;
				columnIndex--;
			}
		}

		List<String> list = new ArrayList<String>();
		if (columnIndex == 3) {
//			System.out.println("setHop=" + currectValue);
			list.add(currectValue);// 放进list里
		} else if (columnIndex == 4) {
			int ipInt = Integer.valueOf(currectValue);   
			String ipValue = new XSSFRichTextString(sst.getEntryAt(ipInt)).toString();
			list.add(ipValue);// 放进list里
//			System.out.println("setA_ip=" + ipValue);

			IP.load("D:\\workspace\\lizw_springboot\\src\\main\\resources\\IpData\\ip.dat");// 引入的文件
			String[] ipArray = IP.find(ipValue);

			list.add(ipArray[0]);// 放进list里
			list.add(ipArray[1]);// 放进list里
			list.add(ipArray[2]);// 放进list里
			list.add(ipArray[4]);// 放进list里

//			System.out.println("setA_country=" + ipArray[0]);
//			System.out.println("setA_province=" + ipArray[1]);
//			System.out.println("setA_city=" + ipArray[2]);
//			System.out.println("setA_operator=" + ipArray[4]);

		} else if (columnIndex == 5) {
//			System.out.println("setA_p=" + currectValue);
			list.add(currectValue);// 放进list里
		}

		allList.add(list);

	}

	/**
	 * 如果文档结束后，发现读取的末尾行正处在当前行中，存储下这行 （存在这样一种情况，当待读取的末尾行正好是文档最后一行时，最后一行无法存到集合中，
	 * 因为最后一行没有下一行了，所以不为启动starElement()方法， 当然我们可以通过指定最大列来处理，但不想那么做，扩展性不好）
	 */
	@Override
	public void endDocument() throws SAXException {

		List<List<String>> finshList = new ArrayList<List<String>>();
		System.out.println(" allList: "+allList.size());
		for (int i = 0; i < allList.size(); i++) {

			if (i != 0 && i % 3 == 0) {
				List<String> partList = new ArrayList<String>();
//				System.out.println(allList.get(i).get(0) + ",");
				partList.add(allList.get(i).get(0));
				for (int j = 0; j < allList.get(i + 1).size(); j++) {
//					System.out.print(allList.get(i + 1).get(j) + ",");
					partList.add(allList.get(i + 1).get(j));
				}
//				System.out.println(allList.get(i + 2).get(0) + ",");
				partList.add(allList.get(i + 2).get(0));
				finshList.add(partList);

			}

		}

//		for (int i = 0; i < finshList.size(); i++) {
//			for (int j = 0; j < finshList.get(i).size(); j++) {
//				System.out.print(finshList.get(i).get(j) + ",");
//			}
//			System.out.println(";");
//		}
		//int hop_sign_index = 152097;
		//int hop_sign_index = 202351;
//		int hop_sign_index = 247504;
//		int hop_sign_index = 301936;
		int hop_sign_index = 334109;
		for (int i = 0; i < finshList.size() - 1; i++) {
			if (Integer.valueOf(finshList.get(i).get(0)) < Integer.valueOf(finshList.get(i + 1).get(0))) {

				TraceAnalysisEntity traceAnalysisEntity = new TraceAnalysisEntity();

				traceAnalysisEntity.setHop(Integer.valueOf(finshList.get(i).get(0)));
				traceAnalysisEntity.setA_ip(finshList.get(i).get(1));
				traceAnalysisEntity.setA_country(finshList.get(i).get(2));
				traceAnalysisEntity.setA_province(finshList.get(i).get(3));
				traceAnalysisEntity.setA_city(finshList.get(i).get(4));
				traceAnalysisEntity.setA_operator(finshList.get(i).get(5));
				traceAnalysisEntity.setA_p(finshList.get(i).get(6));
				traceAnalysisEntity.setB_ip(finshList.get(i + 1).get(1));
				traceAnalysisEntity.setB_country(finshList.get(i + 1).get(2));
				traceAnalysisEntity.setB_province(finshList.get(i + 1).get(3));
				traceAnalysisEntity.setB_city(finshList.get(i + 1).get(4));
				traceAnalysisEntity.setB_operator(finshList.get(i + 1).get(5));
				traceAnalysisEntity.setB_p(finshList.get(i + 1).get(6));
				traceAnalysisEntity.setHop_sign(hop_sign_index);

//				System.out.println("getHop==" + traceAnalysisEntity.getHop());
//				System.out.println("getA_country==" + traceAnalysisEntity.getA_country());
//				System.out.println("getA_province==" + traceAnalysisEntity.getA_province());
//				System.out.println("getA_city==" + traceAnalysisEntity.getA_city());
//				System.out.println("getA_ip==" + traceAnalysisEntity.getA_ip());
//				System.out.println("getA_operator==" + traceAnalysisEntity.getA_operator());
//				System.out.println("getA_p==" + traceAnalysisEntity.getA_p());
//				System.out.println("getB_country==" + traceAnalysisEntity.getB_country());
//				System.out.println("getB_province==" + traceAnalysisEntity.getB_province());
//				System.out.println("getB_city==" + traceAnalysisEntity.getB_city());
//				System.out.println("getB_ip==" + traceAnalysisEntity.getB_ip());
//				System.out.println("getB_operator==" + traceAnalysisEntity.getB_operator());
//				System.out.println("getB_p==" + traceAnalysisEntity.getB_p());

				try {
					
					int index = ToDB.getInstance().save(traceAnalysisEntity);
					resultIndex = resultIndex + index;
					System.out.println(resultIndex);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}else {
				hop_sign_index++;
			}

		}

		System.out.println("-------------endDocument-------------");

	}

}
