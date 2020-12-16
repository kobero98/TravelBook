package travelbook.view.animation;
import javafx.scene.control.ScrollPane;
import javafx.animation.AnimationTimer;
public class SlideImageAnimationHR extends AnimationTimer {
		private ScrollPane scroll;
		private Double maxScroll;
		private double speed;
		@Override
		public void handle(long now) {
			if (scroll.getHvalue()<maxScroll) {
				if(scroll.getHvalue()+speed>maxScroll) {
					scroll.setHvalue(maxScroll);
				}
				else {
				scroll.setHvalue(scroll.getHvalue()+speed);
				}
			}
			else {
				this.stop();
			}
		}
		public void setScrollAndMax(ScrollPane scroll, Double maxScroll) {
			this.scroll=scroll;
			this.maxScroll=maxScroll;
			if(this.maxScroll>1) {
				this.maxScroll=1.0;
			}
		}
		public void setSpeed(int speed) {
			this.speed=(double)speed/100.0;
		}
}
