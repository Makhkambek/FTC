package org.firstinspires.ftc.teamcode.OpMode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.OpMode.hardware.ArmSystem;
import org.firstinspires.ftc.teamcode.OpMode.hardware.IntakeSystem;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.teamcode.OpMode.hardware.RobotHardwareMap;

@Autonomous
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

        visionProcessor = new CSVisionProcessor(90,0,150,350,100,550,150);
        visionPortal = VisionPortal.easyCreateWithDefaults(hardwareMap.get(WebcamName.class, "Webcam 1"), visionProcessor);

        CSVisionProcessor.StartingPosition startingPos = CSVisionProcessor.StartingPosition.NONE;

//        waitForStart();

        while(!this.isStarted() && !this.isStopRequested()) {
            startingPos = visionProcessor.getStartingPosition();
            telemetry.addData("Identified", visionProcessor.getStartingPosition());
            telemetry.update();
        }

        visionPortal.stopStreaming();

        if (opModeIsActive()) {
            //RIGHT side
            if(startingPos == CSVisionProcessor.StartingPosition.RIGHT) {
                if (startingPos == CSVisionProcessor.StartingPosition.RIGHT) {
                    imuDrive(.4, 29.5, 0);
                    imuTurn(.3, 90);
                    imuDrive(.25, 7, 0);
                    imuDrive(.15, -3, 0);

                    //move intake down to deliver
                    intakeSystem.startIntake();
                    sleep(1000);
                    intakeSystem.stopIntake();

                    //drive towards pixel
                    imuDrive(.2, -20, 0);
                    encoderStrafe(0.5, -6.5, 5);
                    imuDrive(.15, -14.5, 0);

                    //deposit back pixel
                    armSystem.startArm();
                    sleep(1000);
                    armSystem.stopArm();

                    //slide out of the way
                    encoderStrafe(0.5, 35, 5);
                }
            }
        }
        //LEFT side
        else if(startingPos == CSVisionProcessor.StartingPosition.LEFT) {
            if (startingPos == CSVisionProcessor.StartingPosition.LEFT) {
                imuDrive(.4, 5, 0);
                encoderStrafe(0.25, -11.75, 5);
                imuDrive(0.25, 25, 0);
                imuDrive(.25, -6, 0);

                //move intake down to deliver
                intakeSystem.startIntake(); // положить пиксель
                sleep(1000);
                intakeSystem.stopIntake();

                //drive to deliver pixel
                imuDrive(0.25, -5.5, 0);
                encoderStrafe(0.5, -35, 5);
                imuTurn(0.5, 90);

                //deliver pixel
                armSystem.startArm();
                sleep(1000);
                armSystem.stopArm();

                //park
                encoderStrafe(0.5, 15, 5);
                sleep(250);
            }
        }
        //CENTER side
        else if(startingPos == CSVisionProcessor.StartingPosition.CENTER) {
            if(startingPos == CSVisionProcessor.StartingPosition.CENTER) {
                imuDrive(.3, 36, 0);
                sleep(750);
                imuDrive(.15, -5, 0);

                //move intake down to deliver
                intakeSystem.startIntake(); //поставить пиксель
                sleep(1000);
                intakeSystem.stopIntake();

                //drive to deliver pixel
                imuDrive(.15, -8, 0);
                imuTurn(.75, 90);
                imuDrive(0.5, -37.5, 0);
                encoderStrafe(0.25, -2.5, 5);

                //deploy back pixel
                armSystem.startArm();
                sleep(1000);
                armSystem.stopArm();

                encoderStrafe(0.5, 30, 5);
            }

        }
        //NONE
        else {
            imuDrive(.5, 5, 0);
            encoderStrafe(0.5, -47, 5);
            telemetry.addData("Park Position Unknown", startingPos);
        }

    }


}
