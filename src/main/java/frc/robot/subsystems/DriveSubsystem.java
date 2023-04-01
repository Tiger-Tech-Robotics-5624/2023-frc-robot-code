// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.PID;

public class DriveSubsystem extends SubsystemBase {
  /** Creates a new DriveSubsystem. */
  private CANSparkMax motorR1; //ID 2
  private CANSparkMax motorR2; //ID 4

  private CANSparkMax motorL1; //ID 1
  private CANSparkMax motorL2; //ID 3

  private MotorControllerGroup rightGroup;
  private MotorControllerGroup leftGroup;

  private AHRS gyro;
  private PID drivePID;
  double kp = 0.1, ki = 0;
  
  double error;
  double value;
  double autoRightSpeed;
  double autoLeftSpeed;
  double autoSpeed;

  private ShuffleboardTab tab;
  private GenericEntry speed;

  public DriveSubsystem() {
    motorR1 = new CANSparkMax(Constants.CANPortR1, MotorType.kBrushless);
    motorR2 = new CANSparkMax(Constants.CANPortR2, MotorType.kBrushless);

    motorL1 = new CANSparkMax(Constants.CANPortL1, MotorType.kBrushless);
    motorL2 = new CANSparkMax(Constants.CANPortL2, MotorType.kBrushless);

    rightGroup = new MotorControllerGroup(motorR1, motorR2);
    leftGroup = new MotorControllerGroup(motorL1, motorL2);

    gyro = new AHRS(SPI.Port.kMXP);
    drivePID = new PID(kp, ki);

    // tab = Shuffleboard.getTab("Autonomous");
    // speed = tab.addPersistent("Speed", 0).getEntry();
  }

  // public void test() {
  //   double testSpeed = speed.getDouble(0);
  //   autonomousDrive(testSpeed, 0);
  // }

  public void drive(double leftY, double rightY, double analogRead) 
  {
    if(rightY > 0.05 || rightY < -0.05 || leftY > 0.05 || leftY < -0.05){
      rightGroup.set(0.75 * (rightY * (0.50 - (0.25 * analogRead))) );
      leftGroup.set(0.75 * (-leftY * (0.50 - (0.25 * analogRead))) ); 
    }
    else{
      stop();
    }
  }

  public void autonomousDrive(double speed, double target) {
    error = target - gyro.getAngle();
    SmartDashboard.putNumber("Gryo Angle", gyro.getAngle());
    SmartDashboard.putNumber("Error", error);
    value = drivePID.calculate(error);
    if(value > 0.25) {
      leftGroup.set(0.05);
      rightGroup.set(0.05);
    }

    else if(value < -0.25) {
      rightGroup.set(-0.05);
      leftGroup.set(-0.05);
    }
    else {
      rightGroup.set(speed);
      leftGroup.set(-speed);
    }
  }

  //AutoBalance
  public void autoBalance(){
    if(gyro.getRoll()+4 > 10){
      rightGroup.set(-0.1);
      leftGroup.set(0.1);
    }
    else if(gyro.getRoll()+4 < -10){
      rightGroup.set(0.1);
      leftGroup.set(-0.1);

    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  
  public void resetGyro() {
    gyro.reset();
  }

  public void stop(){
    rightGroup.set(0);
    leftGroup.set(0);
  }
}