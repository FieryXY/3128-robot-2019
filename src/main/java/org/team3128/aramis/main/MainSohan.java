//AUTHORSES:
//MASON HOLST
//jude
//caylin
//daniel
//tyler
//teo

//not adham :/

package org.team3128.aramis.main;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import org.team3128.athos.autonomous.*;
import org.team3128.athos.util.PrebotDeepSpaceConstants;

import org.team3128.common.NarwhalRobot;
import org.team3128.common.drive.DriveCommandRunning;
import org.team3128.common.drive.SRXTankDrive;
import org.team3128.common.drive.SRXTankDrive.Wheelbase;
import org.team3128.common.drive.calibrationutility.DriveCalibrationUtility;
import org.team3128.common.hardware.limelight.Limelight;
import org.team3128.common.hardware.navigation.Gyro;
import org.team3128.common.hardware.navigation.NavX;
import org.team3128.common.util.Constants;
import org.team3128.common.util.units.Angle;
import org.team3128.common.util.units.Length;
import org.team3128.common.vision.CmdHorizontalOffsetFeedbackDrive;
import org.team3128.gromit.util.DeepSpaceConstants;
import org.team3128.common.util.Log;
import org.team3128.common.util.RobotMath;
import org.team3128.common.util.datatypes.PIDConstants;
import org.team3128.common.narwhaldashboard.NarwhalDashboard;
import org.team3128.common.listener.ListenerManager;
import org.team3128.common.listener.controllers.ControllerExtreme3D;
import org.team3128.common.listener.controltypes.Button;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.team3128.aramis.autonomous.CmdAutoTest;
import org.team3128.aramis.autonomous.CmdObstacleCourse;
    
    //start typing the stuff to make this a robot that isn't non-functional and bad and blank and boring and stuff thanks lol
        // - Mason Holst, "Helpful Reminders", published November 2019

    //This code was made in association with the Starbucks' Developer Crew (TM).
    
    public class MainSohan extends NarwhalRobot {
    
        //TalonSRX testMotor;

        TalonSRX leftDriveLeader;
        TalonSRX rightDriveLeader;
        VictorSPX rightDriveFollower;
        VictorSPX leftDriveFollower;

        ListenerManager lm;
        Joystick joystick;
        SRXTankDrive tankDrive;

    
        @Override
        protected void constructHardware()
        {
            double wheelCirc = 13.21 * Length.in;
            double wheelBase = 32.3 * Length.in;
            int robotFreeSpeed = 3700;

            leftDriveLeader = new TalonSRX(13);
            rightDriveLeader = new TalonSRX(15);
            leftDriveFollower = new VictorSPX(5);
            rightDriveFollower = new VictorSPX(6);


            rightDriveLeader.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, Constants.CAN_TIMEOUT);
            leftDriveLeader.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, Constants.CAN_TIMEOUT);

            rightDriveFollower.set(ControlMode.Follower, rightDriveLeader.getDeviceID());
            leftDriveFollower.set(ControlMode.Follower, leftDriveLeader.getDeviceID());

            leftDriveLeader.setInverted(false);
            leftDriveFollower.setInverted(false);
            rightDriveLeader.setInverted(true);
            rightDriveFollower.setInverted(false);


            //testMotor = new TalonSRX(11);
            SRXTankDrive.initialize(leftDriveLeader, rightDriveLeader, wheelCirc, wheelBase, robotFreeSpeed);
            tankDrive = SRXTankDrive.getInstance();

            joystick = new Joystick(1);
            lm = new ListenerManager(joystick);
            addListenerManager(lm);
        }
        
        @Override
        protected void constructAutoPrograms() {
            NarwhalDashboard.addAuto("Auto Thing-a-majig", new CmdAutoTest());
            NarwhalDashboard.addAuto("Obstacle Crusher", new CmdObstacleCourse());
        }
    
        @Override
        protected void setupListeners() {

        //Joystick Controls and Listeners
        lm.nameControl(ControllerExtreme3D.TWIST, "MoveTurn");
		lm.nameControl(ControllerExtreme3D.JOYY, "MoveForwards");
        lm.nameControl(ControllerExtreme3D.THROTTLE, "Throttle");
        //lm.nameControl(new Button(3), "Command");
        lm.nameControl(new Button(3), "Obstacle Crusher");

        //Chocolate.beEaten()
        //if(chocolate.notEaten()) {Life.destroyWorld();}
        //That is the code of life
        /*lm.addButtonDownListener("Command", () -> {
            Command thingCommand = new CmdAutoTest();
            thingCommand.start();
        });*/

        lm.addButtonDownListener("Obstacle Crusher", () -> {
            Command thingCommand = new CmdObstacleCourse();
            thingCommand.start();
        });

        lm.addMultiListener(() -> {
                tankDrive.arcadeDrive(
                    -0.7 * RobotMath.thresh(lm.getAxis("MoveTurn"), 0.1),
                    -1.0 * RobotMath.thresh(lm.getAxis("MoveForwards"), 0.1),
                    -1.0 * lm.getAxis("Throttle"),
                     true
                );		
			
        }, "MoveTurn", "MoveForwards", "Throttle");

        //Button Controls and Listeners
            /*lm.nameControl(new Button(11), "moveForward");
            lm.addButtonDownListener("moveForward", () -> {
                testMotor.set(ControlMode.PercentOutput, 100);
            });
            lm.addButtonUpListener("moveForward", () -> {
                testMotor.set(ControlMode.PercentOutput, 0);
            });
    
            lm.nameControl(new Button(12), "moveBack");
            lm.addButtonDownListener("moveBack", () -> {
                testMotor.set(ControlMode.PercentOutput, -100);
            });
            lm.addButtonUpListener("moveBack", () -> {
                testMotor.set(ControlMode.PercentOutput, 0);
            });*/
        }
    
        @Override
        protected void teleopPeriodic() {
        }
    
        @Override
        protected void updateDashboard() {

        }
        
    
    
        public static void main(String[] args) {
            RobotBase.startRobot(MainSohan::new);
        }
    }