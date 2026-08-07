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

@Autonomous(name = "SarahIntakeAndParking", group = "test")
public class SarahIntakeAndParking extends OpMode {

    RobotHardware robot;
    MechController mechController;
    VisionController visionController;
    private VisionPortal visionPortal;

    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;
    private int pathState;

    private final Pose startPose = new Pose(11, 82, Math.toRadians(-90));

    private final Pose a1 = new Pose(24, 88, Math.toRadians(-90));
    private final Pose a2 = new Pose(24, 126, Math.toRadians(-90));
    private final Pose a3 = new Pose(75, 118, Math.toRadians(-180));
    private final Pose a4 = new Pose(84, 118, Math.toRadians(-180));
    private final Pose a5 = new Pose(120, 118, Math.toRadians(-180));
    private final Pose a6 = new Pose(24, 88, Math.toRadians(90));
    private final Pose a7 = new Pose(24, 52, Math.toRadians(90));
    private final Pose a8 = new Pose(24, 12, Math.toRadians(90));
    private final Pose a9 = new Pose(75, 24, Math.toRadians(-180));
    private final Pose a10 = new Pose(84, 24, Math.toRadians(-180));
    private final Pose a11 = new Pose(121, 24, Math.toRadians(-180));
    private final Pose a12 = new Pose(85, 70, Math.toRadians(0));
    private final Pose a13 = new Pose(43, 70, Math.toRadians(0));

    private final Pose endPose = new Pose(118, 70, Math.toRadians(0));

    private Path start;
    private PathChain A1, A2, A3, A4 ,A5, A6, A7, A8, A9, A10, A11, A12, A13;

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
                .addPath(new BezierLine(a8, a9))
                .setLinearHeadingInterpolation(a8.getHeading(), a9.getHeading())
                .build();

        A9 = follower.pathBuilder()
                .addPath(new BezierLine(a9, a10))
                .setLinearHeadingInterpolation(a9.getHeading(), a10.getHeading())
                .build();

        A10 = follower.pathBuilder()
                .addPath(new BezierLine(a10, a11))
                .setLinearHeadingInterpolation(a10.getHeading(), a11.getHeading())
                .build();

        A11 = follower.pathBuilder()
                .addPath(new BezierLine(a11, a12))
                .setLinearHeadingInterpolation(a11.getHeading(), a12.getHeading())
                .build();

        A12 = follower.pathBuilder()
                .addPath(new BezierLine(a12, a13))
                .setLinearHeadingInterpolation(a12.getHeading(), a13.getHeading())
                .build();

        A13 = follower.pathBuilder()
                .addPath(new BezierLine(a13, endPose))
                .setLinearHeadingInterpolation(a13.getHeading(), endPose.getHeading())
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
                    mechController.setState(MechState.INTAKE_STATE); //Intake 1
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
                    mechController.setState(MechState.INTAKE_STATE); //Intake 2
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
                    mechController.setState(MechState.INTAKE_STATE); //Intake 3
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
                    follower.followPath(A9, true);
                    setPathState(10);
                }
                break;
            case 10:
                if(!follower.isBusy()) {
                    follower.followPath(A10,true);
                    mechController.setState(MechState.INTAKE_STATE); //Intake 4
                    setPathState(11);
                }
                break;
            case 11:
                if(!follower.isBusy()) {
                    follower.followPath(A11,true);
                    setPathState(12);
                }
                break;
            case 12:
                if(!follower.isBusy()) {
                    follower.followPath(A12,true);
                    mechController.setState(MechState.INTAKE_STATE); // Intake 5
                    setPathState(13);
                }
                break;
            case 13:
                if(!follower.isBusy()) {
                    follower.followPath(A13, true);
                    setPathState(14);
                }
                break;
            case 14:
                if(!follower.isBusy()) {
                    setPathState(-1);
                }
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