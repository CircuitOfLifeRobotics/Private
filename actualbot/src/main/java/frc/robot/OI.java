/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.POVButton;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.command.Command;
import frc.commands.MoveArm;
import frc.commands.PlacePanel;
import frc.commands.ShiftArm;
import frc.subsystems.Arm;
import frc.subsystems.Drivetrain;
import frc.subsystems.Intake;
import frc.subsystems.Wrist;
import frc.subsystems.Arm.ArmState;
import frc.utilities.Limelight;

public class OI {

    private final Joystick xbox = new Joystick(0);
    private final Joystick stick = new Joystick(1);
    private final Joystick wheel = new Joystick(2);
    
    private Button switchButton;
    private Button a;
    private Button b;
    private Button x;
    private Button y;
    private Button start;

    private POVButton grabPanel;

    private Button increasePower;

    private Trigger armDown;
    private Trigger armBottom;
    private Trigger armMiddle;
    private Trigger armHigh;

    private Trigger cargoHigh;
    private Trigger panelLow;
    private Trigger panelMiddle;
    private Trigger panelHigh;

    private Trigger intakeIn;
    private Trigger intakeOut;

    private POVButton armStageDown;
    private POVButton armStageUp;

    private Button shifter;

    private Button aligner;

    private Trigger wristMover;
    private Trigger armMover;

    private Button placer;


    private static OI instance;
    public static OI getInstance(){
        if(instance == null) instance = new OI();

        return instance;
    }

