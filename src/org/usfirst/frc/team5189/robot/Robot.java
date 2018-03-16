package org.usfirst.frc.team5189.robot;

import java.time.LocalDateTime;

import edu.wpi.first.wpilibj.Timer;
//import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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

//	private Compressor c = new Compressor(0);

	private LocalDateTime stopTime;

	private double motorSpeed = 0.0;

//	private int PneumaticsChannelA = 7;
//	private int PneumaticsChannelB = 8;
//	private int PneumaticsChannelX = 9;
//
	private int PneumaticsChannelA = 0;
	private int PneumaticsChannelB = 1;
	private int PneumaticsChannelX = 2;

//	private Solenoid pneumaticsA = new Solenoid(PneumaticsChannelA);
//	private Solenoid pneumaticsB = new Solenoid(PneumaticsChannelB);
//	private Solenoid pneumaticsX = new Solenoid(PneumaticsChannelX);

	@Override
	public void robotInit() {
	}

	@Override
	public  void autonomousInit() {
		stopTime = LocalDateTime.now().plusSeconds(5);
	}

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
		aTimer = new Timer();
		bTimer = new Timer();
		xTimer = new Timer();

		aTimer.start();
		bTimer.start();
		xTimer.start();

		aTime = bTime = xTime = aTimer.get();
	}

	private Timer aTimer;
	private double aTime;
	private Timer bTimer;
	private double bTime;
	private Timer xTimer;
	private double xTime;

	boolean aIsPressed;
	boolean bIsPressed;
	boolean xIsPressed;
	boolean yIsPressed;

	boolean aToggle;
	boolean bToggle;
	boolean xToggle;

	boolean firstAction;

	@Override
	public void teleopPeriodic() {
		// moving
		m_driveBase.driveCartesian(m_joystick.getRawAxis(0), m_controller.getY()*-1, m_controller.getX()*-1);

		aIsPressed = m_controller.getAButtonPressed();
		bIsPressed = m_controller.getBButtonPressed();
		xIsPressed = m_controller.getXButtonPressed();
		yIsPressed = m_controller.getYButtonPressed();

		if(yIsPressed) {
			release(0.0);
			retract(0.0);
			liftUp(0.0);
			
			return;
		}

		if (aIsPressed)
			aTime = aTimer.get();
		else if (bIsPressed)
			bTime = bTimer.get();
		else if (xIsPressed)
			xTime = xTimer.get();

		firstAction = aTime == bTime && bTime == xTime && xTime == aTime;

		if (firstAction && aIsPressed == false && bIsPressed == false && xIsPressed == false)
			return;

		if (aIsPressed && aTime > bTime && aTime > xTime) {
			aTime=aTimer.get();
			aToggle = aToggle == false;
			if (aToggle){
				System.out.println("lift up");
				retract(0.0);
				liftUp(0.0);
			}
			else {
				System.out.println("set down");
				setDown(0.0);
			}
		}

		if (bIsPressed && bTime > aTime && bTime > xTime) {
			bTime=bTimer.get();
			bToggle = bToggle == false;
			if (bToggle)
			{
				System.out.println("push");
				release(0.2);
				push(0.0);
			}
			else
			{
				System.out.println("retract");
				retract(0.0);
			}
		}

		if (xIsPressed && xTime > bTime && xTime > aTime) {
			xTime=xTimer.get();
			xToggle = xToggle == false;
			if (xToggle)
			{
				System.out.println("grab");
				grab(0.0);
			}
			else {
				System.out.println("release");
				release(0.0);
			}
		}
/*
		//		a: tilt up/down position
		if((firstAction) && (aIsPressed )) {
			aTime=aTimer.get();
			aToggle = aToggle == false;
			if (aToggle){
				System.out.println("lift up");
				retract(0.0);
				liftUp(0.0);
			}
			else {
				System.out.println("set down");
				setDown(0.0);
			}
		}

//		b: push/retract
		if((firstAction) && (bIsPressed)) {
			bTime = bTimer.get();
			bToggle = bToggle == false;
			if (bToggle)
			{
				System.out.println("push");
				release(0.2);
				push(0.0);
			}
			else
			{
				System.out.println("retract");
				retract(0.0);
			}
		}

//		x: grab/release (continuous)
		if((firstAction) && (xIsPressed)) {
			xTime = xTimer.get();
			xToggle = xToggle == false;
			if (xToggle)
			{
				System.out.println("grab");
				grab(0.0);
			}
			else {
				System.out.println("release");
				release(0.0);
			}
		}
*/
	}

	@Override
	public void testPeriodic() {
	}

	DoubleSolenoid dsPusher= new DoubleSolenoid(1,6);
	DoubleSolenoid dsLifter= new DoubleSolenoid(2,3);
	DoubleSolenoid dsGrabber= new DoubleSolenoid(5,4);

	@Override
	public  void testInit() {
		System.out.println("starting testInit");

//		RobotDiagnostic diagnostics = new RobotDiagnostic();
//		
//		diagnostics.RunDiagnostic();

		// commenting this while we're testing the pneumatics
//		testMovement();

		// how much time does the pneumatic pump need to get up to pressure?
		System.out.println("pneumatic delay start");
		Timer.delay(4.0);
		System.out.println("pneumatic delay finished");

		// assumes starting from the up position
		setDown(5.0);

		grab();

		liftUp();

		push();
		retract();

		setDown();

		release();

		System.out.println("finished testInit");
		//------------------------------------------

//		pneumaticsA.set(true);
//		Timer.delay(2.0);
//		pneumaticsA.set(false);
//
//		pneumaticsB.set(true);
//		Timer.delay(2.0);
//		pneumaticsB.set(false);
//
//		pneumaticsX.set(true);
//		Timer.delay(2.0);
//		pneumaticsX.set(false);
	}

	private void push(double delay) {
		dsPusher.set(DoubleSolenoid.Value.kForward);
		Timer.delay(delay);
	}

	private void push() {
		push(1.0);
	}

	private void retract(double delay) {
		dsPusher.set(DoubleSolenoid.Value.kReverse);
		Timer.delay(delay);
	}

	private void retract() {
		retract(1.0);
	}
	
	private void liftUp(double delay) {
		dsLifter.set(DoubleSolenoid.Value.kForward);
		Timer.delay(delay);
	}

	private void liftUp() {
		liftUp(8.0);
	}

	private void setDown(double delay) {
		release(0.05);
		dsLifter.set(DoubleSolenoid.Value.kReverse);
		Timer.delay(delay);
	}

	private void setDown() {
		setDown(4.0);
	}

	private void grab(double delay) {
		dsGrabber.set(DoubleSolenoid.Value.kForward);
		Timer.delay(delay);
	}

	private void grab() {
		grab(0.5);
	}

	private void release() {
		release(0.5);
	}

	private void release(double delay) {
		dsGrabber.set(DoubleSolenoid.Value.kReverse);
		Timer.delay(delay);
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

	private void singleSolenoidTest() {
		Solenoid foo0 = new Solenoid(0);
		Solenoid foo1 = new Solenoid(1);
		Solenoid foo2 = new Solenoid(2);
		Solenoid foo3 = new Solenoid(3);
		Solenoid foo4 = new Solenoid(4);
		Solenoid foo5 = new Solenoid(5);
		Solenoid foo6 = new Solenoid(6);
		Solenoid foo7 = new Solenoid(7);

		foo0.set(false);
		foo1.set(false);
		foo2.set(false);
		foo3.set(false);
		foo4.set(false);
		foo5.set(false);
		foo6.set(false);
		foo7.set(false);

		// closes left, then right grabber
		System.out.println("channel 0 true");
		foo0.set(true);
		Timer.delay(2.0);

		// opens right, then left
		System.out.println("channel 4 true");
		foo4.set(true);
		Timer.delay(2.0);

		// pushes pusher out
//		System.out.println("channel 1 true");
//		foo1.set(true);
//		Timer.delay(2.0);
//
		// pulls pusher in
//		System.out.println("channel 6 true");
//		foo6.set(true);
//		Timer.delay(2.0);

		// lifts
//		System.out.println("channel 2 true");
//		foo2.set(true);
//		Timer.delay(4.0);

		// so far nothing I can see
//		System.out.println("channel 0 false");
//		foo0.set(false);
//		Timer.delay(2.0);
//

		// so far nothing I can see
//		System.out.println("channel 1 false");
//		foo1.set(false);
//		Timer.delay(2.0);
//

		// so far nothing I can see
//		System.out.println("channel 2 false");
//		foo2.set(false);
//		Timer.delay(2.0);
//
		// so far nothing I can see
//		System.out.println("channel 3 true");
//		foo3.set(true);
//		Timer.delay(2.0);
//
		// so far nothing I can see
//		System.out.println("channel 3 false");
//		foo3.set(false);
//		Timer.delay(2.0);

//
		// so far nothing I can see
//		System.out.println("channel 4 false");
//		foo4.set(false);
//		Timer.delay(2.0);
//
		// closes right, then left grabber
//		System.out.println("channel 5 true");
//		foo5.set(true);
//		Timer.delay(2.0);
//
		// closes right, then left grabber
//		System.out.println("channel 5 false");
//		foo5.set(false);
//		Timer.delay(2.0);
//
//
		// so far nothing I can see
//		System.out.println("channel 6 false");
//		foo6.set(false);
//		Timer.delay(2.0);

//		System.out.println("channel 7 true");
//		foo7.set(true);
//		Timer.delay(2.0);
//
//		System.out.println("channel 7 false");
//		foo7.set(false);
//		Timer.delay(2.0);
	}
}


	/* *******************************************************************
	 * To Do:
	 * 
	 * Two lift motor; 1 runs in reverse of the other
	 * 3 pneumatic objects, one is Tilt 45, tilt 90, grabber 
	 * when all 3 pneumatics are off naturally the tilt is all the way up 
	 * if you activate 45 tilt it will go to 45 
	 * if you activate 90 it will go all the way down
	 * grabber is either release or grab
	 * camera coding figure out
	 ****************************************************************** */

	 /** 
	  * XBox Controller Mapping:
	  * 
	  * left joystick: strafe
	  * right joystick: forward/back/rotate
	  * y:
	  * a: tilt up/down position
	  * b: push/retract
	  * x: grab/release (continuous)
	  * left/right bumper: camera pan (momentary)
	  *
	  *
	  * dpad up: lift up (momentary)
	  * dpad down: lift down (momentary)
	  */
