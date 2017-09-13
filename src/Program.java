import static org.bytedeco.javacpp.opencv_core.*;  
import static org.bytedeco.javacpp.opencv_imgcodecs.*;

public class Program {
	public static void main(String args[]) throws Exception { 
		IplImage src = cvLoadImage( "D:/xe/x3.jpg", CV_LOAD_IMAGE_COLOR);
		
		PlateFinder plateFinder = new PlateFinder();
		plateFinder.getPlate(src);
		
		System.out.println("End"); 
	} 
}
