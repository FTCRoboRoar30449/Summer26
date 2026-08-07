package org.firstinspires.ftc.teamcode.test;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
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

@Autonomous(name = "SarahFigure8Curve", group = "test")
public class SarahFigure8Curve extends OpMode {
    RobotHardware robot;
    MechController mechController;
    VisionController visionController;
    private VisionPortal visionPortal;

    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;
    private int pathState;

    private final Pose startPose = new Pose(59, 14, Math.toRadians(0));
    private final Pose a1  = new Pose(95, 14, Math.toRadians(0));
    private final Pose a2  = new Pose(127, 37, Math.toRadians(90));
    private final Pose a3  = new Pose(100, 60, Math.toRadians(160));
    private final Pose a4  = new Pose(48, 81, Math.toRadians(160));
    private final Pose a5  = new Pose(16, 106, Math.toRadians(90));
    private final Pose a6  = new Pose(47, 128, Math.toRadians(0));
    private final Pose a7  = new Pose(100, 128, Math.toRadians(0));
    private final Pose a8  = new Pose(126, 106, Math.toRadians(-90));
    private final Pose a9  = new Pose(105, 83, Math.toRadians(-160));
    private final Pose a10 = new Pose(36, 58, Math.toRadians(-160));
    private final Pose a11 = new Pose(16, 37, Math.toRadians(-90));
    private final Pose a12 = new Pose(41, 14, Math.toRadians(0));
    private final Pose endPose = new Pose(59, 14, Math.toRadians(0));

    private Path start;
    private PathChain P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13;

    public void buildPaths() {

        start = new Path(new BezierLine(startPose, a1));
        start.setLinearHeadingInterpolation(startPose.getHeading(), a1.getHeading());

        P2 = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                a1,
                                new Pose(128.252, 11.820),
                                a2
                        )
                )
                .setLinearHeadingInterpolation(a1.getHeading(), a2.getHeading())
                .build();

        P3 = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                a2,
                                new Pose(129.102, 47.509),
                                a3
                        )
                )
                .setLinearHeadingInterpolation(a2.getHeading(), a3.getHeading())
                .build();

        P4 = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                a3,
                                a4
                        )
                )
                .setLinearHeadingInterpolation(a3.getHeading(), a4.getHeading())
                .build();

        P5 = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                a4,
                                new Pose(19.105, 91.457),
                                a5
                        )
                )
                .setLinearHeadingInterpolation(a4.getHeading(), a5.getHeading())
                .build();

        P6 = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                a5,
                                new Pose(14.831, 126.165),
                                a6
                        )
                )
                .setLinearHeadingInterpolation(a5.getHeading(), a6.getHeading())
                .build();

        P7 = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                a6,
                                a7
                        )
                )
                .setLinearHeadingInterpolation(a6.getHeading(), a7.getHeading())
                .build();

        P8 = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                a7,
                                new Pose(128.526, 127.527),
                                a8
                        )
                )
                .setLinearHeadingInterpolation(a7.getHeading(), a8.getHeading())
                .build();

        P9 = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                a8,
                                new Pose(124.101, 90.676),
                                a9
                        )
                )
                .setLinearHeadingInterpolation(a8.getHeading(), a9.getHeading())
                .build();

        P10 = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                a9,
                                a10
                        )
                )
                .setLinearHeadingInterpolation(a9.getHeading(), a10.getHeading())
                .build();

        P11 = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                a10,
                                new Pose(15.439, 53.783),
                                a11
                        )
                )
                .setLinearHeadingInterpolation(a10.getHeading(), a11.getHeading())
                .build();
        P12 = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                a11,
                                new Pose(20.783, 17.717),
                                a12
                        )
                )
                .setLinearHeadingInterpolation(a11.getHeading(), a12.getHeading())
                .build();
        P13 = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                a12,
                                endPose
                        )
                )
                .setLinearHeadingInterpolation(a12.getHeading(), endPose.getHeading())
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
                    follower.followPath(P2, true);
                    setPathState(2);
                }
                break;
            case 2:
                if(!follower.isBusy()) {
                    follower.followPath(P3,true);
                    setPathState(3);
                }
                break;
            case 3:
                if(!follower.isBusy()) {
                    follower.followPath(P4,true);
                    setPathState(4);
                }
                break;
            case 4:
                if(!follower.isBusy()) {
                    follower.followPath(P5,true);
                    setPathState(5);
                }
                break;
            case 5:
                if(!follower.isBusy()) {
                    follower.followPath(P6,true);
                    setPathState(6);
                }
                break;
            case 6:
                if(!follower.isBusy()) {
                    follower.followPath(P7,true);
                    setPathState(7);
                }
                break;
            case 7:
                if(!follower.isBusy()) {
                    follower.followPath(P8,true);
                    setPathState(8);
                }
                break;
            case 8:
                if (!follower.isBusy()) {
                    follower.followPath(P9, true);
                    setPathState(9);
                }
                break;

            case 9:
                if (!follower.isBusy()) {
                    follower.followPath(P10, true);
                    setPathState(10);
                }
                break;

            case 10:
                if (!follower.isBusy()) {
                    follower.followPath(P11, true);
                    setPathState(11);
                }
                break;

            case 11:
                if (!follower.isBusy()) {
                    follower.followPath(P12, true);
                    setPathState(12);
                }
                break;

            case 12:
                if (!follower.isBusy()) {
                    follower.followPath(P13, true);
                    setPathState(13);
                }
                break;

            case 13:
                if (!follower.isBusy()) {
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


