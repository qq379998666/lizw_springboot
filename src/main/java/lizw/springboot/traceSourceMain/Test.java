package lizw.springboot.traceSourceMain;

import lizw.springboot.utils.HttpUtil;

public class Test {
	
	private String APPKEY = "e14d2c39f3a46574";// 你的appkey
    private String URL = "http://api.jisuapi.com/barcode2/query";
    private String barcode = "6917878036526";

	public static void main(String[] args) {
		
        String url = "http://api.jisuapi.com/barcode2/query?appkey=e14d2c39f3a46574&barcode=6917878036526";
        try {
			String result = HttpUtil.sendGet(url, "UTF-8");
			System.out.println(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        long Time = System.currentTimeMillis();
        String s = String.valueOf(Time);
        System.out.println("Time"+s);

	}

}
