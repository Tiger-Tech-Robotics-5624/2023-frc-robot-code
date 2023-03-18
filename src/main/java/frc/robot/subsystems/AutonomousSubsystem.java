// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class AutonomousSubsystem extends SubsystemBase {
  DriveSubsystem driveSub;
  IntakeSubsystem intakeSub;
  VisionSubsystem visionSub;
  ElevatorSubsystem elevatorSub;
  Timer timer; 

  double driveTime = 6;
  double driveStop = 7;
  /** Creates a new AutonomousSubsystem. */
  public AutonomousSubsystem(DriveSubsystem drive, IntakeSubsystem intake, ElevatorSubsystem elevator) {
    driveSub = drive;
    intakeSub = intake;
    elevatorSub = elevator;
    visionSub = new VisionSubsystem();
    timer = new Timer();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void start(){
    timer.start();
  }

  public void runAutonomous() {
    //if(timer.get() < driveTime) {
    //  if(visionSub.getDistance() > 0.05) {
    //    elevatorSub.verticalMove(0.25);
    //  }
    //  else if (visionSub.getDistance() < -0.05) {
    //    elevatorSub.verticalMove(-0.15);
    //  }
    //  else if (visionSub.getDistance() == 0) {
    //    elevatorSub.verticalMove(0.05);
    //  }
    //  else {
    //    intakeSub.lower(0.75); // edit
    //    intakeSub.pullPushIntake(0, 0.75); //edit
    //  }
    //}
    if(timer.get() > driveTime && timer.get() < driveStop) {
      driveSub.autonomousDrive(0.15); //Temp speed
    }
    else if (timer.get() > driveStop && timer.get() < driveStop+1) {
      driveSub.stop();
    }
  }
}
