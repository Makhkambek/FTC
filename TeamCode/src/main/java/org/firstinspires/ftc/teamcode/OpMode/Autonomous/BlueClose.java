package org.firstinspires.ftc.teamcode.OpMode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.OpMode.hardware.ArmSystem;
import org.firstinspires.ftc.teamcode.OpMode.hardware.IntakeSystem;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.teamcode.OpMode.hardware.RobotHardwareMap;

@Autonomous (name = "BlueClose", group = "Autons")
public class BlueClose extends AutonBase {

    RobotHardwareMap theHardwareMap;
    IntakeSystem intakeSystem = new IntakeSystem(theHardwareMap, this); // Создание экземпляра класса IntakeSystem
    ArmSystem armSystem = new ArmSystem(theHardwareMap, this); // Создание переменной класса ArmSystem
    private CSVisionProcessor visionProcessor;
    private VisionPortal visionPortal;

    @Override
    public void runOpMode() {
        theHardwareMap = new RobotHardwareMap(hardwareMap, this );
        super.initialize();

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
        intakeSystem.waitForStartIntakeArm();
        intakeSystem.waitForStartIntake();

        if (opModeIsActive()) {
            //RIGHT side
            if(startingPos == CSVisionProcessor.StartingPosition.RIGHT) {
                imuDrive(0.7, -5, 0);
                sleep(750);
                encoderStrafe(0.5, -20, 5);
                sleep(1000);
                imuDrive(0.5, -22, 0);
                sleep(1000);
                // положить фиолетовый пиксель
                intakeSystem.startIntake();
                sleep(1000);
                intakeSystem.dropPixel();
                sleep(1000);
                imuDrive(0.3, 10, 0);
                sleep(1000);
                imuTurn(0.3, 85);
                sleep(1000);
                imuDrive(0.5, 10, 0);
                //move intake down to deliver
//                    intakeSystem.startIntake();
//                    sleep(1000);
//                    intakeSystem.stopIntake();

                //drive towards pixel
//                imuDrive(.2, -20, 0);
//                encoderStrafe(0.5, -6.5, 5);
//                imuDrive(.15, -14.5, 0);

                //deposit back pixel
//                    armSystem.startArm();
//                    sleep(1000);
//                    armSystem.stopArm();

                //slide out of the way
//                encoderStrafe(0.5, 35, 5);
            }
        }
        //LEFT side
        if(startingPos == CSVisionProcessor.StartingPosition.LEFT) {
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
         if(startingPos == CSVisionProcessor.StartingPosition.CENTER) {
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