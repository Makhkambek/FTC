package org.firstinspires.ftc.teamcode.OpMode.hardware;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

public class ArmSystem {

    DcMotor lift_system;
    DcMotor hang_system_1;
    DcMotor hang_system_2;

    DigitalChannel liftLimitSwitch;
    DigitalChannel touchSensor_1;
    DigitalChannel touchSensor_2;
    public void init () {
        lift_system = hardwareMap.dcMotor.get("lift_system");
        hang_system_1 = hardwareMap.dcMotor.get("hang_system_1");
        hang_system_2 = hardwareMap.dcMotor.get("hang_system_2");

        liftLimitSwitch = hardwareMap.digitalChannel.get("liftLimitSwitch");
        liftLimitSwitch.setMode(DigitalChannel.Mode.INPUT);

        touchSensor_1 = hardwareMap.digitalChannel.get("touchSensor_1");
        touchSensor_1.setMode(DigitalChannel.Mode.INPUT);

        touchSensor_2 = hardwareMap.digitalChannel.get("touchSensor_2");
        touchSensor_2.setMode(DigitalChannel.Mode.INPUT);
    }

    public void startArm() {

        lift_system.setPower(1);
        hang_system_1.setPower(1);
        hang_system_2.setPower(1);
    }

    public void checkSensors() {
        // Проверяем состояние датчиков и выводим их состояния
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
    }

    public void stopArm() {
        // Останавливаем движение систем
        lift_system.setPower(0);
        hang_system_1.setPower(0);
        hang_system_2.setPower(0);

        // Возвращаем системы на нулевую позицию
        while (!liftLimitSwitch.getState()) {
            lift_system.setPower(0.5);
        }
        lift_system.setPower(0);

        while (!touchSensor_1.getState()) {
            hang_system_1.setPower(0.5);
        }
        hang_system_1.setPower(0);

        while (!touchSensor_2.getState()) {
            hang_system_2.setPower(0.5);
        }
        hang_system_2.setPower(0);

        // Показываем состояния датчиков
        checkSensors();
    }


}
