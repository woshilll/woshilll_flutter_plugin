
import 'dart:async';

import 'package:flutter/services.dart';

class WoshilllFlutterPlugin {
  static const MethodChannel channel = MethodChannel('woshilll_flutter_plugin');

  /// 设置屏幕亮度
  static setBrightness(double brightness) async {
    assert(brightness >= 0 && brightness <= 1, "brightness only between 0-1");
    await channel.invokeMethod("setBrightness", brightness);
  }

  /// 设置屏幕默认
  static setBrightnessDefault() async {
    await channel.invokeMethod("setBrightnessDefault");
  }

  /// 获取屏幕亮度
  static getBrightness() async {
    return await channel.invokeMethod("getBrightness");
  }

  /// 额外配置
  static setConfig(String key, Object value) async {
    await channel.invokeMethod("setConfig", {"key": key, "value": value});
  }

  /// 额外配置
  static setVibration(int value) async {
    await channel.invokeMethod("setVibration", value);
  }
}
