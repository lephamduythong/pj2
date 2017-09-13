import static org.bytedeco.javacpp.opencv_core.*;  
import static org.bytedeco.javacpp.opencv_imgproc.*;

import org.bytedeco.javacpp.opencv_core.CvRect;
import org.bytedeco.javacpp.opencv_core.IplConvKernel;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.OpenCVFrameConverter;

public class Preprocessing {
	public IplImage preprocess(IplImage src) {

		IplImage resizeImg = cvCreateImage( cvSize(800,600), src.depth(), src.nChannels() );
		//IplImage grayImg = cvCreateImage( cvGetSize(resizeImg), IPL_DEPTH_8U, 1 );

		cvResize(src, resizeImg);
		
		// cvCvtColor(resizeImg, grayImg, CV_RGB2GRAY);
		// cvNormalize(grayImg, grayImg, 0, 255, CV_MINMAX, null);
		
		src = cvCreateImage( cvSize(800,600), IPL_DEPTH_8U, 1 );
		cvCopy(resizeImg, src);
		
		IplConvKernel S1 = cvCreateStructuringElementEx(3, 1, 1, 0, CV_SHAPE_RECT);
		IplConvKernel S2 = cvCreateStructuringElementEx(6, 1, 3, 0, CV_SHAPE_RECT);
		
		int w = src.cvSize().width();
		int h = src.cvSize().height();
		
		System.out.println("Size: " + w + "x" + h);
		
		IplImage mImg = cvCreateImage(cvSize(w/2,h/2), IPL_DEPTH_8U, 1);
		IplImage src_pyrdown = cvCreateImage(cvSize(w/2,h/2), IPL_DEPTH_8U, 1);
		IplImage tmp = cvCreateImage(cvSize(w/2,h/2), IPL_DEPTH_8U, 1);
		IplImage thresholded = cvCreateImage(cvSize(w/2,h/2), IPL_DEPTH_8U, 1);
		IplImage mini_thresh = cvCreateImage(cvSize(w/2,h/2), IPL_DEPTH_8U, 1);
		IplImage dst = cvCreateImage(cvSize(w/2,h/2), IPL_DEPTH_8U, 1);
		
		cvPyrDown(src, src_pyrdown);
		
		cvMorphologyEx(src_pyrdown, mImg, tmp, S2, CV_MOP_BLACKHAT);
		cvNormalize(mImg, mImg, 0, 255, CV_MINMAX, null);
		System.out.println("cvAvg: " + cvAvg(mImg).val(0));
		cvThreshold(mImg, thresholded, (int)10*cvAvg(mImg).val(0), 255, CV_THRESH_BINARY);
		cvZero(dst);
		cvCopy(thresholded, mini_thresh);
		
		
		
		// Sử dụng hình chữ nhật có size = 8x16 trượt trên toàn bộ ảnh
	    // Biến đếm
		int cnt;
	    int nonZero1, nonZero2, nonZero3, nonZero4;
	    CvRect rect;
	    
	    System.out.println(mini_thresh.width());
	    System.out.println(mini_thresh.height());
	    
	    for (int i = 0; i < mini_thresh.width() - 32; i+=4) {
	    	for (int j = 0; j < mini_thresh.height() - 16; j+=4) {
		    	
	    		rect = cvRect(i, j, 16, 8);
		    	cvSetImageROI(mini_thresh, rect); // ROI = Region of interest
		    	nonZero1 = cvCountNonZero(mini_thresh);
		    	cvResetImageROI(mini_thresh);
		    	
		    	rect = cvRect(i + 16, j, 16, 8);
		    	cvSetImageROI(mini_thresh, rect); // ROI = Region of interest
		    	nonZero2 = cvCountNonZero(mini_thresh);
		    	cvResetImageROI(mini_thresh);
		    	
		    	rect = cvRect(i, j + 8, 16, 8);
		    	cvSetImageROI(mini_thresh, rect); // ROI = Region of interest
		    	nonZero3 = cvCountNonZero(mini_thresh);
		    	cvResetImageROI(mini_thresh);
		    	
		    	rect = cvRect(i + 16, j + 8, 16, 8);
		    	cvSetImageROI(mini_thresh, rect); // ROI = Region of interest
		    	nonZero4 = cvCountNonZero(mini_thresh);
		    	cvResetImageROI(mini_thresh);
		    	
		    	//System.out.println("n1:" + nonZero1 + "n2:" + nonZero2 +"n3:" + nonZero3 + "n4:" + nonZero4);  
		    	
		    	cnt = 0;
		    	if (nonZero1 > 15) { cnt++; }
		    	if (nonZero2 > 15) { cnt++; }
		    	if (nonZero3 > 15) { cnt++; }
		    	if (nonZero4 > 15) { cnt++; }
  	
		    	if (cnt > 2) {
		    		rect = cvRect(i, j, 32, 16);
		    		cvSetImageROI(dst, rect);
		    		cvSetImageROI(mini_thresh, rect);
		    		cvCopy(mini_thresh, dst);
		    		cvResetImageROI(dst);
		    		cvResetImageROI(mini_thresh);
		    	}
		    	
		    }
	    	
	    }
	    
	    IplImage dst_clone = cvCloneImage(dst);
	    
	    cvDilate(dst, dst, null, 2);
	    cvErode(dst, dst, null, 2);
	    cvDilate(dst, dst, S1, 9);
	    cvErode(dst, dst, S1, 10);
	    cvDilate(dst, dst);
	    
	    cvPyrUp(dst, src);
	    
	    // PlateFinder
	   
    	// Show ảnh
 		CanvasFrame canvas = new CanvasFrame("Pre: Source output");
 		canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
 		CanvasFrame canvas2 = new CanvasFrame("Pre: mImg");
 		canvas2.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
 		CanvasFrame canvas3 = new CanvasFrame("Pre: thresholded");
 		canvas3.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
 		CanvasFrame canvas4 = new CanvasFrame("Pre: dst");
 		canvas4.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
 		CanvasFrame canvas5 = new CanvasFrame("Pre: dst_clone");
 		canvas4.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
 		OpenCVFrameConverter<?> converter = new OpenCVFrameConverter.ToIplImage();
	    
		canvas.showImage(converter.convert(src));
		canvas2.showImage(converter.convert(mImg));
		canvas3.showImage(converter.convert(thresholded));
		canvas4.showImage(converter.convert(dst));
		canvas5.showImage(converter.convert(dst_clone));
		
		// Release memory
		cvReleaseImage(mini_thresh);
	    cvReleaseImage(mImg);
	    cvReleaseImage(tmp);
	    cvReleaseImage(src_pyrdown);
	    cvReleaseImage(thresholded);
	    cvReleaseImage(dst);
	    cvReleaseImage(dst_clone);
		
		return src;
	}
}
