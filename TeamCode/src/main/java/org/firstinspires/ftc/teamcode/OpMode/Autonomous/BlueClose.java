package org.firstinspires.ftc.teamcode.OpMode.Autonomous;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DigitalChannel;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.OpMode.hardware.ArmSystem;
import org.firstinspires.ftc.teamcode.OpMode.hardware.IntakeSystem;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.teamcode.OpMode.hardware.RobotHardwareMap;

@Autonomous (name = "BlueClose", group = "Autons")
public class BlueClose extends AutonBase {

    RobotHardwareMap theHardwareMap;
    ArmSystem armSystem = new ArmSystem(theHardwareMap, this); // Создание переменной класса ArmSystem
    private CSVisionProcessor visionProcessor;
    private VisionPortal visionPortal;
    public Servo intake1;
    public Servo intake2;
    public Servo intakeArm; //рука
    DcMotor intake;
    DcMotor lift_system;
    DcMotor hang_system_1;
    DcMotor hang_system_2;
    public Servo servo_lift;
    DigitalChannel liftLimitSwitch;
    DigitalChannel touchSensor_1;
    DigitalChannel touchSensor_2;

    @Override
    public void runOpMode() {
        theHardwareMap = new RobotHardwareMap(hardwareMap, this );
//        ArmSystem armSystem = new ArmSystem(theHardwareMap, this); // Создание переменной класса ArmSystem
        super.initialize();
        intake1 = hardwareMap.get(Servo.class, "intake1");
        intake2 = hardwareMap.get(Servo.class, "intake2");
        intakeArm = hardwareMap.get(Servo.class, "intakeArm");
        intake = hardwareMap.dcMotor.get("intake");
        lift_system = hardwareMap.dcMotor.get("lift_system");
        hang_system_1 = hardwareMap.dcMotor.get("hang_system_1");
        hang_system_2 = hardwareMap.dcMotor.get("hang_system_2");
        servo_lift = hardwareMap.get(Servo.class, "servo_lift");

        liftLimitSwitch = hardwareMap.digitalChannel.get("liftLimitSwitch");
        liftLimitSwitch.setMode(DigitalChannel.Mode.INPUT);

        touchSensor_1 = hardwareMap.digitalChannel.get("touchSensor_1");
        touchSensor_1.setMode(DigitalChannel.Mode.INPUT);

        touchSensor_2 = hardwareMap.digitalChannel.get("touchSensor_2");
        touchSensor_2.setMode(DigitalChannel.Mode.INPUT);
        intake.setDirection(DcMotor.Direction.REVERSE);
        hang_system_1.setDirection(DcMotor.Direction.REVERSE);
        hang_system_2.setDirection(DcMotor.Direction.REVERSE);

        visionProcessor = new CSVisionProcessor(90,0 ,250,270,150,550,150);
        visionPortal = VisionPortal.easyCreateWithDefaults(hardwareMap.get(WebcamName.class, "Webcam 1"), visionProcessor);

        CSVisionProcessor.StartingPosition startingPos = CSVisionProcessor.StartingPosition.NONE;

        while (opModeInInit()) {
            while (!this.isStarted() && !this.isStopRequested()){
                startingPos = visionProcessor.getStartingPosition();
                telemetry.addData("Identified", visionProcessor.getStartingPosition());
                telemetry.update();
            }

        }
        initialize();


        waitForStart();
        if (opModeIsActive()) {
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

            waitForStartIntakeArm();
            waitForStartIntake();
            //RIGHT side
            if (startingPos == CSVisionProcessor.StartingPosition.RIGHT) {
//                imuDrive(0.7, -5, 0);
//                sleep(750);
//                encoderStrafe(0.5, -16, 5);
//                sleep(1000);
//                imuDrive(0.5, -9, -5);
//                sleep(750);
//                // положить фиолетовый пиксель
//                startIntake();
//                sleep(500);
//                dropPixel();
//                sleep(1000);
//                waitForStartIntakeArm();
//                waitForStartIntake();
//                imuTurn(0.3, 88);
//                sleep(750);
                //положить на бекдроп
                startArm();
                sleep(1000);
                stopArm();
                sleep(1000);

            }
            //LEFT side
            if (startingPos == CSVisionProcessor.StartingPosition.LEFT) {
                imuDrive(0.7, -30, 0);
                sleep(750);
                imuTurn(0.5, 90);
                sleep(1000);
                imuDrive(0.3, 10, 0);
                sleep(1000);
                imuDrive(0.5, 13, 0);
                // поставить фиолетовый пикель
//                imuDrive(0.5, 5, 0);

                //move intake down to deliver
//                intakeSystem.startIntake(); // положить пиксель
//                sleep(1000);
//                intakeSystem.stopIntake();

                //drive to deliver pixel
//                imuDrive(0.25, -5.5, 0);
//                encoderStrafe(0.5, -35, 5);
//                imuTurn(0.5, 90);

                //deliver pixel
//                armSystem.startArm();
//                sleep(1000);
//                armSystem.stopArm();

                //park
//                encoderStrafe(0.5, 15, 5);
//                sleep(250);
            }
            //CENTER side
            if (startingPos == CSVisionProcessor.StartingPosition.CENTER) {
                imuDrive(0.7, -20, 0); //вперед
                sleep(750);
                // поставить фиолетовый пиксель
                imuTurn(0.5, 88);
                encoderStrafe(0.5, -7, 5);
                sleep(1000);
                imuDrive(0.7, 18, 0);
                // поставить желтый пиксель

                //move intake down to deliver
//                intakeSystem.startIntake(); //поставить пиксель
//                sleep(1000);
//                intakeSystem.stopIntake();

                //deploy back pixel
//                armSystem.startArm();
//                sleep(1000);
//                armSystem.stopArm();
            }
            //NONE
            else {
//            imuDrive(.5, 5, 0);
//            encoderStrafe(0.5, -47, 5);
                telemetry.addData("Park Position Unknown", startingPos);
            }
        }

    }


//    public void checkSensors() {
//        // Проверяем состояние датчиков и выводим их состояния
//        boolean isMagneticFieldDetected = liftLimitSwitch.getState();
//        boolean isTouching = touchSensor_1.getState() && touchSensor_2.getState();
//
//        if (!isMagneticFieldDetected) {
//            telemetry.addData("Magnetic Sensor State", "yes");
//        } else {
//            telemetry.addData("Magnetic Sensor State", "no");
//        }
//
//        if (!isTouching) {
//            telemetry.addData("Touch Sensor State", "yes");
//        } else {
//            telemetry.addData("Touch Sensor State", "no");
//        }
//
//        telemetry.update();
//    }

    public void stopArm() {
        // Останавливаем движение систем
//        lift_system.setPower(0);
//        hang_system_1.setPower(0);
//        hang_system_2.setPower(0);
        servo_lift.setPosition(0.75);

        // Возвращаем системы на нулевую позицию
        while (liftLimitSwitch.getState()) {
            lift_system.setPower(-0.6);
        }
        lift_system.setPower(0);

        while (touchSensor_1.getState()) {
            hang_system_1.setPower(-0.5);
        }
        hang_system_1.setPower(0);

        while (touchSensor_2.getState()) {
            hang_system_2.setPower(-0.5);
        }
        hang_system_2.setPower(0);

        // Показываем состояния датчиков
    }

    public void startArm() {
        lift_system.setPower(0.4);
        hang_system_1.setPower(0.5);
        hang_system_2.setPower(0.5);
        servo_lift.setPosition(0.2);
    }

    public void startIntake() {
        intake1.setPosition(0.6);
        intake2.setPosition(0);
    }
    public void dropPixel() {
        intakeArm.setPosition(0.2);
        sleep(1000);
        intake.setPower(-0.25);
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