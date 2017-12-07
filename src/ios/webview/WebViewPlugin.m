
#include <sys/types.h>
#include <sys/sysctl.h>

#import <Cordova/CDV.h>
#import "WebViewPlugin.h"

@implementation WebViewPlugin

@synthesize webViewController;

- (void)adjustBehavior{
  #if __IPHONE_OS_VERSION_MAX_ALLOWED >= 110000
    if (@available(iOS 11.0, *)) {
        [self.webView.scrollView setContentInsetAdjustmentBehavior:UIScrollViewContentInsetAdjustmentNever];
    }
  #endif
}

- (void)pluginInitialize {
    [self adjustBehavior];
}

- (void)webViewAdjustmenBehavior:(CDVInvokedUrlCommand*)command{
  [self adjustBehavior];
}

- (void)subscribeCallback:(CDVInvokedUrlCommand*)command
{
    [self.commandDelegate runInBackground:^{
        @try {
            webViewFinishedCallBack = command.callbackId;
        }
        @catch (NSException *exception) {
            NSString* reason=[exception reason];
            CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString: reason];
            [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
        }
    }];
}

- (void)subscribeDebugCallback:(CDVInvokedUrlCommand*)command
{
    [self.commandDelegate runInBackground:^{
        @try {
            debugCallback = command.callbackId;
        }
        @catch (NSException *exception) {
            NSString* reason=[exception reason];
            CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString: reason];
            [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
        }
    }];
}

- (void)load:(CDVInvokedUrlCommand*)command{
    NSString* url =(NSString*)[command.arguments objectAtIndex:0];
    [self.webViewController loadURL:url];
}

- (void)show:(CDVInvokedUrlCommand*)command{
  NSString* url=(NSString*)[command.arguments objectAtIndex:0];
  NSLog(@"showwebViewView %@", url);
  [self.commandDelegate runInBackground:^{
    @try {
      dispatch_async(dispatch_get_main_queue(), ^{
          webViewController = [[WebViewController alloc] init];
          webViewController.delegate = self; // esto es para poder recibir el evento de que webView se cerro
          webViewController.startPage = url;
          [self.viewController presentViewController:webViewController animated:YES completion:nil];
      });

      CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
      [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    }
    @catch (NSException *exception) {
      NSString* reason=[exception reason];
      CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString: reason];
      [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    }
  }];
}

- (void)hide:(CDVInvokedUrlCommand*)command{
  NSLog(@"hidewebViewView");
  [self.commandDelegate runInBackground:^{
    @try {

      dispatch_async(dispatch_get_main_queue(), ^{
        [self.viewController dismissViewControllerAnimated:YES completion:nil];
        [self dispose];
      });

      CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
      [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    }
    @catch (NSException *exception) {
      NSString* reason=[exception reason];
      CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString: reason];
      [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    }
  }];
}

- (void)exitApp:(CDVInvokedUrlCommand*)command{
  exit(0);
}

-(void)webViewFinished{
  NSLog(@"webViewFinished");
  webViewController = nil;

  CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
  [self.commandDelegate sendPluginResult:pluginResult callbackId:webViewFinishedCallBack];
}

-(void)callDebugCallback{
  NSLog(@"callDebugCallback");
  CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
  [pluginResult setKeepCallbackAsBool:YES];
  [self.commandDelegate sendPluginResult:pluginResult callbackId:debugCallback];
}

@end

@implementation WebViewController

@synthesize delegate;

- (id)init {
  self = [super init];
  return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];

    UITapGestureRecognizer *tapGestureRecognizer = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(tapGestureTriggered:)];
    [tapGestureRecognizer setNumberOfTapsRequired:3];
    [tapGestureRecognizer setNumberOfTouchesRequired:2];
    [self.webView addGestureRecognizer:tapGestureRecognizer];
}

- (void) tapGestureTriggered: (UITapGestureRecognizer *)recognizer
{
    //Code to handle the gesture
    NSLog(@"WebViewController tapGestureTriggered");
    [delegate callDebugCallback];
}

- (void)viewDidDisappear:(BOOL)animated {
  NSLog(@"viewDidDisappear");
  [super viewDidDisappear:animated];
  [delegate webViewFinished];
  delegate = nil;
}

- (void)loadURL: (NSString *)url
{
    [self.webViewEngine loadRequest:[NSURLRequest requestWithURL:[NSURL URLWithString:url]]];
}

@end
