#import "WoshilllFlutterPlugin.h"
#if __has_include(<woshilll_flutter_plugin/woshilll_flutter_plugin-Swift.h>)
#import <woshilll_flutter_plugin/woshilll_flutter_plugin-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "woshilll_flutter_plugin-Swift.h"
#endif

@implementation WoshilllFlutterPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftWoshilllFlutterPlugin registerWithRegistrar:registrar];
}
@end
