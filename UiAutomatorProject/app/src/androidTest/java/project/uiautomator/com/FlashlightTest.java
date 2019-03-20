package project.uiautomator.com;

import android.content.Context;
import android.content.Intent;
import android.support.test.filters.SdkSuppress;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

/**
 * 项目名：UiAutomatorProject
 * 包名：  project.uiautomator.com
 * 文件名：FlashlightTest
 * Created by liushengjie on 2019/3/19.
 * 描述：
 */

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class FlashlightTest {

    private static final String TAG = "project.uiautomator.com";
    private static final String PKG = "com.flashlight.brightest.beacon.torch";

    private static final int LUANCH_TIMEOUT = 5000;

    private UiDevice mDevice;

    @Before
    public void startMainActivityFromHomeScreen() {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        mDevice.pressHome();

        Context context = ApplicationProvider.getApplicationContext();
        final Intent intent = context.getPackageManager().getLaunchIntentForPackage(PKG);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        mDevice.wait(Until.hasObject(By.pkg(PKG).depth(0)),
                LUANCH_TIMEOUT);
    }

    @Test
    public void testEnterApplication_news1() throws UiObjectNotFoundException {
        UiObject startButton = mDevice.findObject(new UiSelector().resourceId("com.flashlight.brightest.beacon.torch:id/btn_start"));
        if (startButton.exists() && startButton.isEnabled()) {
            startButton.click();
        }
    }
}
