

        package org.firstinspires.ftc.teamcode.test;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@Autonomous(name = "NiyatiChallenge1", group = "Blue")
public class NiyatiChallenge1 extends OpMode {

    private Follower follower;
    private Timer pathTimer;

    private int pathState = 0;

    // Figure-8 points (temporary coordinates)
    private final Pose startPose = new Pose(20,20,Math.toRadians(90));

    private final Pose pollen1 = new Pose(35,40,Math.toRadians(90));
    private final Pose pollen2 = new Pose(20,60,Math.toRadians(90));
    private final Pose pollen3 = new Pose(35,80,Math.toRadians(90));
    private final Pose pollen4 = new Pose(50,60,Math.toRadians(90));
    private final Pose pollen5 = new Pose(35,40,Math.toRadians(90));

    private final Pose endPose = new Pose(20,20,Math.toRadians(90));


    private Path path1;
    private Path path2;
    private Path path3;
    private Path path4;
    private Path path5;
    private Path path6;


    public void buildPaths() {

        path1 = new Path(new BezierLine(startPose, pollen1));
        path1.setLinearHeadingInterpolation(
                startPose.getHeading(),
                pollen1.getHeading()
        );

        path2 = new Path(new BezierLine(pollen1, pollen2));
        path2.setLinearHeadingInterpolation(
                pollen1.getHeading(),
                pollen2.getHeading()
        );

        path3 = new Path(new BezierLine(pollen2, pollen3));
        path3.setLinearHeadingInterpolation(
                pollen2.getHeading(),
                pollen3.getHeading()
        );

        path4 = new Path(new BezierLine(pollen3, pollen4));
        path4.setLinearHeadingInterpolation(
                pollen3.getHeading(),
                pollen4.getHeading()
        );

        path5 = new Path(new BezierLine(pollen4, pollen5));
        path5.setLinearHeadingInterpolation(
                pollen4.getHeading(),
                pollen5.getHeading()
        );

        path6 = new Path(new BezierLine(pollen5, endPose));
        path6.setLinearHeadingInterpolation(
                pollen5.getHeading(),
                endPose.getHeading()
        );
    }


    public void autonomousPathUpdate() {

        switch(pathState){

            case 0:
                follower.followPath(path1);
                pathState++;
                break;

            case 1:
                if(!follower.isBusy()){
                    follower.followPath(path2);
                    pathState++;
                }
                break;

            case 2:
                if(!follower.isBusy()){
                    follower.followPath(path3);
                    pathState++;
                }
                break;

            case 3:
                if(!follower.isBusy()){
                    follower.followPath(path4);
                    pathState++;
                }
                break;

            case 4:
                if(!follower.isBusy()){
                    follower.followPath(path5);
                    pathState++;
                }
                break;

            case 5:
                if(!follower.isBusy()){
                    follower.followPath(path6);
                    pathState++;
                }
                break;
        }
    }


    @Override
    public void init(){

        follower = Constants.createFollower(hardwareMap);

        pathTimer = new Timer();

        buildPaths();

        follower.setStartingPose(startPose);
    }


    @Override
    public void start(){

        pathState = 0;
    }


    @Override
    public void loop(){

        follower.update();

        autonomousPathUpdate();

        follower.setMaxPower(0.4);

        telemetry.addData("X", follower.getPose().getX());
        telemetry.addData("Y", follower.getPose().getY());
        telemetry.addData("Heading", follower.getPose().getHeading());

        telemetry.update();
    }
}