package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.TelemetryManager;
import com.bylazar.telemetry.PanelsTelemetry;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.follower.Follower;
import com.pedropathing.paths.PathChain;
import com.pedropathing.geometry.Pose;
import com.pedropathing.util.Timer;

@Autonomous(name = "Pedro Pathing Autonomous", group = "Autonomous")
@Configurable // Panels
public class navya_challenge1 extends OpMode {
    private TelemetryManager panelsTelemetry; // Panels Telemetry instance
    public Follower follower; // Pedro Pathing follower instance
    private int pathState; // Current autonomous path state (state machine)
    private Timer pathTimer; // Timer for state machine
    private Paths paths; // Paths defined in the Paths class

    @Override
    public void init() {
        panelsTelemetry = PanelsTelemetry.INSTANCE.getTelemetry();

        pathTimer = new Timer();

        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(new Pose(56, 8, Math.toRadians(90)));

        paths = new Paths(follower); // Build paths

        panelsTelemetry.debug("Status", "Initialized");
        panelsTelemetry.update(telemetry);
    }

    @Override
    public void start() {
        setPathState(0);
    }

    @Override
    public void loop() {
        follower.update(); // Update Pedro Pathing
        pathState = autonomousPathUpdate(); // Update autonomous state machine

        // Log values to Panels and Driver Station
        panelsTelemetry.debug("Path State", pathState);
        panelsTelemetry.debug("X", follower.getPose().getX());
        panelsTelemetry.debug("Y", follower.getPose().getY());
        panelsTelemetry.debug("Heading", follower.getPose().getHeading());
        panelsTelemetry.update(telemetry);
    }

    public static class Paths {
        public PathChain mainChain;

        public Paths(Follower follower) {
            mainChain = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(56.000, 8.000),
                                    new Pose(71.004, 7.049)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .addPath(
                            new BezierCurve(
                                    new Pose(71.004, 7.049),
                                    new Pose(119.326, 7.966),
                                    new Pose(118.000, 35.000)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .addPath(
                            new BezierCurve(
                                    new Pose(118.000, 35.000),
                                    new Pose(103.061, 66.964),
                                    new Pose(71.000, 70.000)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .addPath(
                            new BezierCurve(
                                    new Pose(71.000, 70.000),
                                    new Pose(31.804, 77.542),
                                    new Pose(24.000, 105.622)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .addPath(
                            new BezierCurve(
                                    new Pose(24.000, 105.622),
                                    new Pose(43.061, 129.739),
                                    new Pose(70.574, 129.492)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .addPath(
                            new BezierCurve(
                                    new Pose(70.574, 129.492),
                                    new Pose(102.580, 130.490),
                                    new Pose(117.378, 105.622)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .addPath(
                            new BezierCurve(
                                    new Pose(117.378, 105.622),
                                    new Pose(102.506, 75.354),
                                    new Pose(70.705, 69.917)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .addPath(
                            new BezierCurve(
                                    new Pose(70.705, 69.917),
                                    new Pose(35.672, 65.149),
                                    new Pose(23.555, 35.257)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .addPath(
                            new BezierCurve(
                                    new Pose(23.555, 35.257),
                                    new Pose(29.596, 11.039),
                                    new Pose(55.905, 7.847)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .build();
        }
    }

    /**
     * This method updates the autonomous state machine.
     * It returns the current path state.
     */
    public int autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                follower.followPath(paths.mainChain);
                setPathState(1);
                break;
            case 1:
                if (!follower.isBusy()) {
                    setPathState(-1); // End of autonomous
                }
                break;
            default:
                // Finished or unknown state
                break;
        }
        return pathState;
    }

    /**
     * This helper method sets the path state and resets the timer.
     * @param state The new state to transition to.
     */
    public void setPathState(int state) {
        pathState = state;
        pathTimer.resetTimer();
    }
}