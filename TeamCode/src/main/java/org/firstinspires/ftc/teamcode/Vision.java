package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.openftc.apriltag.AprilTagDetection;

import java.util.ArrayList;

public class Vision {
    private OpenCVPipelineRunner runner;
    private AprilTagDetectionPipeline pipeline;

    public Vision(HardwareMap hardwareMap) {
        pipeline = new AprilTagDetectionPipeline();
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

}


