// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
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

  public DriveSubsystem() {
    motorR1 = new CANSparkMax(Constants.CANPortR1, MotorType.kBrushless);
    motorR2 = new CANSparkMax(Constants.CANPortR2, MotorType.kBrushless);

    motorL1 = new CANSparkMax(Constants.CANPortL1, MotorType.kBrushless);
    motorL2 = new CANSparkMax(Constants.CANPortL2, MotorType.kBrushless);

    rightGroup = new MotorControllerGroup(motorR1, motorR2);
    leftGroup = new MotorControllerGroup(motorL1, motorL2);

    gyro = new AHRS(SPI.Port.kMXP);
    drivePID = new PID(kp, ki);

  }

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

    //rightGroup.set(speed);
    //leftGroup.set(-speed);

    error = target - gyro.getAngle();
    value = drivePID.calculate(error);
    autoLeftSpeed = speed + value;
    autoRightSpeed = speed + value;
    if((autoRightSpeed < 0.4 || autoLeftSpeed < 0.4) || (autoRightSpeed > -0.4 || autoLeftSpeed > -0.4)) {
      rightGroup.set(autoRightSpeed);
      leftGroup.set(autoLeftSpeed);
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