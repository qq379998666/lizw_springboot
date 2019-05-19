package lizw.springboot.callableImpl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import lizw.springboot.callableUtils.TwoThreadSelectUtil;

public class TwoThreadSelectCallableImpl extends Thread implements Callable<String>,Runnable {
	
	private String date;
	private String ip_A;
	private String ip_B;
	private String t;
	
	public TwoThreadSelectCallableImpl() {
		
	}
	
	public TwoThreadSelectCallableImpl(String date,String ip_A,String ip_B,String t) {
		this.date = date;
		this.ip_A = ip_A;
		this.ip_B = ip_B;
		this.t = t;
	}

	@Override
	public String call() throws Exception {
		return " 正在导出  ";
	}

	@Override
	public void run() {
		
		if(t.equals("t1")) {
			System.out.println("t2线程结束");
		}else if(t.equals("t2")) {
			List<Map<String, String>> list;
			try {
				list = TwoThreadSelectUtil.twoThreadSelectUtil.twoThreadSelectU(date, ip_A, ip_B);
				TwoThreadSelectUtil.twoThreadSelectUtil.exportExcelU(list);
				System.out.println("t1线程结束");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}

}
