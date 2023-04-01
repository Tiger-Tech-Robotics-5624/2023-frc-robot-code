// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.Map;
import java.util.function.BooleanSupplier;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutonomousSubsystem {
  DriveSubsystem driveSub;
  IntakeSubsystem intakeSub;
  VisionSubsystem visionSub;
  ElevatorSubsystem elevatorSub;
  Timer timer; 

  boolean cube = true;
  boolean balance = false;

  private ShuffleboardLayout autoDrive;
  private ShuffleboardLayout autoStart;
  private GenericEntry drive;
  private GenericEntry start;
  
  boolean teleop = false;

  /** Creates a new AutonomousSubsystem. */
  public AutonomousSubsystem(DriveSubsystem drive, IntakeSubsystem intake, ElevatorSubsystem elevator) {
    driveSub = drive;
    intakeSub = intake;
    elevatorSub = elevator;
    visionSub = new VisionSubsystem();
    timer = new Timer();

    autoDrive = Shuffleboard.getTab("Autonomous").getLayout("Auto Balance").getLayout("Auto Drive Type", BuiltInLayouts.kList).withSize(1,2).withProperties(Map.of("Label position","HIDDEN"));
    autoStart = Shuffleboard.getTab("Autonomous").getLayout("Auto Balance").getLayout("Auto Start Type", BuiltInLayouts.kList).withSize(1,2).withProperties(Map.of("Label position","HIDDEN"));
    this.drive = autoDrive.add("Auto-Balancing?", false).withWidget(BuiltInWidgets.kToggleButton).getEntry();
    start = autoStart.add("Start with Cube?", true).withWidget(BuiltInWidgets.kToggleButton).getEntry();
  }

  public void periodic(){
    
  }

  public void start(){
    timer.reset();
    teleop = false;
    timer.start();
    driveSub.resetGyro();
    elevatorSub.zeroEncoder();
    intakeSub.zeroEncoder();
  }

  public void stop() {
    teleop = true;
    intakeSub.stop();
    intakeSub.stopLower();
    elevatorSub.stop();
    driveSub.stop();
  }
  //Autonomous Plan without limelight:
  //1. Move elevator up to slightly above Mid for CUBE/BALL
  //2. Lower intake and then spit out cube (Might need to be slow or fast)
  //3. Retract intake and then move elevator down
  //4. Drive across boarder
  
  public void runAutonomous() {

    //Testing
    // while(timer.get() < 1000) {
    //   SmartDashboard.putBoolean("Cube start", cube);
    //   SmartDashboard.putBoolean("Auto-Balance", balance);
    // }

    //Autonomous
    while(timer.get() < 15 && !teleop) {
      if(timer.get() > 0 && timer.get() < 1){
        elevatorSub.autoMove(-0.35);
      }
      else if(timer.get() > 1) {
        elevatorSub.stop();
      }

      //Starting configuration

      //Starting with cube
      if(cube) {
        if(timer.get() > 0 && timer.get() < 2.6) {
          intakeSub.autoLower(1, false);
        }
        else if(timer.get() > 2.6 && timer.get() < 3.6){
          intakeSub.autoLower(0,true);
          intakeSub.shoot(true);
        }
        else {
          intakeSub.stop();
        }
        if(timer.get() > 4 && timer.get() < 6) {
          intakeSub.autoLower(-0.38,false);
        }
        else {
          intakeSub.stopLower();
        }
      } 

      //Starting with cone
      else {
        if(timer.get() > 0 && timer.get() < 3.5) {
          intakeSub.autoLower(1, false);
        }
        else if(timer.get() > 3.5 && timer.get() < 4.5){
          intakeSub.autoLower(0,true);
          intakeSub.shoot(true);
        }
        else {
          intakeSub.stop();
        }
        if(timer.get() > 4.5 && timer.get() < 6) {
          intakeSub.autoLower(-0.38,false);
        }
        else {
          intakeSub.stopLower();
        }
      }

      //Drive Autonomous

      //Straight Drive
      if(!balance){
        if(timer.get() > 6 && timer.get() < 10.5 && !balance) {
          driveSub.autonomousDrive(-0.2, 0);
        }
        else {
          driveSub.stop();
        }
      }

      //Auto-balance??
      else {
        if(timer.get()>6 && timer.get()<8){
          driveSub.autonomousDrive(-0.8, 0);
        }
        else if(timer.get() > 8) {
          driveSub.autoBalance();
        }
      }

    }

    
  }
}
