/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.RobotMap;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

public class Drivetrain extends Subsystem {
  
  
  private final CANSparkMax leftMaster = new CANSparkMax(RobotMap.leftMaster, MotorType.kBrushless);
  private final CANSparkMax leftSlaveA = new CANSparkMax(RobotMap.leftSlaveA, MotorType.kBrushless);
  private final CANSparkMax leftSlaveB = new CANSparkMax(RobotMap.leftSlaveB, MotorType.kBrushless);

  private final CANSparkMax rightMaster = new CANSparkMax(RobotMap.rightMaster, MotorType.kBrushless);
  private final CANSparkMax rightSlaveA = new CANSparkMax(RobotMap.rightSlaveA, MotorType.kBrushless);
  private final CANSparkMax rightSlaveB = new CANSparkMax(RobotMap.rightSlaveB, MotorType.kBrushless);

  private final DoubleSolenoid shift = new DoubleSolenoid(RobotMap.shiftHigh, RobotMap.shiftLow);

  private boolean isHigh;
  private DifferentialDrive drive;

  private static Drivetrain instance;

  public static Drivetrain getInstance(){
    if(instance == null) 
      instance = new Drivetrain();

    return instance;
  }

  private Drivetrain(){

    leftSlaveA.follow(leftMaster);
    leftSlaveB.follow(leftMaster);

    rightSlaveA.follow(rightMaster);
    rightSlaveB.follow(rightMaster);

    /*
    rightMaster.setIdleMode(IdleMode.kBrake);
    rightSlaveA.setIdleMode(IdleMode.kBrake);
    rightSlaveB.setIdleMode(IdleMode.kBrake);
    leftMaster.setIdleMode(IdleMode.kBrake);
    leftSlaveA.setIdleMode(IdleMode.kBrake);
    leftSlaveB.setIdleMode(IdleMode.kBrake);*/

    isHigh = true;
    shift.set(Value.kReverse);

    drive = new DifferentialDrive(leftMaster, rightMaster);

  }

  public void shift(){

    if(isHigh)
      shift.set(Value.kForward);
    else
      shift.set(Value.kReverse);

    isHigh = !isHigh;
  }


  @Override
  public void initDefaultCommand() {
    
  }

  public void setSpeed(double xSpeed, double zRotation){
    drive.arcadeDrive(xSpeed, zRotation);
  }


}
