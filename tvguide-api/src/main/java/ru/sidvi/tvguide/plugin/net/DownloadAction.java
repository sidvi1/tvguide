package ru.sidvi.tvguide.plugin.net;

import ru.sidvi.tvguide.plugin.Event;

import java.io.InputStream;
import java.util.List;

/**
 * Created by sidvi on 26.12.2015.
 */
public interface DownloadAction {
    List<Event> download(InputStream downloaded);
}
