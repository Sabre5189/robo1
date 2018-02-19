package org.usfirst.frc.team5189.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;

import org.usfirst.frc.team5189.robot.DriveBase;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	private DriveBase m_driveBase
		=new DriveBase(new VictorSP(0), new VictorSP(1), new VictorSP(2), new VictorSP(3));
	private XboxController m_controller= new XboxController(0);
	private Joystick m_joystick= new Joystick(0);
	

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
	}


	/**
	 * This function is run once each time the robot enters autonomous mode.
	 */
	@Override
	public  void autonomousInit() {
		//System.out.println(m_joystick.getRawAxis(1));
		//System.out.println(m_controller.getY());
		//System.out.println(m_controller.getX());
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		/**
		 * The robot does not drive in autonomousInit
		 *Create a way to drive the robot (periodic?)
		*/
		
		int x = 0;
		while(x < 5) {
			m_driveBase.driveCartesian(1, 0, 0);
			m_driveBase.driveCartesian(0, 1, 0);
			Timer.delay(.001);
			x++;
		}
	}

	/**
	 * This function is called once each time the robot enters teleoperated mode.
	 */
	@Override
	public void teleopInit() {
	}

	/**
	 * This function is called periodically during teleoperated mode.
	 */
	@Override
	public void teleopPeriodic() {
		m_driveBase.driveCartesian(m_joystick.getRawAxis(0), m_controller.getY()*-1, m_controller.getX()*-1);
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}

	@Override
	public  void testInit() {
		//System.out.println(m_joystick.getRawAxis(1));
		//System.out.println(m_controller.getY());
		//System.out.println(m_controller.getX());
		m_driveBase.driveCartesian(1, 0, 0);
		m_driveBase.driveCartesian(0, 1, 0);
		Timer.delay(4.0);
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
