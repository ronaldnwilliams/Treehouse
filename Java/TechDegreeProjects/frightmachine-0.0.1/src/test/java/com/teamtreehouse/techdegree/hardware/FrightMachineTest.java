package com.teamtreehouse.techdegree.hardware;

import com.example.accessory.CameraAdapter;
import com.example.accessory.Horn;
import com.example.accessory.Strobe;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

public class FrightMachineTest {

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    private Strobe strobe;
    private Horn horn;
    private Camera camera;
    private CameraAdapter cameraAdapter;
    private FrightMachine machine;

    @Before
    public void setUp() throws Exception {
        strobe = new Strobe();
        horn = new Horn();
        camera = new Camera();
        cameraAdapter = new CameraAdapter(camera, 5);
        machine = new FrightMachine();

        machine.addAccessory(strobe);
        machine.addAccessory(horn);
        machine.addAccessory(cameraAdapter);
    }

    @Test
    public void whenMotionIsDetectedExpectedPluginsKickOff() throws Exception {
        machine.simulateMotion();
        assertThat(systemOutRule.getLog(), allOf(containsString("BEEEEEEEP"),
                                                 containsString("Flashing lights"),
                                                 containsString("Photo #5 taken")));
    }
}