package org.firstinspires.ftc.teamcode.OpMode.hardware;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.teamcode.OpMode.hardware.RobotGlobalSettings.initialize;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class IntakeSystem {

    RobotHardwareMap robotHardwareMap;
    LinearOpMode opMode;
    public IntakeSystem(RobotHardwareMap robotHardwareMap, LinearOpMode opMode){
        this.opMode = opMode;
        this.robotHardwareMap = robotHardwareMap;
        initialize();
    }
    public Servo intake1;
    public Servo intake2;
    public Servo intakeArm; //рука
    DcMotor intake;

    public void init () {
        intake1 = hardwareMap.get(Servo.class, "intake1");
        intake2 = hardwareMap.get(Servo.class, "intake2");
        intakeArm = hardwareMap.get(Servo.class, "intakeArm");
        intake = hardwareMap.dcMotor.get("intake");
    }


    public void startIntake() {
        intake1.setPosition(0.6);
        intake2.setPosition(0);
    }
    public void dropPixel() {
        intakeArm.setPosition(0.2);
        intake.setPower(-0.1);
    }
    public void waitForStartIntake() {
        intake1.setPosition(0);
        intake2.setPosition(0.6);
    }
    public void waitForStartIntakeArm() {
        intakeArm.setPosition(0.6);
        intake.setPower(0);
    }

}
