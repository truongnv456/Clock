// IClockService.aidl
package com.paci.training.android.truongnv92.clockservice;

// Declare any non-default types here with import statements

interface IClockService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
     String getCurrentTimeVietNam();
     String getCurrentTimeUSA();
}