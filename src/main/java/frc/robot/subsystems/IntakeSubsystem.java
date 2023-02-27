// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase {
  //Initilize Motors Here
  private VictorSPX talon;
  /** Creates a new IntakeSubsystem. */
  public IntakeSubsystem() {
    //Define Motors Here
    talon = new VictorSPX(Constants.TalonPort1);
  }

  public void pullPushIntake(double leftTAxis,double rightTAxis) {
    if(leftTAxis > 0.03 && rightTAxis <= 0.03) {
      talon.set(VictorSPXControlMode.PercentOutput,-leftTAxis);
    }
    else if(rightTAxis > 0.03 && leftTAxis <= 0.03){
      talon.set(VictorSPXControlMode.PercentOutput,rightTAxis);
    }
    else {
      stop();
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  
  public void stop() {
    talon.set(VictorSPXControlMode.PercentOutput,0);
  }
}
  