/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.subsystems.Arm;
import frc.subsystems.Drivetrain;
import frc.subsystems.Wrist;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {
  //private final Joystick xbox = new Joystick(0);
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {

    // Wrist.getInstance().z();
    // Arm.getInstance().z();

  }

  @Override
  public void robotPeriodic() {

    Scheduler.getInstance().run();

  }

  /**
   * This function is run once each time the robot enters autonomous mode.
   */
  @Override
  public void autonomousInit() {
   
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    
  
  }
  
  /**
   * This function is called once each time the robot enters teleoperated mode.
   */
  @Override
  public void teleopInit() {

    Wrist.getInstance().z();
    Arm.getInstance().z();

  }
  
  /**
   * This function is called periodically during teleoperated mode.
   */
  
  
  @Override
  public void teleopPeriodic() {

    System.out.println("Arm: " + Arm.getInstance().getPos());
    System.out.println("Wrist: " + Wrist.getInstance().getPos());
    // System.out.println("Encoder 0 "+ Drivetrain.getInstance().getPos());

    // //Arm.getInstance().move(xbox.getRawAxis(1) / 3.0);
    // //Wrist.getInstance().move(-xbox.getRawAxis(5) / 3.0);

    Drivetrain.getInstance().setSpeed(OI.getInstance().getDriveFwd(), OI.getInstance().getDriveHoz());

  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }

  @Override
  public void disabledInit(){
  }

}