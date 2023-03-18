// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveSubsystem extends SubsystemBase {
  /** Creates a new DriveSubsystem. */
  private CANSparkMax motorR1; //ID 2
  private CANSparkMax motorR2; //ID 4

  private CANSparkMax motorL1; //ID 1
  private CANSparkMax motorL2; //ID 3

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
    if(rightY > 0.05 || rightY < -0.05 || leftY > 0.05 || leftY < -0.05){
      rightGroup.set(0.75 * (rightY * (0.50 - (0.25 * analogRead))) );
      leftGroup.set(0.75 * (-leftY * (0.50 - (0.25 * analogRead))) );
      SmartDashboard.putNumber("Right Group Speed",0.75 * rightY * (0.50 - (0.25 * analogRead)));
      SmartDashboard.putNumber("Left Group Speed",0.75 * -leftY * (0.50 - (0.25 * analogRead)));

      if(motorL2.getOutputCurrent() > SmartDashboard.getNumber("Highest AMP recorded of ID 3 ", 0)) {
        SmartDashboard.putNumber("Highest AMP recorded of ID 3 ", motorL2.getOutputCurrent());
      }
      if(motorL1.getOutputCurrent() > SmartDashboard.getNumber("Highest AMP recorded of ID 1 ", 0)) {
        SmartDashboard.putNumber("Highest AMP recorded of ID 1 ", motorL1.getOutputCurrent());
      }
      if(motorR2.getOutputCurrent() > SmartDashboard.getNumber("Highest AMP recorded of ID 4 ", 0)) {
        SmartDashboard.putNumber("Highest AMP recorded of ID 4 ", motorR2.getOutputCurrent());
      }
      if(motorR1.getOutputCurrent() > SmartDashboard.getNumber("Highest AMP recorded of ID 2 ", 0)) {
          SmartDashboard.putNumber("Highest AMP recorded of ID 2 ", motorR1.getOutputCurrent());
      }

      if(motorL1.getOutputCurrent() != 0) {
        SmartDashboard.putNumber("Motor id 1 Output Current: ",motorL1.getOutputCurrent());
      }
      if(motorR1.getOutputCurrent() != 0) {
        SmartDashboard.putNumber("Motor id 2 Output Current: ",motorR1.getOutputCurrent());
      }
      if(motorL2.getOutputCurrent() != 0) {
        SmartDashboard.putNumber("Motor id 3 Output Current: ",motorL2.getOutputCurrent());
      }
      if(motorR2.getOutputCurrent() != 0) {
        SmartDashboard.putNumber("Motor id 4 Output Current: ",motorR2.getOutputCurrent());
      }
      SmartDashboard.putNumber("ID 1 Temperature",motorL1.getMotorTemperature());
      SmartDashboard.putNumber("ID 2 Temperature",motorR1.getMotorTemperature());
      SmartDashboard.putNumber("ID 3 Temperature",motorL2.getMotorTemperature());
      SmartDashboard.putNumber("ID 4 Temperature",motorR2.getMotorTemperature());
      SmartDashboard.putNumber("ID 1 Incoming Voltage",motorL1.getBusVoltage());
      SmartDashboard.putNumber("ID 2 Incoming Voltage",motorR1.getBusVoltage());
      SmartDashboard.putNumber("ID 3 Incoming Voltage",motorL2.getBusVoltage());
      SmartDashboard.putNumber("ID 4 Incoming Voltage",motorR2.getBusVoltage());
      SmartDashboard.putNumber("ID 1 Applied Output", motorL1.getAppliedOutput());
      SmartDashboard.putNumber("ID 2 Applied Output",motorR1.getAppliedOutput());
      SmartDashboard.putNumber("ID 3 Applied Output",motorL2.getAppliedOutput());
      SmartDashboard.putNumber("ID 4 Applied Output",motorR2.getAppliedOutput());
      
      

      
    }
    else{
      stop();
    }
  }

  public void autonomousDrive(double speed) {
    rightGroup.set(speed); //Just driving out of base, will consider charge pad later. 
    leftGroup.set(-speed);
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