package main.java.travelbook.util;
import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
public class DateUtil {
	//Definisce una serie di utility da usare con il tipo di dato LocalDate. Per ora solo converter
	//e un minimo di controllo
	private String pattern="dd-MM-yyyy";
	
	public DateUtil() {

	}
	public DateUtil(String pattern) {
		this.pattern=pattern;
	}
	public String toString(LocalDate date) {
		String text=null;
		if(date!=null) {
			text=DateTimeFormatter.ofPattern(pattern).format(date);
		}
		return text;
	}
	public LocalDate toLocalDate(String text) {
		LocalDate date=null;
		if(text!=null && !text.trim().isEmpty()) {
			date=LocalDate.parse(text,DateTimeFormatter.ofPattern(pattern));
		}
		return date;
	}
	public boolean isAfter(LocalDate a,LocalDate b) {
		//return false if a is before b and a!=null and b!=null
		//What is null? Unkown so i say yes every time that you want to compare a date with a null
		if(a!=null && b!=null) {
		return a.isAfter(b);
		}
		else {
			return true;
		}
	}
	public boolean isAfter(String a,String b) {
		return this.isAfter(this.toLocalDate(a), this.toLocalDate(b));
	}
	public boolean isAfter(String a,LocalDate b) {
		return this.isAfter(this.toLocalDate(a), b);
	}
	public boolean isAfter(LocalDate a,String b) {
		return this.isAfter(a, this.toLocalDate(b));
	}
	public long numOfDaysBetween(LocalDate a,LocalDate b) {
		if(a!=null && b!=null) {
		return a.until(b).getDays();
		}
		else
			return 0;
	}
	public boolean isFuture(LocalDate a) {
		return this.isAfter(a,LocalDate.now());
	}
}
