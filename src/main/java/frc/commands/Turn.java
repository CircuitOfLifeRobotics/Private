/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.subsystems.Drivetrain;

public class Turn extends Command {

  private final double TOLERANCE = 100;
  private double position;

  public Turn(double pos) {//right-== true
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    position = pos;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Drivetrain.getInstance().zero();

    if(position > 0){
      Drivetrain.getInstance().setSpeed(0, .2);
    }else{
      Drivetrain.getInstance().setSpeed(0, -.2);
    }

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {


  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Drivetrain.getInstance().getRightPosition() > (position - TOLERANCE) 
    && Drivetrain.getInstance().getRightPosition() < (position + TOLERANCE);
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {

    Drivetrain.getInstance().setSpeed(0, 0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
