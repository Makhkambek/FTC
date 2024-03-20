package org.firstinspires.ftc.teamcode.OpMode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.teamcode.OpMode.Autonomous.AutonBase;
import org.firstinspires.ftc.teamcode.OpMode.Autonomous.SampleVisionOpMode;
import org.firstinspires.ftc.teamcode.OpMode.Autonomous.CSVisionProcessor;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.OpMode.hardware.IntakeSystem;
import org.firstinspires.ftc.teamcode.OpMode.hardware.ArmSystem;
import org.firstinspires.ftc.teamcode.OpMode.hardware.RobotHardwareMap;


@Autonomous(name = "Auton Blue Close To Backstage", group = "Autons")
public class AutonBlueClose extends AutonBase {

    private int parkingPosition = -1;
    private int     lastParkingPosition = -1;
    IntakeSystem intakeSystem = new IntakeSystem(theHardwareMap, this); // Создание экземпляра класса IntakeSystem
    ArmSystem armSystem = new ArmSystem(theHardwareMap, this); // Создание переменной класса ArmSystem

    @Override
    public void runOpMode() {
        theHardwareMap = new RobotHardwareMap(hardwareMap, this );
        super.initialize();

        boolean left = false;
        boolean middle = false;
        boolean right = true;

//        initialize();

        while (opModeInInit()) {
//            //check that the camera is open and working
//            armSystem.checkSensors(); // проверка состояния сенсоров


            if (lastParkingPosition != parkingPosition) {
                if (parkingPosition == 1) {
                    telemetry.addData("Parking position", 1);
                } else if (parkingPosition == 2) {
                    telemetry.addData("Parking position", 2);
                } else if (parkingPosition == 3) {
                    telemetry.addData("Parking position", 3);
                } else if (parkingPosition == 0) {
                    telemetry.addData("Parking position", 0);
                }
                lastParkingPosition = parkingPosition;
            }
        }

            waitForStart();
            armSystem.checkSensors(); // проверка состояния сенсоров
            //Set the arm position up to not drag
            intakeSystem.stopIntake(); //интейк на 0 позиции

            //Left
            if (parkingPosition == 1) {
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
            //Middle
            else if (parkingPosition == 2) {
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
            //Right
            else if (parkingPosition == 3) {
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
            //Error unable to find target so slide to backdrop
            else {
                imuDrive(.5, 5, 0);
                encoderStrafe(0.5, -47, 5);
                telemetry.addData("Park Position Unknown", parkingPosition);
            }

            //set arm down in the end
//            armMotor.moveArmEncoded(ArmPositions.FRONT_ARC_MIN);
        }
    }
