/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.subsystems.Arm.ArmState;

public class OnePanelAuto extends CommandGroup {
  
  public enum StartPosition {LEFT, MIDDLE, RIGHT};

  public OnePanelAuto(StartPosition pos) {
    //position from driver perspective 
    //1: left
    //2: middle
    //3: right
    if(pos.equals(StartPosition.LEFT)){

      addSequential(new Move(10000));
      addSequential(new Turn(500));
      addSequential(new Move(20000));
      addSequential(new Turn(-250));
      addSequential(new MoveArm(ArmState.PANEL_MIDDLE));
      addSequential(new Move(5000));
      addSequential(new PlacePanel());
      addSequential(new Move(-5000));

    }
  }
}
