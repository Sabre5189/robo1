package org.usfirst.frc.team5189.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;

public class RobotDiagnostic extends Robot {
	private DriveBase m_driveBase = new DriveBase(new VictorSP(0), new VictorSP(1), new VictorSP(2), new VictorSP(3));

	public void RunDiagnostic() {
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

		Lift lift=new Lift ();
		lift.raise(100);
		Timer.delay(5.0);
		lift.lower(100);
	}
}
