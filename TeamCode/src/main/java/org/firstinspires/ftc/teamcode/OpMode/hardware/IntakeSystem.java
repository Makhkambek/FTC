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

    private enum State {
        INTAKE_OFF,
        INTAKE_ON,
        INTAKE_ARM_ON
    }
    private State currentState;

    public void init () {
        intake1 = hardwareMap.get(Servo.class, "intake1");
        intake2 = hardwareMap.get(Servo.class, "intake2");
        intakeArm = hardwareMap.get(Servo.class, "intakeArm");
        intake = hardwareMap.dcMotor.get("intake");
//        this.currentState = State.INTAKE_OFF;
    }

    public void startIntake() {
        switch (currentState) {
            case INTAKE_OFF:
                intakeOff();
                currentState = State.INTAKE_ON; // Переключаем состояние
                break;
            case INTAKE_ARM_ON:
                 intakeArmOn();
                currentState = State.INTAKE_OFF; // Переключаем состояние
                break;
            default:
                break;
        }
    }

    public void stopIntake() {
        switch (currentState) {
            case INTAKE_ON:
                intakeOff();
                currentState = State.INTAKE_OFF; // Переключаем состояние
                break;
            default:
                break;
        }
    }

    private void intakeOn() {
        intake1.setPosition(0.6);
        intake2.setPosition(0);
    }
    private void intakeOff() {
        intake1.setPosition(0);
        intake2.setPosition(0.6);
        intakeArm.setPosition(0.9);
        intake.setPower(0);
    }
    private void intakeArmOn() {
        intakeArm.setPosition(0.4);
        intake.setPower(1);
    }


//    public void startIntake() {
//        intake1.setPosition(0);
//        intake2.setPosition(0.6);
//        intakeArm.setPosition(1); //один серво для руки
//        intake.setPower(0.1);
//    }
//
//    public void stopIntake() {
//        intake1.setPosition(0.6);
//        intake2.setPosition(0);
//        intakeArm.setPosition(0);
//        intake.setPower(0);
//    }

}
