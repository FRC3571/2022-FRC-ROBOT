/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.studica.frc.TitanQuad;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.auto.AutoCommand;
import frc.robot.commands.auto.MultipleCommands;
import frc.robot.GamepadConstants;
import frc.robot.commands.driveCommands.SimpleDrive;
import frc.robot.commands.driveCommands.SimpleServo;
import com.studica.frc.Servo;
import frc.robot.subsystems.DriveTrain;


/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;
  private final DifferentialDrive m_robotDrive =
      new DifferentialDrive(new TitanQuad(42,0), new TitanQuad(42,2));
  private final Joystick m_stick = new Joystick(GamepadConstants.DRIVE_USB_PORT);

  private Servo m_servo = new Servo(1);
  
  
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   */
  @Override
  public void disabledInit() {
    //Check to see if autoChooser has been created
    if(null == RobotContainer.autoChooser)
    {
      RobotContainer.autoChooser = new SendableChooser<>();
    }

    //Add the default auto to the auto chooser
    RobotContainer.autoChooser.setDefaultOption("Multiple Commands", "Multiple Commands");
    RobotContainer.autoMode.put("Multiple Commands", new MultipleCommands());

    //Add other autos to the chooser
    addAutoToSelector(RobotContainer.autoChooser, "Multiple Commands", new MultipleCommands());

    //Update Smartdashboard
    SmartDashboard.putData(RobotContainer.autoChooser);
  }

  private void addAutoToSelector(SendableChooser<String> chooser, String auto, AutoCommand cmd)
  {
    chooser.addOption(auto, auto);
    RobotContainer.autoMode.put(auto, cmd);
  }

  @Override
  public void disabledPeriodic() {
  }

  /**
   * This autonomous runs the autonomous command selected by your {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {

  
    double CurrentAngle = m_servo.getAngle();

    while(m_stick.getPOV(0)==90){
      CurrentAngle+=1;
      m_servo.setAngle(CurrentAngle);
    }
    while(m_stick.getPOV(0)==270){
      CurrentAngle-=1;
      m_servo.setAngle(CurrentAngle);
    }
    m_robotDrive.arcadeDrive(m_stick.getY(), m_stick.getX());
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
