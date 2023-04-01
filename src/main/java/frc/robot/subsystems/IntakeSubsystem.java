// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase {
  //Initilize Motors Here
  private TalonSRX talon;
  private CANSparkMax spark;
  private RelativeEncoder s_encoder;

  /** Creates a new IntakeSubsystem. */
  public IntakeSubsystem() {
    //Define Motors Here
    talon = new TalonSRX(Constants.TalonPort1);
    spark = new CANSparkMax(Constants.SparkPort1, MotorType.kBrushless);
    s_encoder = spark.getEncoder();

  }

  public void pullPushIntake(double leftTAxis,double rightTAxis) {
    if(leftTAxis > 0.03 && rightTAxis <= 0.03) {
      talon.set(TalonSRXControlMode.PercentOutput,-leftTAxis * .50); //Push
    }
    else if(rightTAxis > 0.03 && leftTAxis <= 0.03){
      talon.set(TalonSRXControlMode.PercentOutput,rightTAxis * .50); //Pull
    }
    else {
      stop();
    }
  }

  public void place(boolean buttonA) {
    if(buttonA) {
      talon.set(TalonSRXControlMode.PercentOutput, -0.15);
    }
  }

  public void shoot (boolean buttonB) {
    if(buttonB) {
      talon.set(TalonSRXControlMode.PercentOutput, -0.35);
    }
  }

  public void slowIn(boolean rBumper) {
    if(rBumper) {
      talon.set(TalonSRXControlMode.PercentOutput, 0.15);
    }
  }

  public void stop() {
    talon.set(TalonSRXControlMode.PercentOutput,0);
  }

  public void lower(double yAxis,boolean rBumper) {
    SmartDashboard.putNumber("Lower yAxis", yAxis);
    SmartDashboard.putNumber("Elbow Encoder Value", s_encoder.getPosition());
    SmartDashboard.putNumber("Elbow Velocity", s_encoder.getVelocity());
    if(yAxis > 0.1){
      spark.setInverted(false);
      spark.set(0.45 * yAxis);
    }
    else if(yAxis < -0.1) {
      spark.setInverted(true);
      spark.set(-0.6 * yAxis); 
    }
    else if(rBumper) {
      spark.setInverted(false);
      spark.set(-0.25);
    }
    
    else {
      stopLower();
    }
  }

  public void autoLower(double yAxis,boolean rBumper) {
    if(yAxis > 0.1){
      spark.setInverted(false);
      spark.set(yAxis);
    }
    else if(yAxis < -0.1) {
      spark.setInverted(true);
      spark.set(-yAxis); 
    }
    else if(rBumper) {
      spark.setInverted(false);
      spark.set(-0.25);
    }
    else {
      stopLower();
    }
  }
  public double getEncoderPos() {
    return s_encoder.getPosition();
  }

  public void zeroEncoder() {
    s_encoder.setPosition(0);
  }

  public void stopLower() {
    // spark.set(0);
    spark.setVoltage(0);
    // spark.disable();
    
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  
  

}
  