    private OI(){

        switchButton = new JoystickButton(xbox, 5);

        a = new JoystickButton(xbox, 1);
        b = new JoystickButton(xbox, 2);
        x = new JoystickButton(xbox, 3);
        y = new JoystickButton(xbox, 4);
        start = new JoystickButton(xbox, 8);
        grabPanel = new POVButton(xbox, 90);
        increasePower = new JoystickButton(xbox, 7);

        increasePower.whenPressed(new Command(){

            @Override
            protected void initialize() {
                Arm.getInstance().setMaxOutput(.4);
            }

            @Override
            protected boolean isFinished() {
                return true;
            }

        });

        grabPanel.whenPressed(new Command(){

            @Override
            protected void initialize() {
                Arm.getInstance().setState(ArmState.GRAB_PANEL);
            }

            @Override
            protected boolean isFinished() {
                return true;
            }
        });

        start.whenPressed(new Command(){
        
            @Override
            protected void initialize() {
                Wrist.getInstance().z();
                Arm.getInstance().z();
            }

            @Override
            protected boolean isFinished() {
                return true;
            }
        });


        armDown = new Trigger() {

			@Override
			public boolean get() {
				return a.get() && !switchButton.get();
			}
        };
        armBottom = new Trigger() {

			@Override
			public boolean get() {
				return b.get() && !switchButton.get();
			}
		};
        armMiddle = new Trigger() {

			@Override
			public boolean get() {
				return x.get() && !switchButton.get();
			}
		};
        armHigh = new Trigger() {

			@Override
			public boolean get() {
				return y.get() && !switchButton.get();
			}
		};
        
        cargoHigh = new Trigger(){
        
            @Override
            public boolean get() {
                return a.get() && switchButton.get();
            }
        };
        panelLow = new Trigger() {

			@Override
			public boolean get() {
				return b.get() && switchButton.get();
			}
		};
        panelMiddle = new Trigger() {

			@Override
			public boolean get() {
				return x.get() && switchButton.get();
			}
        };
        panelHigh = new Trigger() {

			@Override
			public boolean get() {
				return y.get() && switchButton.get();
			}
		};


        armDown.whenActive(new Command(){
            @Override
			protected void initialize() {
				Arm.getInstance().setState(ArmState.PICKUP);
			}
			@Override
			protected void execute() {
			}
			
			@Override
			protected void end() {
                Arm.getInstance().setState(ArmState.DOWN);
			}
			
			@Override
			protected boolean isFinished() {
                return !a.get();
            }
        });

        armBottom.whenActive(new MoveArm(ArmState.BOTTOM));
        armMiddle.whenActive(new MoveArm(ArmState.MIDDLE));
        armHigh.whenActive(new MoveArm(ArmState.HIGH));

        cargoHigh.whenActive(new MoveArm(ArmState.CARGO_HIGH));
        panelLow.whenActive(new MoveArm(ArmState.CARGO_LOW));
        panelMiddle.whenActive(new MoveArm(ArmState.PANEL_MIDDLE));
        panelHigh.whenActive(new MoveArm(ArmState.PANEL_HIGH));
        
        /*************************************************/

        intakeIn = new Trigger() {

			@Override
			public boolean get() {
				return xbox.getRawAxis(3) > .1;
			}
		};

        intakeOut = new Trigger() {

			@Override
			public boolean get() {
				return xbox.getRawAxis(2) > .1;
			}
        };

        intakeOut.whenActive(new Command() {
			@Override
			protected void initialize() {
				Intake.getInstance().setSpeed(-1);
			}
			@Override
			protected void execute() {
			}
			
			@Override
			protected void end() {
                Intake.getInstance().setSpeed(0);
			}
			
			@Override
			protected boolean isFinished() {
				return xbox.getRawAxis(2) < .1;
			}
        });
        
        intakeIn.whenActive(new Command() {
			@Override
			protected void initialize() {
				Intake.getInstance().setSpeed(1);
			}
			@Override
			protected void execute() {
			}
			
			@Override
			protected void end() {
                Intake.getInstance().setSpeed(0);
			}
			
			@Override
			protected boolean isFinished() {
                return xbox.getRawAxis(3) < .1;
            }
		});
        /***********************************************/
        shifter = new Button(){
            
            @Override
            public boolean get() {
                return wheel.getRawButton(5);
            }
        };
        
        shifter.whenPressed(new Command() {
            @Override
			protected void initialize() {
                Drivetrain.getInstance().shift();
            }
			@Override
			protected void execute() {
            }
			
			@Override
			protected void end() {
                Drivetrain.getInstance().shift();
            }
			
			@Override
			protected boolean isFinished() {
                return !shifter.get();
            }
		});
        
        /****************************************/

        armStageDown = new POVButton(xbox, 180);
        armStageUp = new POVButton(xbox, 0);
        
        armStageDown.whenPressed(new ShiftArm(-1));
        armStageUp.whenPressed(new ShiftArm(1));
        
        /*****************************************/

        aligner = new JoystickButton(stick, 1);

        /****************************************/

        wristMover = new Trigger(){
        
            @Override
            public boolean get() {
                return Math.abs(xbox.getRawAxis(5)) > .1;
            }
        };

        armMover = new Trigger(){
        
            @Override
            public boolean get() {
                return Math.abs(xbox.getRawAxis(1)) > .1;
            }
        };

        armMover.whenActive(new Command(){
            @Override
            protected void initialize() {
                Arm.getInstance().setPIDStatus(false);
            }
            @Override
            protected void execute() {
                Arm.getInstance().move(xbox.getRawAxis(1) / 2.0);
            }
            
            @Override
            protected void end() {
                Arm.getInstance().move(0);
            }
            
            @Override
            protected boolean isFinished() {
                return Math.abs(xbox.getRawAxis(1)) < .1;
            }
        });

        wristMover.whenActive(new Command(){
            @Override
            protected void initialize() {
                Wrist.getInstance().setPIDStatus(false);
            }
            @Override
            protected void execute() {
                Wrist.getInstance().move(-xbox.getRawAxis(5) / 2.0);
            }
            
            @Override
            protected void end() {
                Wrist.getInstance().move(0);
            }
            
            @Override
            protected boolean isFinished() {
                return Math.abs(xbox.getRawAxis(5)) < .1;
            }
        });

        /*********************************/

        placer = new JoystickButton(xbox, 6);
        placer.whenPressed(new PlacePanel());

        /*****************************************/

        aligner.whenPressed(new Command(){

            @Override
            protected void initialize() {
                Limelight.getInstance().switchState();
            }

            @Override
            protected void end() {
                Limelight.getInstance().switchState();
            }

            @Override
            protected boolean isFinished() {
                return !aligner.get();
            }

        });
    }

    public double getDriveHoz(){

        if(aligner.get()){
            double offset = 0;
            offset = Limelight.getInstance().getX();
            if(offset > 0.25){
            offset = (offset / (Math.sqrt(1 + Math.pow(offset, 2)))+4);
            offset /= 2.5;
            }else{
              offset = (offset / (Math.sqrt(1 + Math.pow(offset, 2))));
            offset /= 2.5;
            }
            return -(wheel.getRawAxis(0) + offset);


        }else{
            return -wheel.getRawAxis(0);
        } 

    }
    
    public double getDriveFwd(){
        return stick.getRawAxis(1);
    }
 
    }

