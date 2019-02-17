/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import frc.robot.RobotMap;
import com.ctre.*;

public class Arm extends PIDSubsystem {

  private TalonSRX master = new TalonSRX(RobotMap.armMaster);
  private TalonSRX slaveOne = new TalonSRX(RobotMap.armSlaveOne);
  private TalonSRX slaveTwo = new TalonSRX(RobotMap.armSlaveTwo);
  private TalonSRX slaveThree = new TalonSRX(RobotMap.armSlaveThree);

  public enum ArmState{DOWN, PICKUP, CARGO_LOW, CARGO_HIGH, BOTTOM, MIDDLE, HIGH, PANEL_MIDDLE, PANEL_HIGH}

  public ArmState currentState;
  
  private static Arm instance;
  public static Arm getInstance(){
    if(instance == null) instance = new Arm();

    return instance;

  }
  
  private Arm() {
    super("Arm", 1, 1, 1);
    super.getPIDController().setContinuous(false);
    super.setAbsoluteTolerance(200);

    slaveOne.follow(master);
    slaveTwo.follow(master);
    slaveThree.follow(master);

    master.setNeutralMode(NeutralMode.Brake);
    slaveOne.setNeutralMode(NeutralMode.Brake);
    slaveTwo.setNeutralMode(NeutralMode.Brake);
    slaveThree.setNeutralMode(NeutralMode.Brake);

  }
  
  public void setPIDStatus(boolean status){
    if(status)
      enable();
    else
      disable();
  }

  public void z(){
    master.setSelectedSensorPosition(0);
  }

  public void move(double speed){
    master.set(ControlMode.PercentOutput, speed);
  }

  public void changeSetpoint(double change){
    change += getSetpoint();
    setSetpoint(change);
  }

  public double getPos(){

    return master.getSelectedSensorPosition();
  }

  public void shiftState(int mov){

    if(currentState == null){
      currentState = ArmState.DOWN;

      super.setSetpoint(RobotMap.armDown);
      Wrist.getInstance().setSetpoint(RobotMap.wristDown);

      enable();
      Wrist.getInstance().enable();

      return;
    }

    if(mov == -1){

      switch(currentState){
        case PICKUP:
          super.setSetpoint(RobotMap.armDown);
          Wrist.getInstance().setSetpoint(RobotMap.wristDown);
          currentState = ArmState.DOWN;
        case CARGO_LOW:
          super.setSetpoint(RobotMap.armPickup);
          Wrist.getInstance().setSetpoint(RobotMap.wristPickUp);
          currentState = ArmState.PICKUP;
          break;
        case CARGO_HIGH:
          super.setSetpoint(RobotMap.armCargoLow);
          Wrist.getInstance().setSetpoint(RobotMap.wristCargoLow);
          currentState = ArmState.CARGO_LOW;
          break;
        case BOTTOM:
          super.setSetpoint(RobotMap.armCargoHigh);
          Wrist.getInstance().setSetpoint(RobotMap.wristCargoHigh);
          currentState = ArmState.CARGO_HIGH;
          break;
        case MIDDLE:
          super.setSetpoint(RobotMap.armBottom);
          Wrist.getInstance().setSetpoint(RobotMap.wristBottom);
          currentState = ArmState.BOTTOM;
          break;
        case HIGH:
          super.setSetpoint(RobotMap.armMiddle);
          Wrist.getInstance().setSetpoint(RobotMap.wristMiddle);
          currentState = ArmState.MIDDLE;
          break;
        case PANEL_MIDDLE:
          super.setSetpoint(RobotMap.armHigh);
          Wrist.getInstance().setSetpoint(RobotMap.wristHigh);
          currentState = ArmState.HIGH;
          break;
        case PANEL_HIGH:
          super.setSetpoint(RobotMap.armPanelMiddle);
          Wrist.getInstance().setSetpoint(RobotMap.wristPanelMiddle);
          currentState = ArmState.PANEL_MIDDLE;
          break;
        case DOWN:
          super.setSetpoint(RobotMap.armDown);
          Wrist.getInstance().setSetpoint(RobotMap.wristDown);
          currentState = ArmState.DOWN;
          break;
        default:
          super.setSetpoint(RobotMap.armDown);
          Wrist.getInstance().setSetpoint(RobotMap.wristDown);
          currentState = ArmState.DOWN;
          break;
      }


   }else if(mov == 1){

      switch(currentState){
        case PANEL_HIGH:
          super.setSetpoint(RobotMap.armPanelHigh);
          Wrist.getInstance().setSetpoint(RobotMap.wristPanelHigh);
          currentState = ArmState.PANEL_HIGH;
          break;
        case DOWN:
          super.setSetpoint(RobotMap.armPickup);
          Wrist.getInstance().setSetpoint(RobotMap.wristPickUp);
          currentState = ArmState.PICKUP;
          break;
        case PICKUP:
          super.setSetpoint(RobotMap.armCargoLow);
          Wrist.getInstance().setSetpoint(RobotMap.wristCargoLow);
          currentState = ArmState.CARGO_LOW;
          break;        
        case CARGO_LOW:
          super.setSetpoint(RobotMap.armCargoHigh);
          Wrist.getInstance().setSetpoint(RobotMap.wristCargoHigh);
          currentState = ArmState.CARGO_HIGH;
          break;
        case CARGO_HIGH:
          super.setSetpoint(RobotMap.armBottom);
          Wrist.getInstance().setSetpoint(RobotMap.wristBottom);
          currentState = ArmState.BOTTOM;
          break;
        case BOTTOM:
          super.setSetpoint(RobotMap.armMiddle);
          Wrist.getInstance().setSetpoint(RobotMap.wristMiddle);
          currentState = ArmState.MIDDLE;
          break;
        case MIDDLE:
          super.setSetpoint(RobotMap.armHigh);
          Wrist.getInstance().setSetpoint(RobotMap.wristHigh);
          currentState = ArmState.HIGH;
          break;
        case HIGH:
          super.setSetpoint(RobotMap.armPanelMiddle);
          Wrist.getInstance().setSetpoint(RobotMap.wristPanelMiddle);
          currentState = ArmState.PANEL_MIDDLE;
          break;
        case PANEL_MIDDLE:
          super.setSetpoint(RobotMap.armPanelHigh);
          Wrist.getInstance().setSetpoint(RobotMap.wristPanelHigh);
          currentState = ArmState.PANEL_HIGH;
          break;
        default:
          super.setSetpoint(RobotMap.armDown);
          Wrist.getInstance().setSetpoint(RobotMap.wristDown);
          currentState = ArmState.DOWN;
          break;

        }
    }


    enable();
    Wrist.getInstance().enable();
  }

