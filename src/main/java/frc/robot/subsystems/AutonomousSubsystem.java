// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutonomousSubsystem {
  DriveSubsystem driveSub;
  IntakeSubsystem intakeSub;
  VisionSubsystem visionSub;
  ElevatorSubsystem elevatorSub;
  Timer timer; 

  boolean cube = true;
  boolean balance = false;

  boolean teleop = false;

  /** Creates a new AutonomousSubsystem. */
  public AutonomousSubsystem(DriveSubsystem drive, IntakeSubsystem intake, ElevatorSubsystem elevator) {
    driveSub = drive;
    intakeSub = intake;
    elevatorSub = elevator;
    visionSub = new VisionSubsystem();
    timer = new Timer();
  }

  public void start(){
    timer.reset();
    teleop = false;
    timer.start();
    driveSub.resetGyro();
    
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
    //Cube Autonomous
    // while(timer.get() < 15 && !teleop && cube) {
      SmartDashboard.putNumber("Time 2", timer.get());
      
    //   if(timer.get() > 0 && timer.get() < 1){
    //     elevatorSub.autoMove(-0.3);
    //   }
    //   else if(timer.get() > 1) {
    //     elevatorSub.stop();
    //   }

    //   if(timer.get() > 0 && timer.get() < 1.5) {
    //     intakeSub.lower(0.7, false);
    //   }
    //   else if(timer.get() > 2 && timer.get() < 3){
    //     intakeSub.lower(0,true);
    //     intakeSub.place(true);
    //   }
    //   else {
    //     intakeSub.stop();
    //   }
    //   if(timer.get() > 4 && timer.get() < 6) {
    //     intakeSub.lower(-1.2,false);
    //   }
    //   else {
    //     intakeSub.stopLower();
    //   }
    // }


    //Cone Autonomous
    // while(timer.get() < 15 && !teleop && !cube) {
    //   if(timer.get() > 0 && timer.get() < 1){
    //     elevatorSub.autoMove(-0.3);
    //   }
    //   else if(timer.get() > 1) {
    //     elevatorSub.stop();
    //   }

    //   if(timer.get() > 0 && timer.get() < 1.5) {
    //     intakeSub.lower(0.7, false);
    //   }
    //   else if(timer.get() > 2 && timer.get() < 3){
    //     intakeSub.lower(0,true);
    //     intakeSub.place(true);
    //   }
    //   else {
    //     intakeSub.stop();
    //   }
    //   if(timer.get() > 4 && timer.get() < 6) {
    //     intakeSub.lower(-1.2,false);
    //   }
    //   else {
    //     intakeSub.stopLower();
    //   }
    // }


    
      //Drive Autonomous
    while(true) {//(timer.get() < 15 ){//&& timer.get() > 6) {
       driveSub.autonomousDrive(-0.0, 0);
    }
    
  }
}
