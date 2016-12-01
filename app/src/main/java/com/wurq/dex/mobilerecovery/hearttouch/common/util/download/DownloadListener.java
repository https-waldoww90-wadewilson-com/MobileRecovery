package com.wurq.dex.mobilerecovery.hearttouch.common.util.download;

import java.io.File;

/**
 * Created by ht-template
 **/
public interface DownloadListener {
    void onDownloadComplete(long downloadReference, File destinationFile);
}
