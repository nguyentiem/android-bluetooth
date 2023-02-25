/**
 * Copyright (C) 2021 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.bluetoothadvertisements

import android.Manifest
import android.os.ParcelUuid

const val BT_ADVERTISING_FAILED_EXTRA_CODE = "bt_adv_failure_code"
const val NEGATIVE_ONE = -1
const val ADVERTISING_TIMED_OUT = 6
const val BLE_NOTIFICATION_CHANNEL_ID = "bleChl"
const val FOREGROUND_NOTIFICATION_ID = 3
const val ADVERTISING_FAILED = "com.example.android.bluetoothadvertisements.advertising_failed"
val Service_UUID: ParcelUuid = ParcelUuid.fromString("0000b81d-0000-1000-8000-00805f9b34fb")
const val REQUEST_ENABLE_BT = 11
const val PERMISSION_REQUEST_LOCATION = 101

/**
 * Saving the permission type here makes changing the permission type more efficient
 */
const val LOCATION_PERMISSION_TYPE = Manifest.permission.ACCESS_FINE_LOCATION