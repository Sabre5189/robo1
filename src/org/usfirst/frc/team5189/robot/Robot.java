package org.usfirst.frc.team5189.robot;

import java.time.LocalDateTime;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.VictorSP;

import edu.wpi.first.wpilibj.Spark;

import org.usfirst.frc.team5189.robot.DriveBase;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	private DriveBase m_driveBase = new DriveBase(new VictorSP(0), new VictorSP(1), new VictorSP(2), new VictorSP(3));
	private XboxController m_controller = new XboxController(0);
	private Joystick m_joystick = new Joystick(0);

	private LocalDateTime stopTime;
	
	private int LeftLiftChannel  = 4;
	private int RightLiftChannel  = 5;
	
	
	private Spark leftLift = new Spark(LeftLiftChannel);
	private Spark rightLift= new Spark(RightLiftChannel);

	@Override
	public void robotInit() {
	}

	@Override
	public  void autonomousInit() {
		stopTime = LocalDateTime.now().plusSeconds(5);
	}

	double motorSpeed = 0.0;
	
	@Override
	public void autonomousPeriodic() {
		/**
		 * The robot does not drive in autonomousInit
		 *Create a way to drive the robot (periodic?)
		*/

		if (LocalDateTime.now().isAfter(stopTime)) {
			m_driveBase.driveCartesian(0, 0, 0);
			return;
		}
		
		if (motorSpeed <= 1.0) {
			motorSpeed = motorSpeed + .025;
		}
		
		m_driveBase.driveCartesian(0, motorSpeed, 0);
	}

	@Override
	public void teleopInit() {
	}

	@Override
	public void teleopPeriodic() {
		
		// moving
		m_driveBase.driveCartesian(m_joystick.getRawAxis(0), m_controller.getY()*-1, m_controller.getX()*-1);
		
		// lift
	}

	@Override
	public void testPeriodic() {
	}

	@Override
	public  void testInit() {
//		RobotDiagnostic diagnostics = new RobotDiagnostic();
//		
//		diagnostics.RunDiagnostic();

		//testMovement();
		
		leftLift.set(0.5);
		rightLift.set(0.5);
		Timer.delay(2.0);
		
		leftLift.set(-0.5);
		rightLift.set(-0.5);
		Timer.delay(2.0);
	}
	
	private void testMovement()
	{
		m_driveBase.driveCartesian(.5, 0, 0);
		Timer.delay(2.0);

		m_driveBase.driveCartesian(-.5, 0, 0);
		Timer.delay(2.0);
		
		m_driveBase.driveCartesian(0, .5, 0);
		Timer.delay(2.0);

		m_driveBase.driveCartesian(0, -.5, 0);
		Timer.delay(2.0);
		
		m_driveBase.driveCartesian(0, 0, .5);
		Timer.delay(2.0);

		m_driveBase.driveCartesian(0, 0, -.5);
		Timer.delay(2.0);
	}
}







	/** 
	 * To Do:
	 * 
	 * Two lift motor; 1 runs in reverse of the other
	 * 3 pneumatic objects, one is Tilt 45, tilt 90, grabber 
	 * when all 3 pneumatics are off naturally the tilt is all the way up 
	 * if you activate 45 tilt it will go to 45 
	 * if you activate 90 it will go all the way down
	 * grabber is either release or grab
	 * camera coding figure out
	 */
	 
	 /** 
	  * XBox Controller Mapping:
	  * 
	  * left joystick: strafe
	  * right joystick: forward/back
	  * left/right trigger: z rotation
	  * dpad up: lift up (momentary)
	  * dpad down: lift down (momentary)
	  * y: tilt 0
	  * b: tilt 45
	  * a: tilt 90
	  * x: grab/release (continuous)
	  * left/right bumper: camera pan (momentary)
	  */
