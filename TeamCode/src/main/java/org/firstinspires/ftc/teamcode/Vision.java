package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvTracker;

import java.util.ArrayList;

public class Vision {

    AprilTagDetectionPipeline pipeline;
    OpenCVPipelineRunner runner;

    public Vision(HardwareMap hardwareMap) {
        pipeline = new AprilTagDetectionPipeline();
        runner = new OpenCVPipelineRunner(hardwareMap, pipeline);
        runner.start();
    }

    public void close() {
        runner.close();
    }

   public void addTracker(OpenCvTracker tracker) {
        this.runner.addTracker(tracker);
   }

    public enum Signal {
        One,
        Two,
        Three
    }

    public Signal getSignal() {
        ArrayList<AprilTagDetection> detections = pipeline.getLatestDetections();
        if (detections.size() < 1) {
            return Signal.Two;
        }
        int id = detections.get(1).id;
        switch (id) {
            case 1: return Signal.One;
            case 2: return Signal.Two;
            case 3: return Signal.Three;
        }
        return Signal.Two;
    }
}
