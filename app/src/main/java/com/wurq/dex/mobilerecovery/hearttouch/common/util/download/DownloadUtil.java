package com.wurq.dex.mobilerecovery.hearttouch.common.util.download;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.annotation.Nullable;

import java.io.File;


/**
 * Created by ht-template
 **/
public class DownloadUtil {
    private static DownloadManager sDownloadManager;
    private static boolean sIsDownloadOnlyWifi = true;

    public static void init(Context context, boolean isDownloadOnlyWifi) {
        sIsDownloadOnlyWifi = isDownloadOnlyWifi;
        initDownloadManager(context);
    }

    public static DownloadManager initDownloadManager(Context context) {
        if (sDownloadManager == null) {
            synchronized (DownloadManager.class) {
                if (sDownloadManager == null) {
                    String serviceString = Context.DOWNLOAD_SERVICE;
                    sDownloadManager = (DownloadManager) context.getSystemService(serviceString);
                    return sDownloadManager;
                }
            }
        }
        return sDownloadManager;
    }

    public static long download(Context context, String url, File destinationFile, @Nullable final DownloadListener listener) {
        return download(context, url, destinationFile, sIsDownloadOnlyWifi, listener);
    }

    public static long download(Context context,
                                String requestUrl,
                                final File destinationFile,
                                boolean isDownloadOnlyWifi,
                                @Nullable final DownloadListener listener) {
        Uri uri = Uri.parse(requestUrl);
        final DownloadManager.Request request = new DownloadManager.Request(uri);

        int requestType = isDownloadOnlyWifi ? DownloadManager.Request.NETWORK_WIFI :
                (DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        request.setAllowedNetworkTypes(requestType);
        request.setTitle(uri.getLastPathSegment());
        request.setDescription("安装包");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setVisibleInDownloadsUi(true);
        request.setDestinationUri(Uri.fromFile(destinationFile));

        final long downloadReference = sDownloadManager.enqueue(request);

        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                long reference = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (downloadReference == reference && listener != null) {
                    listener.onDownloadComplete(downloadReference, destinationFile);
                }
            }
        };
        context.registerReceiver(receiver, filter);

        return downloadReference;
    }

    public static void remove(long... requestReferences) {
        if (requestReferences != null) {
            sDownloadManager.remove(requestReferences);
        }
    }

}
