package org.firstinspires.ftc.teamcode.test;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.teamcode.field.Blue;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.robot.MechController;
import org.firstinspires.ftc.teamcode.robot.MechState;
import org.firstinspires.ftc.teamcode.robot.RobotHardware;
import org.firstinspires.ftc.teamcode.robot.VisionController;
import org.firstinspires.ftc.vision.VisionPortal;

@Autonomous(name = "NiyatiChallenge2", group = "Blue")
public class NiyatiChallenge2 extends OpMode {

    RobotHardware robot;
    MechController mechController;
    VisionController visionController;
    private VisionPortal visionPortal;

    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;
    private int pathState;

    // Challenge 2 simple positions

    private final Pose startPose =
            new Pose(20, 20, Math.toRadians(180));

    private final Pose pollen1 =
            new Pose(35, 20, Math.toRadians(180));

    private final Pose pollen2 =
            new Pose(50, 20, Math.toRadians(180));

    private final Pose pollen3 =
            new Pose(65, 20, Math.toRadians(180));

    private final Pose pollen4 =
            new Pose(80, 20, Math.toRadians(180));

    private final Pose pollen5 =
            new Pose(95, 20, Math.toRadians(180));

    private final Pose parkPose =
            new Pose(110, 40, Math.toRadians(90));


    private PathChain pollen1Path;
    private PathChain pollen2Path;
    private PathChain pollen3Path;
    private PathChain pollen4Path;
    private PathChain pollen5Path;
    private PathChain parkPath;

    public void buildPaths() {
        // Go through all 5 pollen groups, then park
        pollen1Path = follower.pathBuilder()
                .addPath(new BezierLine(startPose, pollen1))
                .setLinearHeadingInterpolation(
                        startPose.getHeading(),
                        pollen1.getHeading())
                .build();


        pollen2Path = follower.pathBuilder()
                .addPath(new BezierLine(pollen1, pollen2))
                .setLinearHeadingInterpolation(
                        pollen1.getHeading(),
                        pollen2.getHeading())
                .build();


        pollen3Path = follower.pathBuilder()
                .addPath(new BezierLine(pollen2, pollen3))
                .setLinearHeadingInterpolation(
                        pollen2.getHeading(),
                        pollen3.getHeading())
                .build();


        pollen4Path = follower.pathBuilder()
                .addPath(new BezierLine(pollen3, pollen4))
                .setLinearHeadingInterpolation(
                        pollen3.getHeading(),
                        pollen4.getHeading())
                .build();


        pollen5Path = follower.pathBuilder()
                .addPath(new BezierLine(pollen4, pollen5))
                .setLinearHeadingInterpolation(
                        pollen4.getHeading(),
                        pollen5.getHeading())
                .build();


        parkPath = follower.pathBuilder()
                .addPath(new BezierLine(pollen5, parkPose))
                .setLinearHeadingInterpolation(
                        pollen5.getHeading(),
                        parkPose.getHeading())
                .build();
    }

    public void autonomousPathUpdate() {

        switch (pathState) {

            case 0:
                follower.followPath(pollen1Path);
                mechController.setState(MechState.INTAKE_STATE);
                setPathState(1);
                break;


            case 1:
                if (!follower.isBusy()) {
                    follower.followPath(pollen2Path);
                    setPathState(2);
                }
                break;


            case 2:
                if (!follower.isBusy()) {
                    follower.followPath(pollen3Path);
                    setPathState(3);
                }
                break;


            case 3:
                if (!follower.isBusy()) {
                    follower.followPath(pollen4Path);
                    setPathState(4);
                }
                break;


            case 4:
                if (!follower.isBusy()) {
                    follower.followPath(pollen5Path);
                    setPathState(5);
                }
                break;


            case 5:
                if (!follower.isBusy()) {
                    follower.followPath(parkPath);
                    setPathState(6);
                }
                break;


            case 6:
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

        follower.setMaxPower(0.4);

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

        telemetry.addData("Status", "Challenge 2 Ready");
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