// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveSubsystem extends SubsystemBase {
  /** Creates a new DriveSubsystem. */
  private CANSparkMax motorR1;
  private CANSparkMax motorR2;

  private CANSparkMax motorL1;
  private CANSparkMax motorL2;

  private MotorControllerGroup rightGroup;
  private MotorControllerGroup leftGroup;

  public DriveSubsystem() {
    motorR1 = new CANSparkMax(Constants.CANPortR1, MotorType.kBrushless);
    motorR2 = new CANSparkMax(Constants.CANPortR2, MotorType.kBrushless);

    motorL1 = new CANSparkMax(Constants.CANPortL1, MotorType.kBrushless);
    motorL2 = new CANSparkMax(Constants.CANPortL2, MotorType.kBrushless);

    rightGroup = new MotorControllerGroup(motorR1, motorR2);
    leftGroup = new MotorControllerGroup(motorL1, motorL2);
  }

  public void drive(double leftY, double rightY, double analogRead) 
  {
    if(rightY > 0.03 || rightY < -0.03 || leftY > 0.03 || leftY < -0.03){
      rightGroup.set(rightY * (0.50 - (0.25 * analogRead)) );
      leftGroup.set(-leftY * (0.50 - (0.25 * analogRead)) );
    }
    else{
      stop();
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  
  public void stop(){
    rightGroup.set(0);
    leftGroup.set(0);
  }
}