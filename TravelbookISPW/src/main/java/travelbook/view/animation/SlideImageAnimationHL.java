package main.java.travelbook.view.animation;
import javafx.scene.control.ScrollPane;
import javafx.animation.AnimationTimer;
public class SlideImageAnimationHL extends AnimationTimer {
		private ScrollPane scroll;
		private Double minScroll;
		private double speed;
		@Override
		public void handle(long now) {
			if (scroll.getHvalue()>minScroll) {
				if(scroll.getHvalue()-speed<minScroll) {
					scroll.setHvalue(minScroll);
				}
				else {
				scroll.setHvalue(scroll.getHvalue()-speed);
				}
			}
			else {
				this.stop();
			}
		}
		public void setScrollAndMin(ScrollPane scroll, Double minScroll) {
			this.scroll=scroll;
			this.minScroll=minScroll;
			if(this.minScroll<0) {
				this.minScroll=0.0;
			}
		}
		public void setSpeed(int val) {
			this.speed=((double)val)/100.0;
		}
}