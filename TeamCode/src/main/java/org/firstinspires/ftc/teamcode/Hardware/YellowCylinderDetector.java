package org.firstinspires.ftc.teamcode.Hardware;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;

public class YellowCylinderDetector extends OpenCvPipeline {

    private Telemetry telemetry = null;

    /*
     * These are our variables that will be
     * modifiable from the variable tuner.
     *
     * Scalars in OpenCV are generally used to
     * represent color. So our values in the
     * lower and upper Scalars here represent
     * the Y, Cr and Cb values respectively.
     *
     * YCbCr, like most color spaces, range
     * from 0-255, so we default to those
     * min and max values here for now, meaning
     * that all pixels will be shown.
     */
    ArrayList<double[]> frameList;
    //these are public static to be tuned in dashboard
    public static double strictLowS = 123;
    public static double strictHighS = 218;
    private ArrayList<RotatedRect> boundingBoxes = new ArrayList<>();
    private final MatOfPoint2f mp2f = new MatOfPoint2f();

    public double POLE_Y = 0;

    public YellowCylinderDetector() {
        frameList = new ArrayList<>();
    }


    @Override
    public Mat processFrame(Mat input) {
        boundingBoxes.clear();
        Mat mat = new Mat();
        //input.copyTo(displayMat);

        Imgproc.blur(input, input, new Size(3,3));

        //mat turns into HSV value
        Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV);
        if (mat.empty()) {
            return input;
        }

        // lenient bounds will filter out near yellow, this should filter out all near yellow things(tune this if needed)
        Scalar lowHSV = new Scalar(17, 40, 60); // lenient lower bound HSV for yellow
        Scalar highHSV = new Scalar(42, 255, 255); // lenient higher bound HSV for yellow

        Mat thresh = new Mat();

        // Get a black and white image of yellow objects
        Core.inRange(mat, lowHSV, highHSV, thresh);

        Mat masked = new Mat();
        //color the white portion of thresh in with HSV from mat
        //output into masked
        Core.bitwise_and(mat, mat, masked, thresh);
        //calculate average HSV values of the white thresh values
        Scalar average = Core.mean(masked, thresh);

        Mat scaledMask = new Mat();
        //scale the average saturation to 150
        masked.convertTo(scaledMask, -1, 150 / average.val[1], 0);


        Mat scaledThresh = new Mat();
        //you probably want to tune this
        Scalar strictLowHSV = new Scalar(0, strictLowS, 0); //strict lower bound HSV for yellow
        Scalar strictHighHSV = new Scalar(255, strictHighS, 255); //strict higher bound HSV for yellow
        //apply strict HSV filter onto scaledMask to get rid of any yellow other than pole
        Core.inRange(scaledMask, strictLowHSV, strictHighHSV, scaledThresh);
        Mat finalMask = new Mat();
        //color in scaledThresh with HSV, output into finalMask(only useful for showing result)(you can delete)
        Core.bitwise_and(mat, mat, finalMask, scaledThresh);

        Mat edges = new Mat();
        //detect edges(only useful for showing result)(you can delete)
        Imgproc.Canny(finalMask, edges, 100, 200);

        //contours, apply post processing to information
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        //find contours, input scaledThresh because it has hard edges
        Imgproc.findContours(edges, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

        //list of frames to reduce inconsistency, not too many so that it is still real-time, change the number from 5 if you want
        if (frameList.size() > 5) {
            frameList.remove(0);
        }

        for (MatOfPoint point: contours) {
            point.convertTo(mp2f, CvType.CV_32F);
            boundingBoxes.add(Imgproc.minAreaRect(mp2f));
        }

        if (boundingBoxes.size() > 0) {
            RotatedRect closest = boundingBoxes.get(0);
            for (RotatedRect bbox : boundingBoxes.subList(1, boundingBoxes.size())) {
                if (bbox.boundingRect().br().x > closest.boundingRect().br().x) {
                    closest = bbox;
                }
            }
            POLE_Y =  -1 * (closest.center.y - input.height()/2.0);
        } else {
            POLE_Y = input.height() / 2;
        }

        //POLE_Y = -1 * (holyBox.center.y - input.height()/2.0);

        //release all the data
        //input.release();
        //scaledThresh.copyTo(input);
        scaledThresh.release();
        scaledMask.release();
        mat.release();
        masked.release();
        edges.release();
        thresh.release();
        finalMask.release();
        hierarchy.release();
        //change the return to whatever mat you want
        //for example, if I want to look at the lenient thresh:
        // return thresh;
        // note that you must not do thresh.release() if you want to return thresh
        // you also need to release the input if you return thresh(release as much as possible)


        /*for (RotatedRect bbox: boundingBoxes) {
            Imgproc.rectangle(displayMat, bbox.boundingRect(), new Scalar(255, 0, 0));
        }*/
        //Imgproc.putText(input, "d: "+ boundingBoxes.size(), new Point(240, 240), 0, 2, new Scalar(0, 255, 0));
        return input;
    }
}
