package org.firstinspires.ftc.teamcode.OpMode.Autonomous;
import org.firstinspires.ftc.teamcode.OpMode.hardware.RobotHardwareMap;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "Auton Test", group = "Autons")
public class AutonTest extends AutonBase {

    @Override
    public void runOpMode(){
        theHardwareMap = new RobotHardwareMap(hardwareMap, this);
        super.initialize();
        waitForStart();

        imuDrive(0.2, 20, 0);
        encoderStrafe(0.25, -8, 5);
//        imuDrive(0.25, 6, 0);
        imuTurn(0.5, 87);
//        imuDrive(0.75, 87, 0);
//        sleep(4000);
//        encoderStrafe(.75, 27, /2000);
//        imuDrive(0.5, 12, 0);
    }
}
