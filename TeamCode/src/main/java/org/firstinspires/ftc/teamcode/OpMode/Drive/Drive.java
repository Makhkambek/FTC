package org.firstinspires.ftc.teamcode.OpMode.Drive;


import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DigitalChannel;

@TeleOp(name = "MainDrive")
public class Drive extends OpMode {

    //ArmSystem
    DcMotor lift_system;
    DcMotor hang_system_1;
    DcMotor hang_system_2;
    //IntakeSystem
    public Servo intake1;
    public Servo intake2;
    public Servo intakeArm; //один серво - Рука
    public Servo servo_lift;
    DcMotor intake;


    DigitalChannel liftLimitSwitch;
    DigitalChannel touchSensor_1;
    DigitalChannel touchSensor_2;

    //Base
    DcMotor frontLeftMotor;
    DcMotor rearLeftMotor;
    DcMotor frontRightMotor;
    DcMotor rearRightMotor;

    private final String LIFT_SYSTEM_CONFIG_NAME = "lift_system";
    private final String HANG_SYSTEM_1_CONFIG_NAME = "hang_system_1";
    private final String HANG_SYSTEM_2_CONFIG_NAME = "hang_system_2";

    boolean rTriggerPressed = false;
    boolean lTriggerPressed = false;
    boolean rBumperPressed = false;
    boolean lBumperPressed = false;

    private enum State {
        INTAKE_OFF,
        INTAKE_ON,
        INTAKE_ARM_ON
    }

    private State currentState;

    @Override
    public void init() {
        lift_system = hardwareMap.dcMotor.get(LIFT_SYSTEM_CONFIG_NAME);
        hang_system_1 = hardwareMap.dcMotor.get(HANG_SYSTEM_1_CONFIG_NAME);
        hang_system_2 = hardwareMap.dcMotor.get(HANG_SYSTEM_2_CONFIG_NAME);

        intake1 = hardwareMap.get(Servo.class, "intake1");
        intake2 = hardwareMap.get(Servo.class, "intake2");
        intakeArm = hardwareMap.get(Servo.class, "intakeArm");
        servo_lift = hardwareMap.get(Servo.class, "servo_lift");
        intake = hardwareMap.dcMotor.get("intake");

        frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        rearLeftMotor = hardwareMap.dcMotor.get("rearLeftMotor");
        frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        rearRightMotor = hardwareMap.dcMotor.get("rearRightMotor");

        frontRightMotor.setDirection(DcMotor.Direction.REVERSE);
        rearRightMotor.setDirection(DcMotor.Direction.REVERSE);
        hang_system_1.setDirection(DcMotor.Direction.REVERSE);
        hang_system_2.setDirection(DcMotor.Direction.REVERSE);

        liftLimitSwitch = hardwareMap.digitalChannel.get("liftLimitSwitch");
        liftLimitSwitch.setMode(DigitalChannel.Mode.INPUT);

        touchSensor_1 = hardwareMap.digitalChannel.get("touchSensor_1");
        touchSensor_1.setMode(DigitalChannel.Mode.INPUT);

        touchSensor_2 = hardwareMap.digitalChannel.get("touchSensor_2");
        touchSensor_2.setMode(DigitalChannel.Mode.INPUT);

        currentState = State.INTAKE_OFF;

    }

    @Override
    public void loop() {
        boolean isMagneticFieldDetected = liftLimitSwitch.getState();
        boolean isTouching = touchSensor_1.getState() && touchSensor_2.getState();

        if (!isMagneticFieldDetected) {
            telemetry.addData("Magnetic Sensor State", "yes");
        } else {
            telemetry.addData("Magnetic Sensor State", "no");
        }

        if (!isTouching) {
            telemetry.addData("Touch Sensor State", "yes");
        } else {
            telemetry.addData("Touch Sensor State", "no");
        }
        telemetry.update();

        updateState(gamepad1.right_bumper);

        switch (currentState) {
            case INTAKE_OFF:
                intakeOff();
                break;
            case INTAKE_ON:
                intakeOn();
                break;
            case INTAKE_ARM_ON:
                intakeArmOn();
                break;
            default:
                break;
        }

        processRTrigger();
        processLTrigger();
        processLBumper();
        processJoystick();
//        try {
////            processRBumper();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
    }

    private void updateState(boolean isRBumperPressed) {
        switch (currentState) {
            case INTAKE_OFF:
                if(isRBumperPressed) {
                    currentState = State.INTAKE_ON;
                }
                break;
            case INTAKE_ON:
                currentState = State.INTAKE_ARM_ON;
                break;
            case INTAKE_ARM_ON:
                if(!isRBumperPressed) {
                    currentState = State.INTAKE_OFF;
                }
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


    private void processRTrigger() {
        if (gamepad1.right_trigger > 0) {
            rTriggerPressed = true;
        } else {
            rTriggerPressed = false;
        }

        if (rTriggerPressed) {
//            lift_system.setPower(1);
            hang_system_1.setPower(1);
            hang_system_2.setPower(1);
            servo_lift.setPosition(0.2);
        } else {
            lift_system.setPower(0);
            hang_system_1.setPower(0);
            hang_system_2.setPower(0);
            servo_lift.setPosition(0.8);
        }
    }


    private void processLTrigger() {
        if (gamepad1.left_trigger > 0) {
            lTriggerPressed = true;
        } else {
            lTriggerPressed = false;
        }
        if (lTriggerPressed) {
            if (liftLimitSwitch.getState()) {
//                lift_system.setPower(-0.7);
            } else {
                lift_system.setPower(0);
            }

            if (touchSensor_1.getState()) {
                hang_system_1.setPower(-1);
            } else {
                hang_system_1.setPower(0);
            }

            if (touchSensor_2.getState()) {
                hang_system_2.setPower(-1);
            } else {
                hang_system_2.setPower(0);
            }
        }
    }










//    private void processRBumper() throws InterruptedException {
//        if (gamepad1.right_bumper) {
//            rBumperPressed = true;
//        } else {
//            rBumperPressed = false;
//        }
//        if(rBumperPressed) {
//            intake1.setPosition(0.6);
//            intake2.setPosition(0);
//            sleep(1000);
//            intakeArm.setPosition(0.4);
//            intake.setPower(1);
//        } else {
//            intake1.setPosition(0);
//            intake2.setPosition(0.6);
//            intakeArm.setPosition(0.9);
//            intake.setPower(0);
//        }
//    }

    private void processLBumper() {
        if (gamepad1.left_bumper) {
            lBumperPressed = true;
        } else {
            lBumperPressed = false;
        }
        if(lBumperPressed) intake.setPower(-1);
    }

    private void processJoystick() {
        double y = -gamepad1.left_stick_y;
        double x = gamepad1.left_stick_x * 1.1;
        double rx = -gamepad1.right_stick_x;

        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;

        frontLeftMotor.setPower(frontLeftPower);
        rearLeftMotor.setPower(backLeftPower);
        frontRightMotor.setPower(frontRightPower);
        rearRightMotor.setPower(backRightPower);
    }
}
