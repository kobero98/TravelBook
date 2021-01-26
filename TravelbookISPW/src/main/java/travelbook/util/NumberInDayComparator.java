package main.java.travelbook.util;
import java.util.Comparator;
import main.java.travelbook.model.bean.StepBean;
public class NumberInDayComparator implements Comparator<StepBean>{
	
		
		@Override
		public int compare(StepBean step1,StepBean step2) {
			int retVal=0;
			if(step1.getNumberInDay()>step2.getNumberInDay()) {
				retVal=1;
			}
			else if(step1.getNumberInDay()<step2.getNumberInDay()) {
				retVal=-1;
			}
			return retVal;
		}
}
