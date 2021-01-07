package main.java.travelbook.view.animation;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
public class OpacityAnimation extends AnimationTimer{
	private Pane back;
	private Pane top;
	private double low;
	private double high;

	public void handle(long now) {
		back.setOpacity(back.getOpacity()-0.02);
		top.setOpacity(top.getOpacity()+0.02);
		System.out.println("top Opacity: "+ top.getOpacity() +" back Opacity: "+ back.getOpacity());
		if(back.getOpacity()<=low && top.getOpacity()>=high) {
			
		    this.stop();
		}
	}
	public void setBackTop(Pane back,Pane top) {
		this.back=back;
		this.top=top;
	}
	public void setLimits(double low,double high) {
		this.low=low;
		this.high=high;
	}

}
