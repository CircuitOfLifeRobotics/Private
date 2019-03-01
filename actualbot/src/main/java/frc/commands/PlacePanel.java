/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.subsystems.Arm;
import frc.subsystems.Intake;
import frc.subsystems.Wrist;
import frc.subsystems.Arm.ArmState;

public class PlacePanel extends Command {

  public PlacePanel() {
    
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {

    if(!Arm.getInstance().getCurrentArmState().equals(ArmState.CARGO_LOW)){
      Intake.getInstance().setSpeed(1);
    }

    Wrist.getInstance().setSetpoint(Wrist.getInstance().getSetpoint() - 1000);

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