  public void setState(ArmState state){

      currentState = state;

      switch(currentState){
        case DOWN:
          super.setSetpoint(RobotMap.armDown);
          Wrist.getInstance().setSetpoint(RobotMap.wristDown);
          break;
        case PICKUP:
          super.setSetpoint(RobotMap.armPickup);
          Wrist.getInstance().setSetpoint(RobotMap.wristPickUp);
          break;
        case CARGO_LOW:
          super.setSetpoint(RobotMap.armCargoLow);
          Wrist.getInstance().setSetpoint(RobotMap.wristCargoLow);
          break;
        case CARGO_HIGH:
          super.setSetpoint(RobotMap.armCargoHigh);
          Wrist.getInstance().setSetpoint(RobotMap.wristCargoHigh);
          break;
        case BOTTOM:
          super.setSetpoint(RobotMap.armBottom);
          Wrist.getInstance().setSetpoint(RobotMap.wristBottom);
          break;
        case MIDDLE:
          super.setSetpoint(RobotMap.armMiddle);
          Wrist.getInstance().setSetpoint(RobotMap.wristMiddle);
          break;
        case HIGH:
          super.setSetpoint(RobotMap.armHigh);
          Wrist.getInstance().setSetpoint(RobotMap.wristHigh);
          break;
        case PANEL_MIDDLE:
          super.setSetpoint(RobotMap.armPanelMiddle);
          Wrist.getInstance().setSetpoint(RobotMap.wristPanelMiddle);
          break;
        case PANEL_HIGH:
          super.setSetpoint(RobotMap.armPanelHigh);
          Wrist.getInstance().setSetpoint(RobotMap.wristPanelHigh);
          break;
        default:
          super.setSetpoint(RobotMap.armDown);
          Wrist.getInstance().setSetpoint(RobotMap.wristDown);
          break;
      }

    enable();
    Wrist.getInstance().enable();
  }

  public void set(double x){
    master.set(ControlMode.PercentOutput, x);
  }

  public double getSensorPosition(){
    return master.getSelectedSensorPosition();
  }

  @Override
  public void initDefaultCommand() {
  }

  @Override
  protected double returnPIDInput() {

    System.out.println(currentState);
    //System.out.println("Arm: " + master.getSelectedSensorPosition());
    return master.getSelectedSensorPosition();

  }

  @Override
  protected void usePIDOutput(double output) {
    
    if(Math.abs(output) > .3){
      if(output < 0){
        output = -.3;
      }else{
        output = .3;
      }
    }
    
    if(Math.abs(master.getSelectedSensorPosition() - super.getSetpoint()) < 100){
      output /= 5.0;
    }
    if(Math.abs(master.getSelectedSensorPosition() - super.getSetpoint()) < 50) output = 0;
    
    master.set(ControlMode.PercentOutput, -output);

  }
}