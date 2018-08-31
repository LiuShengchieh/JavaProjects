import android.os.RemoteException;
import android.os.SystemClock;
import com.android.uiautomator.core.*;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HelloUiautomator extends UiAutomatorTestCase {
    private static final String TAG = HelloUiautomator.class.getSimpleName();
    private static final String FORMAT_LOG = ">>> %s [%s] %s";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
            "yyyy-HH-DD hh:mm:ss");
    private static final long TIME_OUT_FOR_EXISTS = 5 * 1000L;

    protected void setUp() throws Exception {
        log(TAG, "setUp of " + getName());
        super.setUp();
    }

    /**
     * 测试场景：验证自动休眠设置是否有效 <br>
     * 操作过程：<br>
     * > 打开系统设置，设置自动休眠时间为30S<br>
     * > 休眠30S，检查屏幕状态<br>
     * 预期结果：屏幕为灭屏状态
     */
    public void testSetScreenOffTime() throws IOException,
            UiObjectNotFoundException, RemoteException {

        // 打开系统设置页面
        Runtime.getRuntime().exec("monkey -p com.android.settings -v 1");

        // 找到滚动列表
        UiScrollable mSettingList = new UiScrollable(new UiSelector()
                .packageName("com.android.settings")
                .resourceId("android:id/list"));
        mSettingList.waitForExists(TIME_OUT_FOR_EXISTS);
        assertTrue("System setting list not exists", mSettingList.exists());

        // 获取显示设置控件，并点击进入显示设置
        log(TAG, "Enter display setting page");
        UiObject mDisplayEntry = mSettingList.getChildByText(
                new UiSelector().resourceIdMatches(".*title"), "Display");
        assertTrue("Display setting entry not exists",
                mDisplayEntry.exists() && mDisplayEntry.isEnabled());
        mDisplayEntry.clickAndWaitForNewWindow();

        // 点击休眠按钮进入设置
        log(TAG, "Enter sleep timeout setting page");
        UiObject mSleepTimeEntry = new UiObject(new UiSelector().textContains("Sleep"));
        assertTrue("Sleeping timeout setting entry not exists",
                mSleepTimeEntry.exists() && mSleepTimeEntry.isEnabled());
        mSleepTimeEntry.clickAndWaitForNewWindow();

        // 点击设置为30S超时
        log(TAG, "Set sleeping timeout to 30s");
        UiObject mTargetTimeOut = new UiObject(new UiSelector().textMatches("30 seconds"));
        assertTrue("Can't find target timeout for setting",
                mTargetTimeOut.exists() && mTargetTimeOut.isEnabled());
        mTargetTimeOut.clickAndWaitForNewWindow();

        // 检验结果
        log(TAG, "Sleep 30s for checking auto sleep");
        UiDevice mUiDevice = getUiDevice();
        SystemClock.sleep(30 * 1000);
        assertFalse("Auto sleep didn't work", mUiDevice.isScreenOn());
    }

    protected void tearDown() throws Exception {
        log(TAG, "tearDown of " + getName());
        super.tearDown();
    }

    /**
     * 返回当前系统时间
     * @return yyyy-HH-DD hh:mm:ss
     */
    private static String getCurTime() {
        return DATE_FORMAT.format(new Date(System.currentTimeMillis()));
    }

    /**
     * 输出日志
     * @param tag TAG
     * @param message 消息
     */
    private static void log(String tag, String message){
        System.out.println(String.format(FORMAT_LOG, getCurTime(), tag, message));
    }
}
