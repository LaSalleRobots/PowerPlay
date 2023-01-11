package org.firstinspires.ftc.teamcode.Hardware;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;

public class PoleCenterDetection extends OpenCvPipeline {

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
    public Scalar lower = new Scalar(0, 0, 0);
    public Scalar upper = new Scalar(255, 255, 99.2);
    public Scalar cannyThresh = new Scalar(200, 255);
    public Size blurr = new Size(4.0, 4.0);


    private Mat blur = new Mat();
    private Mat ycrcbMat       = new Mat();
    private Mat binaryMat      = new Mat();
    private Mat maskedInputMat = new Mat();
    private final Mat edges = new Mat();

    private ArrayList<MatOfPoint> contors = new ArrayList<>();
    private ArrayList<RotatedRect> boundingBoxes = new ArrayList<>();
    private ArrayList<RotatedRect> boundingBoxesClean = new ArrayList<>();
    private final MatOfPoint2f largestContour2f = new MatOfPoint2f();
    public RotatedRect holyBox = new RotatedRect();
    private final MatOfPoint2f mp2f = new MatOfPoint2f();
    RotatedRect selected = new RotatedRect();

    public double POLE_Y = 0;

    public PoleCenterDetection() {

    }


    @Override
    public Mat processFrame(Mat input) {
        contors.clear();
        boundingBoxes.clear();
        boundingBoxesClean.clear();
        /*
         * Converts our input mat from RGB to
         * specified color space by the enum.
         * EOCV ALWAYS returns RGB mats, so you'd
         * always convert from RGB to the color
         * space you want to use.
         *
         * Takes our "input" mat as an input, and outputs
         * to a separate Mat buffer "ycrcbMat"
         */
        Imgproc.cvtColor(input, ycrcbMat, Imgproc.COLOR_RGB2HSV);

        Imgproc.blur(ycrcbMat, ycrcbMat, blurr);

        /*
         * This is where our thresholding actually happens.
         * Takes our "ycrcbMat" as input and outputs a "binary"
         * Mat to "binaryMat" of the same size as our input.
         * "Discards" all the pixels outside the bounds specified
         * by the scalars above (and modifiable with EOCV-Sim's
         * live variable tuner.)
         *
         * Binary meaning that we have either a 0 or 255 value
         * for every pixel.
         *
         * 0 represents our pixels that were outside the bounds
         * 255 represents our pixels that are inside the bounds
         */
        Core.inRange(ycrcbMat, lower, upper, binaryMat);

        /*
         * Release the reusable Mat so that old data doesn't
         * affect the next step in the current processing
         */
        maskedInputMat.release();

        /*
         * Now, with our binary Mat, we perform a "bitwise and"
         * to our input image, meaning that we will perform a mask
         * which will include the pixels from our input Mat which
         * are "255" in our binary Mat (meaning that they're inside
         * the range) and will discard any other pixel outside the
         * range (RGB 0, 0, 0. All discarded pixels will be black)
         */
        Core.bitwise_and(input, input, maskedInputMat, binaryMat);

        /**
         * Add some nice and informative telemetry messages

         telemetry.addData("[>]", "Change these values in tuner menu");
         telemetry.addData("[Color Space]", colorSpace.name());
         telemetry.addData("[Lower Scalar]", lower);
         telemetry.addData("[Upper Scalar]", upper);
         telemetry.update();
         */
        /*
         * The Mat returned from this method is the
         * one displayed on the viewport.
         *
         * To visualize our threshold, we'll return
         * the "masked input mat" which shows the
         * pixel from the input Mat that were inside
         * the threshold range.
         */



        Imgproc.Canny(binaryMat, edges, cannyThresh.val[0], cannyThresh.val[1]);
        Imgproc.findContours(edges, contors, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);

        for (MatOfPoint point: contors) {
            point.convertTo(mp2f, CvType.CV_32F);
            boundingBoxes.add(Imgproc.minAreaRect(mp2f));
        }


        taperEdges();

        for (RotatedRect box: boundingBoxesClean) {
            Imgproc.rectangle(maskedInputMat, box.boundingRect(), new Scalar(255, 0, 0));
            Imgproc.putText(maskedInputMat, "w:"+box.size.width, box.boundingRect().br(), 0, 0.5, new Scalar(0, 255, 0));
            Imgproc.putText(maskedInputMat, "h:"+box.size.height, box.boundingRect().tl(), 0, 0.5, new Scalar(0, 255, 0));
        }


        if (boundingBoxesClean.size() == 0) {
            POLE_Y = 0;
        }
        else {
            selectPole();
            Imgproc.rectangle(maskedInputMat, holyBox.boundingRect(), new Scalar(0, 255, 0));

            POLE_Y = -1 * (holyBox.center.y - input.height()/2.0);
        }



        Imgproc.drawContours(maskedInputMat, contors, -1, new Scalar(255,255,255));

        /*for (MatOfPoint point: contors) {
            point.convertTo(mp2f, CvType.CV_32F);
            bounds = Imgproc.minAreaRect(mp2f);
            Imgproc.rectangle(maskedInputMat, bounds.boundingRect(), new Scalar(255, 0, 0));
            Imgproc.putText(maskedInputMat, "width: "+ bounds.boundingRect().width, bounds.boundingRect().tl(), 0, 0.4, new Scalar(0, 0, 255));
        }*/

        /*MatOfPoint largestContour = max();

        if (largestContour != null) {
            largestContour.convertTo(largestContour2f, CvType.CV_32F);
            bounds = Imgproc.minAreaRect(largestContour2f);
            //Imgproc.rectangle(input, bounds.boundingRect(), new Scalar(255, 0, 0));
            //Imgproc.putText(maskedInputMat, "id: 0", bounds.boundingRect().tl(), 0, 0.5, new Scalar(0, 255, 0));
        }*/

        return maskedInputMat;
    }


    private MatOfPoint max() {
        if (contors.size() > 0) {
            MatOfPoint largest = contors.get(0);
            for (MatOfPoint point : contors.subList(1, contors.size())) {
                if (point.size().width > largest.size().width) {
                    largest = point;
                }
            }
            return largest;
        }
        return null;
    }

    private void taperEdges() {
        for (RotatedRect box : boundingBoxes) {
            if (box.boundingRect().width > box.boundingRect().height
                    && box.boundingRect().width > 50) {
                // now is a rectangle that is tall
                boundingBoxesClean.add(box);
            }
        }
    }


    private void selectPole() {
        selected = boundingBoxesClean.get(0);
        for (RotatedRect box: boundingBoxesClean.subList(1, boundingBoxesClean.size())) {
            if (box.boundingRect().area() > selected.boundingRect().area()) {
                selected = box;
            }
        }
        holyBox = selected;
    }
}
