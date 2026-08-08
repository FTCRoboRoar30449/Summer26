package org.firstinspires.ftc.teamcode;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
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

@Autonomous (name = "SaanviChallengeTwo")
public class SaanviChallengeTwo extends OpMode {

    RobotHardware robot;
    MechController mechController;

    VisionController visionController;
    private VisionPortal visionPortal;

    private Follower follower;
    private Timer pathTimer, opModeTimer;  // Allows robot to do multiple things at once. Ex. checking sensors while running robot

    // using an enum allows us to name our states of FSMs/cases
    public enum PathState {
        // SP -> start position
        // DRIVE -> is moving the robot
        // R* -> Row
        // F* -> Fetch (Intake)
        //EP --> end point

        DRIVE_SP_R1,
        DRIVE_R1_F1,
        DRIVE_F1_R2,
        DRIVE_R2_F2,
        DRIVE_F2_R3,
        DRIVE_R3_F3,
        DRIVE_F3_R4,
        DRIVE_R4_F4,
        DRIVE_F4_R5,
        DRIVE_R5_F5,
        DRIVE_F5_EP,
        IDLE,
    }

    PathState pathState;

    private final Pose startPose = new Pose(56, 8, Math.toRadians(90));
    private final Pose rowOne = new Pose(47.30315614617941, 23.77740863787377, Math.toRadians(0));
    private final Pose fetchOne = new Pose(11.98165103611974, 23.67246405457895, Math.toRadians(0));
    private final Pose rowTwo = new Pose(23.975542138185215, 79.68189368770761, Math.toRadians(-90));
    private final Pose fetchTwo = new Pose(23.96191301032302, 132.98569430802408, Math.toRadians(-90));
    private final Pose rowThree = new Pose(116.91684010004619, 133.04707640052214, Math.toRadians(90));
    private final Pose fetchThree = new Pose(117.80672299358369, 88.34423914377855, Math.toRadians(90));
    private final Pose rowFour = new Pose(84.35772426991444,23.679898820276442, Math.toRadians(180));
    private final Pose fetchFour = new Pose(124.85738185159596,23.813317217556243, Math.toRadians(180));
    private final Pose rowFive = new Pose(70.51540924781976, 31.261627906976738, Math.toRadians(-90));
    private final Pose fetchFive = new Pose (70.74804215577166, 64.88925222692224, Math.toRadians(-90));
    private final Pose endPose = new Pose (71.13443244853701, 117.81745435125326, Math.toRadians(-90));

    private PathChain driveSpR1, driveR1F1, driveF1R2, driveR2F2, driveF2R3, driveR3F3, driveF3R4, driveR4F4, driveF4R5, driveR5F5, driveF5EP;

    public void buildPaths() {
        driveSpR1 = follower.pathBuilder()
                .addPath(new BezierLine(startPose, rowOne))
                .setLinearHeadingInterpolation(startPose.getHeading(), rowOne.getHeading())
                .build();
        driveR1F1 = follower.pathBuilder()
                .addPath(new BezierLine(rowOne, fetchOne))
                .setLinearHeadingInterpolation(rowOne.getHeading(), fetchOne.getHeading())
                .build();
        driveF1R2 = follower.pathBuilder()
                .addPath(new BezierLine(fetchOne, rowTwo))
                .setLinearHeadingInterpolation(fetchOne.getHeading(), rowTwo.getHeading())
                .build();
        driveR2F2 = follower.pathBuilder()
                .addPath(new BezierLine(rowTwo, fetchTwo))
                .setLinearHeadingInterpolation(rowTwo.getHeading(), fetchTwo.getHeading())
                .build();
        driveF2R3 = follower.pathBuilder()
                .addPath(new BezierLine(fetchTwo, rowThree))
                .setLinearHeadingInterpolation(fetchTwo.getHeading(), rowThree.getHeading())
                .build();
        driveR3F3 = follower.pathBuilder()
                .addPath(new BezierLine(rowThree, fetchThree))
                .setLinearHeadingInterpolation(rowThree.getHeading(), fetchThree.getHeading())
                .build();
        driveF3R4 = follower.pathBuilder()
                .addPath(new BezierLine(fetchThree, rowFour))
                .setLinearHeadingInterpolation(fetchThree.getHeading(), rowFour.getHeading())
                .build();
        driveR4F4 = follower.pathBuilder()
                .addPath(new BezierLine(rowThree, fetchThree))
                .setLinearHeadingInterpolation(rowThree.getHeading(), fetchThree.getHeading())
                .build();
        driveF4R5 = follower.pathBuilder()
                .addPath(new BezierLine(fetchFour, rowFive))
                .setLinearHeadingInterpolation(rowThree.getHeading(), fetchThree.getHeading())
                .build();
        driveR5F5 = follower.pathBuilder()
                .addPath(new BezierLine(rowFive, fetchFive))
                .setLinearHeadingInterpolation(rowFive.getHeading(), fetchFive.getHeading())
                .build();
        driveF5EP = follower.pathBuilder()
                .addPath(new BezierLine(fetchFive, endPose))
                .setLinearHeadingInterpolation(fetchFive.getHeading(), endPose.getHeading())
                .build();
    }

