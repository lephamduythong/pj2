import static org.bytedeco.javacpp.opencv_core.*;  
import static org.bytedeco.javacpp.opencv_imgcodecs.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.OpenCVFrameConverter;

public class PlateFinder {
	public IplImage getPlate(IplImage src) {
		IplImage plate;
		IplImage contourImg = cvCreateImage(cvGetSize(src), IPL_DEPTH_8U, 1); // ảnh tìm contour
		IplImage grayImg = cvCreateImage(cvGetSize(src), IPL_DEPTH_8U, 1); // ảnh xám
		cvCvtColor(src, grayImg, CV_RGB2GRAY);
		
		IplImage cloneImg = cvCreateImage(cvGetSize(src), IPL_DEPTH_8U, 3);
		cloneImg = cvCloneImage(src);
		
		// Tiền xử lý
		cvCopy(grayImg, contourImg);
		cvNormalize(contourImg, contourImg, 0, 255, CV_MINMAX, null);
		
//		CanvasFrame canvas = new CanvasFrame("Source");
//		canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
//		OpenCVFrameConverter<?> converter = new OpenCVFrameConverter.ToIplImage();
//		canvas.showImage(converter.convert(contourImg));
		
		Preprocessing preprocessing = new Preprocessing();
		contourImg = preprocessing.preprocess(contourImg);

		// Tìm contour
		
		// Phát hiện kí tự
		
		return null;
	}
}