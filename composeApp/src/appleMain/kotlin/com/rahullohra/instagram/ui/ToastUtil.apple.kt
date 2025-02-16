package com.rahullohra.instagram.ui

import platform.UIKit.UIAlertController
import platform.UIKit.UIAlertControllerStyleAlert
import platform.UIKit.UIApplication
import platform.darwin.DISPATCH_TIME_NOW
import platform.darwin.NSEC_PER_SEC
import platform.darwin.dispatch_after
import platform.darwin.dispatch_get_main_queue
import platform.darwin.dispatch_time

actual fun showToast(message: String) {

    val alert = UIAlertController
        .alertControllerWithTitle(title = null, message = message, preferredStyle = UIAlertControllerStyleAlert)
    val rootViewController = UIApplication.sharedApplication.keyWindow?.rootViewController

    rootViewController?.presentViewController(alert, animated = true) {
        // Dismiss after 2 seconds
        dispatch_after(
            dispatch_time(DISPATCH_TIME_NOW, 2 * NSEC_PER_SEC.toLong()),
            dispatch_get_main_queue()
        ) {
            alert.dismissViewControllerAnimated(true, null)
        }
    }
}