    public void statePathUpdate() {
        switch (pathState) {
            case DRIVE_SP_R1:
                follower.followPath(driveSpR1, true);
                setPathState(PathState.DRIVE_R1_F1);
                break;
            case DRIVE_R1_F1:
                if (!follower.isBusy()) {
                    follower.followPath(driveR1F1, true);
                    mechController.setState(MechState.INTAKE_STATE);
                    setPathState(PathState.DRIVE_F1_R2);
                    break;
                }
            case DRIVE_F1_R2:
                if (!follower.isBusy()) {
                    follower.followPath(driveF1R2, true);
                    setPathState(PathState.DRIVE_R2_F2);
                    break;
                }
            case DRIVE_R2_F2:
                if (!follower.isBusy()) {
                    follower.followPath(driveR2F2, true);
                    mechController.setState(MechState.INTAKE_STATE);
                    setPathState(PathState.DRIVE_F2_R3);
                    break;
                }
            case DRIVE_F2_R3:
                if (!follower.isBusy()) {
                    follower.followPath(driveF2R3, true);
                    setPathState(PathState.DRIVE_R3_F3);
                    break;
                }
            case DRIVE_R3_F3:
                if (!follower.isBusy()) {
                    follower.followPath(driveR3F3, true);
                    mechController.setState(MechState.INTAKE_STATE);
                    setPathState(PathState.DRIVE_F3_R4);
                    break;
                }
            case DRIVE_F3_R4:
                if (!follower.isBusy()) {
                    follower.followPath(driveF3R4, true);
                    setPathState(PathState.DRIVE_R4_F4);
                    break;
                }
            case DRIVE_R4_F4:
                if (!follower.isBusy()) {
                    follower.followPath(driveR4F4, true);
                    mechController.setState(MechState.INTAKE_STATE);
                    setPathState(PathState.DRIVE_F4_R5);
                    break;
                }
            case DRIVE_F4_R5:
                if (!follower.isBusy()) {
                    follower.followPath(driveF4R5, true);
                    setPathState(PathState.DRIVE_R5_F5);
                    break;
                }
            case DRIVE_R5_F5:
                if (!follower.isBusy()) {
                    follower.followPath(driveR5F5, true);
                    mechController.setState(MechState.INTAKE_STATE);
                    setPathState(PathState.DRIVE_F5_EP);
                    break;
                }
            case DRIVE_F5_EP:
                if (!follower.isBusy()) {
                    follower.followPath(driveF5EP, true);
                    setPathState(PathState.IDLE);
                    break;
                }
            case IDLE:
                if (!follower.isBusy()) {
                    telemetry.addLine("DONE!");
                    setPathState(PathState.IDLE);
                    break;
                }
            default:
                telemetry.addLine("No State Commanded");
                break;
        }
    }


    public void setPathState(PathState newState) {
        pathState = newState;
        pathTimer.resetTimer();
    }


    @Override        // Makes OpMode valid
    public void init() {
        pathState = PathState.DRIVE_SP_R1;
        pathTimer    = new Timer();
        opModeTimer = new Timer();
        robot = new RobotHardware(hardwareMap, telemetry);
        follower = Constants.createFollower(hardwareMap);
        visionController = new VisionController(robot);
        visionController.initAprilTag();
        visionPortal = visionController.getVisionPortal();
        mechController = new MechController(robot, visionController);
        mechController.handleMechState(MechState.START);

        buildPaths();
        follower.setPose(startPose);
    }

    public void start() {
        opModeTimer.resetTimer();
        setPathState(pathState);
    }

    @Override       // Makes OpMode valid
    public void loop() {
        follower.update();
        statePathUpdate();
        mechController.update();
        telemetry.addData("path state", pathState.toString());
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.addData("Path time", pathTimer.getElapsedTimeSeconds());
    }
}
