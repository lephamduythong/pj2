//
//import java.awt.image.BufferedImage;
//import java.io.ByteArrayInputStream;
//import java.io.InputStream;
//
//import javax.imageio.ImageIO;
//import javax.swing.ImageIcon;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//
//import org.opencv.core.*;
//import org.opencv.core.Core;
//import org.opencv.core.CvType;
//import org.opencv.core.Mat;
//import org.opencv.core.MatOfByte;
//import org.opencv.core.MatOfPoint;
//import org.opencv.core.Scalar;
//import org.opencv.core.Size;
//import org.opencv.imgcodecs.Imgcodecs;
//import org.opencv.imgproc.Imgproc;
//import org.opencv.core.Point;
//import org.opencv.core.MatOfPoint;
//
//import com.google.common.collect.Lists;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.Objects;
//
//public class JavaCv1 {
//	/*
//	 * 
//	 * threshold: ngưỡng
//	 * morphology: hình thái học
//	 */
//	
//	public static void main2(String args[]) throws Exception { 
//	   // PREPROCESSING IMAGE
//	      // Mat outerBox = new Mat(image.size(), CvType.CV_8UC1);
//	   
//	   //Loading the OpenCV core library  
//      System.loadLibrary( Core.NATIVE_LIBRARY_NAME ); 
//    
//      //Reading the Image from the file and storing it in to a Matrix object 
//      String file = "D:/xe/x1.jpg"; 
//      Mat image = Imgcodecs.imread(file, Imgcodecs.IMREAD_COLOR); 
//      
//      // Trả lại phần tử cấu trúc có kích thước và hình dạng quy định cho các hoạt động hình thái.
//      Mat S2 = Imgproc.getStructuringElement(Imgproc.CV_SHAPE_RECT, new Size(6, 6));
//      
//      Imgproc.cvtColor(image, image, Imgproc.COLOR_RGB2GRAY);
//      
//      // Thực hiện chuyển đổi hình thái tiên tiến.
//      Imgproc.morphologyEx(image, image, Imgproc.MORPH_BLACKHAT, S2);
//      Core.normalize(image, image, 0, 255, Core.NORM_MINMAX);
//   
//      // Imgproc.GaussianBlur(bw, bw, new Size(11, 11), 0);
//      // Imgproc.adaptiveThreshold(bw, bw, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY_INV, 5, 2);
//      
//      System.out.println((int)Core.mean(image).val[0]);
//      
//      Imgproc.threshold(image, image, (int)10 * Core.mean(image).val[0], 255, Imgproc.THRESH_BINARY);
//      
//      // Sử dụng hình chữ nhật có size = 8x16 trượt trên toàn bộ ảnh
//      int cnt;
//      int nonZero1, nonZero2, nonZero3, nonZero4;
//      
//      //List<MatOfPoint> contours = Lists.newArrayList();
//      // Mat hierarchy = new Mat();
//      
//      //Imgproc.findContours(image.clone(), contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
//      
//      // System.out.println(contours);
//      
//      // Imgproc.threshold(image, image, 100, 500, Imgproc.THRESH_BINARY_INV);
//      //Encoding the image 
//      MatOfByte matOfByte = new MatOfByte();       
//      Imgcodecs.imencode(".jpg", image, matOfByte); 
//      
//      //Storing the encoded Mat in a byte array 
//      byte[] byteArray = matOfByte.toArray(); 
//
//      //Preparing the Buffered Image 
//      InputStream in = new ByteArrayInputStream(byteArray); 
//      BufferedImage bufImage = ImageIO.read(in); 
//
//      //Instantiate JFrame 
//      JFrame frame = new JFrame(); 
//
//      //Set Content to the JFrame 
//      frame.getContentPane().add(new JLabel(new ImageIcon(bufImage))); 
//      frame.pack(); 
//      frame.setVisible(true);
//      
//      System.out.println("Image Loaded");     
//   } 
//}
//
//
//
//// Adding Text
////Imgproc.putText (
//// image,                          // Matrix obj of the image
//// "Test text",          // Text to be added
//// new Point(10, 50),               // point
//// Core.FONT_HERSHEY_SIMPLEX ,      // front face
//// 1,                               // front scale
//// new Scalar(120, 255, 120),             // Scalar object for color
//// 4                                // Thickness
////);
//
//// Imgproc.blur(image, image, new Size(45, 45), new Point(20, 30), Core.BORDER_DEFAULT);
//
//
////Imgproc.circle(image, new Point(100,100), 100, new Scalar(0,0,255));
////
////Imgproc.line (
//// image,                    //Matrix obj of the image
//// new Point(10, 200),        //p1
//// new Point(300, 200),       //p2
//// new Scalar(0, 0, 255),     //Scalar object for color
//// 1                          //Thickness of the line
////);
//
////MatOfPoint matOfPoint = new MatOfPoint (
//// new Point(75, 100), new Point(350, 100),
//// new Point(75, 150), new Point(350, 150),
//// new Point(75, 200), new Point(350, 200),
//// new Point(75, 250), new Point(350, 250)
////); 
////// Drawing polylines
////Imgproc.fillConvexPoly (
//// image,                       // Matrix obj of the image
//// matOfPoint,                   // java.util.List<MatOfPoint> pts
//// new Scalar(0, 0, 255)         // Scalar object for color
////);