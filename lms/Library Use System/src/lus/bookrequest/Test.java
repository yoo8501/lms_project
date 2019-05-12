package lus.bookrequest;

import java.util.GregorianCalendar;
import java.util.Locale;

public class Test {
	GregorianCalendar calendar;
	public Test() {
		calendar = new GregorianCalendar();
		calendar.getInstance(Locale.KOREA);
		calendar.set(calendar.get(calendar.YEAR), calendar.get(calendar.MONTH), calendar.get(calendar.DATE)+25);
		System.out.println(calendar.get(calendar.YEAR)+", "+(calendar.get(calendar.MONTH)+1)+", "+calendar.get(calendar.DATE));
	}
	public static void main(String[] args) {
		new Test();
	}
}
