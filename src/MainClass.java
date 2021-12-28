import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainClass {
    public static void main(String[] args) throws IOException {
        File file = null;
        BufferedImage image = null;

        //doc anh tu may tinh
        try {
            file = new File("C:\\Users\\ducla\\Pictures\\Captain-America-2-Political-7709-1394700871.jpg");
            image = ImageIO.read(file);
            System.out.println("hello 1");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // lay chieu cao chieu dai buc anh
        int width = image.getWidth();
        int height = image.getHeight();
        System.out.println("width = " + width + " -" + "height = " + height);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // lay toa do cua anh de sua gia tri x,y;
                int p = image.getRGB(x, y);
                int a = (p >> 24) & 0xff;
                int r = (p >> 16) & 0xff;
                int g = (p >> 8) & 0xff;
                int b = p & 0xff;
                // tinh gia tri trung binh
                int avg = (r + g + b) / 3;
                // thay RGB bang gia tri vua tinh;
                p = (a << 24) | (avg << 16) | (avg << 8) | avg;
                image.setRGB(x, y, p);
            }
        }

        // lua anh
//        try {
//            file = new File("C:\\Users\\ducla\\Pictures\\ducla4.jpg");
//            boolean x = ImageIO.write(image, "jpg", file);
//            System.out.println("hello 3 " + x);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        file = new File("C:\\Users\\ducla\\Pictures");

//        boolean x =  PaintRectangle(file.getAbsolutePath());
        System.out.println(file.isDirectory());
        if (file.isDirectory()) {
            for (File sub : file.listFiles()) {
                if (sub.getAbsolutePath().contains(".jpg")) {
                    System.out.println(sub.getName());
                    boolean y = faceDetection(sub.getAbsolutePath());
                    System.out.println(y);
                }
            }
        }

    }

    public static boolean PaintRectangle(String url) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat mat = Imgcodecs.imread(url);

        Imgproc.rectangle(mat, new Point(10, 10),
                new Point(100, 100),
                new Scalar(0, 255, 0));
        boolean x = Imgcodecs.imwrite("C:\\Users\\ducla\\Pictures\\result_test.jpg", mat);
        return x;
    }

    public static boolean faceDetection(String url) {
        File f = new File(url);
        if (!f.exists()) return false;
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        CascadeClassifier faceDetector = new CascadeClassifier();
        faceDetector.load("C:\\Users\\ducla\\Downloads\\opencv\\sources\\data\\haarcascades_cuda\\haarcascade_frontalface_alt.xml");
        Mat image = Imgcodecs.imread(url);
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image, faceDetections);
        for (Rect rect : faceDetections.toArray()) {
            Imgproc.rectangle(image, new Point(rect.x, rect.y),
                    new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0));
        }
        if (faceDetections.toArray().length > 0) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyy_hhmmss_");
            String format = simpleDateFormat.format(new Date());
            String filename = "C:\\Users\\ducla\\Pictures\\KQ\\" + format + f.getName();
            boolean x = Imgcodecs.imwrite(filename, image);
            return x;
        }
        return false;
    }
}
