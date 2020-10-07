package com.treyherman.employeedirectory.manager.phonenumber

import android.telephony.PhoneNumberUtils
import java.util.*

class PhoneNumberFormatManagerImpl : PhoneNumberFormatManager {
    override fun formatPhoneNumber(phoneNumber: String?): String? {
        return phoneNumber?.let { PhoneNumberUtils.formatNumber(it, Locale.US.country) }
    }
}
