package org.firstinspires.ftc.teamcode.test;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.robot.MechController;
import org.firstinspires.ftc.teamcode.robot.MechState;
import org.firstinspires.ftc.teamcode.robot.RobotHardware;
import org.firstinspires.ftc.teamcode.robot.VisionController;
import org.firstinspires.ftc.vision.VisionPortal;

@Autonomous(name = "SarahFigure8Path", group = "test")
public class SarahFigure8Path extends OpMode {

    RobotHardware robot;
    MechController mechController;
    VisionController visionController;
    private VisionPortal visionPortal;

    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;
    private int pathState;

    private final Pose startPose = new Pose(59, 14, Math.toRadians(0));

    private final Pose a1 = new Pose(118, 13, Math.toRadians(0));
    private final Pose a2 = new Pose(128, 44, Math.toRadians(155));
    private final Pose a3 = new Pose(18, 97, Math.toRadians(155));
    private final Pose a4 = new Pose(21, 127, Math.toRadians(0));
    private final Pose a5 = new Pose(120, 127, Math.toRadians(0));
    private final Pose a6 = new Pose(126, 100, Math.toRadians(-155));
    private final Pose a7 = new Pose(15, 43, Math.toRadians(-155));
    private final Pose a8 = new Pose(22, 14, Math.toRadians(0));

    private final Pose endPose = new Pose(58, 12, Math.toRadians(0));


    private Path start;
    private PathChain A1, A2, A3, A4, A5, A6, A7, A8;

    public void buildPaths() {
        start = new Path(new BezierLine(startPose, a1));
        start.setLinearHeadingInterpolation(startPose.getHeading(), a1.getHeading());

        A1 = follower.pathBuilder()
                .addPath(new BezierLine(a1, a2))
                .setLinearHeadingInterpolation(a1.getHeading(), a2.getHeading())
                .build();

        A2 = follower.pathBuilder()
                .addPath(new BezierLine(a2, a3))
                .setLinearHeadingInterpolation(a2.getHeading(), a3.getHeading())
                .build();

        A3 = follower.pathBuilder()
                .addPath(new BezierLine(a3, a4))
                .setLinearHeadingInterpolation(a3.getHeading(), a4.getHeading())
                .build();

        A4 = follower.pathBuilder()
                .addPath(new BezierLine(a4, a5))
                .setLinearHeadingInterpolation(a4.getHeading(), a5.getHeading())
                .build();

        A5 = follower.pathBuilder()
                .addPath(new BezierLine(a5, a6))
                .setLinearHeadingInterpolation(a5.getHeading(), a6.getHeading())
                .build();

        A6 = follower.pathBuilder()
                .addPath(new BezierLine(a6, a7))
                .setLinearHeadingInterpolation(a6.getHeading(), a7.getHeading())
                .build();

        A7 = follower.pathBuilder()
                .addPath(new BezierLine(a7, a8))
                .setLinearHeadingInterpolation(a7.getHeading(), a8.getHeading())
                .build();

        A8 = follower.pathBuilder()
                .addPath(new BezierLine(a8, endPose))
                .setLinearHeadingInterpolation(a8.getHeading(), endPose.getHeading())
                .build();
    }
    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                follower.followPath(start);
                setPathState(1);
                break;
            case 1:
                if(!follower.isBusy()) {
                    follower.followPath(A1, true);
                    setPathState(2);
                }
                break;
            case 2:
                if(!follower.isBusy()) {
                    follower.followPath(A2,true);
                    setPathState(3);
                }
                break;
            case 3:
                if(!follower.isBusy()) {
                    follower.followPath(A3,true);
                    setPathState(4);
                }
                break;
            case 4:
                if(!follower.isBusy()) {
                    follower.followPath(A4,true);
                    setPathState(5);
                }
                break;
            case 5:
                if(!follower.isBusy()) {
                    follower.followPath(A5,true);
                    setPathState(6);
                }
                break;
            case 6:
                if(!follower.isBusy()) {
                    follower.followPath(A6,true);
                    setPathState(7);
                }
                break;
            case 7:
                if(!follower.isBusy()) {
                    follower.followPath(A7,true);
                    setPathState(8);
                }
                break;
            case 8:
                if(!follower.isBusy()) {
                    follower.followPath(A8,true);
                    setPathState(9);
                }
                break;
            case 9:
                if(!follower.isBusy()) {
                    setPathState(-1);
                }
                break;

        }
    }

    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }

    @Override
    public void loop() {
        mechController.update();
        follower.update();
        autonomousPathUpdate();

        MechState state = mechController.getCurrentState();
        if (state == MechState.SHOOT_STATE || state == MechState.APRIL_TAG) {
            follower.setMaxPower(0.0);
        } else if (state == MechState.INTAKE_STATE) {
            follower.setMaxPower(MechController.INTAKE_DRIVE_POWER);
        } else {
            follower.setMaxPower(MechController.FULL_DRIVE_POWER);
        }

        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        mechController.allTelemetry();
    }

    @Override
    public void init() {
        robot = new RobotHardware(hardwareMap, telemetry);

        visionController = new VisionController(robot);
        visionController.initAprilTag();
        visionPortal = visionController.getVisionPortal();

        mechController = new MechController(robot, visionController);
        mechController.handleMechState(MechState.START);

        telemetry.addData("Status", "Initialized. Detecting April Tag....");
        telemetry.update();

        pathTimer = new Timer();
        actionTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();


        follower = Constants.createFollower(hardwareMap);
        buildPaths();
        follower.setStartingPose(startPose);
    }

    @Override
    public void init_loop() {
        mechController.update();
        mechController.allTelemetry();
    }

    @Override
    public void start() {
        opmodeTimer.resetTimer();
        setPathState(0);
    }

    @Override
    public void stop() {
        visionPortal.stopStreaming();
        mechController.setLifter(0);
        mechController.setIndexer(MechController.INTAKE[0]);
    }
}

