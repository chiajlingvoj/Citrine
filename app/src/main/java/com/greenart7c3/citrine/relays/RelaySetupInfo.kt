/**
 * Copyright (c) 2024 Vitor Pamplona
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the
 * Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN
 * AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.greenart7c3.citrine.relays

import android.util.LruCache
import androidx.compose.runtime.Immutable

@Immutable
data class RelaySetupInfo(
    val url: String,
    val read: Boolean,
    val write: Boolean,
    val errorCount: Int = 0,
    val downloadCountInBytes: Int = 0,
    val uploadCountInBytes: Int = 0,
    val spamCount: Int = 0,
    val feedTypes: Set<FeedType>,
    val paidRelay: Boolean = false
) {
    val briefInfo: RelayBriefInfoCache.RelayBriefInfo = RelayBriefInfoCache.RelayBriefInfo(url)
}

object RelayBriefInfoCache {
    val cache = LruCache<String, RelayBriefInfo?>(50)

    @Immutable
    data class RelayBriefInfo(
        val url: String,
        val displayUrl: String =
            url.trim().removePrefix("wss://").removePrefix("ws://").removeSuffix("/").intern(),
        val favIcon: String = "https://$displayUrl/favicon.ico".intern()
    )

    fun get(url: String): RelayBriefInfo {
        val info = cache[url]
        if (info != null) return info

        val newInfo = RelayBriefInfo(url)
        cache.put(url, newInfo)
        return newInfo
    }
}