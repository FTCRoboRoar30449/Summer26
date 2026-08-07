package org.firstinspires.ftc.teamcode.test;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.robot.RobotHardware;

@Autonomous (name = "Saanvi Challenge One")
public class SaanviChallenge1 extends OpMode {
    RobotHardware robot;
    private Follower follower;
    private Timer pathTimer, opModeTimer;  // Allows robot to do multiple things at once. Ex. checking sensors while running robot

    // using an enum allows us to name our states of FSMs/cases
    public enum PathState {
        // SP -> start position
        // DRIVE -> is moving the robot
        // B* -> Ball

        DRIVE_SP_B1,
        DRIVE_B1_B2,
        DRIVE_B2_B3,
        DRIVE_B3_B4,
        DRIVE_B4_B5,
        DRIVE_B5_B6,
        DRIVE_B6_SP,

        IDLE,
    }

    PathState pathState;
    private final Pose startPose = new Pose(59.056,10.586, Math.toRadians(0));

    private PathChain driveSpB1, driveB1B2, driveB2B3, driveB3B4, driveB4B5, driveB5B6, driveB6Sp;

    public void buildPaths() {
        driveSpB1 = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                startPose, // start pose
                                new Pose(119.461, 6.666), // control point
                                new Pose(118.053, 34.355) // end pose
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();
        driveB1B2 = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(118.053, 34.355),
                                new Pose(111.400, 62.113),
                                new Pose(71.020, 70.362)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();
        driveB2B3 = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(71.020, 70.362),
                                new Pose(39.520, 75.848),
                                new Pose(22.008, 105.787)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();
        driveB3B4 = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(22.008, 105.787),
                                new Pose(36.531, 137.322),
                                new Pose(71.033, 133.241)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();
        driveB4B5 = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(71.033, 133.241),
                                new Pose(111.188, 133.214),
                                new Pose(118.193, 104.981)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();
        driveB5B6 = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(118.193, 104.981),
                                new Pose(118.722, 56.991),
                                new Pose(12.249, 79.607),
                                new Pose(22.770, 36.024)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();
        driveB6Sp = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(22.770, 36.024),
                                new Pose(28.695, 7.564),
                                new Pose(58.707, 10.362)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();
    }

    public void statePathUpdate() {
        switch (pathState) {
            case DRIVE_SP_B1:
                follower.followPath(driveSpB1, true);
                setPathState(PathState.DRIVE_B1_B2);
                break;
            case DRIVE_B1_B2:
                if (!follower.isBusy()) {
                    follower.followPath(driveB1B2, true);
                    setPathState(PathState.DRIVE_B2_B3);
                    break;
                }
            case DRIVE_B2_B3:
                if (!follower.isBusy()) {
                    follower.followPath(driveB2B3, true);
                    setPathState(PathState.DRIVE_B3_B4);
                    break;
                }

            case DRIVE_B3_B4:
                if (!follower.isBusy()) {
                    follower.followPath(driveB3B4, true);
                    setPathState(PathState.DRIVE_B4_B5);
                    break;
                }
            case DRIVE_B4_B5:
                if (!follower.isBusy()) {
                    follower.followPath(driveB4B5, true);
                    setPathState(PathState.DRIVE_B5_B6);
                    break;
                }
            case DRIVE_B5_B6:
                if (!follower.isBusy()) {
                    follower.followPath(driveB5B6, true);
                    setPathState(PathState.DRIVE_B6_SP);
                    break;
                }
            case DRIVE_B6_SP:
                if (!follower.isBusy()) {
                    follower.followPath(driveB6Sp, true);
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


    @Override
    public void init() {
        robot = new RobotHardware(hardwareMap, telemetry);
        pathState = PathState.DRIVE_SP_B1;
        pathTimer = new Timer();
        opModeTimer = new Timer();
        follower = Constants.createFollower(hardwareMap);
        follower.setMaxPower(0.2);
        follower.setPose(startPose);
        buildPaths();
    }
    @Override
    public void start() {
        opModeTimer.resetTimer();
        setPathState(pathState);
    }

    @Override       // Makes OpMode valid
    public void loop() {
        follower.update();
        statePathUpdate();

        follower.setMaxPower(0.4);


        telemetry.addData("path state", pathState.toString());
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.addData("Path time", pathTimer.getElapsedTimeSeconds());
        telemetry.update();

        telemetry.addData("Busy", follower.isBusy());
        telemetry.addData("Path State", pathState);
        telemetry.update();
    }
}