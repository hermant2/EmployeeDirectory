package com.treyherman.employeedirectory.extension

import android.content.Intent
import android.net.Uri

private const val TEL_INTENT = "tel: %s"

fun createPhoneIntent(phoneNumber: String): Intent = Intent(
    Intent.ACTION_DIAL, Uri.parse(
        String.format(
            TEL_INTENT, phoneNumber
        )
    )
)

//fun createEmailIntent(email: String): Intent = Intent(
//    Intent.ACTION_SENDTO, Uri.fromParts("mailto", email, null)
//
//)
