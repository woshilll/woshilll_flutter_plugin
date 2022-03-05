import Flutter
import UIKit

public class SwiftWoshilllFlutterPlugin: NSObject, FlutterPlugin {
  var configMap = [String:AnyObject]()
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "woshilll_flutter_plugin", binaryMessenger: registrar.messenger())
    let instance = SwiftWoshilllFlutterPlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
    if (call.method == "setBrightness") {
        UIScreen.main.brightness = CGFloat(call.arguments as! Double)
    } else if (call.method == "getBrightness") {
        result(UIScreen.main.brightness)
    }else if (call.method == "setConfig") {
        let map = (call.arguments! as! [String:Any])
        self.configMap[map["key"] as! String] = map["value"] as AnyObject?
    }
  }
}
