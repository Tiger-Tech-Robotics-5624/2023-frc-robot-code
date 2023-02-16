// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.Autos;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer {
  public static XboxController xboxController;
  public static Joystick stickLeft, stickRight;

  DriveSubsystem driveSub;
  IntakeSubsystem intakeSub;

  DriveCommand driveCmd;
  IntakeCommand intakeCmd;

  public RobotContainer() {
    xboxController = new XboxController(Constants.xboxPort);

    stickLeft = new Joystick(Constants.stickPortL);
    stickRight = new Joystick(Constants.stickPortR);

    driveSub = new DriveSubsystem();
    intakeSub = new IntakeSubsystem();

    driveCmd = new DriveCommand(driveSub);
    intakeCmd = new IntakeCommand(intakeSub);

    driveSub.setDefaultCommand(driveCmd);
  }

  private void configureBindings() {

  }

//  public Command getAutonomousCommand() {

//  }
}
