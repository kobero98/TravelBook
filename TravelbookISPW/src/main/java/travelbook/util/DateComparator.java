package main.java.travelbook.util;
import java.util.Comparator;
import main.java.travelbook.model.bean.MessageBean;

public class DateComparator implements Comparator<MessageBean>{
	
		
		@Override
		public int compare(MessageBean msg1, MessageBean msg2) {
			int retVal=0;
			if(msg1.getTime().isAfter(msg2.getTime())){
				retVal=1;
			}
			else if(msg1.getTime().isBefore(msg2.getTime())){
				retVal=-1;
			}
			return retVal;
		}
}