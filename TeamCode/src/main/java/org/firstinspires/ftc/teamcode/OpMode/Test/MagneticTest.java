package org.firstinspires.ftc.teamcode.OpMode.Test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DigitalChannel;

@Disabled
@TeleOp(name = "SensorTest")
public class MagneticTest extends OpMode {

    DigitalChannel magneticSensor;
    DigitalChannel touchSensor_1;
    DigitalChannel touchSensor_2;

    @Override
    public void init() {
        magneticSensor = hardwareMap.digitalChannel.get("liftLimitSensor");
        magneticSensor.setMode(DigitalChannel.Mode.INPUT);

        // Инициализация сенсора касания
        touchSensor_1 = hardwareMap.digitalChannel.get("touchSensor_1");
        touchSensor_1.setMode(DigitalChannel.Mode.INPUT);

        touchSensor_2 = hardwareMap.digitalChannel.get("touchSensor_2");
        touchSensor_2.setMode(DigitalChannel.Mode.INPUT);
    }

    @Override
    public void loop() {
        // Получение состояний сенсоров
        boolean isMagneticFieldDetected = magneticSensor.getState();
        boolean isTouching = touchSensor_1.getState() && touchSensor_2.getState();

        if (!isMagneticFieldDetected) {
            telemetry.addData("Magnetic Sensor State", "детектировано");
        } else {
            telemetry.addData("Magnetic Sensor State", "не детектировано");
        }

        if (!isTouching) {
            telemetry.addData("Touch Sensor State", "касание");
        } else {
            telemetry.addData("Touch Sensor State", "нет касания");
        }

        telemetry.update();
    }
}
