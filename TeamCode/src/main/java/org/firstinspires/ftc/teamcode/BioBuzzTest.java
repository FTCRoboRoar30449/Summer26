package org.firstinspires.ftc.teamcode;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.BezierCurve;
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

@Autonomous (name = "BioBuzzTest")
public class BioBuzzTest extends OpMode {
    RobotHardware robot;
    MechController mechController;
    VisionController visionController;
    private VisionPortal visionPortal;
    private Follower follower;
    private Timer pathTimer, opModeTimer;

    public enum PathState {
    //sp = starting position
    //sh = shoot position
    //inp = intake position
    //in = intake
    //sh2 = shoot position two
    //ep = end position
    DRIVE_SP_SH,
    DRIVE_SH_INP,
    DRIVE_INP_IN,
    DRIVE_IN_SH2,
    DRIVE_SH2_EP,
    IDLE,
    }

    private PathState pathState;

    private final Pose startPose = new Pose(56.69533169533169,8,Math.toRadians(90));
    private final Pose shootOne = new Pose(56.69533169533169,33.91400491400491,Math.toRadians(90));
    private final Pose intakePose = new Pose(17.90343308507371,1.508136421222352,Math.toRadians(0));
    private final Pose intakeAction = new Pose(3.6965601965601973,1.24078624078623,Math.toRadians(0));
    private final Pose shootTwo = new Pose(59.49140049140049,104.53685503685503, Math.toRadians(270));
    private final Pose endPoint = new Pose(5.111793611793616,104.68058968058968, Math.toRadians(0));
    private final Pose controlPoint = new Pose(40.57371007371006,140.84889434889436);

    private PathChain driveSpSh, driveShInp, driveInpIn, driveInSh2, driveSh2Ep;

    public void buildPaths() {
        driveSpSh = follower.pathBuilder()
                .addPath(new BezierLine(startPose, shootOne))
                .setLinearHeadingInterpolation(startPose.getHeading(), shootOne.getHeading())
                .build();
        driveShInp = follower.pathBuilder()
                .addPath(new BezierLine(shootOne, intakePose))
                .setLinearHeadingInterpolation(shootOne.getHeading(), intakePose.getHeading())
                .build();
        driveInpIn = follower.pathBuilder()
                .addPath(new BezierLine(intakePose, intakeAction))
                .setLinearHeadingInterpolation(intakePose.getHeading(),intakeAction.getHeading())
                .build();
        driveInSh2 = follower.pathBuilder()
                .addPath(new BezierCurve(intakeAction,controlPoint,shootTwo))
                .setLinearHeadingInterpolation(intakeAction.getHeading(), shootTwo.getHeading())
                .build();
        driveSh2Ep = follower.pathBuilder()
                .addPath(new BezierLine(shootTwo,endPoint))
                .setLinearHeadingInterpolation(shootTwo.getHeading(),endPoint.getHeading())
                .build();
    }

    public void statePathUpdate() {
        switch (pathState) {
            case DRIVE_SP_SH:
                follower.followPath(driveSpSh, true);
                mechController.setState(MechState.SHOOT_STATE);
                setPathState(PathState.DRIVE_SH_INP);
                break;
            case DRIVE_SH_INP:
                if (!follower.isBusy()) {
                    follower.followPath(driveShInp, true);
                    setPathState(PathState.DRIVE_INP_IN);
                    break;
                }
            case DRIVE_INP_IN:
                if (!follower.isBusy()) {
                    follower.followPath(driveInpIn, true);
                    mechController.setState(MechState.INTAKE_STATE);
                    setPathState(PathState.DRIVE_IN_SH2);
                    break;
                }
            case DRIVE_IN_SH2:
                if (!follower.isBusy()) {
                    follower.followPath(driveInSh2, true);
                    mechController.setState(MechState.SHOOT_STATE);
                    setPathState(PathState.DRIVE_SH2_EP);
                    break;
                }
            case DRIVE_SH2_EP:
                if (!follower.isBusy()) {
                    follower.followPath(driveSh2Ep, true);
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


    @Override
    public void init() {
        pathState = PathState.DRIVE_SP_SH;
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



    @Override
    public void loop(){
            follower.update();
            statePathUpdate();
            mechController.update();
            telemetry.addData("path state", pathState.toString());
            telemetry.addData("x", follower.getPose().getX());
            telemetry.addData("y", follower.getPose().getY());
            telemetry.addData("heading", follower.getPose().getHeading());
            telemetry.addData("Path time", pathTimer.getElapsedTimeSeconds());
    }

    @Override
    public void start() {
        opModeTimer.resetTimer();
        setPathState(pathState);
    }

    public void setPathState(PathState newState){
        pathState = newState;
        pathTimer.resetTimer();
    }

}
