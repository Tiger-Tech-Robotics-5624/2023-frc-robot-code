// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase {
  //Initilize Motors Here
  private TalonSRX talon;
  private VictorSPX victor;
  /** Creates a new IntakeSubsystem. */
  public IntakeSubsystem() {
    //Define Motors Here
    talon = new TalonSRX(Constants.TalonPort1);
    victor = new VictorSPX(Constants.VictorPort1);
    victor.configNeutralDeadband(0.03);
    

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

  public void stop() {
    talon.set(TalonSRXControlMode.PercentOutput,0);
  }

  public void lower(double yAxis, boolean rightBumper) {
    SmartDashboard.putNumber("Left Joy Stick X Box", yAxis);
    if(yAxis > 0.1){
      victor.setInverted(false);
      victor.set(VictorSPXControlMode.PercentOutput, 0.35 * yAxis);
    }
    else if(yAxis < -0.1) {
      victor.setInverted(true);
      victor.set(VictorSPXControlMode.PercentOutput, -0.40 * yAxis);
    }
    else if(rightBumper) {
      victor.setInverted(true);
      victor.set(VictorSPXControlMode.PercentOutput,0.185);
    }
    else {
      stopLower();
    }
  }

  public void stopLower() {
    victor.set(VictorSPXControlMode.PercentOutput,0);
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  
  
}
  