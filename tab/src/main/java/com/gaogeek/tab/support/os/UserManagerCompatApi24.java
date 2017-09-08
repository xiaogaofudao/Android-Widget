/*
 * Copyright (C) 2015 The Android Open Source Project
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

package com.gaogeek.tab.support.os;

import android.content.Context;
import android.os.UserManager;
import android.support.annotation.RestrictTo;

import static android.support.annotation.RestrictTo.Scope.GROUP_ID;

/** {@hide} */
@RestrictTo(GROUP_ID)
public class UserManagerCompatApi24 {
    public static boolean isUserUnlocked(Context context) {
        return context.getSystemService(UserManager.class).isUserUnlocked();
    }
}
