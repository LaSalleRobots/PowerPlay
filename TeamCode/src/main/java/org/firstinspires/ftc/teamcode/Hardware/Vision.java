package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.openftc.apriltag.AprilTagDetection;

import java.util.ArrayList;

public class Vision {
    private OpenCVPipelineRunner runner;
    private AprilTagDetectionPipeline pipeline;
    private YellowCylinderDetector poleCenterDetection;

    public Vision(HardwareMap hardwareMap) {
        pipeline = new AprilTagDetectionPipeline();
//        poleCenterDetection = new PoleCenterDetection();
        poleCenterDetection = new YellowCylinderDetector();

        runner = new OpenCVPipelineRunner(hardwareMap, pipeline);
        runner.start();
    }

    public int getIdentifier() {
        ArrayList<AprilTagDetection> detections = pipeline.getLatestDetections();
        if (detections.size() == 0) {
            return -1;
        }
        return detections.get(0).id;
    }

    public void switchToPolesMode() {
        runner.close();
        runner.setPipeline(poleCenterDetection);
        runner.start();
    }

    public void stopCamera() {
        runner.close();
    }

    public double getPolePosition() {
        return poleCenterDetection.POLE_Y;
    }
}


