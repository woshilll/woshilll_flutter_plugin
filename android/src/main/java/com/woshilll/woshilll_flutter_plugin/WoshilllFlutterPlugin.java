package com.woshilll.woshilll_flutter_plugin;

import android.app.Activity;
import android.content.res.Resources;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.NonNull;

import androidx.annotation.Nullable;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static com.woshilll.woshilll_flutter_plugin.MethodName.*;

/** WoshilllFlutterPlugin */
public class WoshilllFlutterPlugin implements FlutterPlugin, MethodCallHandler, ActivityAware {
  private Activity activity;
  private final Map<String, Object> configMap = new HashMap<>(16);
  // 通道名
  private static final String CHANNEL_NAME = "woshilll_flutter_plugin";


  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    MethodChannel channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), CHANNEL_NAME);
    channel.setMethodCallHandler(this);
  }

  @Override
  public void onDetachedFromEngine(@NonNull @NotNull FlutterPluginBinding binding) {

  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    try {
      switch (call.method) {
        case METHOD_SET_BRIGHTNESS_NAME:
          setBrightness((Double) call.arguments);
          break;
        case METHOD_GET_BRIGHTNESS_NAME:
          getBrightness(result);
          break;
        case METHOD_SET_CONFIG_NAME:
          configMap.put(call.argument("key"), call.argument("value"));
          break;
        case METHOD_SET_BRIGHTNESS_DEFAULT_NAME:
          setBrightness(null);
          break;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 获取亮度
   * @param result 返回给flutter
   */
  private void getBrightness(Result result) throws Settings.SettingNotFoundException {
    int res = Settings.System.getInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
    result.success(res / (double) getBrightnessMax());
  }

  /**
   * 获取支持最大亮度数值 - 因为小米手机亮度数值过高
   *
   * @return 最大亮度值
   */
  private int getBrightnessMax() {
    try {
      Resources system = Resources.getSystem();
      int resId = system.getIdentifier("config_screenBrightnessSettingMaximum", "integer", "android");
      if (resId != 0) {
        return system.getInteger(resId);
      }
    }catch (Exception ignore){}
    return 255;
  }

  /**
   * 设置屏幕亮度
   *
   * @param value 0-1 null-default
   */
  private void setBrightness(@Nullable Double value) {
    Window window = activity.getWindow();
    WindowManager.LayoutParams lp = window.getAttributes();
    lp.screenBrightness = value == null ? WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE : value.floatValue();
    window.setAttributes(lp);
  }


  @Override
  public void onAttachedToActivity(@NonNull @NotNull ActivityPluginBinding binding) {
    activity = binding.getActivity();
  }

  @Override
  public void onDetachedFromActivityForConfigChanges() {

  }

  @Override
  public void onReattachedToActivityForConfigChanges(@NonNull @NotNull ActivityPluginBinding binding) {

  }

  @Override
  public void onDetachedFromActivity() {

  }
}
