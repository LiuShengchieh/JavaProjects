package project.uiautomator.com;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import androidx.test.filters.SdkSuppress;
import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiScrollable;
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
    private static final String ON = "ON";
    private static final String OFF = "OFF";

    private static final int LAUNCH_TIMEOUT = 5000;

    private static UiDevice mDevice;

    private String getLauncherPackageName() {
        // Create launcher Intent
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);

        // Use PackageManager to get the launcher package name
        PackageManager pm = getApplicationContext().getPackageManager();
        ResolveInfo resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return resolveInfo.activityInfo.packageName;
    }

    @Before
    public void startMainActivityFromHomeScreen() {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        mDevice.pressHome();

        // Wait for launcher
        final String launcherPackage = getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);

        // Launch a application that name is PKG
        Context context = getApplicationContext();
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(PKG);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // Clear out any previous instances
        context.startActivity(intent);
        mDevice.wait(Until.hasObject(By.pkg(PKG).depth(0)),
                LAUNCH_TIMEOUT);
    }

    @Ignore
    public void testTabSwitch_setting() throws UiObjectNotFoundException {
        // 点击设置tab
        UiObject settingTab = mDevice.findObject(new UiSelector().
                className("android.widget.LinearLayout").index(1)).getChild(new UiSelector()
        .className("android.widget.RelativeLayout").index(3));
        settingTab.click();
    }

    @Ignore
    public void testEnterApplication_news_off() throws UiObjectNotFoundException {
        // 关闭接收新闻
        UiObject checkoutBox = mDevice.findObject(new UiSelector().
                resourceId("com.flashlight.brightest.beacon.torch:id/check_box"));
        if (checkoutBox.exists() && checkoutBox.isEnabled()) {
            checkoutBox.click();
//            checkoutBox.click();
        }

        // 进入应用
//        enterApplication();

        // 点击start
        UiObject startButton = mDevice.findObject(new UiSelector().
                resourceId("com.flashlight.brightest.beacon.torch:id/btn_start"));
        if (startButton.exists() && startButton.isEnabled()) {
            startButton.click();
            startButton.click();
        }

        // 关闭权限弹窗
        UiObject closeDialog = mDevice.findObject(new UiSelector().
                resourceId("com.flashlight.brightest.beacon.torch:id/dialog_pm_close"));
        if (closeDialog.exists() && closeDialog.isEnabled()) {
            closeDialog.click();
        }

        // 同意权限申请
        UiObject allowButton = mDevice.findObject(new UiSelector().
                resourceId("com.sonymobile.cta:id/permission_allow_button"));
        if (allowButton.exists() && allowButton.isEnabled()) {
            allowButton.click();
        }

        // 点击设置tab
        UiObject settingTab = mDevice.findObject(new UiSelector().
                className("android.widget.LinearLayout").index(1)).getChild(new UiSelector()
                .className("android.widget.RelativeLayout").index(3));
        settingTab.click();

        // 滑动到底部，点击"About feeds"
        UiScrollable uiScrollable = new UiScrollable(new UiSelector().scrollable(true));
        uiScrollable.scrollIntoView(new UiSelector().text("About feeds"));
        mDevice.findObject(new UiSelector().text("About feeds")).clickAndWaitForNewWindow();

        // 判断开关关闭状态，预期：OFF
        UiObject acceptNews = mDevice.findObject(new UiSelector().
                resourceId("com.flashlight.brightest.beacon.torch:id/cb_setting_check"));
        assertEquals(OFF, acceptNews.getText());
    }

    @Ignore
    public void testEnterApplication_news_on() throws UiObjectNotFoundException {
//        enterApplication();

        // 点击start
        UiObject startButton = mDevice.findObject(new UiSelector().
                resourceId("com.flashlight.brightest.beacon.torch:id/btn_start"));
        if (startButton.exists() && startButton.isEnabled()) {
            startButton.click();
            startButton.click();
        }

        // 关闭权限弹窗
        UiObject closeDialog = mDevice.findObject(new UiSelector().
                resourceId("com.flashlight.brightest.beacon.torch:id/dialog_pm_close"));
        if (closeDialog.exists() && closeDialog.isEnabled()) {
            closeDialog.click();
        }

        // 同意权限申请
        UiObject allowButton = mDevice.findObject(new UiSelector().
                resourceId("com.sonymobile.cta:id/permission_allow_button"));
        if (allowButton.exists() && allowButton.isEnabled()) {
            allowButton.click();
        }

        // 点击设置tab
        UiObject settingTab = mDevice.findObject(new UiSelector().
                className("android.widget.LinearLayout").index(1)).getChild(new UiSelector()
                .className("android.widget.RelativeLayout").index(3));
        settingTab.click();

        // 滑动到底部，点击"About feeds"
        UiScrollable uiScrollable = new UiScrollable(new UiSelector().scrollable(true));
        uiScrollable.scrollIntoView(new UiSelector().text("About feeds"));
        mDevice.findObject(new UiSelector().text("About feeds")).clickAndWaitForNewWindow();

        // 判断开关关闭状态，预期：OFF
        UiObject acceptNews = mDevice.findObject(new UiSelector().
                resourceId("com.flashlight.brightest.beacon.torch:id/cb_setting_check"));
        assertEquals(ON, acceptNews.getText());
    }

    @Ignore
    public void testTab1_flashlight_switch() throws UiObjectNotFoundException {
        // 关闭手电筒
        UiObject flashlightButton = mDevice.findObject(new UiSelector().
                resourceId("com.flashlight.brightest.beacon.torch:id/flashlight_btn"));
//        if (flashlightButton.exists() && flashlightButton.isEnabled()) {
//            flashlightButton.click();
//        }
        flashlightButton.click();
//        File path = new File("/sdcard/flashlight_off.png");
//        mDevice.takeScreenshot(path);
        uScreenshot("flashlight_off");
    }

    @Ignore
    public void testTab1_change_skin() throws UiObjectNotFoundException {
        // 点击皮肤按钮
        UiObject skinButton = mDevice.findObject(new UiSelector()
            .resourceId("com.flashlight.brightest.beacon.torch:id/btn_skin_top"));
//        if (skinButton.exists() && skinButton.isEnabled()) {
//            skinButton.click();
//        }
        skinButton.click();
        // 换肤
        UiObject skinLinear = mDevice.findObject(new UiSelector().
                className("android.widget.LinearLayout")).getChild(new UiSelector().
                className("android.widget.RelativeLayout").index(5));
//        if (skinLinear.exists() && skinLinear.isClickable()) {
//            skinLinear.click();
//        }
        skinLinear.click();
    }

    @Ignore
    public void testTab1_change_flashlight() throws UiObjectNotFoundException {
        // 点击手电筒tab
        tabSwitch(0);
        // 点击手机屏幕图标
        UiObject screenButton = mDevice.findObject(new UiSelector().
                className("android.widget.LinearLayout"));
        screenButton.click();
        // 截图
//        mDevice.takeScreenshot(new File("/sdcard/Pictures/Screenshots/changeworld.png"));
        uScreenshot("changeworld");
    }

    @Test
    public void testTab2_apply_screen_flash() throws UiObjectNotFoundException {
        enterApplication();
        tabSwitch(1);
        uScreenshot("tab2_screen_flash");
        UiObject alwaysAllowButton = mDevice.findObject(new UiSelector().
                resourceId("com.sonymobile.cta:id/permission_allow_button"));
        if (alwaysAllowButton.exists() && alwaysAllowButton.isClickable()) {
            alwaysAllowButton.click();
            alwaysAllowButton.click();
        }
        // 点击第二个caller
        UiObject gridView = mDevice.findObject(new UiSelector().
                resourceId("com.flashlight.brightest.beacon.torch:id/style_gridview")).
                getChild(new UiSelector().className("android.widget.RelativeLayout").index(1));
        gridView.click();
        // 打开cool screen flash
        UiObject laterButton = mDevice.findObject(new UiSelector().
                resourceId("android:id/button2"));
        laterButton.click();
        // 点击apply
        UiObject applyButton = mDevice.findObject(new UiSelector().
                resourceId("com.flashlight.brightest.beacon.torch:id/tvApply"));
        applyButton.click();
        // 截图
        uScreenshot("tab2_apply_screen_flash");
    }

    @After
    public void tearDown() {
//        clearData(PKG);
        for (int i = 0; i < 3; i++) {
            mDevice.pressBack();
        }
        // to-do:将截图取出到本地
    }

    // 清空应用数据
    public static void clearData (String packageName) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                InstrumentationRegistry.getInstrumentation().getUiAutomation()
                        .executeShellCommand("pm clear " + packageName)
                        .close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 初次进入应用
    public void enterApplication() throws UiObjectNotFoundException {
        // 点击start
        UiObject startButton = mDevice.findObject(new UiSelector().
                resourceId("com.flashlight.brightest.beacon.torch:id/btn_start"));
        if (startButton.exists() && startButton.isEnabled()) {
            startButton.click();
//            startButton.click();
        }

        // 关闭权限弹窗
        UiObject closeDialog = mDevice.findObject(new UiSelector().
                resourceId("com.flashlight.brightest.beacon.torch:id/dialog_pm_close"));
        if (closeDialog.exists() && closeDialog.isEnabled()) {
            closeDialog.click();
        } else {
            startButton.click();
            closeDialog.click();
        }

        // 同意权限申请
        UiObject allowButton = mDevice.findObject(new UiSelector().
                resourceId("com.sonymobile.cta:id/permission_allow_button"));
        if (allowButton.exists() && allowButton.isEnabled()) {
            allowButton.click();
        }
    }

    // 切换tab
    public void tabSwitch(int tabdex) throws UiObjectNotFoundException {
        // 0:setting;1:call;2:news;3:setting
        UiObject tabObject = mDevice.findObject(new UiSelector().
                className("android.widget.LinearLayout").index(1)).getChild(new UiSelector()
                .className("android.widget.RelativeLayout").index(tabdex));
        tabObject.click();
    }

    // 截图
    public void uScreenshot(String pngName) {
        File path = new File("/sdcard/Pictures/Screenshots/" + pngName + ".png");
        mDevice.takeScreenshot(path);
    }

}
